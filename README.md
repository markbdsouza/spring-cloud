# spring-cloud
A Spring Cloud Photo App application using Spring boot micro services

## Pre requisite
### Rabbit MQ
> Install comapctible versions of erlang followed by rabbitMQ from : https://www.rabbitmq.com/install-windows.html
> In the windows start menu and search for rabbitmq command prompt and type command "rabbitmq-plugins enable rabbitmq_management"
> This will launch rabbit mq on your local machine on port http://localhost:15672/
> login with username:guest password:guest 

## Running the application:
Launch the application in the below order 
1. Discovery Service
2. Config Server
3. API Gateway
4. Users
5. Albums
6. Account Management

### Generic Micrsoervice URLs
Discovery Service URL : http://localhost:8010/
Config Server URL: http://localhost:8888/users/default
Encrypt URL: localhost:8888/encrypt
Decrypt URL: localhost:8888/decrypt

Below are the HTTP Endpoints 
### 1) Create New User 
Create a new user for the application
#### Request Details
   POST  http://localhost:8082/users/users
   Request Body
   {
   "firstName" : "Mark",
   "lastName" :"DSouza",
   "email": "test@test.com",
   "password": "12345678"
   }
   
#### Response Details 
Http Response Status : 201
Response Body
{
"userId": "bf926487-a0fe-400e-8ce2-f7399c44b2ac",
"firstName": "Mark",
"lastName": "DSouza",
"email": "test@test.com"
}
   
### 2) Login with the user created
Once user is created Login to get the JWT Token which is used in all other requests
#### Request Details
POST http://localhost:8082/users/users/login
Request Body
{
"email": "test@test.com",
"password": "12345678"
}
#### Response Details 
Response Status: 200
Response Headers
token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiZjkyNjQ4Ny1hMGZlLTQwMGUtOGNlMi1mNzM5OWM0NGIyYWMiLCJleHAiOjE2MzM3MTE1NDV9.IuQsnMxon-FyR4he4fh5wddunYvx5xCM-rG8oB51fCnelM52s2gRW0P0VR57j0CU7FYZDe6Llm8Sv6DB7n-Q9w
userId: bf926487-a0fe-400e-8ce2-f7399c44b2ac
    
### 3) User Status Check

#### Http Request
GET http://localhost:8082/users-status-check
Request Header:
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiZjkyNjQ4Ny1hMGZlLTQwMGUtOGNlMi1mNzM5OWM0NGIyYWMiLCJleHAiOjE2MzM3MTE1NDV9.IuQsnMxon-FyR4he4fh5wddunYvx5xCM-rG8oB51fCnelM52s2gRW0P0VR57j0CU7FYZDe6Llm8Sv6DB7n-Q9w

#### Http Response
Response Status: 200
Response Body: working on 51571 having secret token =ZXw412ifionzxcv214sf2

### 4) Get User Details
#### Request Details
POST http://localhost:8082/users/users/bf926487-a0fe-400e-8ce2-f7399c44b2ac
or http://localhost:8082/users/users/{userId}
Request Header: 
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiZjkyNjQ4Ny1hMGZlLTQwMGUtOGNlMi1mNzM5OWM0NGIyYWMiLCJleHAiOjE2MzM3MTE1NDV9.IuQsnMxon-FyR4he4fh5wddunYvx5xCM-rG8oB51fCnelM52s2gRW0P0VR57j0CU7FYZDe6Llm8Sv6DB7n-Q9w

#### Response Details
Response Body
{
"userId": "4ba87c1a-663f-45bb-ab17-ee0cf4847b74",
"firstName": "Mark",
"lastName": "DSouza",
"email": "test@test.com",
"albums": [
{
"albumId": "album1Id",
"userId": "4ba87c1a-663f-45bb-ab17-ee0cf4847b74",
"name": "album 1 name",
"description": "album 1 description"
},
{
"albumId": "album2Id",
"userId": "4ba87c1a-663f-45bb-ab17-ee0cf4847b74",
"name": "album 2 name",
"description": "album 2 description"
}
]
}