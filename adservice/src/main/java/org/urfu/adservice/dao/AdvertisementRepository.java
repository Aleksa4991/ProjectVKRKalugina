package org.urfu.adservice.dao;

import java.util.List;
import java.util.UUID;

import org.urfu.adservice.dtos.Advertisement;

public interface AdvertisementRepository {
    public Advertisement getAdvertisementbyId(UUID advertisementId);
    public List<Advertisement> getAdvertisementsForProducerById(UUID producerId);
    public List<Advertisement> getAdvertisementsForSubscriberById(UUID subscriberId);
    public UUID createAdvertisement(Advertisement advertisement);
    public int deleteAdvertisementById(UUID advertisementId);
}
