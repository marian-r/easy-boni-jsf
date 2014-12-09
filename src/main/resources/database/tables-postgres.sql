
CREATE TABLE "comments" (
    "restaurant_id" INT4      NOT NULL,
    "user_id"       INT4      NOT NULL,
    "text"          TEXT      NOT NULL,
    "timestamp"     TIMESTAMP NOT NULL,
    PRIMARY KEY ("restaurant_id",
                 "user_id")
);

CREATE TABLE "ratings" (
    "restaurant_id" INT4 NOT NULL,
    "user_id"       INT4 NOT NULL,
    "value"         INT4 NOT NULL,
    PRIMARY KEY ("restaurant_id",
                 "user_id")
);

CREATE TABLE "users" (
    "id"         SERIAL       NOT NULL,
    "email"      VARCHAR(255) NOT NULL UNIQUE,
    "nickname"   VARCHAR(255),
    "first_name" VARCHAR(255) NOT NULL,
    "last_name"  VARCHAR(255) NOT NULL,
    "password"   VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id")
);

ALTER TABLE "comments" ADD CONSTRAINT "FKcomments967191" FOREIGN KEY ("user_id") REFERENCES "users" ("id");
ALTER TABLE "ratings" ADD CONSTRAINT "FKratings47708" FOREIGN KEY ("user_id") REFERENCES "users" ("id");
