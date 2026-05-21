# 🚗 API Concessionária

API REST desenvolvida em Java com Spring Boot para gerenciamento de clientes e veículos de uma concessionária.
Projeto individual desenvolvido como atividade prática do curso de API do SERRATEC.

---

## 👨‍💻 Autor

**Victor Lima**
---

## 🛠️ Tecnologias

- Java 17+
- Spring Boot 3
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Lombok
- DevTools

---

## 📦 Estrutura de Pacotes

```
org.serratec.concessionaria
├── controller      → recebe as requisições HTTP e delega para o service
├── service         → contém as regras de negócio
├── repository      → comunicação com o banco de dados
├── entity          → mapeamento das tabelas do banco
├── model           → DTOs de request e response
└── exception       → exceptions customizadas e handler global de erros
```

---

## ⚙️ Como rodar o projeto

### Pré-requisitos
- Java 17 ou superior instalado
- PostgreSQL instalado e rodando
- Maven instalado

### 1. Clonar o repositório
```bash
git clone https://github.com/aralimavictor/api-concessionaria.git
cd api-concessionaria
```

### 2. Criar o banco de dados
No pgAdmin ou psql, execute:
```sql
CREATE DATABASE concessionaria;
```

### 3. Habilitar a extensão unaccent
Ainda no pgAdmin, com o banco `concessionaria` selecionado:
```sql
CREATE EXTENSION IF NOT EXISTS unaccent;
```
> Essa extensão é necessária para a busca de clientes por nome ignorar acentuação e maiúsculas/minúsculas.

### 4. Rodar a aplicação
```bash
mvn spring-boot:run
```

API disponível em `http://localhost:8080`

> As tabelas `clientes` e `veiculos` são criadas automaticamente pelo Hibernate na primeira execução.

---

## 📋 Endpoints

### Clientes

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | /api/v1/cliente | Cadastrar cliente |
| GET | /api/v1/cliente | Listar todos os clientes |
| GET | /api/v1/cliente?nome= | Buscar por nome |
| GET | /api/v1/cliente?cpf= | Buscar por CPF |
| DELETE | /api/v1/cliente/{id} | Remover cliente |

### Veículos

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | /api/v1/veiculo | Cadastrar veículo |
| GET | /api/v1/veiculo | Listar todos os veículos |
| GET | /api/v1/veiculo?placa= | Buscar por placa |
| GET | /api/v1/veiculo?marca= | Buscar por marca |
| GET | /api/v1/veiculo?modelo= | Buscar por modelo |
| PUT | /api/v1/veiculo/{id} | Atualizar veículo |
| DELETE | /api/v1/veiculo/{id} | Remover veículo |

---

## 📨 Exemplos de Request

### Cadastrar Cliente
```json
POST /api/v1/cliente
{
  "nome": "João Silva",
  "telefone": "22999990000",
  "cpf": "12345678900",
  "email": "joao@email.com"
}
```

### Cadastrar Veículo
```json
POST /api/v1/veiculo
{
  "clienteId": "uuid-do-cliente",
  "marca": "Toyota",
  "modelo": "Corolla",
  "ano": 2022,
  "valor": 120000.00,
  "placa": "ABC1D23",
  "maximoDesconto": 5000.00,
  "vendido": false
}
```

### Atualizar Veículo (campos opcionais — envie só o que quiser alterar)
```json
PUT /api/v1/veiculo/{id}
{
  "vendido": true,
  "valorVenda": 115000.00
}
```

---

## ⚠️ Regras de Negócio

- CPF do cliente deve ser único
- Placa do veículo deve ser única
- O campo `valorVenda` é obrigatório quando `vendido` for `true`
- O ano do veículo deve ser maior ou igual a 1900
- O valor do veículo deve ser maior ou igual a 1
- O máximo de desconto deve ser maior ou igual a 0
- Todos os campos são obrigatórios exceto `valorVenda`
- A busca por nome ignora acentuação e diferença entre maiúsculas/minúsculas

---

## 🚨 Tratamento de Erros

| Status | Situação |
|--------|----------|
| 400 | Dados inválidos ou regra de negócio violada |
| 404 | Recurso não encontrado |
| 409 | CPF ou placa já cadastrados |
| 500 | Erro interno do servidor |

Exemplo de resposta de erro:
```json
{
  "status": 409,
  "mensagem": "CPF já cadastrado: 12345678900",
  "timestamp": "2026-05-21T10:30:00"
}
```

---

## 🔒 Segurança e Boas Práticas

- Separação em camadas: controller, service, repository, entity, model e exception
- DTOs separados para request e response — a entity nunca é exposta diretamente
- `VeiculoUpdateRequest` separado do `VeiculoRequest` — atualização parcial sem quebrar validações do cadastro
- Injeção de dependências via construtor com `@RequiredArgsConstructor` do Lombok
- Handler global de exceções com respostas HTTP semânticas

---
