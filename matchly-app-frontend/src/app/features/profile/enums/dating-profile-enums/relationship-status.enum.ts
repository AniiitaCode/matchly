export enum RelationshipStatus {
  SINGLE = 'SINGLE',
  IN_RELATIONSHIP = 'IN_RELATIONSHIP',
  OPEN_RELATIONSHIP = 'OPEN_RELATIONSHIP',
  MARRIED = 'MARRIED',
  DIVORCED = 'DIVORCED',
  NOT_SPECIFIED = 'NOT_SPECIFIED',
}

export const RelationshipStatusDisplayName: Record<RelationshipStatus, string> = {
  [RelationshipStatus.SINGLE]: 'Необвързан/а',
  [RelationshipStatus.IN_RELATIONSHIP]: 'Във връзка',
  [RelationshipStatus.OPEN_RELATIONSHIP]: 'В отворена връзка',
  [RelationshipStatus.MARRIED]: 'Женен/а',
  [RelationshipStatus.DIVORCED]: 'Разведен/а',
  [RelationshipStatus.NOT_SPECIFIED]: 'Не споделям',
};