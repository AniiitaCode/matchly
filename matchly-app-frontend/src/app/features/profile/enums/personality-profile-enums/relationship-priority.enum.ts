export enum RelationshipPriority {
    LOVE = 'LOVE',
    TRUST = 'TRUST',
    RESPECT = 'RESPECT',
    STABILITY = 'STABILITY',
    FREEDOM = 'FREEDOM',
}

export const RelationshipPriorityDisplayName: Record<RelationshipPriority, string> = {
    [RelationshipPriority.LOVE]: 'Любов',
    [RelationshipPriority.TRUST]: 'Доверие',
    [RelationshipPriority.RESPECT]: 'Уважение',
    [RelationshipPriority.STABILITY]: 'Стабилност',
    [RelationshipPriority.FREEDOM]: 'Свобода',
}