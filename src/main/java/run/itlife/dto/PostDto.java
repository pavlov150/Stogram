package run.itlife.dto;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;

//Класс, который представляет таблицу post в виде Java-класса (dto - dataobject)
//задача объектов класса содержать некоторые данные по информации в таблице.
//Каждый объект класса будет соответствовать некоторому посту
public class PostDto {

    private Long postId;
    private String photo;
    private String content;
    private String username;

    public PostDto() {}

    public PostDto(String content) {
        this.content = content;
    }

    public PostDto(String photo, String content) {
        this.photo = photo;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}