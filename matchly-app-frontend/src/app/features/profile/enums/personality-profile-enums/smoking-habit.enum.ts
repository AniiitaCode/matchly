export enum SmokingHabit {
    YES = 'YES',
    NO = 'NO',
    RARELY = 'RARELY',
    ONLY_IN_COMPANY = 'ONLY_IN_COMPANY',
}

export const SmokingHabitDisplayName: Record<SmokingHabit, string> = {
    [SmokingHabit.YES]: 'Да',
    [SmokingHabit.NO]: 'Не',
    [SmokingHabit.RARELY]: 'Рядко',
    [SmokingHabit.ONLY_IN_COMPANY]: 'Само в компания',
}