
package com.teddy.lc4e.entity;

import java.util.Arrays;
import java.util.List;

public class Article {

    private String imageUrl;

    private Popup popUp;

    private String articleTitle;

    private String articleUrl;

    private String category;

    private String user;

    private Integer comments;

    private String publishTime;

    private String lastCommentUser;

    private List<String> statusText;

    private Integer page;

    public Article() {
    }


    public Article(String imageUrl, Popup popUp, String articleTitle, String category, String user, Integer comments, String publishTime, String lastCommentUser, Integer page, String... statusText) {
        this.imageUrl = imageUrl;
        this.popUp = popUp;
        this.articleTitle = articleTitle;
        this.category = category;
        this.user = user;
        this.comments = comments;
        this.publishTime = publishTime;
        this.lastCommentUser = lastCommentUser;
        this.page = page;
        this.statusText = Arrays.asList(statusText);
    }

    public Article(String imageUrl, Popup popUp, String articleTitle, String articleUrl, String category, String user, Integer comments, String publishTime, String lastCommentUser, Integer page, String... statusText) {
        this.imageUrl = imageUrl;
        this.popUp = popUp;
        this.articleTitle = articleTitle;
        this.articleUrl = articleUrl;
        this.category = category;
        this.user = user;
        this.comments = comments;
        this.publishTime = publishTime;
        this.lastCommentUser = lastCommentUser;
        this.page = page;
        this.statusText = Arrays.asList(statusText);
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<String> getStatusText() {
        return statusText;
    }

    public void setStatusText(List<String> statusText) {
        this.statusText = statusText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Popup getPopUp() {
        return popUp;
    }

    public void setPopUp(Popup popUp) {
        this.popUp = popUp;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getLastCommentUser() {
        return lastCommentUser;
    }

    public void setLastCommentUser(String lastCommentUser) {
        this.lastCommentUser = lastCommentUser;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }
}
