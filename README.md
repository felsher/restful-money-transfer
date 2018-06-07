# RESTful-Money-Transfer

# Disclaimer
It's a RESTful service, written on Java 8, without Spring Framework usage, with in-memory datastore and as simple as a table!

## API
Application listen to port: 5555  

1. /accounts  
    * GET: `curl -H "Content-Type: application/json" -X GET http://0.0.0.0:5555/accounts`
    * POST: I didn't implemented create method for bank account, but a couple of prepared accounts are exists in database, so you don't need to create them
2. /transfers  
    * POST: `curl -H "Content-Type: application/json" -X POST -d '{ "amount":20.000, "fromAccountId":"1", "toAccountId":1 }' http://0.0.0.0:5555/transfers`  
    * GET: `curl -H "Content-Type: application/json" -X GET http://0.0.0.0:5555/transfers`