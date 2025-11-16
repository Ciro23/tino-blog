import { Article } from "../article/article";

export interface RssArticle extends Article {
  feed: {
    id: string;
    url: string;
    description: string;
    showArticlesDescription: boolean;
  };
}
