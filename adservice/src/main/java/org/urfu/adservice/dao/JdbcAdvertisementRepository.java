package org.urfu.adservice.dao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.urfu.adservice.dtos.Constants;
import org.urfu.adservice.dtos.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAdvertisementRepository implements AdvertisementRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Advertisement getAdvertisementbyId(UUID advertisementId) {
        List<Advertisement> advertisements = jdbcTemplate.query(Constants.GET_ADVERTISEMENT_BY_ID,
                (rs, rowNum) -> new Advertisement(DaoHelper.bytesArrayToUuid(rs.getBytes("advertisements.id")),
                        DaoHelper.bytesArrayToUuid(rs.getBytes("advertisements.producer_id")),
                        rs.getString("advertisements.content"), rs.getLong("advertisements.created")),
                advertisementId.toString());

        // better to return empty message instead of null (for automatic processing)
        return Optional.ofNullable(advertisements.get(0)).orElse(new Advertisement());
    }

    @Override
    public List<Advertisement> getAdvertisementsForProducerById(UUID prodicerId) {
        List<Advertisement> advertisements = jdbcTemplate.query(Constants.GET_ADVERTISEMENTS_FOR_PRODUCER,
                (rs, rowNum) -> new Advertisement(DaoHelper.bytesArrayToUuid(rs.getBytes("advertisements.id")),
                        DaoHelper.bytesArrayToUuid(rs.getBytes("advertisements.producer_id")),
                        rs.getString("advertisements.content"), rs.getLong("advertisements.created")),
                prodicerId.toString());

        return Optional.ofNullable(advertisements).orElse(new ArrayList<>());
    }

    @Override
    public List<Advertisement> getAdvertisementsForSubscriberById(UUID subscriberId) {
        List<Advertisement> advertisements = jdbcTemplate.query(Constants.GET_ADVERTISEMENTS_FOR_SUBSCRIBER,
                (rs, rowNum) -> new Advertisement(DaoHelper.bytesArrayToUuid(rs.getBytes("advertisements.id")),
                        DaoHelper.bytesArrayToUuid(rs.getBytes("advertisements.producer_id")),
                        rs.getString("advertisements.content"), rs.getLong("advertisements.created")),
                subscriberId.toString());
        return Optional.ofNullable(advertisements).orElse(new ArrayList<>());
    }

    @Override
    public UUID createAdvertisement(Advertisement advertisement) {
        advertisement.setId(UUID.randomUUID());
        advertisement.setTimestamp(Instant.now().getEpochSecond());
        // check for empty advertisement
        if ((advertisement.getAuthor() == null || advertisement.getContent() == null)
                & this.createProducer(advertisement.getAuthor()) == null)
            return null;

        try {
            jdbcTemplate.update(Constants.CREATE_ADVERTISEMENT, advertisement.getId().toString(), advertisement.getAuthor().toString(),
                    advertisement.getContent(), advertisement.getTimestamp());
        } catch (Exception e) {
            return null;
        }
        return advertisement.getId();
    }

    @Override
    public int deleteAdvertisementById(UUID advertisementId) {
        return jdbcTemplate.update(Constants.DELETE_ADVERTISEMENT, advertisementId.toString());
    }

    UUID createProducer(UUID producerID) {
        try {
            jdbcTemplate.update(Constants.CREATE_PRODUCER, producerID.toString());
        } catch (Exception e) {
            return null;
        }
        return producerID;
    }
}
