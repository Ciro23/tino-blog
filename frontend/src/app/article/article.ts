export interface Article {
  id: string;
  creationDateTime?: Date;

  title: string;
  minutesToRead: number;

  category?: string;
  categoryUrl?: string;

  shortDescription: string;
  content: string;
}
