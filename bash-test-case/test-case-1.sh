#!/bin/bash +x

# cria veículos; um conjunto de 5 veículos
curl -v -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "AAA1111", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'
curl -v -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "BBB2222", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'
curl -v -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "CCC3333", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'
curl -v -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "DDD4444", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'
curl -v -w '\n' localhost:8080/vehicles -H 'Content-Type: application/json' -d '{"plate": "EEE5555", "location": "Rua X, Nr 1098, bairro Y, cidade baixa"}'

# coloca determinados veículos em manutenção; 3 dos 5 veículos ficarão em manutenção
curl -v -w '\n' localhost:8080/vehicles/AAA1111/repair/do -X PATCH
curl -v -w '\n' localhost:8080/vehicles/CCC3333/repair/do -X PATCH
curl -v -w '\n' localhost:8080/vehicles/EEE5555/repair/do -X PATCH

# retira determinados veículos de manutenção; apenas 1 dos 3 veículos em manutenção voltará a ficar disponível
curl -v -w '\n' localhost:8080/vehicles/CCC3333/repair/done -X PATCH

# cria usuários
curl -v -w '\n' localhost:8080/users -H 'Content-Type: application/json' -d '{"nickname": "user-a"}'
curl -v -w '\n' localhost:8080/users -H 'Content-Type: application/json' -d '{"nickname": "user-b"}'
curl -v -w '\n' localhost:8080/users -H 'Content-Type: application/json' -d '{"nickname": "user-c"}'

# solicita viagens
# curl -v -w '\n' localhost:8080/users/user-a/trips -H 'Content-Type: application/json' -d '{"fromLocation": "Rua José, Nr 18, centro", "toLocation": "Rua X, Nr 1098, bairro Y, cidade baixa"}'
# curl -v -w '\n' localhost:8080/users/user-b/trips -H 'Content-Type: application/json' -d '{"fromLocation": "Rua ABC, Nr 96333, bairro UPI", "toLocation": "Rua PLSK, Nr 2222, bairro LLS, cidade LAOKLDK"}'
# curl -v -w '\n' localhost:8080/users/user-c/trips -H 'Content-Type: application/json' -d '{"fromLocation": "", "toLocation": ""}'

# confirma embarque
# conclui viagens
# lista veículos e seus estados