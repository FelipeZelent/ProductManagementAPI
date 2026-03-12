# Product Management API

API REST para gerenciamento de produtos com autenticação e autorização via JWT, desenvolvida em Spring Boot.  
Permite cadastro e autenticação de usuários, além de operações de CRUD de produtos protegidas por token.

![Demonstração do Swagger UI](./.docs/swagger-ui-screenshot.png)

---

## 🚀 Como Executar

Você pode rodar este projeto de duas maneiras principais:

### Opção 1: Usando Docker

Esta é a maneira mais fácil, pois o Docker configurará o banco de dados e a API automaticamente em containers separados.

```bash
# Na raiz do projeto, execute o comando:
docker-compose up -d --build
```
A API ficará disponível em `http://localhost:8080`.

> **Nota:** Na primeira execução, o banco de dados pode levar alguns segundos para inicializar. A API aguardará automaticamente até que ele esteja pronto.

### Opção 2: Localmente usando Maven

1. **Banco de Dados**: Certifique-se de ter um PostgreSQL rodando localmente na porta `5432` com as seguintes credenciais padrão:
   - Banco de dados: `productdb`
   - Usuário: `postgres`
   - Senha: `postgres`
   *(Caso precise, ajuste essas configurações no arquivo `src/main/resources/application.properties`)*

2. **Iniciando a Aplicação**: No terminal, na raiz do projeto, execute:
```bash
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

---

## 📖 Documentação da API (Swagger / OpenAPI)

A documentação interativa da API já está configurada com o **SpringDoc OpenAPI**. Após a aplicação estar rodando, você pode testar todos os endpoints pela interface:

- **Interface Visual (Swagger UI)**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **JSON Docs**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🛠️ Tecnologias e Stack

- **Java 17**
- **Spring Boot 3** (Web, Data JPA, Security, Validation)
- **PostgreSQL**
- **JWT** (`io.jsonwebtoken`) para autenticação
- **Lombok**
- **Maven**
- **Docker & Docker Compose**

---

## 🔒 Autenticação

A aplicação utiliza autenticação baseada em tokens JWT. O fluxo básico de uso é:

1. Registre um novo usuário através do endpoint `POST /api/auth/register`.
2. Realize o login no endpoint `POST /api/auth/login`.
3. Utilize o token gerado passando-o no header `Authorization` de todas as chamadas protegidas:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

---

## 🔀 Endpoints Principais

### Autenticação (`/api/auth`)

- `POST /api/auth/register`: Registra um novo usuário no sistema.
- `POST /api/auth/login`: Valida as credenciais e retorna o token JWT.

*Exemplo de Body (Registro e Login):*
```json
{
  "name": "Usuário Teste",           // Apenas no Register
  "email": "user@example.com",
  "password": "senha123",
  "role": "ADMIN"                    // Apenas no Register (Opcional)
}
```

### Produtos (`/api/products`)

*Atenção: A maioria dos endpoints de produtos são protegidos e exigem que o token JWT seja enviado no Header.*

- `POST /api/products`: Cria um novo produto no banco de dados.
- `GET /api/products`: Retorna uma lista paginada de todos os produtos (suporta parâmetros na URL: `page`, `size`, `sort`).
- `GET /api/products/{id}`: Retorna os dados de um produto específico através do ID.
- `PUT /api/products/{id}`: Atualiza completamente os dados de um produto existente.
- `DELETE /api/products/{id}`: Remove o produto do sistema.

---

## ⚠️ Tratamento de Erros

A API possui um controle global de exceções coordenado pela classe `GlobalExceptionHandler`. 
Erros de validação (ex: campos obrigatórios não preenchidos) e exceções de negócio são capturados e retornam um formato padronizado amigável (`ApiError`), incluindo detalhes claros sobre os campos que falharam.

---

## 🧪 Testes

A aplicação conta com testes unitários e de integração para garantir seu correto funcionamento. Para executar toda a suíte de testes, rode:

```bash
mvn test
```
