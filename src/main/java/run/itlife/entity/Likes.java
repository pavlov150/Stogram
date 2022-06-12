package run.itlife.entity;

import javax.persistence.*;

@Entity
@Table(name="likes")
public class Likes {

    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userLikeId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postLikeId;

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public User getUserLikeId() {
        return userLikeId;
    }

    public void setUserLikeId(User userLikeId) {
        this.userLikeId = userLikeId;
    }

    public Post getPostLikeId() {
        return postLikeId;
    }

    public void setPostLikeId(Post postLikeId) {
        this.postLikeId = postLikeId;
    }

}
