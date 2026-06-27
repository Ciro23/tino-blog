/**
 * E.g. "Jun 27, 2026".
 * This function also converts the UTC dates, received by the APIs, to local
 * time.
 */
export function getFormattedCreationDateTime(date: Date): string {
  date = new Date(date);
  return new Intl.DateTimeFormat('en-US', {
    day: 'numeric',
    month: 'short',
    year: 'numeric'
  }).format(date);
}
