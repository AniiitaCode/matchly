export enum WantsChildren {
  YES = 'YES',
  NO = 'NO',
  NOT_SURE = 'NOT_SURE',
}

export const WantsChildrenDisplayName: Record<WantsChildren, string> = {
  [WantsChildren.YES]: 'Искам деца',
  [WantsChildren.NO]: 'Не искам деца',
  [WantsChildren.NOT_SURE]: 'Не съм сигурен/на',
};