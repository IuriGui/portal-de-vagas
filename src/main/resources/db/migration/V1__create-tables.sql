CREATE TABLE usuario(
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        email VARCHAR(100) NOT NULL UNIQUE,
                        senha VARCHAR(60) NOT NULL
);


CREATE TABLE endereco(
                         id SERIAL PRIMARY KEY,
                         cep VARCHAR(9) NOT NULL,
                         uf char(2) NOT NULL,
                         cidade VARCHAR(100) NOT NULL,
                         bairro VARCHAR(100) NOT NULL,
                         rua VARCHAR(100) NOT NULL,
                         numero VARCHAR(10) NOT NULL,
                         complemento VARCHAR(100),
                         latitude DECIMAL(9,6),
                         longitude DECIMAL(9,6)
);

CREATE TABLE instituicao(
                            id SERIAL PRIMARY KEY ,
                            nome_fantasia VARCHAR(100) NOT NULL,
                            descricao TEXT,
                            telefone VARCHAR(15),
                            endereco_id INT REFERENCES endereco(id),
                            usuario_id UUID REFERENCES usuario(id)
);


CREATE TABLE area_atuacao(
                             id SERIAL PRIMARY KEY ,
                             nome VARCHAR(100) NOT NULL
);

CREATE TABLE oportunidade(
                             id SERIAL PRIMARY KEY ,
                             instituicao_id INT REFERENCES instituicao(id) NOT NULL,
                             endereco_id INT REFERENCES endereco(id),
                             area_atuacao_id INT REFERENCES area_atuacao(id) NOT NULL,
                             titulo VARCHAR(100) NOT NULL,
                             descricao TEXT NOT NULL,
                             data_publicacao TIMESTAMP NOT NULL,
                             data_validade TIMESTAMP NOT NULL,
                             remoto BOOLEAN NOT NULL,
                             carga_horaria INT NOT NULL,
                             remuneracao NUMERIC(12,2),
                             beneficios TEXT,
                             requisitos TEXT
);








CREATE TABLE candidato(
                          id SERIAL PRIMARY KEY ,
                          nome VARCHAR(100) NOT NULL,
                          telefone VARCHAR(15),
                          endereco_id INT REFERENCES endereco(id),
                          usuario_id UUID REFERENCES usuario(id),
                          data_nascimento DATE NOT NULL,
                          curriculo_url VARCHAR(255)
);

CREATE TABLE experiencia_profissional(
                                         id SERIAL PRIMARY KEY ,
                                         candidato_id INT REFERENCES candidato(id) NOT NULL,
                                         cargo VARCHAR(100) NOT NULL,
                                         empresa VARCHAR(100) NOT NULL,
                                         data_inicio DATE NOT NULL,
                                         data_fim DATE
);

CREATE TABLE formacao_academica(
                                   id SERIAL PRIMARY KEY ,
                                   candidato_id INT REFERENCES candidato(id) NOT NULL,
                                   instituicao VARCHAR(100) NOT NULL,
                                   curso VARCHAR(100) NOT NULL,
                                   data_inicio DATE NOT NULL,
                                   data_conclusao DATE
);

CREATE TABLE habilidade(
                           id SERIAL PRIMARY KEY ,
                           nome VARCHAR(100) NOT NULL
);

CREATE TABLE candidato_habilidade(
                                     candidato_id INT REFERENCES candidato(id),
                                     habilidade_id INT REFERENCES habilidade(id),
                                     PRIMARY KEY (candidato_id, habilidade_id)
);

CREATE TABLE inscricao(
                          id SERIAL PRIMARY KEY ,
                          candidato_id INT REFERENCES candidato(id) NOT NULL,
                          oportunidade_id INT REFERENCES oportunidade(id) NOT NULL,
                          data_inscricao TIMESTAMP NOT NULL,
                          UNIQUE(candidato_id, oportunidade_id),
                          status VARCHAR(50) NOT NULL
)

