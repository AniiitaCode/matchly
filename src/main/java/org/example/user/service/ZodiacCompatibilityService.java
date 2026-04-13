package org.example.user.service;

import org.example.user.model.question.ZodiacSign;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZodiacCompatibilityService {

    public List<ZodiacSign> getCompatibleZodiacs(ZodiacSign sign) {

        if (sign == null) {
            return List.of();
        }

        return switch (sign) {
            case ARIES -> List.of(ZodiacSign.LEO, ZodiacSign.SAGITTARIUS, ZodiacSign.GEMINI);
            case TAURUS -> List.of(ZodiacSign.VIRGO, ZodiacSign.CAPRICORN, ZodiacSign.CANCER);
            case GEMINI -> List.of(ZodiacSign.LIBRA, ZodiacSign.AQUARIUS, ZodiacSign.ARIES);
            case CANCER -> List.of(ZodiacSign.SCORPIO, ZodiacSign.PISCES, ZodiacSign.TAURUS);
            case LEO -> List.of(ZodiacSign.ARIES, ZodiacSign.SAGITTARIUS, ZodiacSign.LIBRA);
            case VIRGO -> List.of(ZodiacSign.TAURUS, ZodiacSign.CAPRICORN, ZodiacSign.CANCER);
            case LIBRA -> List.of(ZodiacSign.GEMINI, ZodiacSign.AQUARIUS, ZodiacSign.LEO);
            case SCORPIO -> List.of(ZodiacSign.CANCER, ZodiacSign.PISCES, ZodiacSign.VIRGO);
            case SAGITTARIUS -> List.of(ZodiacSign.ARIES, ZodiacSign.LEO, ZodiacSign.AQUARIUS);
            case CAPRICORN -> List.of(ZodiacSign.TAURUS, ZodiacSign.VIRGO, ZodiacSign.SCORPIO);
            case AQUARIUS -> List.of(ZodiacSign.GEMINI, ZodiacSign.LIBRA, ZodiacSign.SAGITTARIUS);
            case PISCES -> List.of(ZodiacSign.CANCER, ZodiacSign.SCORPIO, ZodiacSign.CAPRICORN);
        };
    }

}
