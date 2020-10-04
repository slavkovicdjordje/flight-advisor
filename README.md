# Flight advisor API #
Flight advisor API can be used for  finding the cheapest flights.

## Requirements

For building and running the application you will need:

- Java 11
- Maven
- Lombok plugin

Flight advisor is created with H2 in-memory DB, so no need for DB setup.

DB details:
- jdbc url = jdbc:h2:mem:flightdb
- username = root 
- password = root

Application is running on port 8080.


## API usage examples


##### Endpoints without authentication


###### Register

`POST /users`

Body of request:
```
{
	"firstName": {firstName},
	"lastName":  {lastName},
	"username":  {username},
	"password":  {password}
}
```

------

###### Login

`POST /login`

Body of request (for ADMIN):
```
{
	"username":  "johnAdmin",
	"password":  "123456"
}
```

Body of request (for REGULAR USER):
```
{
	"username":  "johnUser",
	"password":  "123456"
}
```
**IMPORTANT**: In response you will get `Authorization` header which must be used in all succeeding endpoints.

------
##### Admin endpoints

###### Add city

`POST /cities`

Body of request:
```
{
	"name":{cityName},
	"countryName": {countryName},
	"description":{description}
}
```

------

###### Upload airports

`POST /upload/airports`

Add upload file as form-parameter with key "file".

------

###### Upload routes

`POST /upload/routes`

Add upload file as form-parameter named "file".

------

##### User endpoints

###### Get cities

`GET /cities?cityName={cityName}&noOfComments={numberOfComments}`

------

###### Get flights

`GET /flights?fromCity={startingCityId}&toCity={destinationCityId}`

------

###### Add comment

`POST /comments`

Body of request:

```
{
    "cityId":{cityId},
    "comment":{text}
}
```

------

###### Edit comment

`PUT /comments`

Body of request:

```
{
    "id":{commnetId},
    "comment":{text}
}
```

------

###### Delete comment

`DELETE /comments/{commentId}`

------
