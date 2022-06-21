-- password in plaintext: "password"
INSERT INTO USERS (password, email, username, name, last_name, active)
VALUES
  ('$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'user@mail.com', 'user', 'Name', 'Surname',
   1);
-- password in plaintext: "password"
INSERT INTO USERS (password, email, username, name, last_name, active)
VALUES
  ( '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'johndoe@gmail.com', 'johndoe', 'John', 'Doe', 1);
-- password in plaintext: "password"
INSERT INTO USERS (password, email, username, name, last_name, active)
VALUES ('$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'name@gmail.com', 'namesurname', 'Name',
        'Surname', 1);

INSERT INTO ROLE (role)
VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (role)
VALUES ('ROLE_USER');


INSERT INTO PRODUCT (name, description, quantity, price)
VALUES ('Cpmputer', '4GB RAM, 1 TB disc space, in very good condition', 1, 15000.75);
