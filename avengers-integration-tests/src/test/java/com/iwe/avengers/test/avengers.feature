Feature: Contem os testes de integracao para a API Gerenciadora dos Avengers

Background:
* url 'https://s1l920zsqk.execute-api.us-east-1.amazonaws.com/dev'

Scenario: Creates a new Avenger and search by Id

Given path 'avengers'
And request {name: 'Iron Man', secretIdentity: 'Tony Stark'}
When method post
Then status 201
And match response ==  {id: '#string', name: "Iron Man", secretIdentity: 'Tony Stark'}

* def savedAvenger = response

# Get Avenger by id
Given path 'avengers' , savedAvenger.id
When method get
Then status 200
And match $ == savedAvenger

Scenario: Updates the Avenger data

#Create a new Avenger
Given path 'avengers'
And request {name: 'Captain', secretIdentity: 'Steve'}
When method post
Then status 201

* def avengerToUpdate = response

Given path 'avengers', avengerToUpdate.id
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method put
Then status 200
And match $.id ==  avengerToUpdate.id
And match $.name == 'Captain America'
And match $.secretIdentity == 'Steve Rogers'

Scenario: Attempt to upgrade a non-existent Avenger
Given path 'avengers', 'sss-ddd-fff-eee'
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method put
Then status 404

Scenario: Deletes the Avenger by Id

#Create a new Avenger
Given path 'avengers'
And request {name: 'Hulk', secretIdentity: 'Bruce Banner'}
When method post
Then status 201

* def avengerToDelete = response

#Delete the Avenger
Given path 'avengers', avengerToDelete.id
When method delete
Then status 204

#Search deleted Avenger
Given path 'avengers', avengerToDelete
When method get
Then status 404

Scenario: Attempt to delete a non-existent Avenger
Given path 'avengers', 'sss-ddd-fff-eee'
When method delete
Then status 404

Scenario: Must return 400 for invalid creation payload

Given path 'avengers'
And request {name: 'Iron Man', identity: 'Tony Stark'}
When method post
Then status 400

Scenario: Must return 400 for invalid update payload

Given path 'avengers', 1
And request {name: 'Iron Man', identity: 'Tony Stark'}
When method put
Then status 400


Scenario: Deletes all Data
Given path 'avengers'
When method delete
Then status 200






