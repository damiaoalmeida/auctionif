ALTER TABLE tb_user ADD CONSTRAINT user_email_unique UNIQUE (login);

INSERT INTO public.tb_user_content(id, photo) VALUES (1, null);
INSERT INTO public.tb_role(id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.tb_role(id, role) VALUES (2, 'ROLE_MANAGER');
INSERT INTO public.tb_role(id, role) VALUES (3, 'ROLE_USER');

INSERT INTO public.tb_user(
	date_birth, id, id_user_content_fk, first_name, gender, last_name, login, password)
	VALUES ('1-1-2000', 1, 1, 'ROLE_ADMIN', 'MALE', '', 'admin@admin', '$2a$10$XhulzyO5y3pHJFXRhES6U.ObZr/eQrKZSDsZ8uN7tvBebAHGy0i0e');

INSERT INTO public.tb_user_role(user_id, role_id) VALUES (1, 1);

SELECT nextval('tb_user_seq');

