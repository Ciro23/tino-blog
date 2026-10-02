import { Article } from "../article/article";

export interface RssArticle extends Article {
  link: string;
  feed: {
    id: string;
    url: string;
    title: string;
    showArticlesDescription: boolean;
  };
}
