import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { ChatMessageResponse, ChatRoomPreview, ChatRoomResponse, Page } from '../../interfaces/chat.interface';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastService } from '../../../../core/toast/services/toast.service';
import { UserService } from '../../../profile/services/user.service';
import { AuthService } from '../../../../core/services/auth.service';
import { switchMap, map } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-chat',
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css',
})

export class ChatComponent implements OnInit {
  private chatService = inject(ChatService);
  private ref = inject(ChangeDetectorRef);
  private location = inject(Location);
  private toastService = inject(ToastService);
  private userService = inject(UserService);
  private authService = inject(AuthService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  currentUser: string = '';

  messages: ChatMessageResponse[] = [];
  chats: ChatRoomPreview[] = [];
  selectedMessage?: ChatMessageResponse;
  currentPage = 0;
  totalPage = 0;
  newMessage = '';
  page: number = 0;
  size: number = 50;
  chatRoomId!: string;
  loading: boolean = false;
  hasMore: boolean = true;

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe({
      next: (user) => {
        this.currentUser = user.id;
        this.loadMessages();
        this.loadChats();
        this.ref.detectChanges();
      },
      error: () => {
        this.toastService.show('Грешка при зареждането на текущия потребител!', 'error');
      }
    });

    this.route.queryParams.subscribe(params => {
      const chatId = params['chatId'];
      if (chatId) {
        this.chatRoomId = chatId;
        this.loadSelectedMessages(true);
        this.ref.detectChanges();
      }
    });
  }

  loadMessages(): void {
    this.chatService.getChats().subscribe({
      next: (chats: ChatRoomResponse[]) => {
        const chatMap = new Map<string, ChatRoomPreview>();
        chats.forEach(chat => {
          chatMap.set(chat.id, {
            id: chat.id,
            userName: chat.otherUsername,
            lastMessage: chat.lastMessage || '',
            senderId: '',
            senderUsername: '',
            otherUsername: chat.otherUsername
          });
        });


        this.chatService.getAllMessages(this.currentPage, 50).subscribe({
          next: (pageData: Page<ChatMessageResponse>) => {
            pageData.content.forEach(msg => {
              const chat = chatMap.get(msg.chatRoomId);
              if (chat) {
                chat.lastMessage = msg.content;
                chat.senderId = msg.senderId;
                chat.senderUsername = msg.senderUsername;
                chat.lastMessageSentAt = msg.sentAt;

                this.ref.detectChanges();
              }
            });

            this.chats = Array.from(chatMap.values())
              .sort((a, b) => {
                const timeA = new Date(a.lastMessageSentAt || 0).getTime();
                const timeB = new Date(b.lastMessageSentAt || 0).getTime();
                return timeB - timeA;
              });

            this.ref.detectChanges();
          },
          error: () => {
            this.toastService.show('Грешка при зареждането на съобщенията!', 'error');
          }
        });

      },
      error: () => {
        this.toastService.show('Грешка при зареждане на чатовете!', 'error');
      }
    });
  }

  loadChats(): void {
    this.chatService.getChats().subscribe({
      next: (chats: ChatRoomResponse[]) => {
        this.chats = chats.map(chat => ({
          id: chat.id,
          userName: chat.otherUsername,
          lastMessage: chat.lastMessage || '',
          senderId: '',
          senderUsername: '',
          otherUsername: chat.otherUsername
        }));
        this.ref.detectChanges();
      },
      error: () => {
        this.toastService.show('Грешка при зареждане на чатовете!', 'error');
      }
    });
  }

  nextPage(): void {
    if (this.currentPage + 1 < this.totalPage) {
      this.currentPage++;
      this.loadMessages();
    }
  }

  loadSelectedMessages(initial: boolean = false) {
    this.loading = true;

    this.chatService.getSelectedMessages(this.chatRoomId, this.page, this.size)
      .subscribe({
        next: (pageData) => {
          const msgs = pageData.content.reverse().map(msg => ({
            ...msg,
            isMine: msg.senderId === this.currentUser
          }));

          if (initial) {
            this.messages = msgs;

            setTimeout(() => {
              const chatContainer = document.getElementById('chat-container');
              if (chatContainer) chatContainer.scrollTop = chatContainer.scrollHeight;
            }, 0);

          } else {
            this.messages = [...msgs, ...this.messages];
          }

          this.hasMore = !pageData.last;
          this.page++;
          this.loading = false;

          this.ref.detectChanges();
        },
        error: () => {
          this.loading = false;
          this.toastService.show('Грешка при зареждането на съобщенията!', 'error');
        }
      });
  }

  onScroll(event: Event): void {
    const target = event.target as HTMLElement;

    if (target.scrollTop === 0 && this.hasMore && !this.loading) {
      this.loadSelectedMessages(false);
      this.ref.detectChanges();
    }
  }

  selectChat(chat: ChatRoomPreview) {
    this.chatRoomId = chat.id;
    this.messages = [];
    this.page = 0;
    this.hasMore = true;

    this.loadSelectedMessages(true);

    this.router.navigate([], { queryParams: { chatId: chat.id }, queryParamsHandling: 'merge' });
  }

  selectMessage(msg: ChatMessageResponse): void {
    this.selectedMessage = msg;
  }

  createChat(otherUserId: string): void {
    this.chatService.createChat(otherUserId).pipe(
      switchMap((chatRoom: ChatRoomResponse) => {
        const otherId = chatRoom.userAId === this.currentUser ? chatRoom.userBId : chatRoom.userAId;
        return this.userService.getUserById(otherId).pipe(
          map(otherUser => ({ chatRoom, otherUser }))
        );
      })
    ).subscribe({
      next: ({ chatRoom, otherUser }) => {
        const newChat: ChatRoomPreview = {
          id: chatRoom.id,
          userName: otherUser?.username || 'Unknown',
          lastMessage: chatRoom.lastMessage || '',
          senderId: otherUser.id,
          senderUsername: otherUser.username,
          otherUsername: otherUser?.username || 'Unknown'
        };

        this.chats.unshift(newChat);
        this.selectChat(newChat);
      },
      error: () => {
        this.toastService.show('Грешка при създаването на чат!', 'error');
      }
    });
  }

  sendMessage(): void {
    if (this.newMessage.trim() && this.chatRoomId) {
      const messageText = this.newMessage.trim();
      this.newMessage = '';

      this.chatService.sendMessage(this.chatRoomId, messageText).subscribe({
        next: (msg) => {
          msg.isMine = msg.senderId === this.currentUser;

          this.messages.push(msg);

          const chatIndex = this.chats.findIndex(c => c.id === this.chatRoomId);
          if (chatIndex !== -1) {
            const chat = this.chats[chatIndex];
            chat.lastMessage = msg.content;
            chat.senderId = msg.senderId;
            chat.senderUsername = msg.senderUsername;

            this.chats.splice(chatIndex, 1);
            this.chats.unshift(chat);
          }

          setTimeout(() => {
            const chatContainer = document.getElementById('chat-container');
            if (chatContainer) chatContainer.scrollTop = chatContainer.scrollHeight;
          }, 0);

          this.ref.detectChanges();
        },
        error: () => {
          this.toastService.show('Грешка при изпращане на съобщението!', 'error');
        }
      });
    }
  }

  onBack(): void {
    this.location.back();
  }

}
