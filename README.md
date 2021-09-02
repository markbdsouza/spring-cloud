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
2. API Gateway
3. Config User
4. User
5. Account Management
