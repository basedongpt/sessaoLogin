CREATE TABLE livro (
    id BIGINT AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(155) NOT NULL,
    ano INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    
    CONSTRAINT pk_livro PRIMARY KEY (id)
);
