# Banking App

Baking App is a backend service built by spring boot and java programming language with H2 database, the content of this service is one api, and by this api you can send all kinds of transaction that you will see below.
The main parameter in all operations is "operation" and by this parameter the service can know what the implementation that shall work on.

#### URL
```
http://localhost:8080/baraka/swagger-ui.html#
```

#### TDD
We have 78 test cases as integration test, and you can run it by:
```
mvn clean install
```

#### Technology
```
Programming language: Java
Frameworks: Spring boot, Spring Data
Libraries: lombok, jackson, flyway, commons
Database: H2
Documentation: Swagger
```

#### General Information
```
Date Format: YYYY-MM-DD
```

#### Banking Operation
The operations that service provided below:
```
create_account
delete_account 
money_deposit
money_withdrawal
money_local_transfer
money_international_transfer
get_balance
list_accounts
```

##### Validation

I covered all validation cases in each request like (extra params, params format, required params, etc ...)

1. Extra params: like if you add any parameter extra of that exist in the sample request, you will receive extra parameter error.
2. Params format: like account number should be numeric but if you send letters in the value,  you will receive error.
3. Missing params: like if you don't send one of required params, you will receive error.

##### Sample requests and responses:
```
Create Account

Request:
{
  "request": {
     "operation" : "create_account",
     "customerName" : "Mohammed Kherfan",
     "customerBirthDate" : "1990-05-05",
     "customerAddress" : "Dubai, Paradise view 1, 904",
     "customerPhone" : "+971564110447",
     "customerEmail" : "mohammed.kherfan@gmail.com",
     "accountType" : "CURRENT_ACCOUNT", //(SAVING_ACCOUNT, CURRENT_ACCOUNT)
     "accountBranch" : "Downtown",
     "accountCurrency" : "AED"
  }
}

Response:
{
  "response": {
    "customerExternalId": "945990f8-6b0f-4700-ab82-8a668cd0db21",
    "customerName": "Mohammed Kherfan",
    "customerBirthDate": "1990-05-05",
    "customerAddress": "Dubai, Paradise view 1, 904",
    "customerPhone": "+971564110447",
    "customerEmail": "mohammed.kherfan@gmail.com",
    "accountNumber": "06983836515000",
    "accountIban": "AE9986394306983836515000",
    "accountCurrency": "AED",
    "accountType": "CURRENT_ACCOUNT",
    "accountStatus": "ACTIVE",
    "accountCreatedDate": "2022-04-23T22:00:25.408189",
    "accountBalance": 0,
    "accountBranch": "Downtown"
  }
}
```

```
Delete Account

Request:
{
  "request": {
     "operation" : "delete_account",
     "accountNumber" : "06983836515000"
  }
}

Response:
{
  "response": {
    "customerExternalId": "945990f8-6b0f-4700-ab82-8a668cd0db21",
    "accountNumber": "06983836515000",
    "accountStatus": "DELETED"
  }
}
```

```
Get Balance

Request:
{
  "request": {
     "operation" : "get_balance",
     "accountNumber" : "06983836515000"
  }
}

Response:
{
  "response": {
    "customerExternalId": "945990f8-6b0f-4700-ab82-8a668cd0db21",
    "customerName": "Mohammed Kherfan",
    "customerBirthDate": "1990-05-05",
    "customerAddress": "Dubai, Paradise view 1, 904",
    "customerPhone": "+971564110447",
    "customerEmail": "mohammed.kherfan@gmail.com",
    "accountNumber": "06983836515000",
    "accountIban": "AE9986394306983836515000",
    "accountCurrency": "AED",
    "accountType": "CURRENT_ACCOUNT",
    "accountStatus": "ACTIVE",
    "accountCreatedDate": "2022-04-23T22:00:25.408189",
    "accountBalance": 0,
    "accountBranch": "Downtown"
  }
}
```

```
Money Deposit

Request:
{
  "request": {
     "operation" : "money_deposit",
     "accountNumber" : "84912958430009",
     "transactionAmount" : 100.00
  }
}

Response:
{
  "response": {
    "customerExternalId": "2e69864b-efbc-4b75-8777-89990d2ab520",
    "accountNumber": "84912958430009",
    "transactionExternalId": "e18e3ef6-0468-414d-ae3a-5fa633eb13eb",
    "transactionType": "DEPOSIT",
    "transactionAmount": 100,
    "accountBalance": 100
  }
}
```

