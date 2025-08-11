export function getFormattedCreationDateTime(date: Date): string {
  date = new Date(date);
  return new Intl.DateTimeFormat('en-US', {
    day: 'numeric',
    month: 'short',
    year: 'numeric'
  }).format(date);
}
