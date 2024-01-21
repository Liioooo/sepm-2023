export type PageableRequest = {
  page: number;
  size: number;
};

export type PageableState = {
  maxSize: number;
  currentPage: number;
  pageSize: number;
  totalPages: number;
  totalElements: number;
}

export const DEFAULT_PAGEABLE_STATE: PageableState = {
  maxSize: 10,
  currentPage: 1,
  pageSize: 20,
  totalPages: 0,
  totalElements: 0
};
