package org.urfu.adservice.controllers;

import java.util.Map;
import java.util.UUID;

import org.urfu.adservice.dtos.Constants;
import org.urfu.adservice.dtos.Advertisement;
import org.urfu.adservice.services.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisements;

    @RequestMapping(method = RequestMethod.GET, path = Constants.URI_ADVERTISEMENT + "/{advertisement-id}")
    public Mono<ResponseEntity<Map<String, Object>>> getAdvertisementbyId(
            @PathVariable(value = "advertisement-id", required = true) String advertisementId) {
        return advertisements.getAdvertisementbyId(UUID.fromString(advertisementId));
    }

    @RequestMapping(method = RequestMethod.GET, path = Constants.URI_PRODUCER + "/{producer-id}")
    public Mono<ResponseEntity<Map<String, Object>>> getAdvertisementsForProducerById(
            @PathVariable(value = "producer-id", required = true) String producerId) {
        return advertisements.getAdvertisementsForProducerById(UUID.fromString(producerId));
    }

    @RequestMapping(method = RequestMethod.GET, path = Constants.URI_SUBSCRIBER + "/{subscriber-id}")
    public Mono<ResponseEntity<Map<String, Object>>> getAdvertisementsForSubscriberById(
            @PathVariable(value = "subscriber-id", required = true) String subscriberId) {
        return advertisements.getAdvertisementsForSubscriberById(UUID.fromString(subscriberId));
    }

    @RequestMapping(method = RequestMethod.POST, path = Constants.URI_ADVERTISEMENT, consumes = Constants.APPLICATION_JSON)
    public Mono<ResponseEntity<Map<String, Object>>> createAdvertisement(@RequestBody Advertisement advertisement) {
        return advertisements.createAdvertisement(advertisement);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = Constants.URI_ADVERTISEMENT + "/{advertisement-id}")
    public Mono<ResponseEntity<Map<String, Object>>> deleteMessageById(
            @PathVariable(value = "advertisement-id", required = true) String advertisementId) {
        return advertisements.deleteAdvertisementById(UUID.fromString(advertisementId));
    }

}