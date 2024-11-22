import {Article} from "../article/article";

export interface RssArticle extends Article {
  links: RssLink[];
}

export interface RssLink {
  title: string;
  url: string;
}
