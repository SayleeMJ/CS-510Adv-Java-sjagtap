This is a README file!

Name: Saylee Mohan Jagtap
Odin ID: sjagtap
PSU ID: 972267084


This application creates an Airline object with Airline name and all the flights for this airline.
Each flight object has flight number, source , depart date time, destination, arrive date time.
After creating Airline it reads/writes the object's data into the text file.

The application takes four options print, README, textFile and pretty
Print option prints the details of all flights
README option will print the readme file.
TextFile option will read and write into text file.
Pretty option will Pretty print the airlines flights to a text file or standard out by providing filename as '-'.
XmlFile option will read and write into xml file.

usage: java -jar target/airline-2023.0.0.jar [options] <args>
             args are (in this order):
                 airline The name of the airline
                 flightNumber The flight number
                 src Three-letter code of departure airport
                 depart Departure date and time (am/pm)
                 dest Three-letter code of arrival airport
                 arrive Arrival date and time (am/pm)
             options are (options may appear in any order):
                -xmlFile file Where to read/write the airline info
                -textFile file Where to read/write the airline info
                -pretty file Pretty print the airline’s flights to a text file or standard out (file -)
                -print Prints a description of the new flight
                -README Prints a README for this project and exits

             Date and time should be in the format: mm/dd/yyyy hh:mm am/pm
             It is an error to specify both -textFile and -xmlFile.
