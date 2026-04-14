export enum RelationshipRoles {
    EQUAL_ROLES = 'EQUAL_ROLES',
    TRADITIONAL_ROLES = 'TRADITIONAL_ROLES',
    SITUATION_DEPENDENT = 'SITUATION_DEPENDENT',
}

export const RelationshipRolesDisplayName: Record<RelationshipRoles, string> = {
    [RelationshipRoles.EQUAL_ROLES]: 'Равни роли',
    [RelationshipRoles.TRADITIONAL_ROLES]: 'Традиционни роли',
    [RelationshipRoles.SITUATION_DEPENDENT]: 'Според ситуацията',
}