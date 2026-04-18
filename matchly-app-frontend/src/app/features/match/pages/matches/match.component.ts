import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatchService } from '../../services/match.service';
import { MatchResponse } from '../../interfaces/match.interface';
import { CommonModule, Location } from '@angular/common';
import { RouterLink } from '@angular/router';
import { switchMap } from 'rxjs';
import { ToastService } from '../../../../core/toast/services/toast.service';

@Component({
  selector: 'app-match',
  imports: [CommonModule, RouterLink],
  templateUrl: './match.component.html',
  styleUrl: './match.component.css',
})

export class MatchComponent implements OnInit {
  private matchService = inject(MatchService);
  private ref = inject(ChangeDetectorRef);
  private toastService = inject(ToastService);
  private location = inject(Location);

  animating = false;
  animationDirection: 'left' | 'right' | null = null;

  pendingMatches: MatchResponse[] = [];
  currentIndex = 0;
  currentMatch?: MatchResponse;

  loading = true;

  ngOnInit(): void {
    this.loading = true;

    const minLoadingTime = 1000;

    const startTime = Date.now();

    this.matchService.postGenerateMatches()
      .pipe(
        switchMap(() => this.matchService.getMatchesPage())
      )
      .subscribe({
        next: (data) => {
          const elapsed = Date.now() - startTime;
          const remaining = Math.max(minLoadingTime - elapsed, 0);

          console.log('All matches:', data);
          setTimeout(() => {
            this.pendingMatches = data.filter(m => m.matchStatus === 'PENDING');
            this.showNext();
            this.loading = false;
            this.ref.detectChanges();
          }, remaining);
        },
        error: (err) => {
          this.toastService.show(err, 'error');
          this.loading = false;
        }
      });
  }

  onApprove(matchId: string) {
    this.animationDirection = 'left';
    this.animating = true;

    setTimeout(() => {
      this.matchService.approveMatch(matchId).subscribe(() => {
        this.afterAction();
      });
    }, 300);
  }

  onReject(matchId: string) {
    this.animationDirection = 'right';
    this.animating = true;

    setTimeout(() => {
      this.matchService.rejectMatch(matchId).subscribe(() => {
        this.afterAction();
      });
    }, 300);
  }

  afterAction() {
    this.pendingMatches.splice(this.currentIndex, 1);
    this.showNext();

    this.animating = false;
    this.animationDirection = null;

    this.ref.detectChanges();
  }

  showNext() {
    if (this.pendingMatches.length > 0) {
      this.currentIndex = 0;
      this.currentMatch = this.pendingMatches[this.currentIndex];
    } else {
      this.currentMatch = undefined;
    }
  }

  onBack(): void {
    this.location.back();
  }
}
