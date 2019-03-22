# RESTful-Money-Transfer

# Disclaimer
Small restful service, written on Java 8, without Spring Framework usage

## API
>Application listen to port: 5555  

##### Request examples:  

1. /accounts  
    * Create account: `curl -H "Content-Type: application/json" -X POST -d '{ "balance":1000.0000 }' http://0.0.0.0:5555/accounts`
    * Get all accounts: `curl -H "Content-Type: application/json" -X GET http://0.0.0.0:5555/accounts`
    * Get account by {id}: `curl -H "Content-Type: application/json" -X GET http://0.0.0.0:5555/accounts/{id}`
    * Deposit some money to account: `curl -H "Content-Type: application/json" -X POST -d '{ "accountId":1, "amount":222.0000 }' http://0.0.0.0:5555/accounts/deposit`
    * Delete account by {id}: `curl -H "Content-Type: application/json" -X DELETE http://0.0.0.0:5555/accounts/{id}`
2. /transfers  
    * Make a transfer between accounts: `curl -H "Content-Type: application/json" -X POST -d '{ "amount":123.000, "fromAccountId":"0", "toAccountId":1 }' http://0.0.0.0:5555/transfers`  
