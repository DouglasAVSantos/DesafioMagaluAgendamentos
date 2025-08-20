# 📌 Desafio Magalu - API de Agendamentos

API desenvolvida em **Java 17 + Spring Boot** para gerenciamento de agendamentos, com validação centralizada no **Service**, tratamento de exceções via **ControllerAdvice**, documentação via **Swagger** e logs usando **SLF4J**.

Repositório: [https://github.com/DouglasAVSantos/DesafioMagaluAgendamentos](https://github.com/DouglasAVSantos/DesafioMagaluAgendamentos)

---

## ⚙️ Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Gradle
- Swagger (OpenAPI 3)
- SLF4J para logs
- JUnit 5 + Mockito (testes unitários)

---
## 📂 Estrutura do Projeto
```src/main/java/com/desafio/magaluAgendamento
├── controller # Endpoints REST
│ └── dto # DTOs de request e response
├── enums # Enums do projeto
├── exception # Exceptions e handlers
├── model # Entidades JPA
├── repository # Repositórios JPA
└── service # Regras de negócio

src/test/java/com/desafio/magaluAgendamento
├── controllerTestes # Testes unitários de controllers e advices
└── serviceTestes # Testes unitários de services
```
---

## 🚀 Funcionalidades
- Criar, buscar, listar e deletar agendamentos
- Validação centralizada no **Service**
- Tratamento de exceções customizado com mensagens claras
- Documentação via **Swagger**
- Logs de operações via **SLF4J**
- Cobertura de testes **100%** (Controller, ControllerAdvice e Service)

---

## 🧪 Testes

Para rodar todos os testes unitários:

```bash
./gradlew test
```
### Resultados:

Testes do Service: ✅ todos passando

Testes do Controller e ControllerAdvice: ✅ cobertura completa

Cobertura geral: 100%

---

## ▶️ Como executar o projeto
- Pré-requisitos

Java 17+

Gradle

PostgreSQL rodando (crie um banco para a aplicação)

- Configuração do banco

Edite o application.yml ou application.properties com os dados do PostgreSQL:

### Exemplo de yaml
```
spring:
datasource:
url: jdbc:postgresql://localhost:5432/seu_banco
username: seu_usuario
password: sua_senha
jpa:
hibernate:
ddl-auto: update
show-sql: true
```
---
# Rodando a aplicação

### Build do projeto
```./gradlew build```

### Rodar a aplicação
``./gradlew bootRun``

### A API estará disponível em:
👉 http://localhost:8080/agendamento

### Swagger UI:
👉 http://localhost:8080/swagger-ui.html ou /swagger-ui/index.html

# 📜 Endpoints principais
````
Método	Endpoint	        Descrição
POST	/agendamento	        Criar agendamento
GET	/agendamento	        Listar todos os agendamentos
GET	/agendamento/{id}	Buscar agendamento por ID
DELETE	/agendamento/{id}	Deletar agendamento por ID