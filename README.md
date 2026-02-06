# Product Management API

API REST para gerenciamento de produtos com autenticação e autorização via JWT, desenvolvida em Spring Boot.  
Permite cadastro e autenticação de usuários, além de operações de CRUD de produtos protegidas por token.

## Stack

- Java 17  
- Spring Boot 3 (Web, Data JPA, Security, Validation)  
- PostgreSQL  
- JWT (io.jsonwebtoken)  
- Lombok  
- Maven

## Funcionalidades

- Registro e login de usuários (`/api/auth`)
- Geração de token JWT
- Proteção de endpoints com Spring Security + JWT
- CRUD completo de produtos (`/api/products`)
- Paginação de produtos (`Pageable`)
- Tratamento global de erros (via `GlobalExceptionHandler` e `ApiError`)

## Requisitos

- Java 17+
- Maven 3+
- PostgreSQL rodando localmente
  - Banco padrão: `productdb`
  - Usuário: `postgres`
  - Senha: `postgres`

Você pode ajustar essas configurações em `src/main/resources/application.properties`.

## Configuração

Arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/productdb
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
server.port=8080

security.jwt.secret=...        # altere para um segredo forte em produção
security.jwt.expiration-ms=3600000
```

## Como executar

```bash
# na raiz do projeto
mvn spring-boot:run
```

A API ficará disponível em: `http://localhost:8080`.

## Autenticação

A autenticação é baseada em JWT. Fluxo:

1. Registrar usuário em `/api/auth/register`
2. Fazer login em `/api/auth/login`
3. Usar o token retornado no header `Authorization` das chamadas protegidas:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

### Endpoints de Autenticação

`AuthController` (`/api/auth`):

- `POST /api/auth/register`  
  - Body (JSON):
    ```json
    {
      "name": "Usuário Teste",
      "email": "user@example.com",
      "password": "senha123",
      "role": "ADMIN" 
    }
    ```
  - Retorno:
    ```json
    {
      "token": "jwt..."
    }
    ```

- `POST /api/auth/login`  
  - Body (JSON):
    ```json
    {
      "email": "user@example.com",
      "password": "senha123"
    }
    ```
  - Retorno:
    ```json
    {
      "token": "jwt..."
    }
    ```

## Endpoints de Produtos

`ProductController` (`/api/products`) – normalmente protegidos por JWT.

- `POST /api/products`  
  - Cria um produto.
  - Body (JSON, `ProductCreateRequest`), ex.:
    ```json
    {
      "name": "Notebook",
      "description": "Notebook i7",
      "price": 4500.0,
      "stock": 10
    }
    ```
  - Response: objeto `Product` criado.

- `GET /api/products/{id}`  
  - Busca um produto por ID.

- `GET /api/products`  
  - Lista paginada de produtos.  
  - Suporta parâmetros de paginação padrão (`page`, `size`, `sort`).

- `PUT /api/products/{id}`  
  - Atualiza um produto existente.
  - Body (JSON, `ProductUpdateRequest`).

- `DELETE /api/products/{id}`  
  - Remove um produto (HTTP 204 em sucesso).

## Tratamento de Erros

- Erros de validação e exceções de negócio são tratados por `GlobalExceptionHandler`.
- Resposta de erro segue o formato de `ApiError`, incluindo mensagem, status e detalhes dos campos quando aplicável.

## Testes

Para executar os testes:

```bash
mvn test
```

## Docker

Para rodar a aplicação com banco de dados usando Docker Compose:

```bash
docker-compose up -d --build
```

A API ficará disponível em `http://localhost:8080` e o banco de dados PostgreSQL na porta `5432`.

---

## Swagger / OpenAPI

A documentação interativa da API já está configurada com **SpringDoc OpenAPI**.
Após a aplicação estar rodando, acesse:

- Interface Visual (Swagger UI): `http://localhost:8080/swagger-ui.html`
- JSON Docs: `http://localhost:8080/v3/api-docs`

