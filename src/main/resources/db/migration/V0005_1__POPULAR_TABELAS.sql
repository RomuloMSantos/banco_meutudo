INSERT INTO SIMULADOR.BANCO (CODIGO, DESCRICAO) VALUES ('077', 'BANCO INTER');
INSERT INTO SIMULADOR.BANCO (CODIGO, DESCRICAO) VALUES ('260', 'NUBANK');
INSERT INTO SIMULADOR.BANCO (CODIGO, DESCRICAO) VALUES ('237', 'BRADESCO');

INSERT INTO SIMULADOR.CONTA (AGENCIA, NUMERO, DIGITO, BANCO_FK) VALUES ('0001', '123456', '1', (SELECT ID FROM SIMULADOR.BANCO WHERE CODIGO LIKE '077'));
INSERT INTO SIMULADOR.MOVIMENTACAO (TIPO, VALOR, CONTA_FK) VALUES (1, 5000, (SELECT ID FROM SIMULADOR.CONTA ORDER BY ID DESC LIMIT 1));

INSERT INTO SIMULADOR.CONTA (AGENCIA, NUMERO, DIGITO, BANCO_FK) VALUES ('1234', '1234567', '2', (SELECT ID FROM SIMULADOR.BANCO WHERE CODIGO LIKE '237'));
INSERT INTO SIMULADOR.MOVIMENTACAO (TIPO, VALOR, CONTA_FK) VALUES (1, 10000, (SELECT ID FROM SIMULADOR.CONTA ORDER BY ID DESC LIMIT 1));

INSERT INTO SIMULADOR.CONTA (AGENCIA, NUMERO, DIGITO, BANCO_FK) VALUES ('365', '12345', '1', (SELECT ID FROM SIMULADOR.BANCO WHERE CODIGO LIKE '260'));
INSERT INTO SIMULADOR.MOVIMENTACAO (TIPO, VALOR, CONTA_FK) VALUES (1, 20000, (SELECT ID FROM SIMULADOR.CONTA ORDER BY ID DESC LIMIT 1));

INSERT INTO SIMULADOR.CONTA (AGENCIA, NUMERO, DIGITO, BANCO_FK) VALUES ('0001', '12345678', '9', (SELECT ID FROM SIMULADOR.BANCO WHERE CODIGO LIKE '077'));
INSERT INTO SIMULADOR.MOVIMENTACAO (TIPO, VALOR, CONTA_FK) VALUES (1, 30000, (SELECT ID FROM SIMULADOR.CONTA ORDER BY ID DESC LIMIT 1));