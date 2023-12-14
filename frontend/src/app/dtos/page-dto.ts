export interface PageDto<T> {
  content: T[];
  currentPage: number;
  totalPages: number;
  totalElements: number;
}
