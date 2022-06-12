package run.itlife.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

//Класс, который представляет таблицу post в виде Java-класса (dto - dataobject)
//задача объектов класса содержать некоторые данные по информации в таблице.
//Каждый объект класса будет соответствовать некоторому посту
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    private Long postId;
    private String photo;
    private String extFile;
    private String content;
    private String username;

    public PostDto() {}

    public String getExtFile() {
        return extFile;
    }

    public void setExtFile(String extFile) {
        this.extFile = extFile;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CommentDto> comments;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content1) {
        this.content = content1;
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

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}