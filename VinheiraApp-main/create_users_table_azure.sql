-- ============================================================
-- Script: Criação da tabela USERS no Azure SQL Database
-- Compatível com: SQL Server / Azure SQL
-- ============================================================

-- Verifica se a tabela já existe antes de criar
IF NOT EXISTS (
    SELECT * FROM INFORMATION_SCHEMA.TABLES
    WHERE TABLE_NAME = 'USERS'
)
BEGIN
    CREATE TABLE USERS (
        id       INT IDENTITY(1,1) PRIMARY KEY,   -- auto-increment, substitui o Oracle USER_SEQ
        name     NVARCHAR(150)  NOT NULL,
        email    NVARCHAR(255)  NOT NULL UNIQUE,
        password NVARCHAR(255)  NOT NULL,
        created_at DATETIME2 DEFAULT GETDATE()
    );

    PRINT 'Tabela USERS criada com sucesso.';
END
ELSE
BEGIN
    PRINT 'Tabela USERS já existe. Nenhuma alteração foi feita.';
END
