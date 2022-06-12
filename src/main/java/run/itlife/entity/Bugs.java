package run.itlife.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Bugs {

    @Id
    @Column(name="bug_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bugId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name="bug_text")
    private String bugText;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Long getBugId() {
        return bugId;
    }

    public void setBugId(Long bugId) {
        this.bugId = bugId;
    }

    public String getBugText() {
        return bugText;
    }

    public void setBugText(String bugText) {
        this.bugText = bugText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
