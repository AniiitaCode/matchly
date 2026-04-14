export enum ZodiacSign {
    ARIES = 'ARIES',
    TAURUS = 'TAURUS',
    GEMINI = 'GEMINI',
    CANCER = 'CANCER',
    LEO = 'LEO',
    VIRGO = 'VIRGO',
    LIBRA = 'LIBRA',
    SCORPIO = 'SCORPIO',
    SAGITTARIUS = 'SAGITTARIUS',
    CAPRICORN = 'CAPRICORN',
    AQUARIUS = 'AQUARIUS',
    PISCES = 'PISCES',
}

export const ZodiacSignDisplayName: Record<ZodiacSign, string> = {
    [ZodiacSign.ARIES]: 'Овен',
    [ZodiacSign.TAURUS]: 'Телец',
    [ZodiacSign.GEMINI]: 'Близнаци',
    [ZodiacSign.CANCER]: 'Рак',
    [ZodiacSign.LEO]: 'Лъв',
    [ZodiacSign.VIRGO]: 'Дева',
    [ZodiacSign.LIBRA]: 'Везни',
    [ZodiacSign.SCORPIO]: 'Скорпион',
    [ZodiacSign.SAGITTARIUS]: 'Стрелец',
    [ZodiacSign.CAPRICORN]: 'Козирог',
    [ZodiacSign.AQUARIUS]: 'Водолей',
    [ZodiacSign.PISCES]: 'Риби',
}