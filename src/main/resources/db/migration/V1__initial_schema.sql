CREATE TABLE abordagem
(
    id               INT     NOT NULL AUTO_INCREMENT,
    telefone         VARCHAR(20) NULL,
    etapa_abordagem  VARCHAR(20) NULL,
    ativo            BOOLEAN NOT NULL,
    sucesso          BOOLEAN NOT NULL,
    ultima_tentativa datetime NULL,
    criado_em        datetime NULL,
    PRIMARY KEY (ID)
);
