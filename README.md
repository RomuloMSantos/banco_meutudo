# banco_meutudo

Aplicação simulando operações bancarias para teste no processo seletivo da empresa MeuTudo.

### Bibliotecas
- _Java 15_
- _Maven Project_
- _Spring Boot_ 
- _Lombok_ 
- _FlyWay_ 
- _H2Database_ 
- _junit_ 

### Como iniciar
Ao executar aplicação com o Main localizado na classe src/main/java/br/com/banco_meutudo/BancoMeutudoApplication será 
iniciado também o Swagger e criado a base de dados H2. <br/>
Para acessar o Swagger usar o link http://localhost:8080/swagger-ui/ <br/>
Para acessar a base de dados usar o link http://localhost:8080/h2-console/ <br/>

### Registros pre-cadastrados para teste
BANCO {id = 1, codigo = '077', descricao = 'BANCO INTER'}<br/>
BANCO {id = 2, codigo = '260', descricao = 'NUBANK'}<br/>
BANCO {id = 3, codigo = '237', descricao = 'BRADESCO'}<br/>
CONTA {id = 1, banco = 1, agencia = '0001', numero = '123456', digito = '1'} com saldo R$5000,00<br/>
CONTA {id = 2, banco = 3, agencia = '1234', numero = '1234567', digito = '2'} com saldo R$10000,00<br/>
CONTA {id = 3, banco = 2, agencia = '365', numero = '12345', digito = '1'} com saldo R$20000,00<br/>
CONTA {id = 4, banco = 1, agencia = '0001', numero = '12345678', digito = '9'} com saldo R$30000,00<br/>

### License

Copyright © 2021, [Romulo Santos](https://github.com/romulomsantos).