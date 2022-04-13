INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('5 element', '15% discount', 30, 100, now(3), now(3)),
       ('european countries tours', 'provides 17% discount for any 1 chosen tour', 72, 200, now(3), now(3));

INSERT INTO tags (name) VALUES ('road_trip'), ('household'), ('travelling'), ('europe_tour');

INSERT INTO certificate_purchase (id_certificate, id_tag) VALUES (1, 2), (2, 1), (2, 4);