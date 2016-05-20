To run this project from within Maven use
    mvn camel:run

curl -X POST -H "Content-Type: application/json" -d '{"name":"poc-hq-dev", "duration":0.25, "employeeId":111007}' http://localhost:8080/activities/
curl http://localhost:8080/activities/1
curl -X PUT -H "Content-Type: application/json" -d '{"id":1, "name":"poc-hq-dev", "duration":0.75, "employeeId":111007}' http://localhost:8080/activities/
curl http://localhost:8080/activities
curl -X DELETE http://localhost:8080/activities/1
