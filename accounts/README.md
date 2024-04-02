# Account service
CRUD on following entities
* Account
* Customer

## Manual test executions

### Create account
```sh
http POST :8080/api/v1/accounts name="Vaibhav Pujari" email="vaibhav@example.com" mobileNumber="123456"
HTTP/1.1 201 
Connection: keep-alive
Content-Type: application/json
Date: Tue, 02 Apr 2024 10:57:37 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "statusCode": "201 CREATED",
    "statusMsg": "Account Created"
}
```

### Get customer by mobile number
```sh
http :8080/api/v1/accounts?mobileNumber=123456
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Tue, 02 Apr 2024 10:57:38 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "accountNumber": 1084907777,
    "accountType": "Savings",
    "branchAddress": "123 Dummy Street, Dummy Town",
    "customerDto": {
        "email": "vaibhav@example.com",
        "mobileNumber": "123456",
        "name": "Vaibhav Pujari"
    }
}
```