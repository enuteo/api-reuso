CREATE TABLE servicos_cliente(

    id_servicos int UNSIGNED NOT NULL,
    id_cliente int UNSIGNED NOT NULL,

    FOREIGN KEY (id_servicos) REREFENCES servicos.id
    FOREIGN KEY (id_cliente) REREFENCES cliente.id,
)
