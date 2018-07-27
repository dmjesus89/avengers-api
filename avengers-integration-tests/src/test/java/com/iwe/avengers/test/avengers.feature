Feature: Contem os testes de integracao para a API Gerenciadora dos Avengers

Background:
* url 'https://s1l920zsqk.execute-api.us-east-1.amazonaws.com/dev'

Scenario: Creates a new Avenger and search by Id and Name

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
And match response == savedAvenger

# Get Avenger By Name
Given path 'avengers'
And param name = savedAvenger.name
When method get 
Then status 200
And match response contains savedAvenger












#lambda
Scenario: Deve atualizar dados de um Avenger

Given path 'avengers', 1
And request {name: 'Captain America', secretIdentity: 'Steve Rogers'}
When method put
Then status 200
And match response ==  {id: "1", name: 'Captain America', secretIdentity: 'Steve Rogers'}

#lambda
Scenario: Deve remover um Avenger por id

Given path 'avengers', 1
When method delete
Then status 204

Scenario: Deve retornar 400 para payload invalido de criacao

Given path 'avengers'
And request {name: 'Iron Man', identity: 'Tony Stark'}
When method post
Then status 400

Scenario: Deve retornar 400 para payload invalido de alteracao

Given path 'avengers', 1
And request {name: 'Iron Man', identity: 'Tony Stark'}
When method put
Then status 400




