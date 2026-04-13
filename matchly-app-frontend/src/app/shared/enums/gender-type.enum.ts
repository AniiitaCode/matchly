export enum GenderType {
    MALE = 'MALE',
    FEMALE = 'FEMALE'
}

export const GenderDisplayName: Record<GenderType, string> = {
  [GenderType.MALE]: 'МЪЖ',
  [GenderType.FEMALE]: 'ЖЕНА'
};