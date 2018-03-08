# Mercedes Readme

## How to build and run your application:

1- In the directory Mercedes, do 

```
mvn install
```

2- Then do, to run localy on port 8080 (to change, go to file Application.java)

```
mvn clean spring-boot:run
```

3- Now you can open the urls in a browser, and visualize de output of the get request
```
http://localhost:8085/getvehicles?type=fuel
```
```
http://localhost:8085/getclosestvehicle?model=AMG&fuel=ELECTRIC&transmission=AUTO&latitude=0&longitude=0
```
```
http://localhost:8085/getallclosestvehicles?model=AMG&fuel=ELECTRIC&transmission=AUTO&latitude=0&longitude=0
```


## How to run your tests:

1- In the directory Mercedes, do

```
mvn install
```

2- Then do

```
mvn test
```


## API documentation:

In class Controller.java there`s five rest functions that are available, 3 are GET requests and 2 POST.

### Vehicle[] GetVehicles (String type)

Receives one argument, a string , the string it must be "Model" or "Fuel" or "Transmission" or "Dealer"
It returns a list of all vehicles order by the argument pass, in case of a draw of the first argument, will chose the next condition to tie break, until the last one by this order "Model,Fuel,Transmission,Dealer"
```
Example localhost:8085/getvehicles?type=fuel
```

### Dealers GetClosestVehicle (String model,String fuel,String transmission,float latitude,float longitude)

Receives 5 arguments, 3 strings and 2 floats, the following Strings model, fuel, transmission . And the floats latitude, longitude 
it returns an object dealer with is the closest one that has the vehicles with that characteristics, or null if none is found
```
Example	http://localhost:8085/getclosestvehicle?model=AMG&fuel=ELECTRIC&transmission=AUTO&latitude=0&longitude=0
```

### Dealer[] GetAllClosestVehicles (String model,String fuel,String transmission,float latitude,float longitude)

It is an Improvement  from GetClosestVehicle. 
Finds the dealers sorted by distance according to my location that have a vehicle with specific attributes, It returns an array of Dealer
```
Example http://localhost:8085/getallclosestvehicles?model=AMG&fuel=ELECTRIC&transmission=AUTO&latitude=0&longitude=0
```

### String CreateBooking (String vehicleId, String firstName, String lastName, String pickupDate)

It’s to create a booking for a car with vehicle Id, and the pickupDate must be in time slot available of the vehicle, and it cannot conflict with a previas book.
It returns a string with the ID for the booking or "error"

### String CancelBooking (String bookId, String cancelledReason)

It’s to cancel an existing book, identifies by bookId
It returns a String "cancel" if the booking was cancel, or "error" if it was not possible to cancel it

# Explanation about your choices, their limitations and possible improvements:

I chose to create classes to all the object, to facilitate the logic of the domain, and for that reason i have a dependency on fasterxml for easily pass the Json file with the data to memory.

I chose Java because it’s a language that i am comfortable with and an object-oriented language facilitates this project.

I also use maven because it’s a tool used in MB.io and it’s easy to use, i have also use springframework with was a tool that i have already use and allows me to deploy the web service easily with friendly annotations to use in the code.

For the tests i use Junit, it comes with spring, and i have already work with it. 

One limitation is that i am passing objects, which obligates the front end to have knowledge of the implementation on the domain. 

A possible improvement is to separate more the domain logic from the implementation of the API, and because the dealers have arrays of vehicle, when i concatenate all vehicles i am creating multiple array, whit is very expensive in terms of processing, i could change to list, avoiding the multiple memory allocation


# Any additional information you may think is relevant:

dependencies:

springframework

fasterxml




