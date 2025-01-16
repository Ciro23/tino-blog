export interface Article {
  id: string;
  creationDateTime?: Date;
  title: string;
  category?: string;
  categoryUrl?: string;
  shortDescription: string;
  content: string;
}
