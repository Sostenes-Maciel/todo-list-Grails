# TODO-List — API REST

## 1. Informações gerais

Backend desenvolvido com Grails Framework 8.0.0, utilizando arquitetura RESTful, GORM e PostgreSQL.

**URL base:** `http://localhost:8080`

**Formato de comunicação:** JSON

## 2. Tecnologias utilizadas

- Grails Framework 8.0.0
- Groovy
- Spring Boot
- GORM
- Hibernate
- PostgreSQL 18.6
- Gradle
- Spock

## 3. Modelo de dados

### Tarefa

| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador gerado pelo GORM |
| nome | String | Nome da tarefa |
| descricao | String | Descrição da tarefa |
| dataTermino | LocalDate | Prazo de conclusão |
| prioridade | Integer | Prioridade entre 1 e 5 |
| categoria | Categoria | Categoria associada |
| status | Enum | Estado atual da tarefa |
| horarioAlarme | LocalDateTime | Horário do alarme |

### Categoria

| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador gerado pelo GORM |
| nome | String | Nome da categoria |

### Status

A tarefa possui três estados possíveis:

| Estado | Descrição | Próximo estado |
|---|---|---|
| TODO | A Fazer | DOING |
| DOING | Em Andamento | DONE |
| DONE | Concluído | DONE |

O status inicial de uma nova tarefa é `TODO`.

## 4. Endpoints

### 4.1 Listar tarefas

**Método:** GET

**Endpoint:** `/api/tarefas`

**Resposta:** `200 OK`

Retorna a lista de tarefas cadastradas.

### 4.2 Consultar tarefa por ID

**Método:** GET

**Endpoint:** `/api/tarefas/{id}`

**Resposta:** `200 OK`

Retorna os dados da tarefa identificada pelo ID.

**Erro:** `404 Not Found`, quando a tarefa não existe.

### 4.3 Criar tarefa

**Método:** POST

**Endpoint:** `/api/tarefas`

**Content-Type:** `application/json`

**Exemplo de requisição:**

```json
{
    "nome": "Estudar Grails",
    "descricao": "Implementar API REST",
    "dataTermino": "2026-12-10",
    "prioridade": 3,
    "status": "TODO"
}
```

A categoria é opcional. Quando informada, deve ser enviada por meio do campo `categoriaId`, com o identificador de uma categoria existente.

**Resposta:** `201 Created`

Retorna a tarefa criada, incluindo seu identificador.

**Erro:** `422 Unprocessable Entity`, quando os dados não passam pelas validações.

### 4.4 Atualizar tarefa completamente

**Método:** PUT

**Endpoint:** `/api/tarefas/{id}`

**Content-Type:** `application/json`

Substitui a representação da tarefa pelos dados enviados.

Os campos obrigatórios devem ser informados. Campos opcionais omitidos são redefinidos como nulos.

**Exemplo de requisição:**

```json
{
    "nome": "Estudar Grails avançado",
    "descricao": "Revisar GORM e REST",
    "dataTermino": "2026-12-15",
    "prioridade": 4,
    "status": "DOING"
}
```

**Resposta:** `200 OK`

Retorna os dados atualizados.

**Erros:**
- `404 Not Found`: tarefa inexistente.
- `422 Unprocessable Entity`: dados inválidos.

### 4.5 Atualizar tarefa parcialmente

**Método:** PATCH

**Endpoint:** `/api/tarefas/{id}`

**Content-Type:** `application/json`

Permite modificar somente os campos informados na requisição, preservando os demais atributos da tarefa.

**Exemplo de requisição:**

```json
{
    "descricao": "Descrição atualizada parcialmente"
}
```

Nesse exemplo, somente a descrição será alterada. O nome, a prioridade, o status e os demais atributos serão preservados.

**Resposta:** `200 OK`

Retorna os dados atualizados.

**Erros:**
- `404 Not Found`: tarefa inexistente.
- `422 Unprocessable Entity`: dados inválidos.

### 4.6 Excluir tarefa

**Método:** DELETE

**Endpoint:** `/api/tarefas/{id}`

Remove a tarefa identificada pelo ID do banco de dados.

**Resposta:** `204 No Content`

**Erro:** `404 Not Found`, quando a tarefa não existe.

## 5. Regras de validação

| Regra | Comportamento |
|---|---|
| Prioridade | Obrigatória, entre 1 e 5 |
| Status | Obrigatório |
| Status inicial | TODO |
| Categoria | Opcional, mas deve existir quando informada |
| Nome da categoria | Obrigatório e não vazio |

As validações são realizadas pelas Domain Classes utilizando os recursos do GORM.

## 6. Códigos HTTP utilizados

| Código | Descrição |
|---|---|
| 200 | Operação realizada com sucesso |
| 201 | Recurso criado |
| 204 | Recurso excluído |
| 404 | Recurso não encontrado |
| 422 | Dados inválidos ou não processáveis |

## 7. Arquitetura e organização

A aplicação segue o padrão MVC, com separação de responsabilidades:

- **Controller:** recebe as requisições HTTP e retorna respostas JSON.
- **Service:** concentra as operações de negócio e o acesso aos dados.
- **Domain Classes:** representam as entidades e suas validações.
- **GORM:** realiza o mapeamento objeto-relacional e a persistência.
- **PostgreSQL:** armazena os dados permanentemente.

O fluxo de comunicação segue:

`Controller → Service → Domain Class/GORM → PostgreSQL`

## 8. Testes automatizados

Foram implementados testes utilizando Spock.

| Classe de teste | Objetivo |
|---|---|
| StatusSpec | Validar as transições de status |
| TarefaSpec | Verificar as validações da entidade |
| TarefaServiceSpec | Testar as operações do serviço |
| TarefaApiSpec | Validar os endpoints e o CRUD |

Os testes de integração utilizam o ambiente de testes configurado com H2, mantendo o banco PostgreSQL de desenvolvimento independente.

## 9. Execução do projeto

### Pré-requisitos

- Java 21 ou superior compatível.
- Grails Framework 8.0.0.
- PostgreSQL.
- Banco de dados `todolist_bd` criado.

### Configuração das credenciais

As credenciais são recebidas por variáveis de ambiente:

```properties
TODO_DB_USERNAME=seu_usuario
TODO_DB_PASSWORD=sua_senha
```

### Inicialização

Na pasta `todolistBackend`, execute:

```bash
./gradlew bootRun
```

A aplicação ficará disponível em:

`http://localhost:8080`

### Execução dos testes

Testes unitários:

```bash
./gradlew test
```

Testes de integração:

```bash
./gradlew integrationTest
```

## 10. Escopo da implementação

Esta versão contempla o desenvolvimento do backend com Grails Framework, disponibilização de operações CRUD por API RESTful e persistência utilizando GORM e PostgreSQL.

Não faz parte do escopo desta atualização a integração com o frontend ou a implementação de uma nova interface de usuário.