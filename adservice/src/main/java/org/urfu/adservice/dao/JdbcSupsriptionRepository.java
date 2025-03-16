package org.urfu.adservice.dao;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.urfu.adservice.dtos.Constants;
import org.urfu.adservice.dtos.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSupsriptionRepository implements SubscriptionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcAdvertisementRepository jdbcAdvertisementRepository;

    @Override
    public boolean createSubscription(Subscription subscription) {
        if (subscription.getSubscriber() == null || subscription.getProducers() == null
                || subscription.getProducers().size() == 0
                || this.createSubscriber(subscription.getSubscriber()) == null)
            return false;

        try {
            subscription.getProducers().forEach(producerId -> {
                if (jdbcAdvertisementRepository.createProducer(producerId) != null) {
                    jdbcTemplate.update(Constants.CREATE_SUBSCRIPTION, subscription.getSubscriber().toString(),
                            producerId.toString());
                }
            });
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateSubscription(Subscription subscription) {
        this.deleteSubscription(subscription.getSubscriber());
        return this.createSubscription(subscription);
    }

    @Override
    public Subscription getSubscription(UUID subscriberId) {
        List<Subscription> subscriptions = jdbcTemplate.query(Constants.GET_SUBSCRIPTION,
                (rs, rowNum) -> new Subscription(DaoHelper.bytesArrayToUuid(rs.getBytes("subscriptions.subscriber_id")),
                        Arrays.asList(DaoHelper.bytesArrayToUuid(rs.getBytes("subscriptions.producer_id")))),
                subscriberId.toString());

        Subscription subscription = new Subscription();
        for (Object oSubscriptions : subscriptions) {
            if (subscription.getSubscriber() == null) {
                subscription.setSubscriber(((Subscription) oSubscriptions).getSubscriber());
            }
            subscription.addProducer(((Subscription) oSubscriptions).getProducers().get(0));
        }

        return subscription;
    }

    @Override
    public boolean deleteSubscription(UUID subscriberId) {
        try {
            jdbcTemplate.update(Constants.DELETE_SUBSCRIPTION, subscriberId.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    UUID createSubscriber(UUID subscriberId) {
        try {
            jdbcTemplate.update(Constants.CREATE_SUBSCRIBER, subscriberId.toString());
        } catch (Exception e) {
            return null;
        }
        return subscriberId;
    }
}
