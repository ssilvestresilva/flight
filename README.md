# Run with docker

### How to run our application step by step

With docker intalled and the project cloned, go to the project repository and tip on console:
-> docker build -t flightapi .
It will take some minutes to complete. After that we can see if our image was created with:
-> docker image ls
If there's a Repository named flightapi, everything was fine and now we can run our image with:
-> docker run -p 8090:8090 flightapi

Now we can send a post to our project on Postman for example:
http://localhost:8090/api/v1/flight/avg
with the body:
{
	"flyFrom": "OPO",
	"flyTo": "LIS",
	"dateFrom": "2020/07/08",
	"dateTo": "2020/07/10"
}

To stop our application with docker, we need to run:
-> docker ps
This will show all images running, with the container id, we can stop this one running:
-> docker container stop "CONTAINER ID"
If we forgot the container id, we can check the information with:
-> docker ps -a
To run again, all we need to do is run the same command, like:
-> docker container start "CONTAINER ID"

