/**
 * A single page of a paginated list returned by the backend.
 */
export interface PageResult<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

/**
 * The largest page size accepted by the backend.<br>
 * Lists without a pagination UI request it to load as many items as possible.
 */
export const MAX_PAGE_SIZE = 100;
