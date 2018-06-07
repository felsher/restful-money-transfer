# RESTful-Money-Transfer
It's a RESTful service, written on Java 8, without Spring Framework usage, with in-memory datastore and as simple as a table!

API
===============
Checkout prepared accounts, so you don't need to create them 
GET: /accounts/transfer 
Example: curl -H "Content-Type: application/json" -X GET http://0.0.0.0:5555/accounts/all

Make a transfer between two accounts
POST: /accounts/transfer 
Example: curl -H "Content-Type: application/json" -X POST -d '{ "amount": 500.0000, "fromAccountId":"6344a2e6-2f9c-4a11-9052-82203ffcde43", "toAccountId":"9ea540ec-5004-4333-9c2b-3024227069c3" }' http://0.0.0.0:5555/accounts/transfer