# Demo bank

## Build
The service docker images can be built using these commands

```sh
cd accounts/
mvn clean package
mvn compile jib:dockerBuild
cd ..

cd loans/
mvn clean package
mvn compile jib:dockerBuild
cd ..

cd cards/
mvn clean package
mvn compile jib:dockerBuild
cd ..

cd configserver/
mvn clean package
mvn compile jib:dockerBuild
cd ..
```

## Run
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

## Test
### Business services
The swagger UI for each service is exposed as follows, and can be used for manual testing the APIs
* Accounts - http://localhost:8080/swagger-ui/index.html
* Loans - http://localhost:8090/swagger-ui/index.html
* Cards - http://localhost:9000/swagger-ui/index.html

### Config server
The config server is exposed at
http://localhost:8071/

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
