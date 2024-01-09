# Final Project
##  __DACD -Desarollo de Aplicaciones para Ciencia de Datos__ 
==
- _Course:_ 2nd Year
- _Titulation:_ Engineering & Data Science
- _School:_ School of Informatic Engineering and Mathematics
- _College:_ ULPGC - University of Las Palmas of Gran Canaria

#
#
#
-------------

## Final Project

The project is separated beetwen 4 modules.
·1 - PredictionProvider
·2 - HotelProvider
·3 - DatalakeBuilder
·4 - BusinessUnit

Each one has its proper Main method to run from the IntelliJ Idea project if wanted. If not, there is a release avaiable.
There is no order defined for the first 3 modules, but the BusinessUnit is only going to work properly after the adderance of information into the Datalake.
There will be further explanation below for each module 

# 1 - Prediction Provider

# Functinality Resume

The prediction provider module is in charge of getting weather predictions from the OpenWeather [5 day Weather Forecast](https://openweathermap.org/forecast5) to
know the future weather of the next 5 days of each Canary Island. Once it is obtained, it is serialized into a JSON String and published on a local ActiveMQ Broker
It starts sending information at the 12PM, and once it starts, the module will keep sending new predictions every 6 hours.

## How to use the Prediction Provider

When the user runs the Main method of the Prediction Provider module the only thing needed to its proper functinallity is to provide a personal apikey to do
all the required calls to OpenWeather [5 day Weather Forecast](https://openweathermap.org/forecast5)
Once the programm is running it will start to send to the broker all the predictions obtained (5 predictions per island), informing the user when a
prediction is send

## Resources

The resources used for this module are

- IntelliJ Idea as programming environment
- Gson Library on Maven for Json treatment
- OkHttp library on Maven for Http requests
- OpenWeatherMap to get the predictions
- Concurrent Java library to do the task every 6 hours
- Git as version controler
- ActiveMQ on Maven for all the opperations with the broker
- org.slf4j on Maven for the correct functinallity of the module

# 
--------

# 2 - Hotel Provider

# Functinality resume

The second module, HotelProvider, gets a list of Hotels from the [Amadeus Hotel List](https://developers.amadeus.com/self-service/category/hotels/api-doc/hotel-list) using the geocode of each
island and looking for all the hotels on a radius of 50 KM (in order to prevent the repetition caused by how close are some island, each search is done by filtering the iataCode
of the island). Once this is acomplished, it is serialized into a JSON String and send to a local ActiveMQ Broker

## How to use the Event Store Builder

The only thing needed for the Main method to run without failure is to provide a personal [Amadeus](https://developers.amadeus.com/self-service/category/hotels/api-doc/hotel-list) API Key and API Secret to the program (in that order).

## Resources

The resources used for this module are

- IntelliJ Idea as programming environment
- Gson Library on Maven for Json treatment
- Git as version controler
- Amadeus Hotel API
- ActiveMQ on Maven for all the opperations with the broker
- org.slf4j on Maven for the correct functinallity of the module

#
--------

# 3 - Datalake Builder

# Functionality resume

The third module connects to the same broker as the 2 modules above to get all the Weather predictions & Hotels information uploaded in order to write them down
on a .events file that follows this directory structure datalake/eventstore/{topic}/{ss}/{YYYYMMDD}.events, being {topic} the topic of the broker, {ss} the source (in this case prediction-provider and hotel-provider)
and {YYYYMMDD} the timestap date of the moment this information was taken.

# How to use the Datalake Builder

In this case the only thing needed to run the Main method without trouble is to provide the program with a path to write down all the information described.

## Resources

- IntelliJ Idea as programming environment
- Gson Library on Maven for Json treatment
- Git as version controler
- ActiveMQ on Maven for all the opperations with the broker
- org.slf4j on Maven for the correct functinallity of the module

#
--------

# 4 - Business Unit

# Functionalty resume

The Business Unit inicializes the creation of a Datamart made on SQLite with tables of all the information on the Datalake before suscribing to the broker
to get the information from there once it stays running.
After doing this the Business Unit will start running an user interface through the console to acces to both, the hotels information, and the weather information of the next 5 days
Like it was mentioned before, the BusinessUnit will only work if there are .events files avaiable on the directory datalake/eventstore/{topic}/{ss}/{YYYYMMDD}.events, since it inicializes
using the Datalake to have information to show. So, make sure that there are .events files on the indicated directory

# How to use the Business Unit

Since the format the data is saved is creating an SQLite database, a path will be required to know where the Datamart is going to be

## Resources

- IntelliJ Idea as programming environment
- Gson Library on Maven for Json treatment
- SQLite as Datamart format
- Git as version controler
- ActiveMQ on Maven for all the opperations with the broker
- org.slf4j on Maven for the correct functinallity of the module

#
--------

## Development & Design

The pattern used for the project is the Publisher/Suscriber pattern along with an Hexagonal-architecture, being in simple terms a program that follows the model/view/controller model. 
The program works as a real time system to show information to the used, even tho it inicilizes with a database, while it works the main way to get the information
is through a streaming layer.

All the classed are related following the next diagram:

![Captura de pantalla 2024-01-09 202331](https://github.com/Yurazu-n/Final-Project-/assets/90729313/4290b4d5-6afe-44b5-9d2b-11548bde69b0)

