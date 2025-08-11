--
-- PostgreSQL database dump
--

-- Dumped from database version 17.1 (Debian 17.1-1.pgdg120+1)
-- Dumped by pg_dump version 17.1 (Debian 17.1-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: articles; Type: TABLE; Schema: public
--

CREATE TABLE public.articles
(
    id                 uuid                        NOT NULL,
    content            text                        NOT NULL,
    creation_date_time timestamp(6) with time zone NOT NULL,
    short_description  character varying(255)      NOT NULL,
    title              character varying(255)      NOT NULL,
    slug               character varying(255)      NOT NULL
);


--
-- Name: rss_feeds; Type: TABLE; Schema: public
--

CREATE TABLE public.rss_feeds
(
    id                        uuid                   NOT NULL,
    description               character varying(255) NOT NULL,
    url                       character varying(255) NOT NULL,
    show_articles_description boolean DEFAULT true   NOT NULL
);


--
-- Name: users; Type: TABLE; Schema: public
--

CREATE TABLE public.users
(
    id       uuid NOT NULL,
    email    character varying(255),
    password character varying(255),
    username character varying(255)
);


--
-- Name: articles articles_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.articles
    ADD CONSTRAINT articles_pkey PRIMARY KEY (id);


--
-- Name: rss_feeds rss_feeds_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.rss_feeds
    ADD CONSTRAINT "rss_feeds_pkey" PRIMARY KEY (id);


--
-- Name: rss_feeds uk15xqoycjge2p0yvn87kycgwmx; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.rss_feeds
    ADD CONSTRAINT uk15xqoycjge2p0yvn87kycgwmx UNIQUE (url);


--
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: articles uksn7al9fwhgtf98rvn8nxhjt4f; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.articles
    ADD CONSTRAINT uksn7al9fwhgtf98rvn8nxhjt4f UNIQUE (slug);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--
