CREATE TABLE ACCOUNTS
  (
     ID                         SERIAL NOT NULL UNIQUE,
     CUSTOMER_EXTERNAL_ID       VARCHAR(255) NOT NULL UNIQUE,
     ACCOUNT_NUMBER             VARCHAR(15) NOT NULL UNIQUE,
     ACCOUNT_IBAN               VARCHAR(25) NOT NULL UNIQUE,
     ACCOUNT_CURRENCY           VARCHAR(25) NOT NULL,
     ACCOUNT_TYPE               VARCHAR(25) NOT NULL,
     ACCOUNT_STATUS             VARCHAR(25) NOT NULL,
     ACCOUNT_CREATED_DATE       TIMESTAMP NOT NULL,
     ACCOUNT_BALANCE            NUMERIC(19,2) NOT NULL,
     ACCOUNT_BANK               VARCHAR(255) NOT NULL,
     ACCOUNT_BRANCH             VARCHAR(255) NOT NULL,
     PRIMARY KEY (ID)
  ) ;

CREATE TABLE CUSTOMERS
  (
     ID                         SERIAL NOT NULL UNIQUE,
     CUSTOMER_EXTERNAL_ID       VARCHAR(255) NOT NULL UNIQUE,
     CUSTOMER_NAME              TEXT NOT NULL,
     CUSTOMER_BIRTHDATE         TIMESTAMP NOT NULL,
     CUSTOMER_ADDRESS           TEXT NOT NULL,
     CUSTOMER_PHONE             VARCHAR(25) NOT NULL,
     CUSTOMER_EMAIL             VARCHAR(501) NOT NULL,
     PRIMARY KEY (ID)
  ) ;

CREATE TABLE TRANSACTIONS
  (
     ID                         SERIAL NOT NULL UNIQUE,
     CUSTOMER_EXTERNAL_ID       VARCHAR(255) NOT NULL,
     ACCOUNT_NUMBER             VARCHAR(15) NOT NULL,
     TRANSACTION_EXTERNAL_ID    VARCHAR(255) NOT NULL UNIQUE,
     TRANSACTION_TYPE           VARCHAR(25) NOT NULL,
     TRANSACTION_AMOUNT         NUMERIC(19,2) NOT NULL,
     TRANSACTION_DATE           TIMESTAMP NOT NULL,
     PRIMARY KEY (ID)
  ) ;

CREATE TABLE TRANSFERS
  (
     ID                         SERIAL NOT NULL UNIQUE,
     CUSTOMER_EXTERNAL_ID       VARCHAR(255) NOT NULL,
     ACCOUNT_NUMBER             VARCHAR(15) NOT NULL,
     PAY_ACCOUNT_NUMBER         VARCHAR(15) NOT NULL,
     PAY_IBAN                   VARCHAR(25) NOT NULL,
     PAY_BANK                   VARCHAR(255) NOT NULL,
     PAY_BRANCH                 VARCHAR(255) NOT NULL,
     TRANSFER_TYPE              VARCHAR(25) NOT NULL,
     TRANSFER_AMOUNT            NUMERIC(19,2) NOT NULL,
     TRANSFER_DATE              TIMESTAMP NOT NULL,
     TRANSACTION_EXTERNAL_ID    VARCHAR(255) NOT NULL UNIQUE,
     TRANSFER_EXTERNAL_ID       VARCHAR(255) NOT NULL UNIQUE,
     PRIMARY KEY (ID)
  ) ;

CREATE TABLE PARAMETERS
  (
     ID                         SERIAL NOT NULL UNIQUE,
     OPERATION                  VARCHAR(255) NOT NULL,
     PARAMETER                  VARCHAR(255) NOT NULL,
     TYPE                       VARCHAR(25) NOT NULL,
     REQUIRED                   BOOLEAN NOT NULL,
     PRIMARY KEY (ID)
  ) ;

INSERT INTO PARAMETERS (OPERATION, PARAMETER, TYPE, REQUIRED)
VALUES
('create_account',                      'customerName',             'ALPHANUMERIC',     TRUE),
('create_account',                      'customerBirthDate',        'DATE',             TRUE),
('create_account',                      'customerAddress',          'ALPHANUMERIC',     TRUE),
('create_account',                      'customerPhone',            'NUMERIC',          TRUE),
('create_account',                      'customerEmail',            'ALPHANUMERIC',     TRUE),
('create_account',                      'accountType',              'ALPHA',            TRUE),
('create_account',                      'accountBranch',            'ALPHA',            TRUE),
('create_account',                      'accountCurrency',          'ALPHA',            TRUE),
('create_account',                      'operation',                'ALPHANUMERIC',     TRUE),

('delete_account',                      'accountNumber',            'NUMERIC',          TRUE),
('delete_account',                      'operation',                'ALPHANUMERIC',     TRUE),

('money_deposit',                       'accountNumber',            'NUMERIC',          TRUE),
('money_deposit',                       'transactionAmount',        'NUMERIC',          TRUE),
('money_deposit',                       'operation',                'ALPHANUMERIC',     TRUE),

('get_balance',                         'accountNumber',            'NUMERIC',          TRUE),
('get_balance',                         'operation',                'ALPHANUMERIC',     TRUE),

('list_accounts',                       'operation',                'ALPHANUMERIC',     TRUE),

('money_withdrawal',                    'accountNumber',            'NUMERIC',          TRUE),
('money_withdrawal',                    'transactionAmount',        'NUMERIC',          TRUE),
('money_withdrawal',                    'operation',                'ALPHANUMERIC',     TRUE),

('money_international_transfer',        'accountNumber',            'NUMERIC',          TRUE),
('money_international_transfer',        'payAccountNumber',         'NUMERIC',          TRUE),
('money_international_transfer',        'payIban',                  'ALPHANUMERIC',     TRUE),
('money_international_transfer',        'payBank',                  'ALPHA',            TRUE),
('money_international_transfer',        'payBranch',                'ALPHA',            TRUE),
('money_international_transfer',        'transferAmount',           'NUMERIC',          TRUE),
('money_international_transfer',        'operation',                'ALPHANUMERIC',     TRUE),

('money_local_transfer',                'accountNumber',            'NUMERIC',          TRUE),
('money_local_transfer',                'payAccountNumber',         'NUMERIC',          TRUE),
('money_local_transfer',                'transferAmount',           'NUMERIC',          TRUE),
('money_local_transfer',                'operation',                'ALPHANUMERIC',     TRUE);