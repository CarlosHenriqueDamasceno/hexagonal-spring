package hexagonal.link.adapter.driven.database;

import hexagonal.link.domain.Link;
import hexagonal.user.adapter.driven.database.UserModel;
import jakarta.persistence.*;

@Entity
@Table(name = "links")
public class LinkModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String url;
    @Column(unique = true, nullable = false)
    private String slug;
    @ManyToOne
    private UserModel user;
    private Long accesses;

    public LinkModel() {
    }

    public LinkModel(Long id, String url, String slug, UserModel user, Long accesses) {
        this.id = id;
        this.url = url;
        this.slug = slug;
        this.user = user;
        this.accesses = accesses;
    }

    public Link toEntity() {
        return Link.buildExistentLink(id, url, slug, user.getId(), accesses);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Long getAccesses() {
        return accesses;
    }

    public void setAccesses(Long accesses) {
        this.accesses = accesses;
    }
}
