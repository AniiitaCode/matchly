export enum CheatingDefinition {
    INTIMATE_CONTACT = 'INTIMATE_CONTACT',
    KISS = 'KISS',
    EMOTIONAL_RELATIONSHIP = 'EMOTIONAL_RELATIONSHIP',
    FLIRT_OR_CHATS = 'FLIRT_OR_CHATS',
}

export const CheatingDefinitionDisplayName: Record<CheatingDefinition, string> = {
    [CheatingDefinition.INTIMATE_CONTACT]: 'Само интимен контакт',
    [CheatingDefinition.KISS]: 'Целувка',
    [CheatingDefinition.EMOTIONAL_RELATIONSHIP]: 'Емоционална връзка',
    [CheatingDefinition.FLIRT_OR_CHATS]: 'Флирт / чатове',
}