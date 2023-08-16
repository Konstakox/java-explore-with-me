CREATE TABLE IF NOT EXISTS users
(
    user_id   INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_name VARCHAR(250)                         NOT NULL,
    email     VARCHAR(254)                         NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id),
    CONSTRAINT uq_user_email UNIQUE (email)
);
CREATE TABLE IF NOT EXISTS categories
(
    category_id   INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    category_name VARCHAR(50)                          NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (category_id),
    CONSTRAINT uq_category_name UNIQUE (category_name)
);
CREATE TABLE IF NOT EXISTS locations
(
    location_id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat         FLOAT                                NOT NULL,
    lon         FLOAT                                NOT NULL,
    CONSTRAINT pk_locations PRIMARY KEY (location_id)
);
CREATE TABLE IF NOT EXISTS events
(
    event_id           INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000)                        NOT NULL,
    category_id        INT                                  NOT NULL,
    confirmed_requests INT                                  NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE          NOT NULL,
    description        VARCHAR(7000)                        NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE          NOT NULL,
    initiator_id       INT                                  NOT NULL,
    location_id        INT                                  NOT NULL,
    paid               BOOLEAN                              NOT NULL,
    participant_limit  INT                                  NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN                              NOT NULL,
    state              VARCHAR                              NOT NULL,
    title              VARCHAR(120)                         NOT NULL,
    views              INT                                  NOT NULL,
    CONSTRAINT pk_events PRIMARY KEY (event_id),
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE CASCADE,
    CONSTRAINT fk_initiator_id FOREIGN KEY (initiator_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_location_id FOREIGN KEY (location_id) REFERENCES locations (location_id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS requests
(
    requests_id  INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE          NOT NULL,
    event_id     INT                                  NOT NULL,
    requester_id INT                                  NOT NULL,
    status       VARCHAR                              NOT NULL,
    CONSTRAINT pk_requests PRIMARY KEY (requests_id),
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE,
    CONSTRAINT fk_requester_id FOREIGN KEY (requester_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT uq_event_id UNIQUE (event_id),
    CONSTRAINT uq_requester_id UNIQUE (requester_id)
);
CREATE TABLE IF NOT EXISTS compilations
(
    compilation_id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned         BOOLEAN                              NOT NULL,
    title          VARCHAR(50)                          NOT NULL,
    CONSTRAINT pk_compilations PRIMARY KEY (compilation_id),
    CONSTRAINT uq_title UNIQUE (title)
);
CREATE TABLE IF NOT EXISTS compilation_events
(
    compilation_id INT NOT NULL,
    event_id       INT NOT NULL,
    CONSTRAINT pk_compilation_events PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT fk_compilation_id FOREIGN KEY (compilation_id) REFERENCES compilations (compilation_id) ON DELETE CASCADE,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS comments
(
    comment_id INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text       VARCHAR(7000)                        NOT NULL,
    author_id  INT                                  NOT NULL,
    event_id   INT                                  NOT NULL,
    created    TIMESTAMP WITHOUT TIME ZONE          NOT NULL,
    updated    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_comment_id PRIMARY KEY (comment_id),
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE
);
