package org.urfu.adservice.dtos;

public class Constants {
        private Constants() {
                throw new IllegalStateException("Constants only class");
        }

        // HTTP Section
        public static final String CODE = "code";
        public static final String ADVERTISEMENT = "advertisement";
        public static final String DATA = "data";
        public static final String URI_ADVERTISEMENT= "/advertisements/advertisement";
        public static final String URI_ADVERTISEMENTS = "/advertisements";
        public static final String URI_PRODUCER = "/advertisements/producer";
        public static final String URI_SUBSCRIBER = "/advertisements/subscriber";
        public static final String URI_SUBSCRIPTION = "/subscriptions/subscriber";
        public static final String URI_SUBSCRIPTIONS = "/subscriptions";

        // HEADERS Section
        public static final String APPLICATION_JSON = "application/json";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String ACCEPT = "Accept";

        // Database Section
        public static final String DB = "`advertisements`";
        public static final String TABLE_ADVERTISEMENTS = "`advertisements`";
        public static final String TABLE_PRODUCERS = "`producers`";
        public static final String TABLE_SUBSCRIBERS = "`subscribers`";
        public static final String TABLE_SUBSCRIPTIONS = "`subscriptions`";

        public static final String GET_ADVERTISEMENT_BY_ID = "SELECT * FROM " + TABLE_ADVERTISEMENTS + " WHERE id = UUID_TO_BIN(?)";
        public static final String GET_ADVERTISEMENTS_FOR_PRODUCER = "SELECT * FROM " + TABLE_ADVERTISEMENTS
                        + " WHERE producer_id = UUID_TO_BIN(?)";
        public static final String GET_ADVERTISEMENTS_FOR_SUBSCRIBER = "SELECT advertisements.id as id, advertisements.producer_id as producer_id, "
                        + "advertisements.content as content, advertisements.created as created "
                        + "FROM advertisements LEFT JOIN producers ON advertisements.producer_id "
                        + "= producers.producer_id LEFT JOIN subscriptions ON producers.producer_id = subscriptions.producer_id "
                        + "LEFT JOIN subscribers ON subscriptions.subscriber_id = subscribers.subscriber_id "
                        + "WHERE subscribers.subscriber_id = UUID_TO_BIN(?);";
        public static final String CREATE_PRODUCER = "INSERT IGNORE INTO " + TABLE_PRODUCERS
                        + " (`producer_id`) VALUES(UUID_TO_BIN(?));";
        public static final String CREATE_SUBSCRIBER = "INSERT IGNORE INTO " + TABLE_SUBSCRIBERS
                        + " (`subscriber_id`) VALUES(UUID_TO_BIN(?));";
        public static final String CREATE_ADVERTISEMENT = "INSERT INTO " + TABLE_ADVERTISEMENTS
                        + " (`id`, `producer_id`, `content`, `created`) "
                        + "VALUES(UUID_TO_BIN(?), UUID_TO_BIN(?), ?, ?);";
        public static final String CREATE_SUBSCRIPTION = "INSERT IGNORE INTO " + TABLE_SUBSCRIPTIONS
                        + " (`subscriber_id`,`producer_id`) VALUES(UUID_TO_BIN(?),UUID_TO_BIN(?));";
        public static final String DELETE_ADVERTISEMENT = "DELETE FROM " + TABLE_ADVERTISEMENTS + " WHERE `id`=UUID_TO_BIN(?);";
        public static final String DELETE_SUBSCRIPTION = "DELETE FROM " + TABLE_SUBSCRIPTIONS
                        + " WHERE `subscriber_id`=UUID_TO_BIN(?);";
        public static final String GET_SUBSCRIPTION = "SELECT * FROM " + TABLE_SUBSCRIPTIONS
                        + " WHERE subscriber_id=UUID_TO_BIN(?);";
}
