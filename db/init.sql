CREATE TABLE deadlift.log (
    id          SERIAL     PRIMARY KEY,
    datetime    DATETIME   NOT     NULL,
    data_json   JSON       NOT     NULL
);
