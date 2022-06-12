package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Subscriptions;

import javax.transaction.Transactional;
import java.util.List;

public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Integer> {

    @Query(value = "select s.*, u.username, u1.username from subscriptions s " +
            "join users u on u.user_id = s.user_sub_id " +
            "join users u1 on u1.user_id = s.user_id " +
            "where u1.username = ? ; ", nativeQuery = true)
    List<Subscriptions> findSubscribes(String username);

    @Query(value = "select s.*, u.username, u1.username from subscriptions s " +
            "join users u on u.user_id = s.user_sub_id " +
            "join users u1 on u1.user_id = s.user_id " +
            "where u.username = ? ; ", nativeQuery = true)
    List<Subscriptions> findSubscribers(String username);

    @Query(value = "select count(s.sub_id) from subscriptions s " +
            "join users u on u.user_id = s.user_sub_id " +
            "join users u1 on u1.user_id = s.user_id " +
            "where u1.username = ? and u.username = ? ; ", nativeQuery = true)
    int isSubscribe(String currentUsername, String subUsername);

    @Query(value = "select count(s.sub_id) from subscriptions s " +
            "join users u on u.user_id = s.user_sub_id " +
            "join users u1 on u1.user_id = s.user_id " +
            "where u1.username = ? ; ", nativeQuery = true)
    int countSubscribe(String currentUsername);

    @Query(value = "select count(s.sub_id) from subscriptions s " +
            "join users u on u.user_id = s.user_sub_id " +
            "join users u1 on u1.user_id = s.user_id " +
            "where u.username = ? ; ", nativeQuery = true)
    int countSubscribers(String currentUsername);

    @Modifying
    @Transactional
    @Query(value = "delete from subscriptions s " +
            "where user_id = ? and user_sub_id = ? ; ", nativeQuery = true)
    void deleteSubscribeLong(long currentUserId, long subUserId);

}




