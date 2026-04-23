CREATE TABLE servicos_usuario (

    id_servicos int UNSIGNED NOT NULL,
    is_usuario int UNSIGNED NOT NULL,

    FOREIGN KEY (id_servicos) REFERENCES servico.id,
    FOREIGN KEY (id_usuario) REFERENCES usuario.id
)