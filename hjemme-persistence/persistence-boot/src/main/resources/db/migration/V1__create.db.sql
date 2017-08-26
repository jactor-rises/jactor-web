CREATE TABLE T_ADDRESS (
    ID             BIGINT GENERATED BY DEFAULT AS IDENTITY,
    CREATION_TIME  TIMESTAMP,
    CREATED_BY     VARCHAR(50),
    UPDATED_TIME   TIMESTAMP,
    UPDATED_BY     VARCHAR(50),
    ADDRESS_LINE_1 VARCHAR(50) NOT NULL,
    ADDRESS_LINE_2 VARCHAR(50),
    ADDRESS_LINE_3 VARCHAR(50),
    CITY           VARCHAR(50) NOT NULL,
    COUNTRY        VARCHAR(7),
    ZIP_CODE       INTEGER NOT NULL
);

CREATE TABLE T_BLOG (
    ID            BIGINT GENERATED BY DEFAULT AS IDENTITY,
    CREATION_TIME TIMESTAMP,
    CREATED_BY    VARCHAR(50),
    UPDATED_TIME  TIMESTAMP,
    UPDATED_BY    VARCHAR(50),
    CREATED       VARCHAR(10) NOT NULL,
    TITLE         VARCHAR(50) NOT NULL,
    USER_ID       BIGINT NOT NULL
);

CREATE TABLE T_BLOG_ENTRY (
    ID            BIGINT GENERATED BY DEFAULT AS IDENTITY,
    CREATION_TIME TIMESTAMP,
    CREATED_BY    VARCHAR(50),
    UPDATED_TIME  TIMESTAMP,
    UPDATED_BY    VARCHAR(50),
    CREATED_TIME  TIMESTAMP NOT NULL,
    CREATOR_NAME  VARCHAR(50) NOT NULL,
    ENTRY         VARCHAR(2500) NOT NULL,
    BLOG_ID       BIGINT NOT NULL
);

CREATE TABLE T_GUEST_BOOK (
    ID            BIGINT GENERATED BY DEFAULT AS IDENTITY,
    CREATION_TIME TIMESTAMP,
    CREATED_BY    VARCHAR(50),
    UPDATED_TIME  TIMESTAMP,
    UPDATED_BY    VARCHAR(50),
    TITLE         VARCHAR(50) NOT NULL,
    USER_ID       BIGINT NOT NULL
);

CREATE TABLE T_GUEST_BOOK_ENTRY (
    ID            BIGINT GENERATED BY DEFAULT AS IDENTITY,
    CREATION_TIME TIMESTAMP,
    CREATED_BY    VARCHAR(50),
    UPDATED_TIME  TIMESTAMP,
    UPDATED_BY    VARCHAR(50),
    CREATED_TIME  TIMESTAMP NOT NULL,
    GUEST_NAME    VARCHAR(50) NOT NULL,
    ENTRY         VARCHAR(2500) NOT NULL,
    GUEST_BOOK_ID BIGINT NOT NULL
);

CREATE TABLE T_PERSON (
    ID            BIGINT GENERATED BY DEFAULT AS IDENTITY,
    CREATION_TIME TIMESTAMP,
    CREATED_BY    VARCHAR(50),
    UPDATED_TIME  TIMESTAMP,
    UPDATED_BY    VARCHAR(50),
    DESCRIPTION   VARCHAR(250),
    USER_ID       BIGINT,
    ADDRESS_ID    BIGINT
);

CREATE TABLE T_USER (
    ID            BIGINT GENERATED BY DEFAULT AS IDENTITY,
    CREATION_TIME TIMESTAMP,
    CREATED_BY    VARCHAR(50),
    UPDATED_TIME  TIMESTAMP,
    UPDATED_BY    VARCHAR(50),
    USER_NAME     VARCHAR(50) NOT NULL,
    PASSWORD      VARCHAR(250) NOT NULL,
    EMAIL         VARCHAR(50),
    PERSON_ID     BIGINT,
    EMAIL_AS_NAME BOOLEAN NOT NULL
);
