#!/bin/bash +x

# cria veículos; um conjunto de 5 veículos
echo "adição veículo AAA1111"
curl -v -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "AAA1111", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

echo "adição veículo BBB2222"
curl -v -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "BBB2222", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

echo "adição veículo CCC3333"
curl -v -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "CCC3333", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

echo "adição veículo DDD4444"
curl -v -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "DDD4444", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

echo "adição veículo EEE5555"
curl -v -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "EEE5555", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

# coloca determinados veículos em manutenção; 3 dos 5 veículos ficarão em manutenção
echo "colocando veículo AAA1111 em reparo"
curl -v -w '\n' localhost:8080/vehicles/AAA1111/repair/do -X PATCH

echo "colocando veículo CCC3333 em reparo"
curl -v -w '\n' localhost:8080/vehicles/CCC3333/repair/do -X PATCH

echo "colocando veículo EEE5555 em reparo"
curl -v -w '\n' localhost:8080/vehicles/EEE5555/repair/do -X PATCH

# retira determinados veículos de manutenção; apenas 1 dos 3 veículos em manutenção voltará a ficar disponível
echo "removendo veículo CCC3333 do estado de reparo"
curl -v -w '\n' localhost:8080/vehicles/CCC3333/repair/done -X PATCH

# cria usuários
echo "cadastro do usuário user-a"
curl -v -w '\n' localhost:8080/users -H 'Content-Type: application/json' -d '{"nickname": "user-a"}'

echo "cadastro do usuário user-b"
curl -v -w '\n' localhost:8080/users -H 'Content-Type: application/json' -d '{"nickname": "user-b"}'

echo "cadastro do usuário user-c"
curl -v -w '\n' localhost:8080/users -H 'Content-Type: application/json' -d '{"nickname": "user-c"}'

# solicita viagens
echo "solicitando nova viagem para usuário user-a"
tripUUIDForUserA=$( curl -v -w '\n' localhost:8080/users/user-a/trips -H 'Content-Type: application/json' -d '{"fromLocation": "Rua José, Nr 18, centro", "toLocation": "Rua X, Nr 1098, bairro Y, cidade baixa"}' | jq .uuid | tr -d '"' )
echo "id da viagem para user-a; ${tripUUIDForUserA}"

echo "solicitando nova viagem para usuário user-b"
tripUUIDForUserB=$( curl -v -w '\n' localhost:8080/users/user-b/trips -H 'Content-Type: application/json' -d '{"fromLocation": "Rua ABC, Nr 96333, bairro UPI", "toLocation": "Rua PLSK, Nr 2222, bairro LLS, cidade LAOKLDK"}' | jq .uuid | tr -d '"' )
echo "id da viagem para userba; ${tripUUIDForUserB}"

echo "requisição inválida para solicitação de nova viagem para usuário user-c"
curl -v -w '\n' localhost:8080/users/user-c/trips -H 'Content-Type: application/json' -d '{"fromLocation": "", "toLocation": ""}'

# confirma embarque
echo "confirma embarque de viagem para usuário user-a, com id da viagem; ${tripUUIDForUserA}"
curl -v -w '\n' localhost:8080/users/user-a/trips/boarding/${tripUUIDForUserA} -X PATCH

echo "confirma embarque de viagem para usuário user-b, com id da viagem; ${tripUUIDForUserB}"
curl -v -w '\n' localhost:8080/users/user-b/trips/boarding/${tripUUIDForUserB} -X PATCH

# conclui viagens; somente usuário user-b vai confirmar fim da viagem

# lista veículos e seus estados