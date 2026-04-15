import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../../../core/services/auth.service';
import { RouterLink } from '@angular/router';
import { HomeService } from '../../services/home.service';
import { HomePageResponse } from '../../interfaces/home.interface';
import { ZodiacSign, ZodiacDisplayName } from '../../enums/zodiac-sign.enum';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../../../core/toast/services/toast.service';

@Component({
  selector: 'app-home',
  imports: [RouterLink, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})

export class HomeComponent implements OnInit {
  private authService = inject(AuthService);
  isLoggedIn = this.authService.isLoggedIn;
  private homeService = inject(HomeService);
  private ref = inject(ChangeDetectorRef);
  private toastService = inject(ToastService);

  currentUserId?: string;
  compatibleZodiacs: ZodiacSign[] = [];
  profileCompletion: number = 0;

  zodiacImages: Record<ZodiacSign, string> = {
    [ZodiacSign.ARIES]: 'assets/images/zodiac/aries.jpg',
    [ZodiacSign.TAURUS]: 'assets/images/zodiac/taurus.jpg',
    [ZodiacSign.GEMINI]: 'assets/images/zodiac/gemini.jpg',
    [ZodiacSign.CANCER]: 'assets/images/zodiac/cancer.jpg',
    [ZodiacSign.LEO]: 'assets/images/zodiac/leo.jpg',
    [ZodiacSign.VIRGO]: 'assets/images/zodiac/virgo.jpg',
    [ZodiacSign.LIBRA]: 'assets/images/zodiac/libra.jpg',
    [ZodiacSign.SCORPIO]: 'assets/images/zodiac/scorpio.jpg',
    [ZodiacSign.SAGITTARIUS]: 'assets/images/zodiac/sagittarius.jpg',
    [ZodiacSign.CAPRICORN]: 'assets/images/zodiac/capricorn.jpg',
    [ZodiacSign.AQUARIUS]: 'assets/images/zodiac/aquarius.jpg',
    [ZodiacSign.PISCES]: 'assets/images/zodiac/pisces.jpg'
  };

  getZodiacName(sign: ZodiacSign): string {
    return ZodiacDisplayName[sign];
  }

  getZodiacImage(sign: ZodiacSign): string {
    return this.zodiacImages[sign];
  }

  ngOnInit(): void {
    if (!this.authService.isLoggedIn()) return;

    this.homeService.getHomePage().subscribe({
      next: (response) => {
        this.currentUserId = response.userId;
        this.compatibleZodiacs = response.compatibleZodiacs;
        this.profileCompletion = response.profileCompletion;

        this.ref.detectChanges();
      },
      error: () => {
        this.toastService.show('Неуспешно зареждане на зодиите!', 'error');
      }
    })
  }

}
