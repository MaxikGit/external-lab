INSERT INTO Gift_certificate
values (DEFAULT, '100$', 'anything gift card', 131.5, 8, current_timestamp, current_timestamp),
       (DEFAULT, '200$', 'only for toys shops card', 255.3, 11, current_timestamp, current_timestamp),
       (DEFAULT, '200$', 'anything gift card', 255.3, 12, current_timestamp, current_timestamp),
       (DEFAULT, '500$', 'Nike shop only card', 535.25, 12, current_timestamp, current_timestamp),
       (DEFAULT, '500$', 'car shops only card', 535.25, 12, current_timestamp, current_timestamp);

INSERT INTO Tag
values (DEFAULT, 'anything'),
       (DEFAULT, 'auto-moto'),
       (DEFAULT, 'sports'),
       (DEFAULT, 'toys');


INSERT INTO gift_certificate_tag
values (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (2, 4),
       (5, 2),
       (4, 3);