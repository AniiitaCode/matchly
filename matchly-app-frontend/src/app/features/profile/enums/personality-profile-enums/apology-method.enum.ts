export enum ApologyMethod {
    SAY_SORRY = 'SAY_SORRY',
    ADMIT_FAULT = 'ADMIT_FAULT',
    REAL_CHANGE = 'REAL_CHANGE',
    ACTION = 'ACTION',
}

export const ApologyMethodDisplayName: Record<ApologyMethod, string> = {
    [ApologyMethod.SAY_SORRY]: 'Съжалявам',
    [ApologyMethod.ADMIT_FAULT]: 'Признаване на вина',
    [ApologyMethod.REAL_CHANGE]: 'Реална промяна',
    [ApologyMethod.ACTION]: 'Действие',
}