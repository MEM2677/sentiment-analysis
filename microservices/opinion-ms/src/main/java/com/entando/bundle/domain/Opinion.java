package com.entando.bundle.domain;

import com.entando.bundle.domain.enumeration.Sentiment;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Opinion.
 */
@Entity
@Table(name = "opinion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Opinion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "pageid")
    private String pageid;

    @Column(name = "contentid")
    private String contentid;

    @Column(name = "langcode")
    private String langcode;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "text")
    private String text;

    @Column(name = "sentences")
    private Integer sentences;

    @Column(name = "score")
    private Float score;

    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private Sentiment result;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Opinion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public Opinion username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPageid() {
        return this.pageid;
    }

    public Opinion pageid(String pageid) {
        this.setPageid(pageid);
        return this;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getContentid() {
        return this.contentid;
    }

    public Opinion contentid(String contentid) {
        this.setContentid(contentid);
        return this;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getLangcode() {
        return this.langcode;
    }

    public Opinion langcode(String langcode) {
        this.setLangcode(langcode);
        return this;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public Opinion created(ZonedDateTime created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getText() {
        return this.text;
    }

    public Opinion text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getSentences() {
        return this.sentences;
    }

    public Opinion sentences(Integer sentences) {
        this.setSentences(sentences);
        return this;
    }

    public void setSentences(Integer sentences) {
        this.sentences = sentences;
    }

    public Float getScore() {
        return this.score;
    }

    public Opinion score(Float score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Sentiment getResult() {
        return this.result;
    }

    public Opinion result(Sentiment result) {
        this.setResult(result);
        return this;
    }

    public void setResult(Sentiment result) {
        this.result = result;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opinion)) {
            return false;
        }
        return id != null && id.equals(((Opinion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opinion{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", pageid='" + getPageid() + "'" +
            ", contentid='" + getContentid() + "'" +
            ", langcode='" + getLangcode() + "'" +
            ", created='" + getCreated() + "'" +
            ", text='" + getText() + "'" +
            ", sentences=" + getSentences() +
            ", score=" + getScore() +
            ", result='" + getResult() + "'" +
            "}";
    }
}
