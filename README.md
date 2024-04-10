# Demo bank
This is a project to demo a group of microservices and infrastructure components, mainly using Spring ecosystem projects.

## Architecture
There are three main business services:
* [Accounts](./accounts/)
* [Loans](./loans/)
* [Cards](./cards/)

### Database and stores
* [H2](https://h2database.com/html/main.html) - for quick testing
* [MySQL](https://www.mysql.com/) - for actual deployment

> [!info] branch: mysql
>
> A separate branch is maintained for MySQL based architecture

### API documentation
APIs are documented using Open API specs, automatically generated using [Spring DOC](https://springdoc.org/). Consequently, API documents in swagger format are exposed on respective ports where services run.

### Externalized configurations
Configurations are externalized by building a separate Config Server. It is inside the `configserver` directory and uses [Spring Cloud Config](https://spring.io/projects/spring-cloud-config)

### Dynamic configuration updates
Configuration of the microservices in maintained in a separate repository: https://github.com/vaibhav276/demo-bank-config

The Config server microservice automatically updates itself when there are changes in the config repo.

[RabbitMQ](https://www.rabbitmq.com/) is used as queuing service to relay configuration updates to all microservices.

> [!info] branch: `rabbitmq-busrefresh`
> 
> A separate branch is maintained for RabbitMQ based busrefresh architecture.

### Management APIs
Production ready management APIs (health checks, config refresh etc.) are exposed using [Spring boot actuator](https://docs.spring.io/spring-boot/docs/2.5.6/reference/html/actuator.html)

### Containerization
[Google Jib](https://cloud.google.com/java/getting-started/jib) is used to build docker containers of all microservices.

### Infrastructure as Code (IaC)
IaC is maintained using [Docker compose](https://docs.docker.com/compose/). There are three profiles of running the entire suite of microservices - prod, qa and default. These profiles are maintained in following docker compose directories:
* `docker-compose/prod`
* `docker-compose/default`
* `docker-compose/qa`

### Service Registration and Discovery
Service discovery is implemented using [Spring Cloud Netflix - Eureka Server](https://spring.io/projects/spring-cloud-netflix), where each service registers with the Eureka server and sends periodic heartbeats.

To enable service-to-service communication, [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign) is used, where a client looks for other services on the Eureka Server using its name. And the OpenFeign implementation provides REST API calling mechanism internally.

### Load balancing
Client side load balancing is provided by [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign).

### API Gateway
API gateway is implemented using [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)

### Distributed Request Tracing
A correlation ID is added to header of each request in Gateway server code (all requests go through it), and also in all services so that a request can be traced using logging.

### Resilience
#### Circuit Breaker Pattern
Circuit breakers are implemented using [Resilience4j](https://resilience4j.readme.io/docs/circuitbreaker) in:
* Gateway Server
* Accounts server for `/api/v1/customers` endpoint because it internally depends on other services
  
#### Timeouts
Request timeouts are globally configured in Gateway server

#### Retries
* Gateway server automatically retries for `/info/build` endpoint of Loan service if there is any error or timeout event
* Account server's `/info/build` endpoint has retry fallback method defined over it
* Gateway server has configuration to make sure circuit breaker timeout is greater than retry timeout

## Deployment
### Build
The service docker images can be built using these commands

```sh
mvn clean package
mvn compile jib:dockerBuild
```

### Run
Run the following command to bring all services up

```sh
cd docker-compose/{profile}/
docker compose up -d
```

To tear down
```sh
cd docker-compose/{profile}/
docker compose down
```

## Manual Testing
### Business services
The swagger UI for each service is exposed as follows, and can be used for manually testing the APIs
* Accounts - http://localhost:8080/swagger-ui/index.html
* Loans - http://localhost:8090/swagger-ui/index.html
* Cards - http://localhost:9000/swagger-ui/index.html

### Config server
The config server is exposed at - http://localhost:8071/

Each service's configuration is available at corresponding paths in config server. For example, `accounts` service `default` config is available as:

```sh
http :8071/accounts/default

HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Mon, 08 Apr 2024 04:40:36 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "label": null,
    "name": "accounts",
    "profiles": [
        "default"
    ],
    "propertySources": [
        {
            "name": "https://github.com/vaibhav276/demo-bank-config.git/accounts.yml",
            "source": {
                "accounts.contactDetails.email": "john@DemoBank.com",
                "accounts.contactDetails.name": "John Doe - Developer",
                "accounts.message": "Welcome to DemoBank accounts related local APIs (docker) ",
                "accounts.onCallSupport[0]": "(555) 555-1234",
                "accounts.onCallSupport[1]": "(555) 523-1345",
                "build.version": "3.0"
            }
        }
    ],
    "state": null,
    "version": "27f643d95aa3149dda6e7d2aed33199b761e4438"
}
```

### Testing config updates
RabbitMQ is used to trigger config updates in all services when a change in made in Config Server. The Config server automatically updates itself when the github config repo (https://github.com/vaibhav276/demo-bank-config) is updated. So the typical steps to test a config update would be

1. Update config in https://github.com/vaibhav276/demo-bank-config
2. Verify that the updated config is picked up by Config server. Run `http :8071/{service}/{profile}` to get its config from Config server.
3. Run `http :8071/actuator/busupdate` to relay the updates to all business services
4. Verify that the business services have got the updated config. This can be done by executing APIs like `http :8080/api/v1/info/config` (For example, for accounts service)

### Eureka Server
Eureka Server is exposed at - http://localhost:8070/eureka

### Gateway server
The gateway server routes are exposed at - http://localhost:8072/actuator/gateway/routes
The gateway server circuit breakers are exposed at - http://localhost:8072/actuator/circuitbreakers

## Automated Testing
A basic test script using [Httpie](https://httpie.io/docs) and [Jq](https://jqlang.github.io/jq/) can be found inside `e2e-test/` directory
