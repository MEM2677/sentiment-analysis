package com.entando.bundle.service.dto;

import java.io.Serializable;

public class ReviewDTO implements Serializable {

    private String username;
    private String pageid;
    private String contentid;
    private String langcode;
    private String text;

    public OpinionDTO toOpinionDTO() {
        OpinionDTO opinion = new OpinionDTO();

        opinion.setUsername(username);
        opinion.setPageid(pageid);
        opinion.setContentid(contentid);
        opinion.setText(text);
        opinion.setLangcode(langcode);
        return opinion;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
            "username='" + username + '\'' +
            ", pageid='" + pageid + '\'' +
            ", contentid='" + contentid + '\'' +
            ", langcode='" + langcode + '\'' +
            ", text='" + text + '\'' +
            '}';
    }
}
