##  country-search-app
* Simple Spring boot app(micro-service), which returns a country or an error, upon phone number query.(+372 12389821). Client-side is build with vue.js. 

## Task and Task Requirements:

* Write a micro-service to determine the country by phone number.
* The user enters a phone number, the system processes it and displays the country or error message as a result.
* For country codes, use the table from https://en.wikipedia.org/wiki/List_of_country_calling_codes 
(each time the service is restarted, data must be retrieved from this source).

### Backend
Java (>= 9)
Spring Boot
Maven / Gradle
HTTP, RESTFul service with JSON data format. 

## Frontend:
HTML
Javascript 

 * The libraries used are at your disposal, but you are not allowed to use those that contain a direct task solver (eg Google Phone Validator, etc.).
 
## Notes:
* The application must be started from the command line via port 8080. It must be possible to perform tests and view reports. 
* All requests to the application must be made using a RESTFul-based service with JSON data formatting.
* Interface design is not important, organized HTML will suffice.
* To get queries, use any AJAX-capable framework, or just jQuery. Data verification, tests required.


##  Matching up to requirements and beyond:

# Back-end(micro-service):
*  Back-end(B.E) is build with java 11, Spring boot and  Maven.

*  Back-end exposes one RESTFul endpoint, which accepts a payload(phone number) of json format using http POST method at port number 8080.

*  WikiData API used as data source, as provided in the task. 

*  Back-end fetches, parses and initialize the data upon application start-up, it does this only once during the app life-cycle and repeats upon app re-start.

*  If call to fetch data from `external API` fails, the back-end throws an `informative error message` for easy debugging. Also, the app does not start-up,
 since the back-end is only useful if it contains the fetched data. This ensures predictability and trust for our micro-service. 
 
* Back-end contains extensive `unit test suits` and `integration test`.

* Back-end also contains input(data) verification checks.

* App can be ran from the command line.
 
# front-end:
 
* The client-side(Front-end) is separated from the micro-service. The reason was to ensure separation of concerns and improve UX.

* Front-end uses html, css, javascript, vue.js.

* Front-end contains 3 views(pages), which includes: Homepage, success and error pages.

* Homepage loads up upon opening the client after running `yarn serve` as described below under front-end installation.

* The user enters a phone number in the form, RESTFul request send to B.E and base on the response, front-end either shows an error or success page.

* Front-end performs simple input validations.

## Folder structure
* Front-end and Back-end source codes are in same parent folder, just for simplicity when running the app in the IDE.


## Building and running locally:
To run this locally, you need Java 11, Maven for B.E and npm, yarn for F.E.

## Back-end:

# Installation

* To start APP: 
```sh
cd callingCodeValidator-service
$ mvn spring-boot:run
```
* To test APP: 
```sh
cd callingCodeValidator-service
$ mvn test
```

* Spring Boot app should start-up at listening to request on port 8080.
* All test cases should be passed.

| Purpose | RESTFul API endpoint
| - | -
| submit user phone number | /api/countryPrefixAndNumber


## Front-End:

# installation:

* front-end:
```sh
cd callingCodeValidator-service/front-end/country-search-app
$ yarn install
$ yarn serve
```

* Navigate to: http://localhost:8081 or url generated from running `yarn serve` to view  running client side.


