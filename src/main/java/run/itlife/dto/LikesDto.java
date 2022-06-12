package run.itlife.dto;

public class LikesDto {

    private Long likeId;
    private Long userLikeId;
    private Long postLikeId;

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Long getUserLikeId() {
        return userLikeId;
    }

    public void setUserLikeId(Long userLikeId) {
        this.userLikeId = userLikeId;
    }

    public Long getPostLikeId() {
        return postLikeId;
    }

    public void setPostLikeId(Long postLikeId) {
        this.postLikeId = postLikeId;
    }
}
