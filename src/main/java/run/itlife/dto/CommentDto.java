package run.itlife.dto;

// Используется в CommentController
public class CommentDto {

    private Long commentId;

    private Long postId;

    private String content;

    private String username;

    public CommentDto() { }

    public CommentDto(long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

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

}