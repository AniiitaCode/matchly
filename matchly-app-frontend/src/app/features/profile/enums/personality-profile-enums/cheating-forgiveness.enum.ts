export enum CheatingForgiveness {
    NEVER = 'NEVER',
    SOMETIMES = 'SOMETIMES',
    DEPENDS_ON_SITUATION = 'DEPENDS_ON_SITUATION',
}

export const CheatingForgivenessDisplayName: Record<CheatingForgiveness, string> = {
    [CheatingForgiveness.NEVER]: 'Никога',
    [CheatingForgiveness.SOMETIMES]: 'Понякога',
    [CheatingForgiveness.DEPENDS_ON_SITUATION]: 'Зависи от ситуацията',
}