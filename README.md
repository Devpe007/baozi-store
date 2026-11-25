# Baozi Store — API REST

API REST acadêmica para gerenciamento de clientes, produtos e pedidos da loja fictícia Baozi Store.

## Tecnologias

- Java 25
- Spring Boot 4.2.0
- Spring Web MVC
- Spring Data JPA / Hibernate
- MySQL 8.4
- Maven
- JSON

## Requisitos

- JDK 25
- Docker com o container MySQL `mysql8` em execução e porta `3306` publicada em `127.0.0.1:3306`
- Maven Wrapper (`./mvnw`, incluído no projeto gerado pelo Spring Initializr)

## Banco de dados

O banco utilizado é `baozi_store`, com usuário `baozi_user`. A senha não fica versionada no projeto.

No terminal, defina a senha que foi configurada no MySQL:

```bash
export DB_PASSWORD='SUA_SENHA_DO_MYSQL'
```

Se o container `mysql8` estiver parado:

```bash
docker start mysql8
```

O banco e o usuário devem existir. Se ainda não existirem, execute no cliente MySQL:

```sql
CREATE DATABASE baozi_store CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'baozi_user'@'%' IDENTIFIED BY 'SUA_SENHA';
GRANT ALL PRIVILEGES ON baozi_store.* TO 'baozi_user'@'%';
```

## Executar

Na raiz do projeto:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

O Hibernate cria/atualiza as tabelas automaticamente durante a execução (`spring.jpa.hibernate.ddl-auto=update`).

## Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/clientes` | Criar cliente |
| GET | `/api/clientes` | Listar clientes |
| GET | `/api/clientes/{id}` | Consultar cliente |
| PUT | `/api/clientes/{id}` | Atualizar cliente |
| DELETE | `/api/clientes/{id}` | Excluir cliente |
| POST | `/api/produtos` | Criar produto |
| GET | `/api/produtos` | Listar produtos |
| GET | `/api/produtos/{id}` | Consultar produto |
| PUT | `/api/produtos/{id}` | Atualizar produto |
| DELETE | `/api/produtos/{id}` | Excluir produto |
| POST | `/api/pedidos` | Criar pedido |
| GET | `/api/pedidos` | Listar pedidos |
| GET | `/api/pedidos/{id}` | Consultar pedido |
| PUT | `/api/pedidos/{id}` | Atualizar pedido |
| DELETE | `/api/pedidos/{id}` | Excluir pedido |

## Exemplos JSON

### Cliente

`POST /api/clientes`

```json
{
  "nome": "Pedro5181300",
  "clienteDesde": "2026-11-25"
}
```

### Produto

`POST /api/produtos`

```json
{
  "nome": "Baozi Tradicional",
  "preco": 8.50,
  "estoque": true
}
```

### Pedido

Use os IDs retornados nas respostas de criação de cliente e produto.

`POST /api/pedidos`

```json
{
  "clienteId": 1,
  "produtoId": 1,
  "quantidade": 5
}
```

A API verifica se o cliente e o produto informados existem. A quantidade deve ser positiva.

## Postman

Importe `postman/Baozi-Store.postman_collection.json`. A coleção contém requisições de cadastro, listagem, consulta, atualização e exclusão. Execute os POSTs de cliente e produto antes do POST de pedido; a coleção guarda automaticamente os IDs retornados.

**Importante para a entrega:** faça as requisições no Postman instalado no seu computador e capture prints reais das respostas. Não use exemplos deste README como evidência de execução.

## Estrutura

```text
src/main/java/com/baozistore/baozi_store/
├── controller/
├── exception/
├── model/
└── repository/
```
