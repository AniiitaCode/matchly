export enum ChildrenStatus {
  NO_CHILDREN = 'NO_CHILDREN',
  HAS_CHILDREN = 'HAS_CHILDREN',
}

export const ChildrenStatusDisplayName: Record<ChildrenStatus, string> = {
  [ChildrenStatus.NO_CHILDREN]: 'Нямам дете',
  [ChildrenStatus.HAS_CHILDREN]: 'Имам дете',
};