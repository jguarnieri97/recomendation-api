-- Creación de Esquemas --
CREATE SCHEMA RECOM;

CREATE TABLE RECOM.RECOMMENDATIONS
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    applicant_id BIGINT      NOT NULL,
    probability  FLOAT       NOT NULL,
    tag          VARCHAR(30) NOT NULL,
    created_at   DATE        NOT NULL
);