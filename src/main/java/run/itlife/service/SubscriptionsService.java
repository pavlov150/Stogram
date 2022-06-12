package run.itlife.service;

import run.itlife.entity.Subscriptions;
import java.util.List;

public interface SubscriptionsService {

    void createSub(String userSub);
    List<Subscriptions> findSubscribes(String username);
    List<Subscriptions> findSubscribers(String username);
    int isSubscribe(String currentUsername, String subUsername);
    int countSubscribe(String currentUsername);
    int countSubscribers(String currentUsername);
    void deleteSubscribeLong(long currentUserId, long subUserId);

}
