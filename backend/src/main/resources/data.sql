-- The password is literally 'password'.
insert into public.users (id, username, email, password)
values ('4c7dbc23-b524-4dd2-95f0-c0cb974588c7', 'admin', 'admin@test.org',
        '$2a$12$Hp1V.oI.VKiOBSkb85sXMepcUmwZeyzWxZbpgg1JwMTfklaGeeoB2');

insert into public.rss_feeds (id, url, description)
values ('112d544a-66d7-4b4d-a5e2-4b5c2f6b5f59', 'https://medium.com/feed/flutter/tagged/announcements',
        'Flutter announcements'),
       ('606ee257-0cb0-4b30-a8e0-e15855aa5b58', 'https://spring.io/blog/category/releases.atom', 'Spring releases'),
       ('4f6d69af-b603-480f-be99-18421e9fceea', 'https://blog.angular.dev/feed', 'Angular'),
       ('ae214f15-d564-418c-a1f9-b7f2c4f77f32', 'https://blog.getbootstrap.com/feed.xml', 'Bootstrap'),
       ('c39ee6fa-02e1-4e90-a501-1860504c7526', 'https://www.php.net/releases/feed.php', 'PHP Releases'),
       ('4311862d-fd9e-4199-ae02-328fb04cab1f', 'https://medium.com/feed/dartlang', 'Dart');
