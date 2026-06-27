export interface Article {
  id: string;

  title: string;
  slug: string;

  shortDescription: string;
  content: string;

  createdAt: Date;
  minutesToRead: number;
}
