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

/**
 * The number of articles shown in each page of the article lists.
 */
export const ARTICLES_PAGE_SIZE = 10;

/**
 * Converts the 1-based "page" query parameter shown in the URL into the
 * 0-based page index used by the backend.<br>
 * A missing or invalid value is treated as the first page.
 */
export function pageFromQueryParam(value: string | null): number {
  const page = Number(value);
  if (!Number.isInteger(page) || page < 1) {
    return 0;
  }
  return page - 1;
}