```
Money Withdrawal

Request:
{
  "request": {
     "operation" : "money_withdrawal",
     "accountNumber" : "84912958430009",
     "transactionAmount" : 200.00
  }
}

Response:
{
  "response": {
    "customerExternalId": "2e69864b-efbc-4b75-8777-89990d2ab520",
    "accountNumber": "84912958430009",
    "transactionExternalId": "b554bf46-727f-436b-8551-6c50fa5dbbf3",
    "transactionType": "WITHDRAWAL",
    "transactionAmount": 50,
    "accountBalance": 50
  }
}
```

```
Money Local Transfer

Request:
{
  "request": {
     "operation" : "money_local_transfer",
     "accountNumber" : "61163011522069",
     "payAccountNumber" : "86762932789164",
     "transferAmount" : 100.00
  }
}

Response:
{
  "response": {
    "customerExternalId": "3d0becf5-949b-4945-8514-7810d65640c3",
    "accountNumber": "61163011522069",
    "transactionExternalId": "9bd19473-8646-44a7-9d59-6fee0066c612",
    "transferExternalId": "76139542-9c51-435d-b2fd-95d906d9c5ca",
    "payAccountNumber": "86762932789164",
    "payIban": "AE8080005986762932789164",
    "payBank": "DEFAULT BANK NAME",
    "payBranch": "Downtown",
    "transferType": "LOCAL",
    "transferAmount": 100,
    "transferDate": "2022-04-23T22:46:29.360418"
  }
}
```

```
Money International Transfer

Request:
{
  "request": {
     "operation" : "money_international_transfer",
     "accountNumber" : "61163011522069",
     "payAccountNumber" : "86762932789111",
     "payIban" : "AE3002600086762932789111",
     "payBank" : "ENBD",
     "payBranch" : "Business Bay",
     "transferAmount" : 100.00
  }
}

Response:
{
  "response": {
    "customerExternalId": "3d0becf5-949b-4945-8514-7810d65640c3",
    "accountNumber": "61163011522069",
    "transactionExternalId": "c2cda2f5-3a28-4c71-8f93-3c1cf961e700",
    "transferExternalId": "efa03ab6-d3c7-497a-8185-05e709bf954b",
    "payAccountNumber": "86762932789111",
    "payIban": "AE3002600086762932789111",
    "payBank": "ENBD",
    "payBranch": "Business Bay",
    "transferType": "INTERNATIONAL",
    "transferAmount": 100,
    "transferDate": "2022-04-23T22:48:40.762684"
  }
}
```

```
List Accounts

Request:
{
  "request": {
     "operation" : "list_accounts"
  }
}

Response:
{
  "response": [
    {
      "customerExternalId": "6b6a1b19-5a84-4b87-9023-e8024dce173f",
      "customerName": "Ali Kherfan",
      "customerBirthDate": "2021-10-15",
      "customerAddress": "Dubai, Paradise view 1, 904",
      "customerPhone": "+971564110447",
      "customerEmail": "ali.kherfan@gmail.com",
      "accountNumber": "86762932789164",
      "accountIban": "AE8080005986762932789164",
      "accountCurrency": "AED",
      "accountType": "CURRENT_ACCOUNT",
      "accountStatus": "ACTIVE",
      "accountCreatedDate": "2022-04-23T22:44:13.392412",
      "accountBalance": 200,
      "accountBranch": "Downtown"
    },
    {
      "customerExternalId": "3d0becf5-949b-4945-8514-7810d65640c3",
      "customerName": "Mohammed Kherfan",
      "customerBirthDate": "1990-05-05",
      "customerAddress": "Dubai, Paradise view 1, 904",
      "customerPhone": "+971564110447",
      "customerEmail": "mohammed.kherfan@gmail.com",
      "accountNumber": "61163011522069",
      "accountIban": "AE2734318461163011522069",
      "accountCurrency": "AED",
      "accountType": "CURRENT_ACCOUNT",
      "accountStatus": "ACTIVE",
      "accountCreatedDate": "2022-04-23T22:44:25.194725",
      "accountBalance": 700,
      "accountBranch": "Downtown"
    }
  ]
}
```