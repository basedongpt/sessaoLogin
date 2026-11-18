CREATE TABLE sugestao (
    id BIGINT AUTO_INCREMENT,
    nome_cliente VARCHAR(255) NOT NULL,
    livro_sugerido VARCHAR(255) NOT NULL,
    
    CONSTRAINT pk_sugestao PRIMARY KEY (id)
);
