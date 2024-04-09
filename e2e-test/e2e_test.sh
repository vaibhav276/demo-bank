#!/bin/bash

MOBILE_NUMBER=9087654321
GATEWAY_SERVER=":8072"

echo "Creating a new account for customer with mobile number 9087654321..."
http POST :8072/demobank/accounts/api/v1/accounts name="Bruce Wayne" email="bruce@wayne.com" mobileNumber="9087654321"

echo "Getting details of account with mobile number ${MOBILE_NUMBER}..."
http ${GATEWAY_SERVER}/demobank/accounts/api/v1/accounts mobileNumber==${MOBILE_NUMBER} > get_account.json
cat get_account.json | jq .

ACCOUNT_NUMBER=`cat get_account.json | jq -r .accountNumber`

echo "Updating account ${ACCOUNT_NUMBER}..."
http PUT ${GATEWAY_SERVER}/demobank/accounts/api/v1/accounts/$ACCOUNT_NUMBER accountNumber=${ACCOUNT_NUMBER} accountType="Savings" branchAddress="124 Dummy Street, Dummy Town" customerDto:='{"name": "Peter Pan", "email":"peter@pan.com"}' > get_account1.json

echo "Account after updating"
cat get_account1.json | jq .

echo "*****"

echo "Creating loan for customer with mobile number ${MOBILE_NUMBER}..."
http POST ${GATEWAY_SERVER}/demobank/loans/api/v1/loans mobileNumber==${MOBILE_NUMBER}

echo "Getting details of loan with mobile number ${MOBILE_NUMBER}..."
http ${GATEWAY_SERVER}/demobank/loans/api/v1/loans mobileNumber==${MOBILE_NUMBER} > get_loan.json
cat get_loan.json | jq .

LOAN_NUMBER=`cat get_loan.json | jq -r .loanNumber`

echo "Updating loan ${LOAN_NUMBER} for customer with mobile number ${MOBILE_NUMBER}..."
http PUT ${GATEWAY_SERVER}/demobank/loans/api/v1/loans mobileNumber=${MOBILE_NUMBER} loanNumber=${LOAN_NUMBER} loanType="Home Loan" totalLoan:=1272 amountPaid:=233 outstandingAmount:=348

echo "Getting details of loan with mobile number ${MOBILE_NUMBER}..."
http ${GATEWAY_SERVER}/demobank/loans/api/v1/loans mobileNumber==${MOBILE_NUMBER} > get_loan1.json

echo "Loan after updating"
cat get_loan1.json | jq .

echo "*****"

echo "Creating card for customer with mobile number ${MOBILE_NUMBER}..."
http POST ${GATEWAY_SERVER}/demobank/cards/api/v1/cards mobileNumber==${MOBILE_NUMBER}

echo "Getting details of card with mobile number ${MOBILE_NUMBER}..."
http ${GATEWAY_SERVER}/demobank/cards/api/v1/cards mobileNumber==${MOBILE_NUMBER} > get_card.json
cat get_card.json | jq .

CARD_NUMBER=`cat get_card.json | jq -r .cardNumber`

echo "Updating card ${CARD_NUMBER} for customer with mobile number ${MOBILE_NUMBER}..."
http PUT ${GATEWAY_SERVER}/demobank/cards/api/v1/cards mobileNumber=${MOBILE_NUMBER} cardNumber=${CARD_NUMBER} cardType="Credit Card" totalLimit:=200000 amountUsed:=20454 availableAmount:=23499

echo "Getting details of card with mobile number ${MOBILE_NUMBER}..."
http ${GATEWAY_SERVER}/demobank/cards/api/v1/cards mobileNumber==${MOBILE_NUMBER} > get_card1.json
cat get_card1.json | jq .

echo "*****"

echo "Getting all customer details for customer with mobile number ${MOBILE_NUMBER}..."
http ${GATEWAY_SERVER}/demobank/accounts/api/v1/customers mobileNumber==${MOBILE_NUMBER} > get_all.json
cat get_all.json | jq .