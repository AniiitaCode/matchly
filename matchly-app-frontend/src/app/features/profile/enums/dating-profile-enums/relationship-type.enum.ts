export enum RelationshipType {
  SERIOUS_RELATIONSHIP = 'SERIOUS_RELATIONSHIP',
  DATING = 'DATING',
  FRIENDSHIP = 'FRIENDSHIP',
  CHAT = 'CHAT',
  CASUAL_DATING = 'CASUAL_DATING',
}

export const RelationshipTypeDisplayName: Record<RelationshipType, string> = {
  [RelationshipType.SERIOUS_RELATIONSHIP]: 'Сериозна връзка',
  [RelationshipType.DATING]: 'Срещи',
  [RelationshipType.FRIENDSHIP]: 'Приятелство',
  [RelationshipType.CHAT]: 'Чат',
  [RelationshipType.CASUAL_DATING]: 'Нещо неангажиращо',
};