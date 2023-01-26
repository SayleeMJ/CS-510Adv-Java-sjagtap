This is a README file!

Name: Saylee Mohan Jagtap
Odin ID: sjagtap
PSU ID: 972267084

This application creates an Airline object with Airline name and all the flights for this airline.
Each flight object has flight number, source , depart date time, destination, arrive date time.

The application takes two options print and README
Print option prints the details of all flights and README option will print the readme file.

usage: java -jar target/airline-2023.0.0.jar [options] <args>
             args are (in this order):
                 airline The name of the airline
                 flightNumber The flight number
                 src Three-letter code of departure airport
                 depart Departure date and time (24-hour time)
                 dest Three-letter code of arrival airport
                 arrive Arrival date and time (24-hour time)
             options are (options may appear in any order):
                -print Prints a description of the new flight
                -README Prints a README for this project and exits
             Date and time should be in the format: mm/dd/yyyy hh:mm