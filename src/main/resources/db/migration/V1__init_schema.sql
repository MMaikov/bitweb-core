DROP TABLE IF EXISTS upload_metadata;
CREATE TABLE upload_metadata (
                                 id UUID PRIMARY KEY,
                                 filename varchar(256) NOT NULL,
                                 status varchar(20) NOT NULL,
                                 result text
);
