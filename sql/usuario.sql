CREATE TABLE usuario (
    
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR (255) NOT NULL,
    id_cliente int UNSIGNED NOT NULL,
    master int (1) NOT NULL DEFAULT 0,

    PRIMARY KEY (id),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
)
