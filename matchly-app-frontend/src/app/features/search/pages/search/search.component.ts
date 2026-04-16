import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SearchService } from '../../services/search.service';
import { GetSearchPageResponse, SearchRequest, UserSearchResult } from '../../interfaces/search.interface';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-search',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css',
})

export class SearchComponent implements OnInit {
  private searchService = inject(SearchService);
  private ref = inject(ChangeDetectorRef);

  womenRow = ['👩', '👩🏼', '👩🏻', '👩🏽‍🦱', '👩🏼‍🦱', '👩🏻‍🦰', '👩🏻‍🦱'];
  menRow = ['👨', '👨🏻', '👨🏻‍🦰', '🧔🏻', '🧑🏼‍🦱', '🧑🏽‍🦱', '🧔🏼‍♂️'];

  rows = Array.from({ length: 8 });

  username = '';
  town = '';
  minAge: number | null = null;
  maxAge: number | null = null;

  results: UserSearchResult[] = [];
  loading = false;
  searchPerformed = false;

  ngOnInit(): void {
    this.searchService.getSearchPage().subscribe({
      next: (response: GetSearchPageResponse) => {
        const profile = response.userSearchRequest;

        this.username = profile?.username || '';
        this.town = profile?.town || '';
        this.minAge = profile?.minAge ?? 0;
        this.maxAge = profile?.maxAge ?? 0;

      }
    })
  }

  onSearch() {
    if (!this.username && !this.town && !this.minAge && !this.maxAge) {
      this.results = [];
      this.searchPerformed = true;
      return;
    }

    this.loading = true;
    this.searchPerformed = true;
    const data: SearchRequest = {
      username: this.username || undefined,
      town: this.town || undefined,
      minAge: this.minAge || undefined,
      maxAge: this.maxAge || undefined,
    };

    this.searchService.searchUsers(data).subscribe({
      next: (response: any) => {
        this.results = Array.isArray(response) ? response : [];
        this.loading = false;

        this.ref.detectChanges();
      },
      error: () => {
        this.results = [];
        this.loading = false;

        this.ref.detectChanges();
      }
    });
  }

  closeModal() {
    this.results = [];
    this.searchPerformed = false;
  }
}
