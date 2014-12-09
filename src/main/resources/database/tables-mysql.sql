
CREATE TABLE `users` (
    `id`         INT      NOT NULL AUTO_INCREMENT,
    `email`      VARCHAR(100) NOT NULL UNIQUE,
    `nickname`   VARCHAR(30),
    `first_name` VARCHAR(50) NOT NULL,
    `last_name`  VARCHAR(50) NOT NULL,
    `password`   VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE =InnoDB
    CHARACTER SET `UTF8`;

CREATE TABLE `ratings` (
    `restaurant_id` INT NOT NULL,
    `user_id`       INT NOT NULL,
    `value`         INT NOT NULL,
    PRIMARY KEY (`restaurant_id`, `user_id`)
)
    ENGINE =InnoDB
    CHARACTER SET `UTF8`;

CREATE TABLE `comments` (
    `restaurant_id` INT     NOT NULL,
    `user_id`       INT     NOT NULL,
    `text`          TEXT        NOT NULL,
    `timestamp`     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`restaurant_id`, `user_id`)
)
    ENGINE =InnoDB
    CHARACTER SET `UTF8`;

ALTER TABLE `ratings`
    ADD INDEX `FKratings47708` (`user_id`),
    ADD CONSTRAINT `FKratings47708` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
ALTER TABLE `comments`
    ADD INDEX `FKcomments967191` (`user_id`),
    ADD CONSTRAINT `FKcomments967191` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
