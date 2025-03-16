-- Удаление схемы, если она существует
DROP SCHEMA IF EXISTS advertisements CASCADE;

-- Создание схемы
CREATE SCHEMA IF NOT EXISTS advertisements;

-- Удаление таблицы producers, если она существует
DROP TABLE IF EXISTS advertisements.producers;

-- Создание таблицы producers
CREATE TABLE IF NOT EXISTS advertisements.producers (
    id BYTEA NOT NULL,
    comment VARCHAR(128),
    PRIMARY KEY (id)
    );

-- Удаление таблицы advertisements, если она существует
DROP TABLE IF EXISTS advertisements.advertisements;

-- Создание таблицы advertisements
CREATE TABLE IF NOT EXISTS advertisements.advertisements (
    id BYTEA NOT NULL,
    content VARCHAR(140),
    created INT NOT NULL,
    producer_id BYTEA NOT NULL,
    PRIMARY KEY (id, producer_id),
    CONSTRAINT fk_advertisements_producers1
    FOREIGN KEY (producer_id)
    REFERENCES advertisements.producers (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    );

-- Создание индекса для таблицы advertisements
CREATE INDEX fk_advertisements_producers1_idx ON advertisements.advertisements (producer_id);

-- Удаление таблицы subscribers, если она существует
DROP TABLE IF EXISTS advertisements.subscribers;

-- Создание таблицы subscribers
CREATE TABLE IF NOT EXISTS advertisements.subscribers (
    id BYTEA NOT NULL,
    comment VARCHAR(128),
    PRIMARY KEY (id)
    );

-- Удаление таблицы subscriptions, если она существует
DROP TABLE IF EXISTS advertisements.subscriptions;

-- Создание таблицы subscriptions
CREATE TABLE IF NOT EXISTS advertisements.subscriptions (
    producers_id BYTEA NOT NULL,
    subscribers_id BYTEA NOT NULL,
    PRIMARY KEY (producers_id, subscribers_id),
    CONSTRAINT fk_producers_has_subscribers_producers
    FOREIGN KEY (producers_id)
    REFERENCES advertisements.producers (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT fk_producers_has_subscribers_subscribers1
    FOREIGN KEY (subscribers_id)
    REFERENCES advertisements.subscribers (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    );

-- Создание индексов для таблицы subscriptions
CREATE INDEX fk_producers_has_subscribers_subscribers1_idx ON advertisements.subscriptions (subscribers_id);
CREATE INDEX fk_producers_has_subscribers_producers_idx ON advertisements.subscriptions (producers_id);

-- Вставка данных в таблицу subscribers
INSERT INTO advertisements.subscribers (id, comment) VALUES
('\x70A64B5443C34C18BBEC64590FF7E0CC', 'Россия'),
('\xABB04B9F5D1040DD9076EB27CA76891A', 'Россия');

-- Вставка данных в таблицу subscriptions
INSERT INTO advertisements.subscriptions (producers_id, subscribers_id) VALUES
('\x0DD03A597DBC4D0081073271B3345434', '\xABB04B9F5D1040DD9076EB27CA76891A'),
('\x1CD89E11602A4186AFBFE0149B59EB08', '\x70A64B5443C34C18BBEC64590FF7E0CC'),
('\x6E27EA06A7164C89AF88813749A8BD48', '\xABB04B9F5D1040DD9076EB27CA76891A');

-- Вставка данных в таблицу advertisements
INSERT INTO advertisements.advertisements (id, content, created, producer_id) VALUES
('\x212AC66C3E39464A830D33FACDB099AB', 'Книги в хорощем состоянии. Торг. ', 1605579881, '\x0DD03A597DBC4D0081073271B3345434'),
('\x462894B2B5A14088B7AF701C71D6D304', 'Окно белое. Самовывоз. 6000 рублей.', 1605194769, '\x6E27EA06A7164C89AF88813749A8BD48'),
('\xB7A1C8D5C04E4FE38F986392BB751AD9', 'Арбузы. Оптовая цена. 20 рублей за килограмм', 1605194747, '\x6E27EA06A7164C89AF88813749A8BD48'),
('\xDF7E3D8FE19A4458BAD64BE7585200F5', 'Репетитор по физике. 600 рублей', 1605197637, '\x1CD89E11602A4186AFBFE0149B59EB08'),
('\xE5C6F13A76D648B681F020B74FA9F04C', 'Рюкзак коричневый. Цена 1000 рублей', 1605194709, '\x6E27EA06A7164C89AF88813749A8BD48'),
('\xFCCC455BFF284DFD8153F07C0F869118', 'Раковина от застройщика. Бесплатно. Самовывоз.', 1605195323, '\x1CD89E11602A4186AFBFE0149B59EB08');



