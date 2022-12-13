package com.entando.bundle.service.criteria;

import com.entando.bundle.domain.enumeration.Sentiment;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.entando.bundle.domain.Opinion} entity. This class is used
 * in {@link com.entando.bundle.web.rest.OpinionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /opinions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OpinionCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Sentiment
     */
    public static class SentimentFilter extends Filter<Sentiment> {

        public SentimentFilter() {}

        public SentimentFilter(SentimentFilter filter) {
            super(filter);
        }

        @Override
        public SentimentFilter copy() {
            return new SentimentFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter username;

    private StringFilter pageid;

    private StringFilter contentid;

    private StringFilter langcode;

    private ZonedDateTimeFilter created;

    private StringFilter text;

    private IntegerFilter sentences;

    private FloatFilter score;

    private SentimentFilter result;

    private Boolean distinct;

    public OpinionCriteria() {}

    public OpinionCriteria(OpinionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.pageid = other.pageid == null ? null : other.pageid.copy();
        this.contentid = other.contentid == null ? null : other.contentid.copy();
        this.langcode = other.langcode == null ? null : other.langcode.copy();
        this.created = other.created == null ? null : other.created.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.sentences = other.sentences == null ? null : other.sentences.copy();
        this.score = other.score == null ? null : other.score.copy();
        this.result = other.result == null ? null : other.result.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OpinionCriteria copy() {
        return new OpinionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUsername() {
        return username;
    }

    public StringFilter username() {
        if (username == null) {
            username = new StringFilter();
        }
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPageid() {
        return pageid;
    }

    public StringFilter pageid() {
        if (pageid == null) {
            pageid = new StringFilter();
        }
        return pageid;
    }

    public void setPageid(StringFilter pageid) {
        this.pageid = pageid;
    }

    public StringFilter getContentid() {
        return contentid;
    }

    public StringFilter contentid() {
        if (contentid == null) {
            contentid = new StringFilter();
        }
        return contentid;
    }

    public void setContentid(StringFilter contentid) {
        this.contentid = contentid;
    }

    public StringFilter getLangcode() {
        return langcode;
    }

    public StringFilter langcode() {
        if (langcode == null) {
            langcode = new StringFilter();
        }
        return langcode;
    }

    public void setLangcode(StringFilter langcode) {
        this.langcode = langcode;
    }

    public ZonedDateTimeFilter getCreated() {
        return created;
    }

    public ZonedDateTimeFilter created() {
        if (created == null) {
            created = new ZonedDateTimeFilter();
        }
        return created;
    }

    public void setCreated(ZonedDateTimeFilter created) {
        this.created = created;
    }

    public StringFilter getText() {
        return text;
    }

    public StringFilter text() {
        if (text == null) {
            text = new StringFilter();
        }
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public IntegerFilter getSentences() {
        return sentences;
    }

    public IntegerFilter sentences() {
        if (sentences == null) {
            sentences = new IntegerFilter();
        }
        return sentences;
    }

    public void setSentences(IntegerFilter sentences) {
        this.sentences = sentences;
    }

    public FloatFilter getScore() {
        return score;
    }

    public FloatFilter score() {
        if (score == null) {
            score = new FloatFilter();
        }
        return score;
    }

    public void setScore(FloatFilter score) {
        this.score = score;
    }

    public SentimentFilter getResult() {
        return result;
    }

    public SentimentFilter result() {
        if (result == null) {
            result = new SentimentFilter();
        }
        return result;
    }

    public void setResult(SentimentFilter result) {
        this.result = result;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OpinionCriteria that = (OpinionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(username, that.username) &&
            Objects.equals(pageid, that.pageid) &&
            Objects.equals(contentid, that.contentid) &&
            Objects.equals(langcode, that.langcode) &&
            Objects.equals(created, that.created) &&
            Objects.equals(text, that.text) &&
            Objects.equals(sentences, that.sentences) &&
            Objects.equals(score, that.score) &&
            Objects.equals(result, that.result) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, pageid, contentid, langcode, created, text, sentences, score, result, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpinionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (username != null ? "username=" + username + ", " : "") +
            (pageid != null ? "pageid=" + pageid + ", " : "") +
            (contentid != null ? "contentid=" + contentid + ", " : "") +
            (langcode != null ? "langcode=" + langcode + ", " : "") +
            (created != null ? "created=" + created + ", " : "") +
            (text != null ? "text=" + text + ", " : "") +
            (sentences != null ? "sentences=" + sentences + ", " : "") +
            (score != null ? "score=" + score + ", " : "") +
            (result != null ? "result=" + result + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
