export enum AlcoholConsumption {
    YES = 'YES',
    NO = 'NO',
    RARELY = 'RARELY',
    ONLY_IN_COMPANY = 'ONLY_IN_COMPANY',
}

export const AlcoholConsumptionDisplayName: Record<AlcoholConsumption, string> = {
    [AlcoholConsumption.YES]: 'Да',
    [AlcoholConsumption.NO]: 'Не',
    [AlcoholConsumption.RARELY]: 'Рядко',
    [AlcoholConsumption.ONLY_IN_COMPANY]: 'Само в компания',
}