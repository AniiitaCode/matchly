import { ChangeDetectorRef, Component, inject, OnInit, Input, ViewChildren, QueryList, ElementRef, OnChanges, SimpleChanges } from '@angular/core';
import { UserPhotosService } from '../../services/user-photos.service';
import { PhotoLikeResponse, PhotoResponse } from '../../interfaces/user-photos.interface';
import { CommonModule } from '@angular/common';
import { forkJoin, of } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import { AuthService } from '../../../../core/services/auth.service';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { UserViewDto } from '../../interfaces/user-view.interface';

@Component({
  selector: 'app-user-photos',
  imports: [CommonModule, FormsModule],
  templateUrl: './user-photos.component.html',
  styleUrl: './user-photos.component.css',
})

export class UserPhotosComponent implements OnInit, OnChanges {
  private userPhotosService = inject(UserPhotosService);
  private ref = inject(ChangeDetectorRef);
  private authService = inject(AuthService);
  private userService = inject(UserService);
  private route = inject(ActivatedRoute);

  photos: PhotoResponse[] = [];
  MAX_SLOTS = 9;

  @Input() userId!: string;
  @Input() showUpload: boolean = false;

  @ViewChildren('fileInput') fileInputs!: QueryList<ElementRef<HTMLInputElement>>;

  activeCommentIndex: number | null = null;
  activeLikesIndex: number | null = null;
  commentText: string = '';

  user: UserViewDto | null = null;

  toast = { message: '', id: 0 };

  hoveredIndex: number | null = null;

  goToProfile(userId: string) {
    this.closeLikes();
    window.location.href = `/user/${userId}`;
  }

