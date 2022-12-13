package com.entando.bundle.service.dto;

import com.entando.bundle.domain.enumeration.Sentiment;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.entando.bundle.domain.Opinion} entity.
 */
public class OpinionDTO implements Serializable {

    private Long id;

    private String username;

    private String pageid;

    private String contentid;

    private String langcode;

    private ZonedDateTime created;

    private String text;

    private Integer sentences;

    private Float score;

    private Sentiment result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getLangcode() {
        return langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getSentences() {
        return sentences;
    }

    public void setSentences(Integer sentences) {
        this.sentences = sentences;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Sentiment getResult() {
        return result;
    }

    public void setResult(Sentiment result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OpinionDTO)) {
            return false;
        }

        OpinionDTO opinionDTO = (OpinionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, opinionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpinionDTO{" +
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
