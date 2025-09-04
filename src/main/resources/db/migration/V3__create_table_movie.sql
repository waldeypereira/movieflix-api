CREATE TABLE movie (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       release_date DATE,
                       rating NUMERIC,
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP
);

CREATE TABLE movie_category (
                                movie_id BIGINT,
                                category_id BIGINT,
                                CONSTRAINT fk_movie_category_movie FOREIGN KEY(movie_id) REFERENCES movie(id),
                                CONSTRAINT fk_movie_category_category FOREIGN KEY(category_id) REFERENCES category(id)
);

-- Tabela de relacionamento para serviços de streaming de filmes.
-- A coluna movie_id agora é BIGINT para compatibilidade com movie.id
CREATE TABLE movie_streaming (
                                 movie_id BIGINT,
                                 streaming_id BIGINT,
                                 CONSTRAINT fk_movie_streaming_movie FOREIGN KEY(movie_id) REFERENCES movie(id),
                                 CONSTRAINT fk_movie_streaming_streaming FOREIGN KEY(streaming_id) REFERENCES streaming(id)
);