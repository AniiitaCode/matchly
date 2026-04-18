import { Component, inject, OnInit, ChangeDetectorRef, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { MatchService } from '../../services/match.service';
import { MatchResponse } from '../../interfaces/match.interface';

@Component({
  selector: 'app-rejected-matches',
  imports: [RouterLink, CommonModule],
  templateUrl: './rejected-matches.component.html',
  styleUrl: './rejected-matches.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class RejectedMatchesComponent implements OnInit {
  private matchService = inject(MatchService);
  private ref = inject(ChangeDetectorRef);
  private location = inject(Location);

  currentIndex = 0;
  rejectedMatches: MatchResponse[] = [];
  currentMatch?: MatchResponse;

  ngOnInit(): void {
    this.matchService.getRejectedMatches().subscribe((data) => {
      this.rejectedMatches = data.filter(m => m.matchStatus === 'REJECTED');
      this.currentMatch = this.rejectedMatches[0];
      this.ref.detectChanges();
    });
  }

  onApprove(matchId: string) {
    this.matchService.approveMatch(matchId).subscribe(() => {
    this.rejectedMatches = this.rejectedMatches.filter(m => m.matchId !== matchId);
    this.currentMatch = this.rejectedMatches[0];
    this.ref.detectChanges();
    });
  }

  onDelete(matchId: string) {
    this.matchService.deleteMatch(matchId).subscribe(() => {
      this.rejectedMatches = this.rejectedMatches.filter(m => m.matchId !== matchId);
      this.ref.detectChanges();
    })
  }

  onBack(): void {
    this.location.back();
  }
}
