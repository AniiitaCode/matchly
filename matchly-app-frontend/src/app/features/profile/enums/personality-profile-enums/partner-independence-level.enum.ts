export enum PartnerIndependenceLevel {
    ALMOST_EVERYTHING_TOGETHER = 'ALMOST_EVERYTHING_TOGETHER',
    BALANCED = 'BALANCED',
    COMPLETELY_SEPARATE_LIVES = 'COMPLETELY_SEPARATE_LIVES',

}

export const PartnerIndependenceLevelDisplayName: Record<PartnerIndependenceLevel, string> = {
    [PartnerIndependenceLevel.ALMOST_EVERYTHING_TOGETHER]: 'Почти всичко заедно',
    [PartnerIndependenceLevel.BALANCED]: 'Баланс',
    [PartnerIndependenceLevel.COMPLETELY_SEPARATE_LIVES]: 'Напълно отделни животи',
}