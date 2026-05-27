CREATE DATABASE oficina;
USE oficina;

CREATE TABLE peca (
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR (255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE servico (
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    descricao VARCHAR (255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE servico_cliente (
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    cliente_id int UNSIGNED NOT NULL,
    servico_id int UNSIGNED NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (servico_id) REFERENCES servico(id)
);

CREATE TABLE acesso_funcionario (
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    funcionario_id int UNSIGNED NOT NULL,
    servico_id int UNSIGNED NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
    FOREIGN KEY (servico_id) REFERENCES servico(id)
);

CREATE TABLE usuario (
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR (255) NOT NULL,
    senha VARCHAR (255) NOT NULL,
    classe int(1) unsigned NOT NULL DEFAULT 0 COMMENT '0 para funcionario, 1 para cliente',

    PRIMARY KEY (id)
);

CREATE TABLE funcionario (
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR (255) NOT NULL,
    cargo VARCHAR (255) NOT NULL,
    id_usuario int UNSIGNED NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE cliente (
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR (255) NOT NULL,
    email VARCHAR (255) NOT NULL,
    id_usuario int UNSIGNED NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE estoque(
    id int UNSIGNED NOT NULL AUTO_INCREMENT,
    id_peca int UNSIGNED NOT NULL,
    quantidade int UNSIGNED NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (peca_id) REFERENCES peca(id)
);