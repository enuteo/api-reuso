CREATE TABLE pecas (
    
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR (100) NOT NULL,
    quantidade int NOT NULL DEFAULT 0,
    atributos JSON NOT NULL,

    PRIMARY KEY (id)
);