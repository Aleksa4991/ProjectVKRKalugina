package org.urfu.adservice.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.urfu.adservice.dao.SubscriptionRepository;
import org.urfu.adservice.dtos.Constants;
import org.urfu.adservice.dtos.HttpResponseExtractor;
import org.urfu.adservice.dtos.Roles;
import org.urfu.adservice.dtos.Subscription;
import org.urfu.adservice.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class SubscriptionsService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserServiceConnector userserviceConnector;

    @Value("${userservice.paths.user}")
    private String uriUser;

    Map<String, Object> response = new HashMap<>();

    public Mono<ResponseEntity<Map<String, Object>>> getSubscriptionsForSubscriberById(UUID subscriberId) {
        return userserviceConnector.retrieveuserserviceData(uriUser + "/" + subscriberId.toString()).flatMap(res -> {
            Subscription subscriptions = new Subscription();
            User user = HttpResponseExtractor.extractDataFromHttpClientResponse(res, User.class);

            if (user.hasRole(Roles.SUBSCRIBER)) {
                subscriptions = subscriptionRepository.getSubscription(subscriberId);
            }
            if (subscriptions.getSubscriber() == null) {
                response.put(Constants.CODE, "404");
                response.put(Constants.ADVERTISEMENT,
                        "Subscriptions for user with ID " + subscriberId.toString() + " is not found");
                response.put(Constants.DATA, subscriptions);
            } else {
                response.put(Constants.CODE, "201");
                response.put(Constants.ADVERTISEMENT, "Subscriptions have been retrieved");
                response.put(Constants.DATA, subscriptions);
            }
            return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
        });
    }

    public Mono<ResponseEntity<Map<String, Object>>> createSubscription(Subscription subscription) {
        return userserviceConnector.retrieveuserserviceData(uriUser + "/" + subscription.getSubscriber().toString()).flatMap(res -> {
            boolean subscriptionId = false;
            User user = HttpResponseExtractor.extractDataFromHttpClientResponse(res, User.class);

            if (user.hasRole(Roles.SUBSCRIBER)) {
                subscriptionId = subscriptionRepository.createSubscription(subscription);
            }
            if (!subscriptionId) {
                response.put(Constants.CODE, "500");
                response.put(Constants.ADVERTISEMENT, "Subscriptions has not been created");
                response.put(Constants.DATA, false);
            } else {
                response.put(Constants.CODE, "200");
                response.put(Constants.ADVERTISEMENT, "Subscription has been created");
                response.put(Constants.DATA, true);
            }
            return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
        });
    }

    public Mono<ResponseEntity<Map<String, Object>>> updateSubscriptionForSubscriberById(Subscription subscription) {
        return userserviceConnector.retrieveuserserviceData(uriUser + "/" + subscription.getSubscriber().toString()).flatMap(res -> {
            boolean subscriptionId = false;
            User user = HttpResponseExtractor.extractDataFromHttpClientResponse(res, User.class);

            if (user.hasRole(Roles.SUBSCRIBER)) {
                subscriptionId = subscriptionRepository.updateSubscription(subscription);
            }
            if (!subscriptionId) {
                response.put(Constants.CODE, "500");
                response.put(Constants.ADVERTISEMENT, "Subscription has not been updated");
                response.put(Constants.DATA, false);
            } else {
                response.put(Constants.CODE, "201");
                response.put(Constants.ADVERTISEMENT, "Subscription has been updated");
                response.put(Constants.DATA, true);
            }
            return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
        });
    }

    public Mono<ResponseEntity<Map<String, Object>>> deleteSubscriptionForSubscriberById(UUID subscriberId) {
        return userserviceConnector.retrieveuserserviceData(uriUser + "/" + subscriberId.toString()).flatMap(res -> {
            boolean subscriptionId = false;
            User user = HttpResponseExtractor.extractDataFromHttpClientResponse(res, User.class);

            if (user.hasRole(Roles.SUBSCRIBER)) {
                subscriptionId = subscriptionRepository.deleteSubscription(subscriberId);
            }
            if (!subscriptionId) {
                response.put(Constants.CODE, "500");
                response.put(Constants.ADVERTISEMENT, "Subscription has not been deleted");
                response.put(Constants.DATA, false);
            } else {
                response.put(Constants.CODE, "201");
                response.put(Constants.ADVERTISEMENT, "Subscription has been deleted");
                response.put(Constants.DATA, true);
            }
            return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
        });
    }
}
