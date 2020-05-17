#!/bin/bash +x

# cria veículos; um conjunto de 5 veículos
echo "=== === === === === === === === ==="

echo "adição veículo AAA1111"
curl -s -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "AAA1111", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

echo "adição veículo BBB2222"
curl -s -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "BBB2222", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

echo "adição veículo CCC3333"
curl -s -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "CCC3333", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

echo "adição veículo DDD4444"
curl -s -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "DDD4444", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

echo "adição veículo EEE5555"
curl -s -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "EEE5555", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

# coloca determinados veículos em manutenção; 3 dos 5 veículos ficarão em manutenção
echo "=== === === === === === === === ==="

echo "colocando veículo AAA1111 em reparo"
curl -s -w '\n' localhost:8080/vehicles/AAA1111/repair/do -X PATCH

echo "colocando veículo CCC3333 em reparo"
curl -s -w '\n' localhost:8080/vehicles/CCC3333/repair/do -X PATCH

echo "colocando veículo EEE5555 em reparo"
curl -s -w '\n' localhost:8080/vehicles/EEE5555/repair/do -X PATCH

# retira determinados veículos de manutenção; apenas 1 dos 3 veículos em manutenção voltará a ficar disponível
echo "removendo veículo CCC3333 do estado de reparo"
curl -s -w '\n' localhost:8080/vehicles/CCC3333/repair/done -X PATCH

# cria usuários
echo "=== === === === === === === === ==="

echo "cadastro do usuário user-a"
curl -s -w '\n' localhost:8080/users -H 'Content-Type: application/json' -d '{"nickname": "user-a"}'

echo "cadastro do usuário user-b"
curl -s -w '\n' localhost:8080/users -H 'Content-Type: application/json' -d '{"nickname": "user-b"}'

echo "cadastro do usuário user-c"
curl -s -w '\n' localhost:8080/users -H 'Content-Type: application/json' -d '{"nickname": "user-c"}'

# solicita viagens
echo "=== === === === === === === === ==="

echo "solicitando nova viagem para usuário user-a"
tripUUIDForUserA=$( curl -s -w '\n' localhost:8080/users/user-a/trips -H 'Content-Type: application/json' -d '{"fromLocation": "Rua José, Nr 18, centro", "toLocation": "Rua X, Nr 1098, bairro Y, cidade baixa"}' | jq .uuid | tr -d '"' )
echo "id da viagem para user-a; ${tripUUIDForUserA}"

echo "solicitando nova viagem para usuário user-b"
tripUUIDForUserB=$( curl -s -w '\n' localhost:8080/users/user-b/trips -H 'Content-Type: application/json' -d '{"fromLocation": "Rua ABC, Nr 96333, bairro UPI", "toLocation": "Rua PLSK, Nr 2222, bairro LLS, cidade LAOKLDK"}' | jq .uuid | tr -d '"' )
echo "id da viagem para userba; ${tripUUIDForUserB}"

echo "requisição inválida para solicitação de nova viagem para usuário user-c"
curl -s -w '\n' localhost:8080/users/user-c/trips -H 'Content-Type: application/json' -d '{"fromLocation": "", "toLocation": ""}'

# confirma embarque
echo "confirma embarque de viagem para usuário user-a, com id da viagem; ${tripUUIDForUserA}"
curl -s -w '\n' localhost:8080/users/user-a/trips/boarding/${tripUUIDForUserA} -X PATCH

echo "confirma embarque de viagem para usuário user-b, com id da viagem; ${tripUUIDForUserB}"
curl -s -w '\n' localhost:8080/users/user-b/trips/boarding/${tripUUIDForUserB} -X PATCH

# conclui viagens; somente usuário user-b vai confirmar fim da viagem
echo "confirma conclusão da viagem (desembarque) de viagem para usuário user-b, com id da viagem; ${tripUUIDForUserB}"
curl -s -w '\n' localhost:8080/users/user-b/trips/landing/${tripUUIDForUserB} -X PATCH

# lista veículos e seus estados
echo "=== === === === === === === === ==="

echo "avaliando status dos veículos da fronta"
echo "veículo AAA1111 deve estar em manutenção, pois ele foi colocado neste estado e não foi retirado; localização é a mesma do cadastro do veículo; Rua X, Nr 1098, bairro Y, cidade baixa"
echo "veículo BBB2222 deve estar em trânsito com o usuário user-a, pois foi efetuado o embarque mas não foi concluída a viagem; localização origem da viagem; Rua José, Nr 18, centro"
echo "veículo CCC3333 deve estar disponível para uma nova viagem, pois este veículo foi utilizado na viagem do usuário user-b com embarque e desembarque, ou seja, viagem concluída; localização é o destino da viagem; Rua PLSK, Nr 2222, bairro LLS, cidade LAOKLDK"
echo "veículo DDD4444 deve estar disponível, nenhuma viagem foi feita com este veículo; localização é a mesma do cadastro do veículo; Rua X, Nr 1098, bairro Y, cidade baixa"
echo "veículo EEE5555 deve estar em manutenção, pois ele foi colocado neste estado e não foi retirado; localização é a mesma do cadastro do veículo; Rua X, Nr 1098, bairro Y, cidade baixa"
curl -s -w '\n' localhost:8080/vehicles | jq