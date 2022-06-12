package run.itlife.dto;

// Используется в CommentController
public class CommentDto {

    private Long commentId;
    private Long postId;
    private String commentText;
    private String username;

    public CommentDto() { }

    public CommentDto(long commentId, String commentText) {
        this.commentId = commentId;
        this.commentText = commentText;
    }

    public CommentDto(String commentText) {
        this.commentText = commentText;
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}