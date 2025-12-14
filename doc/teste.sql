INSERT INTO public.tb_user(
	date_birth, id, id_user_content_fk, first_name, gender, last_name, login, password, status)
	VALUES ('1-1-2000', nextval('tb_user_seq'), null, 'Teste', 'MALE', '', 'admin@' || nextval('tb_user_seq'), '$2a$10$XhulzyO5y3pHJFXRhES6U.ObZr/eQrKZSDsZ8uN7tvBebAHGy0i0e', 'ATIVO');