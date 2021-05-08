# Documentation REST API

## Endpoints
### /houses
Get House by ID\
`curl -i -X GET http://localhost:8080/pa165/rest/houses/1`

Create a new house\
`curl -X POST -i -H "Content-Type: application/json" --data '{"name":"Home","street":"Nova","code":null,"city":"Slovak","postCode":"90801", "country":"Slovakia", "running": true, "portalUserId": 1}' http://localhost:8080/pa165/rest/houses/create`

Update the fields of the house\
`curl -X PUT -i -H "Content-Type: application/json" --data '{"name":"Home","street":"Nova","code":null,"city":"Slovak","postCode":"90801", "country":"Slovakia"}' http://localhost:8080/pa165/rest/houses/1`

Change running status\
`curl -X PUT -i -H "Content-Type: application/json" --data '{"running": false}' http://localhost:8080/pa165/rest/houses/running/1`

Delete one house by ID\
`curl -i -X DELETE http://localhost:8080/pa165/rest/houses/1`

Get all Houses for user with given ID\
`curl -i -X GET http://localhost:8080/pa165/rest/houses/findByUser/1`
