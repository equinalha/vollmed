-- Conteúdo de V6__create-table-consultas.sql

CREATE TABLE consultas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL, -- Supondo que você também tenha uma FK para médicos
    data DATETIME NOT NULL,
    
    PRIMARY KEY(id),
    CONSTRAINT fk_consultas_paciente_id FOREIGN KEY (paciente_id) REFERENCES paciente(id),
    CONSTRAINT fk_consultas_medico_id FOREIGN KEY (medico_id) REFERENCES medico(id)
);