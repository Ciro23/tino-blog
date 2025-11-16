import { Article } from "../article/article";

export interface RssArticle extends Omit<Article, 'id'> {
  id: Article['slug'];
}
