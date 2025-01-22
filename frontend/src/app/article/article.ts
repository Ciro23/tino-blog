export interface Article {
  id: string,
  creationDateTime?: Date;

  title: string;
  slug: string;
  minutesToRead: number;

  category?: string;
  categoryUrl?: string;

  shortDescription: string;
  content: string;
}
