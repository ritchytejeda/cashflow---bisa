# cashflow---bisa
Evaluación Tecnica - Manejo de flujo
La aplicación está desarrollada bajo el manejo de API REST desarrollada en tecnologia JAVA utilizando el framework SPRING BOOT.

Se tiene las siguientes request:
1) Crear cuenta
localhost:8082/addAccount

{
"identificationDocument": 3397167,
"firstName": "RICHARD",
"lastName": "TEJEDA",
"address": "CALLE GERONIMO DE SORIA 1344",
"initialBalance": 80,
"currency": 1,
} 

2)Deposito de dinero
localhost:8082/depositMoney/{accountCode}/{amount}/{currency}

3)Retiro de dinero
localhost:8082/withdrawMoney/{accountCode}/{amount}/{currency}

4)Consultar Saldo
localhost:8082/checkBalance/{accountCode}
