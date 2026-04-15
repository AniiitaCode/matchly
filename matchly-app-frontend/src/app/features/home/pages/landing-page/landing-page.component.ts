import { Component, inject, OnInit } from '@angular/core';
import { LandingService } from '../../services/landing.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing-page',
  imports: [],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css',
})
export class LandingPageComponent implements OnInit {
  private landingService = inject(LandingService);
  private router = inject(Router);

  isChecking = true;

  ngOnInit(): void {
    this.landingService.checkRedirect().subscribe({
      next: (response) => {
        if (response.redirectTo) {
          this.router.navigate([response.redirectTo]);
        } else {
          this.isChecking = false;
        }
      },
      error: () => this.isChecking = false
    });
  }
}


