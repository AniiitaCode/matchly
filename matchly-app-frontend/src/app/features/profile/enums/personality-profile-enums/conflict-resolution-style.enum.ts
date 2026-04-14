export enum ConflictResolutionStyle {
    IMMEDIATELY = 'IMMEDIATELY',
    AFTER_EMOTIONS_CALM = 'AFTER_EMOTIONS_CALM',
    AVOID_CONFLICTS = 'AVOID_CONFLICTS',
}

export const ConflictResolutionStyleDisplayName: Record<ConflictResolutionStyle, string> = {
    [ConflictResolutionStyle.IMMEDIATELY]: 'Веднага',
    [ConflictResolutionStyle.AFTER_EMOTIONS_CALM]: 'След като емоциите спаднат',
    [ConflictResolutionStyle.AVOID_CONFLICTS]: 'Избягвам конфликти',
}