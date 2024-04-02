# Account service
CRUD on following entities
* Account
* Customer

## Manual test executions

### Create account
```sh
http POST :8080/api/v1/accounts name="Bruce Wayne" email="bruce@wayne.com" mobileNumber="123456"
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
        "email": "bruce@wayne.com",
        "mobileNumber": "123456",
        "name": "Bruce Wayne"
    }
}
```

## Updating account (mis-matching account number)
```sh
http PUT :8080/api/v1/accounts/1834362036 customerDto:='{"name": "Peter Pan", "email":"peter@pan.c
om"}'
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/json
Date: Tue, 02 Apr 2024 14:23:31 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "accountNumber": 1834362036,
    "accountType": "Savings",
    "branchAddress": "123 Dummy Street, Dummy Town",
    "customerDto": {
        "email": "peter@pan.com",
        "mobileNumber": "123456",
        "name": "Peter Pan"
    }
}
```