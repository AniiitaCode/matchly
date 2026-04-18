import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { MatchService } from '../../services/match.service';
import { MatchResponse } from '../../interfaces/match.interface';
import { ToastService } from '../../../../core/toast/services/toast.service';
import { Router } from '@angular/router';
import { ChatService } from '../../../chat/services/chat.service';

@Component({
  selector: 'app-mutual-matches',
  imports: [RouterLink, CommonModule],
  templateUrl: './mutual-matches.component.html',
  styleUrl: './mutual-matches.component.css',
})

export class MutualMatchesComponent implements OnInit {
  private matchService = inject(MatchService);
  private ref = inject(ChangeDetectorRef);
  private toastService = inject(ToastService);
  private router = inject(Router);
  private chatService = inject(ChatService);
  private location = inject(Location);

  mutualMatches: MatchResponse[] = [];

  ngOnInit(): void {
    this.matchService.getMutualMatches().subscribe((data) => {
      this.mutualMatches = data;
      this.ref.detectChanges();
    });
  }

  openChat(otherUserId: string): void {
    this.chatService.createChat(otherUserId).subscribe({
      next: (chatRoom) => {
        this.router.navigate(['/chats'], {
          queryParams: { chatId: chatRoom.id }
        });
      },
      error: () => {
        this.toastService.show('Грешка при създаване на чат!', 'error');
      }
    });
  }

  onBack(): void {
    this.location.back();
  }
}
