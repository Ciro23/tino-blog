package it.tino.blog.article;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "articles")
@Data
public class SpringArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title = "";

    @Column(nullable = false)
    private String shortDescription = "";

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content = "";

    @Column(nullable = false)
    @CreationTimestamp
    private Instant creationDateTime;
}
