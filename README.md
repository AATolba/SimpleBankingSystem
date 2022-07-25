# SimpleBankingSystem

</hr>
## Table of Contents

- [**_Simple Banking App_**]
  - [Table of Contents]
  - [Requirements]
  - [Dependencies]
  - [Java Spring Dependencies]
    - [1. Lombok]
    - [2. Spring Web]
    - [3. Aerospike Client]
    - [4. Aerospike Server]
  - [Usage Instructions]
  - [API Document File]

</hr>

## Requirements

## Features

- [x] Add/Update User
- [x] Delete USer
- [x] Check Balance 
- [x] Withdraw Money
- [x] Deposit Money 
- [x] Get statistics about user account
- [x] Get Statistics about All bank users

## Dependencies

1. [OpenJDK8](https://openjdk.org/projects/jdk8/)
2. [Gradle](https://gradle.org/)
3. [Aerospike](https://developer.aerospike.com/)
4. [Java Spring 2.6+](https://spring.io/)


## Usage Instructions

1. Install and run Aerospike server (recomended : run it inside a docker container) on port 3000
2. TomCat Server is running on the defualt port (8080)

## API Document File

[API_doc].<br />
-Get requests : 
1. /stats <br />
  --> Gets the statistic of all the users in the bank<br />
2. /users/{id}<br />
   --> Gets all the information about the user with id (id).<br />
3. /users/getBalance/{id}.<br />
   --> Gets the balance of the user. <br />
4. /users/BriefTransactions/{id}.<br />
   --> Gets all the transactions that the user performed in the format X when user deposits X amount of mony and -X when          withdrawing money.<br />
5. /users/DetailedTransactions/{id}<br />
   --> returens a full description of the transactions made by the user <br />
6. /users/statistics/{id}<br />
   --> returns statistics about the user tranactions <br />
   
-Post requests <br />
1. users<br />
   --> adds a new user <br />
   new user as json <br />
   {<br />
		"id":1,<br />
		"name":"Abdelrahman",<br />
		"email":"abdelrahman.tolba@gmail.com",<br />
		"contactNumber":"0102030405060",<br />
		"accountNumber":"1000333556850",<br />
		"age":"23",<br />
		"balance":"1000"<br />
	 }<br />
Put requests <br />
1. users/withdraw/{id}<br />
  withdraws X amount of mony where X is passeed trhrow the body <br />
2. /users/deposit/{id}<br />

Delete requests <br />
1. /users/{id}<br />
  --> deletes a user <br />
Update Requests <br />

