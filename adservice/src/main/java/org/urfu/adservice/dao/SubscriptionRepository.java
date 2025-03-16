package org.urfu.adservice.dao;

import java.util.UUID;

import org.urfu.adservice.dtos.Subscription;

public interface SubscriptionRepository {
    public Subscription getSubscription(UUID subscriberId);
    public boolean createSubscription(Subscription subscription);
    public boolean updateSubscription(Subscription subscription);
    public boolean deleteSubscription(UUID subscriberId);
}
