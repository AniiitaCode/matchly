export enum HurtResponseExpectation {
    APOLOGY = 'APOLOGY',
    CHANGE_OF_BEHAVIOR = 'CHANGE_OF_BEHAVIOR',
    TIME = 'TIME',
    CONVERSATION = 'CONVERSATION',
}

export const HurtResponseExpectationDisplayName: Record<HurtResponseExpectation, string> = {
    [HurtResponseExpectation.APOLOGY]: 'Извинение',
    [HurtResponseExpectation.CHANGE_OF_BEHAVIOR]: 'Промяна на поведението',
    [HurtResponseExpectation.TIME]: 'Време',
    [HurtResponseExpectation.CONVERSATION]: 'Разговор',
}