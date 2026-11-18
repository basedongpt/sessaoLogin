SET @rename_livro_sql = IF(
    (SELECT COUNT(*) FROM information_schema.tables
     WHERE table_schema = DATABASE() AND table_name = 'livro') = 1
    AND
    (SELECT COUNT(*) FROM information_schema.tables
     WHERE table_schema = DATABASE() AND table_name = 'livros') = 0,
    'RENAME TABLE livro TO livros',
    'SELECT 1'
);

PREPARE rename_livro_stmt FROM @rename_livro_sql;
EXECUTE rename_livro_stmt;
DEALLOCATE PREPARE rename_livro_stmt;
