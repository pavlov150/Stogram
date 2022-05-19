package run.itlife.dto;

import run.itlife.entity.Bugs;
import run.itlife.entity.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class BugsDto {

    private Long bugId;
    private User userId;
    private String bugText;
    private LocalDateTime createdAt;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User  userId) {
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
