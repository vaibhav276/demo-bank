# Account service
CRUD on following entities
* Account
* Customer

## Manual test executions

### Create account
```sh
http POST :8080/api/v1/accounts name="Vaibhav Pujari" email="vaibhav@example.com" mobileNumber="2034939"
HTTP/1.1 201 
Connection: keep-alive
Content-Type: application/json
Date: Tue, 02 Apr 2024 08:36:02 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "statusCode": "201 CREATED",
    "statusMsg": "Account Created"
}
```