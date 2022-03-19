package run.itlife.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    private Long postId;
    private String title;
    private String content;
    private String tags;
    private String username;

    public PostDto() {
    }

    public PostDto(String title) {
        this.title = title;
    }

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CommentDto> commets;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CommentDto> getCommets() {
        return commets;
    }

    public void setComments(List<CommentDto> comments) {
        this.commets = comments;
    }
}

