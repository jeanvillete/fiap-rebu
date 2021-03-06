CREATE TABLE USER_DETAILS (
    id INTEGER NOT NULL AUTO_INCREMENT,
    nickname VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE LOCATION (
    id INTEGER NOT NULL AUTO_INCREMENT,
    address VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE VEHICLE (
    id INTEGER NOT NULL AUTO_INCREMENT,
    plate VARCHAR(7) NOT NULL,
    location_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (location_id) REFERENCES LOCATION(id)
);

CREATE TABLE REPAIR (
    id INTEGER NOT NULL AUTO_INCREMENT,
    open_date_time TIMESTAMP NOT NULL,
    close_date_time TIMESTAMP,
    vehicle_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (vehicle_id) REFERENCES VEHICLE(id)
);

CREATE TABLE TRIP (
    id INTEGER NOT NULL AUTO_INCREMENT,
    uuid VARCHAR(15) NOT NULL,
    user_details_id INTEGER NOT NULL,
    from_location_id INTEGER NOT NULL,
    to_location_id INTEGER NOT NULL,
    request_date_time TIMESTAMP NOT NULL,
    boarding_date_time TIMESTAMP,
    landing_date_time TIMESTAMP,
    vehicle_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_details_id) REFERENCES USER_DETAILS(id),
    FOREIGN KEY (from_location_id) REFERENCES LOCATION(id),
    FOREIGN KEY (to_location_id) REFERENCES LOCATION(id),
    FOREIGN KEY (vehicle_id) REFERENCES VEHICLE(id)
);