-- ==================================================
-- Sistemas de Banco de Dados - Pós-Graduação Tecnologia Java
-- Caio Mantovani Borba
-- Atividade 02 - Revisão SQL
-- ==================================================

-- ==================================================
-- 2) CRIAÇÃO DO BANCO DE DADOS

CREATE DATABASE empresa;
USE empresa;

CREATE TABLE Departamentos (
    cod_departamento INT AUTO_INCREMENT PRIMARY KEY,
    nome             VARCHAR(100) NOT NULL
);

CREATE TABLE Funcionarios (
    cod_funcionario   INT AUTO_INCREMENT PRIMARY KEY,
    nome              VARCHAR(100) NOT NULL,
    qtd_dependentes   INT NOT NULL DEFAULT 0,
    salario           DECIMAL(10,2) NOT NULL,
    cargo             VARCHAR(50) NOT NULL,
    cod_departamento  INT NOT NULL,
    CONSTRAINT fk_funcionario_departamento
        FOREIGN KEY (cod_departamento) REFERENCES Departamentos(cod_departamento)
);

-- ==================================================
-- 3) POPULAÇÃO DO BANCO DE DADOS

INSERT INTO Departamentos (nome) VALUES
    ('Diretoria Financeira'),   -- 1
    ('Diretoria de TI'),        -- 2
    ('Recursos Humanos'),       -- 3
    ('Vendas'),                 -- 4
    ('Marketing');              -- 5

INSERT INTO Funcionarios (nome, qtd_dependentes, salario, cargo, cod_departamento) VALUES
    -- Diretoria Financeira (cod 1)
    ('Carlos Alberto Souza',   2, 15000.00, 'Gerente',    1),
    ('Fernanda Lima',          0,  6500.00, 'Analista',   1),
    ('João Pedro Alves',       1,  5800.00, 'Assistente', 1),

    -- Diretoria de TI (cod 2)
    ('Mariana Costa',          3, 14500.00, 'Gerente',    2),
    ('Rafael Nogueira',        0,  8200.00, 'Desenvolvedor', 2),
    ('Bruna Martins',          1,  7900.00, 'Desenvolvedor', 2),
    ('Eduardo Ferreira',       0,  7600.00, 'Analista',   2),

    -- Recursos Humanos (cod 3)
    ('Patrícia Gomes',         2, 12500.00, 'Gerente',    3),
    ('Lucas Ribeiro',          0,  4800.00, 'Assistente', 3),

    -- Vendas (cod 4)
    ('Antônio Barbosa',        1, 13000.00, 'Gerente',    4),
    ('Camila Rocha',           0,  5200.00, 'Vendedor',   4),
    ('Diego Santos',           2,  5300.00, 'Vendedor',   4),
    ('Vanessa Pereira',        0,  5100.00, 'Vendedor',   4),

    -- Marketing (cod 5)
    ('Renata Cardoso',         1, 11800.00, 'Gerente',    5),
    ('Thiago Almeida',         0,  6100.00, 'Analista',   5);

-- ==================================================
-- 4) VIEWS

-- a) Departamento com o MAIOR número de funcionários + quantidade
CREATE OR REPLACE VIEW vw_departamento_mais_funcionarios AS
SELECT d.nome AS nome_departamento, COUNT(f.cod_funcionario) AS qtd_funcionarios
FROM Departamentos d
JOIN Funcionarios f ON f.cod_departamento = d.cod_departamento
GROUP BY d.cod_departamento, d.nome
ORDER BY qtd_funcionarios DESC
LIMIT 1;

-- b) Departamento com a MENOR quantidade de funcionários SEM dependentes
CREATE OR REPLACE VIEW vw_departamento_menos_funcionarios_sem_dependentes AS
SELECT d.nome AS nome_departamento, COUNT(f.cod_funcionario) AS qtd_funcionarios_sem_dependentes
FROM Departamentos d
JOIN Funcionarios f ON f.cod_departamento = d.cod_departamento
WHERE f.qtd_dependentes = 0
GROUP BY d.cod_departamento, d.nome
ORDER BY qtd_funcionarios_sem_dependentes ASC
LIMIT 1;

-- c) Departamentos cujo nome começa com "DIR", com seus respectivos funcionários
CREATE OR REPLACE VIEW vw_departamentos_dir AS
SELECT d.nome AS nome_departamento, f.nome AS nome_funcionario
FROM Departamentos d
JOIN Funcionarios f ON f.cod_departamento = d.cod_departamento
WHERE d.nome LIKE 'DIR%';

-- d) Funcionário com o MAIOR salário e seu departamento
CREATE OR REPLACE VIEW vw_funcionario_maior_salario AS
SELECT f.nome AS nome_funcionario, d.nome AS nome_departamento, f.salario
FROM Funcionarios f
JOIN Departamentos d ON d.cod_departamento = f.cod_departamento
ORDER BY f.salario DESC
LIMIT 1;

-- e) Departamento e funcionário de cada departamento com cargo "Gerente"
CREATE OR REPLACE VIEW vw_gerentes_por_departamento AS
SELECT d.nome AS nome_departamento, f.nome AS nome_funcionario
FROM Departamentos d
JOIN Funcionarios f ON f.cod_departamento = d.cod_departamento
WHERE f.cargo = 'Gerente';

-- ==================================================
-- 5) USUÁRIOS

-- a) Usuário "funcionario" - acesso limitado (somente leitura nas views)
CREATE USER 'funcionario'@'localhost' IDENTIFIED BY 'Funcionario#2026';
GRANT SELECT ON empresa.vw_departamento_mais_funcionarios TO 'funcionario'@'localhost';
GRANT SELECT ON empresa.vw_departamento_menos_funcionarios_sem_dependentes TO 'funcionario'@'localhost';
GRANT SELECT ON empresa.vw_departamentos_dir TO 'funcionario'@'localhost';
GRANT SELECT ON empresa.vw_funcionario_maior_salario TO 'funcionario'@'localhost';
GRANT SELECT ON empresa.vw_gerentes_por_departamento TO 'funcionario'@'localhost';

-- b) Usuário "gerente" - acesso completo ao banco
CREATE USER 'gerente'@'localhost' IDENTIFIED BY 'Gerente#2026';
GRANT ALL PRIVILEGES ON empresa.* TO 'gerente'@'localhost';

FLUSH PRIVILEGES;

-- ==================================================
-- Consultas de conferência
-- ==================================================
-- SELECT * FROM vw_departamento_mais_funcionarios;
-- SELECT * FROM vw_departamento_menos_funcionarios_sem_dependentes;
-- SELECT * FROM vw_departamentos_dir;
-- SELECT * FROM vw_funcionario_maior_salario;
-- SELECT * FROM vw_gerentes_por_departamento;
