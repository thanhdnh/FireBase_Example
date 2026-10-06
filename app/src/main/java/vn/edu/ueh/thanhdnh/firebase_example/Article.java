package vn.edu.ueh.thanhdnh.firebase_example;

import java.io.Serializable;

public class Article implements Serializable {
  private String id;
  private String title;
  private String description;
  private String imgCover;
  private int views;

  public Article() {
  }

  public Article(String title, String description) {
    this(title, description, "", 0);
  }

  public Article(String title, String description, String imgCover, int views) {
    this("", title, description, imgCover, views);
  }

  public Article(String id, String title, String description, String imgCover, int views) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.imgCover = imgCover;
    this.views = views;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImgCover() {
    return imgCover != null ? imgCover : "";
  }

  public void setImgCover(String imgCover) {
    this.imgCover = imgCover;
  }

  public int getViews() {
    return views;
  }

  public void setViews(int views) {
    this.views = views;
  }

  public String getContent() {
    return description;
  }

  public void setContent(String content) {
    this.description = content;
  }

  public String getImageUrl() {
    return imgCover;
  }

  public void setImageUrl(String imageUrl) {
    this.imgCover = imageUrl;
  }

  public String getAvatar_url() {
    return imgCover;
  }

  public void setAvatar_url(String avatar_url) {
    this.imgCover = avatar_url;
  }

  @Override
  public String toString() {
    return "Article{" +
      "title='" + title + '\'' +
      ", description='" + description + '\'' +
      ", imgCover='" + imgCover + '\'' +
      ", views=" + views +
      '}';
  }
}
