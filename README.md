##  country-search-app
 Simple spring boot app(micro-service), which returns a country upon the country call-code and phone number. Client-side is 
 build with vue.js. 

## Building and running locally:
To run this locally, you need Java >= 9,Maven for B.E and NPM, Yarn for F.E.


## Installation
Back-end:

To start APP: 
```sh
cd callingCodeValidator-service
$ mvn spring-boot:run
```
To test APP: 
```sh
cd callingCodeValidator-service
$ mvn test
```
* Spring Boot should start app at port number 8080(http://localhost:8080).
* Test should have all test cases passed.

| Purpose | API URL
| - | -
| submit user phone number | /api/countryPrefixAndNumber

## Folder structure
Front-end and Back-end source codes are in same parent folder, just for simplicity in the IDE.

#### Front-End:
### installation:
front-end:
```sh
cd callingCodeValidator-service/front-end/country-search-app
$ yarn install
$ yarn serve
```

* Navigate to: http://localhost:8081 or url generated from `yarn serve` to view app


