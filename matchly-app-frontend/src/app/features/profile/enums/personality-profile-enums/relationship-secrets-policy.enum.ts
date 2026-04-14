export enum RelationshipSecretsPolicy {
    NO_SECRETS = 'NO_SECRETS',
    SMALL_PERSONAL_SECRETS = 'SMALL_PERSONAL_SECRETS',
    RIGHT_TO_PRIVACY = 'RIGHT_TO_PRIVACY',
}

export const RelationshipSecretsPolicyDisplayName: Record<RelationshipSecretsPolicy, string> = {
    [RelationshipSecretsPolicy.NO_SECRETS]: 'Никакви тайни',
    [RelationshipSecretsPolicy.SMALL_PERSONAL_SECRETS]: 'Малки лични тайни',
    [RelationshipSecretsPolicy.RIGHT_TO_PRIVACY]: 'Всеки има право на личен живот',
}