# intro

O projeto corrente atende ao trabalho da matéria ***Webservices And Restful Technologies***, que propõe o
desenvolvimento de serviços web atendendo requisições RESTFUL para o gerenciamento de uma frota de carros autonomos e
também a possibilidade de usuários se inscreverem e solicitar viagens nestes carros.

###### Escola: FIAP
###### Curso: MBA FULLSTACK DEVELOPER, MICROSERVICES, CLOUD & IoT
###### Matéria: WEBSERVICES AND RESTFUL TECHNOLOGIES
###### Prof. EDUARDO FERREIRA GALEGO

| Aluno | RM |
| --- | --- |
| JEAN BRUNO SOUTO VILLETE | RM 335435 |
| MARCOS DE MENEZES SOUTO MOURA | RM 335907 |

> O projeto é versionado no github através do repositório [github.com/fiap-rebu](https://github.com/jeanvillete/fiap-rebu)  
> As atividades levantadas para serem desenvolvidas de acordo com cada ***caso de uso*** estão geridas no [projeto github/fiap-spring](https://github.com/jeanvillete/fiap-rebu/projects/1)

---

# instrução de execução

### a partir do binário; download e execução do jar empacotado
Uma das opções é o download do **jar** já empacotado, o que dispensaria a necessidade de utilização de ferramentas para build do código fonte.  
Necessário fazer download da versão corrente disponível em; [TBD](https://github.com/jeanvillete/-)  
Após o download, invocar comando abaixo via terminal;  

`$ java -jar fiap.rebu-0.0.1-RELEASE.jar`

### a partir do código fonte; clone e build/empacotamento do projeto
Após efetuar o clone do código, utilize o comando abaixo para excução da aplicação.  
**NOTA:** Requisitos, maven e Java 8.  

`$ mvn spring-boot:run`

---

# requisitos trabalho

Elaborar uma aplicação Web consumindo os Web Services de um sistema de controle de uma frota de veículos autônomos.  

Elaborar os serviços necessários para o controle de uma frota de veículos autônomos, ideia principal:
  - Usuário solicita um veículo;
  - Sistema seleciona um veículo que esteja
  - disponível e desloca até o local do usuário;
  - Usuário entra no veículo;
  - Sistema conduz o usuário até o local informado;

Pontos críticos para avaliação:
- Construção de API dentro dos padrões REST (visto em sala de aula);
- Documentação (OpenAPI, Postman etc);
- Aplicação Web consumindo os serviços.
- Código-fonte em um VSC (exemplo: GitHub).

Pontos extras:
- Persistência;
- Consumo de outras API's (exemplo: Geolocalização do Google Maps);
- Deploy na Nuvem;

Será necessário entregar aqui no Portal do Aluno:
- Nome da aplicação;
- Endereço do Código-fonte;
- Se hospedado, informar o endereço da aplicação;
- Documentação da API;
- Print’s ou vídeo comprovando a implementação (telas no navegador e chamadas no Postman).

---

# domínio e premissas

#### usuário (USER_DETAILS)
- o usuário que faz utilização do serviço de viagens com veículos autônomos
    - será mantido apenas um `nickname` (apelido/login) do usuário

#### localização (LOCATION)
- composto basicamente da informação de endereço (address), utilizado pela entidade veículo e viagem/corrida (TRIP).

#### veículo (VEHICLE)
- um veículo que faz parte da frota de automóveis autônomos.
- para o registro de um veículo, as informações deste são;
    - placa (identificador, não pode haver duplicidades)
    - localização (no registro inicial, deve-se indicar sua localização inicial, e depois de atender viagens, sua localização será dinâmica)

#### manutenção (RAPAIR)
- entidade composta de dados de quando um veículo entrou e saiu do estado de manutenção

#### corrida (TRIP)
- entidade que compõe e liga um usuário a um veículo e a viagem/corrida em si, com os dados de origem e destino

---

# casos de uso e seus endpoints

abaixo segue a lista de casos de uso e exemplos de requisições e respostas;  

#### listar veículos da frota; [i4]
- o caso de uso para listagem de veículos tem por objetivo devolver uma lista de payloads que represente os dados dos
veículos e que seja renderizado esta lista em um frontend, com as informações;
    - placa (identificador)
    - localização
    - status
        - Em Manutenção
        - Em Trânsito Buscar Passageiro
        - Em Trânsito Aguardando Embarque Passageiro
        - Em Trânsito Com Passageiro
        - Disponível

```
[request]
GET /vehicles

[response]
200 Ok
[
    {
        "plate": "ABC-1234",
        "location": "Rua X, Nr 1098, bairro Y, cidade baixa",
        "status": "RAPAIRING"
    },
    {
        "plate": "CBA-6633",
        "location": "Rua José, Nr 18, centro",
        "status": "ON_THE_WAY_TO_GET_PASSENGER"
    },
    {
        "plate": "ABC-0099",
        "location": "Rua Madureira, Nr 111, são paulo",
        "status": "HOLDING_ON_FOR_ON_BOARDING"
    },
    {
        "plate": "DDD-6633",
        "location": "Rua José, Nr 18, centro",
        "status": "TRIP_ON_THE_WAY"
    },
    {
        "plate": "ABC-4321",
        "location": "Rua José, Nr 18, centro",
        "status": "AVAILABLE"
    }
]
```

#### cadastrar novo veículo; 
- para cadastrar um veículo, deve-se fornecer um payload com os dados;
    - placa
    - localização

```
[request]
POST /vehicles
{
    "plate": "ZZZ-9999",
    "location": "Rua X, Nr 1098, bairro Y, cidade baixa"
}

[response]
201 Created
```

#### colocar um veículo em manutenção; 
- para atualização dos dados de um veículo, deve-se fornecer via query string a placa atual e um payload com os dados;
    - possível nova placa
    - localização
- caso a placa corrente fornecida via query string seja inválida ou não existir um registro para a mesma, devolver ***404 Not Found***

```
[request]
PATCH /vehicles/ABC-1234/repair/do

[response]
200 Ok
```

##### remover estado de manutenção do veículo; 
- para atualização dos dados de um veículo, deve-se fornecer via query string a placa atual e um payload com os dados;
    - possível nova placa
    - localização
- caso a placa corrente fornecida via query string seja inválida ou não existir um registro para a mesma, devolver ***404 Not Found***

```
[request]
PATCH /vehicles/ABC-1234/repair/done

[response]
200 Ok
```

#### cadastrar usuário; 
- para cadastrar um usuário, deve-se fornecer um payload com os dados;
    - nickname

```
[request]
POST /users/
{
    "nickname": "sampleusr"
}

[response]
201 Created
```

#### usuário solicita veículo para viagem; 
- para uma nova viagem, o usuário deve fornecer o local de encontro e o destino
- o nickname para login deve ser fornecido via "path variable"
- o retorno da criação da viagem é um identificador (uuid)

```
[request]
POST /users/sampleusr/trips/
{
    "fromLocation": "Rua José, Nr 18, centro",
    "toLocation": "Rua X, Nr 1098, bairro Y, cidade baixa"
}

[response]
201 Created
{
    "uuid": "7364259a-94aa-4c37-ad39-5e9837c0fd3e",
    "fromLocation": "Rua José, Nr 18, centro",
    "toLocation": "Rua X, Nr 1098, bairro Y, cidade baixa"
}
```

#### confirmar embarque da viagem; 
- usuário precisa confirmar o embarque na viagem

```
[request]
PATCH /users/sampleusr/trips/boarding/7364259a-94aa-4c37-ad39-5e9837c0fd3e

[response]
200 OK
```

#### confirmar conclusão da viagem; 
- ao concluir a viagem, usuário precisa confirmar fim da viagem
- veículo tem a localização alterada para o destino da viagem
- veículo tem o status alterado para disponível

```
[request]
PATCH /users/sampleusr/trips/landing/7364259a-94aa-4c37-ad39-5e9837c0fd3e

[response]
200 OK
```
