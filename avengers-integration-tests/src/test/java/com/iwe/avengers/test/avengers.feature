Feature: Contem os testes de integracao para a API Gerenciadora dos Avengers

Background:
* url 'https://s1l920zsqk.execute-api.us-east-1.amazonaws.com/dev'

#lambda
Scenario: Retorna todos os Avengers

Given path 'avengers'
When method get
Then status 200
#And match response contains {id: '#string', name: 'Spider Man', secretIdentity: 'Peter Parker'}

#lambda
Scenario: Pesquisar Avenger por name ou secretIdentity

Given path 'avengers'
And param name = 'Captain'
And param secretIdentity = 'Rogers'
When method get 
Then status 200
#And match response == {id: "#string", name: 'Captain America', secretIdentity: 'Steve Rogers'}

#lambda
Scenario: Criação de um novo Avenger com sucesso

Given path 'avengers'
And request {name: 'Iron Man', secretIdentity: 'Tony Stark'}
When method post
Then status 201
And match response ==  {id: '#string', name: "Iron Man", secretIdentity: 'Tony Stark'}

* def retorno = response

Given path 'avengers' , response.id
When method get
Then status 200
#And match response == retorno


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




