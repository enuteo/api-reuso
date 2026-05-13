CREATE TABLE servicos (
    
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR (255) NOT NULL,
    precos FLOAT NOT NULL,
    descricao TEXT,

    PRIMARY Key (id)
);