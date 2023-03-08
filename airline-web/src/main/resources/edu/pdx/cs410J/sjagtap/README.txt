This is a README file!

Name: Saylee Mohan Jagtap
Odin ID: sjagtap
PSU ID: 972267084


This application creates an Airline object with Airline name and all the flights for this airline using REST-ful API.
Each flight object has flight number, source , depart date time, destination, arrive date time.
After creating Airline it reads/writes the object's data into the text file.

The application takes five options host, port, search, print, README.
Host option specifies on which computer server will run
Port option specifies that which port the server is listening to
Search option will search flights for specified airline along with source and destination or only with specified airline name
Print option prints the details of all flights
README option will print the readme file

To run the jetty server use following command:
$ mvnw jetty:run
or
$ ./mvnw jetty:run

usage: java -jar target/airline-client.jar -host [host] -port [port] <args>
             args are (in this order):
                 airline The name of the airline
                 flightNumber The flight number
                 src Three-letter code of departure airport
                 depart Departure date and time (am/pm)
                 dest Three-letter code of arrival airport
                 arrive Arrival date and time (am/pm)
             options are (options may appear in any order):
                -host hostname --> Host computer on which the server runs
                -port port -->  Port on which the server is listening
                -search --> Search for flights
                -print --> Prints a description of the new flight
                -README --> Prints a README for this project and exits

             Date and time should be in the format: mm/dd/yyyy hh:mm am/pm
             It is an error to specify a host without a port and vice versa
