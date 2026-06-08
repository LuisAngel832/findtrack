SET client_encoding = 'UTF8';

INSERT INTO categories (name, type, user_id, is_default, icon) VALUES
	('Alimentación', 'expense', NULL, TRUE, NULL),
	('Transporte', 'expense', NULL, TRUE, NULL),
	('Salud', 'expense', NULL, TRUE, NULL),
	('Entretenimiento', 'expense', NULL, TRUE, NULL),
	('Ropa', 'expense', NULL, TRUE, NULL),
	('Servicios', 'expense', NULL, TRUE, NULL),
	('Educación', 'expense', NULL, TRUE, NULL),
	('Otros', 'expense', NULL, TRUE, NULL),
	('Salario', 'income', NULL, TRUE, NULL),
	('Freelance', 'income', NULL, TRUE, NULL),
	('Inversiones', 'income', NULL, TRUE, NULL),
	('Regalo', 'income', NULL, TRUE, NULL),
	('Otros ingresos', 'income', NULL, TRUE, NULL);
