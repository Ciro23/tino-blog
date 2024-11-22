package it.tino.blog.rss;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "rss_feeds", schema = "public")
@Data
public class SpringRssFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String url = "";

    @Column(nullable = false)
    private String description = "";
}
