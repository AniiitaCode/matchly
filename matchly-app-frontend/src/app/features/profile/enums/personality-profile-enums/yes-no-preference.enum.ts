export enum YesNoPreference {
    YES = 'YES',
    NO = 'NO',
}

export const YesNoPreferenceDisplayName: Record<YesNoPreference, string> = {
    [YesNoPreference.YES]: 'ДА',
    [YesNoPreference.NO]: 'НЕ'
}