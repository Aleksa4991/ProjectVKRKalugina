package org.urfu.adservice.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.urfu.adservice.dao.AdvertisementRepository;
import org.urfu.adservice.dtos.Constants;
import org.urfu.adservice.dtos.HttpResponseExtractor;
import org.urfu.adservice.dtos.Advertisement;
import org.urfu.adservice.dtos.Roles;
import org.urfu.adservice.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class AdvertisementService {
    
    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserServiceConnector userserviceConnector;

    @Value("${userservice.paths.user}")
    private String uriUser;

    Map<String, Object> response = new HashMap<>();

    public Mono<ResponseEntity<Map<String, Object>>> createAdvertisement(Advertisement advertisement) {
        return userserviceConnector.retrieveuserserviceData(uriUser + "/" + advertisement.getAuthor().toString())
            .flatMap(res -> {
            UUID advertisementId = null;
            User user = HttpResponseExtractor.extractDataFromHttpClientResponse(res, User.class);

            if (user.hasRole(Roles.PRODUCER)) {
                advertisementId = advertisementRepository.createAdvertisement(advertisement);
            }
            if (advertisementId == null) {
                response.put(Constants.CODE, "500");
                response.put(Constants.ADVERTISEMENT, "Message has not been created");
                response.put(Constants.DATA, "Something went wrong");
            } else {
                response.put(Constants.CODE, "201");
                response.put(Constants.ADVERTISEMENT, "Message has been created");
                response.put(Constants.DATA, advertisementId.toString());
            }
            return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
        });
    }

    public Mono<ResponseEntity<Map<String, Object>>> getAdvertisementbyId(UUID advertisementId) {
        Advertisement advertisement = advertisementRepository.getAdvertisementbyId(advertisementId);
        if (advertisement.getId() == null) {
            response.put(Constants.CODE, "404");
            response.put(Constants.ADVERTISEMENT, "Ad not found");
            response.put(Constants.DATA, advertisement);
        } else {
            response.put(Constants.CODE, "200");
            response.put(Constants.ADVERTISEMENT, "Ad has been found");
            response.put(Constants.DATA, advertisement);
        }
        return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));

    }

    public Mono<ResponseEntity<Map<String, Object>>> getAdvertisementsForProducerById(UUID producerId) {
        List<Advertisement> advertisements = advertisementRepository.getAdvertisementsForProducerById(producerId);
        if (advertisements.size() == 0) {
            response.put(Constants.CODE, "404");
            response.put(Constants.ADVERTISEMENT, "Either producer didn't produce any messages or producer not found");
            response.put(Constants.DATA, new ArrayList<>());
        } else {
            response.put(Constants.CODE, "200");
            response.put(Constants.ADVERTISEMENT, "List of messages has been requested successfully");
            response.put(Constants.DATA, advertisements);
        }
        return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));

    }

    public Mono<ResponseEntity<Map<String, Object>>> getAdvertisementsForSubscriberById(UUID subscriberId) {
        return userserviceConnector.retrieveuserserviceData(uriUser + "/" + subscriberId.toString()).flatMap(res -> {
            User user = HttpResponseExtractor.extractDataFromHttpClientResponse(res, User.class);
            List<Advertisement> advertisements = new ArrayList<>();

            if (user.hasRole(Roles.SUBSCRIBER)) {
                advertisements = advertisementRepository.getAdvertisementsForSubscriberById(subscriberId);
            }
            if (advertisements.size() == 0) {
                response.put(Constants.CODE, "404");
                response.put(Constants.ADVERTISEMENT, "Subscription not found or empty");
                response.put(Constants.DATA, new ArrayList<>());
            } else {
                response.put(Constants.CODE, "200");
                response.put(Constants.ADVERTISEMENT, "List of messages has been requested successfully");
                response.put(Constants.DATA, advertisements);
            }
            return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
        });
    }

    public Mono<ResponseEntity<Map<String, Object>>> deleteAdvertisementById(UUID advertisementId) {
        int result = advertisementRepository.deleteAdvertisementById(advertisementId);
        if (result != 1) {
            response.put(Constants.CODE, "500");
            response.put(Constants.ADVERTISEMENT, "Advertisement" + advertisementId.toString() + " has not been deleted");
            response.put(Constants.DATA, false);
        } else {
            response.put(Constants.CODE, "200");
            response.put(Constants.ADVERTISEMENT, "Advertisement " + advertisementId.toString() + " successfully deleted");
            response.put(Constants.DATA, true);
        }
        return Mono.just(ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .header(Constants.ACCEPT, Constants.APPLICATION_JSON).body(response));
    }
}
