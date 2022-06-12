package run.itlife.entity;

import javax.persistence.*;

@Entity
@Table(name="subscriptions")
public class Subscriptions {

    @Id
    @Column(name="sub_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User currentUserId;

    @ManyToOne
    @JoinColumn(name = "user_sub_id")
    private User subUserId;

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public User getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(User currentUserId) {
        this.currentUserId = currentUserId;
    }

    public User getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(User subUserId) {
        this.subUserId = subUserId;
    }

}