  currentUserId: string = '';
  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(currentUser => {
      this.currentUserId = currentUser?.id ?? '';

      if (!this.userId) {
        this.userId = this.route.snapshot.paramMap.get('id') ?? '';
      }

      if (this.userId) {
        this.userService.getUserById(this.userId).subscribe({
          next: (userDto) => {
            this.user = userDto;
            this.ref.detectChanges();
          },
          error: () => {
            this.showError('Потребителят не беше намерен!');
          }
        })
      }
    });
  }

  triggerUpload(slotIndex: number) {
    const input = this.fileInputs.toArray()[slotIndex];
    if (input) {
      input.nativeElement.click();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['userId'] && this.userId) {
      this.loadPhotos();
    }
  }

  loadPhotos(offset: number = 0): void {
    if (!this.userId) return;

    this.userPhotosService.getPhotosByUser(this.userId, this.MAX_SLOTS, offset).subscribe({
      next: (photos: PhotoResponse[]) => {

        if (photos.length === 0) {
          this.fillEmptySlots();
        }

        const observables = photos.map(photo => {
          if (photo.url) {
            const fileName = photo.url.split('/').pop()!;
            return this.userPhotosService.getPhotoFile(fileName).pipe(
              map((blob: Blob) => ({
                ...photo,
                url: URL.createObjectURL(blob)
              } as PhotoResponse)),
              catchError(err => {
                this.showError('Грешка при зареждане на снимката!');
                return of({ ...photo, url: null } as PhotoResponse);
              })
            );
          } else {
            return of({ ...photo } as PhotoResponse);
          }
        });

        forkJoin(observables).subscribe({
          next: (result: PhotoResponse[]) => {
            this.photos = result.map(photo => ({
              ...photo,
              commentsList: photo.commentsList || [],
              likedBy: []
            }));

            this.fillEmptySlots();
            this.ref.detectChanges();
          },
          error: (err) => this.showError('Грешка при зареждане на снимките! ' + err.message)
        });
      },
      error: (err) => this.showError('Грешка при зареждане на снимките! ' + err.message)
    });
  }

  fillEmptySlots(): void {
    while (this.photos.length < this.MAX_SLOTS) {
      this.photos.push({
        id: '',
        url: null,
        description: '',
        uploadedAt: '',
        likes: 0,
        comments: 0,
        user: { id: this.currentUserId },
        commentsList: [],
        likedBy: []
      });
      this.ref.detectChanges();
    }
  }

  onFileSelected(event: Event, slotIndex: number) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.uploadPhoto(slotIndex, undefined, file);
    }
  }

  uploadPhoto(slotIndex: number, url?: string, file?: File, description?: string) {
    if (!this.showUpload) return;

    const formData = new FormData();
    if (file) formData.append('file', file);
    if (url) formData.append('url', url);
    if (description) formData.append('description', description);

    this.userPhotosService.postPhoto(formData).pipe(
      switchMap(photo => {
        if (!photo.url) return of(photo as PhotoResponse);
        const fileName = photo.url.split('/').pop()!;
        return this.userPhotosService.getPhotoFile(fileName).pipe(
          map(blob => ({ ...photo, url: URL.createObjectURL(blob) } as PhotoResponse)),
          catchError(err => {
            this.showError('Грешка при зареждане на новата снимка!');
            return of(photo as PhotoResponse);
          })
        );
      })
    ).subscribe({
      next: (photoWithBlob: PhotoResponse) => {
        if (slotIndex >= 0 && slotIndex < this.photos.length) {
          this.photos[slotIndex] = photoWithBlob;
        } else {
          this.photos.push(photoWithBlob);
        }
        this.photos = [...this.photos];
        this.ref.detectChanges();
      },
      error: (err) => this.showError('Грешка при качване!')
    });
  }

  openLikes(index: number) {
    const photo = this.photos[index];
    if (!photo.id || (photo.likes || 0) === 0) return;

    this.activeLikesIndex = index;

    this.userPhotosService.getPhotoLikes(photo.id).subscribe({
      next: (users: PhotoLikeResponse[]) => {
        photo.likedBy = users;
        this.activeLikesIndex = index;
        this.ref.detectChanges();
      },
      error: (err) => this.showError('Грешка при зареждане на лайковете!')
    })
  }

  closeLikes() {
    this.activeLikesIndex = null;
    this.ref.detectChanges();
  }

  likePhoto(slotIndex: number) {
    const photo = this.photos[slotIndex];
    if (!photo?.id) return;

    this.userPhotosService.postLikePhoto(photo.id).subscribe({
      next: () => {
        this.photos[slotIndex].likes = (this.photos[slotIndex].likes || 0) + 1;

        this.ref.detectChanges();
      },
      error: (err) => {
        if (err.status === 409) {
          this.showError('Вече сте харесали тази снимка!');
        } else {
          this.showError('Грешка при харесване на снимката!');
        }
      }
    });
  }

  showError(message: string) {
    this.toast = {
      message,
      id: Date.now()
    };

    setTimeout(() => {
      this.toast.message = '';
    }, 3000);
    this.ref.detectChanges();
  }

  openCommentBox(index: number) {
    this.activeCommentIndex = index;
    this.commentText = '';

    const photo = this.photos[index];
    if (photo.id) {
      this.userPhotosService.getPhotoComments(photo.id).subscribe({
        next: (comments) => {
          photo.commentsList = comments;
          this.ref.detectChanges();
        },
        error: (err) => this.showError('Грешка при зареждане на коментарите!')
      });
    }
  }

  closeCommentBox() {
    this.activeCommentIndex = null;
    this.commentText = '';
  }

  submitComment(slotIndex: number) {
    const content = this.commentText.trim();
    if (!content) return;

    const photo = this.photos[slotIndex];
    if (!photo?.id) return;

    this.userPhotosService.postCommentPhoto(photo.id, content).subscribe({
      next: (comment) => {
        if (!photo.commentsList) photo.commentsList = [];

        photo.commentsList.push(comment);
        photo.comments = (photo.comments || 0) + 1;

        this.activeCommentIndex = null;
        this.commentText = '';

        this.photos = [...this.photos];
        this.ref.detectChanges();
      },
      error: (err) => this.showError('Грешка при добавяне на коментар!')
    });
  }

  deletePhoto(slotIndex: number, photoId: string) {
    if (!photoId) return;

    this.userPhotosService.deletePhoto(photoId).subscribe({
      next: () => {
        this.photos[slotIndex] = {
          id: '',
          url: null,
          description: '',
          uploadedAt: '',
          likes: 0,
          comments: 0,
          user: { id: this.currentUserId },
          commentsList: []
        };
        this.fillEmptySlots();
        this.ref.detectChanges();
      },
      error: (err) => this.showError('Грешка при изтриване на снимката!')
    });
  }

}
