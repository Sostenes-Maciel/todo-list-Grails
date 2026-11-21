# TODO-List — API REST

## 1. Informações gerais

Backend desenvolvido com Grails Framework 8.0.0, utilizando arquitetura RESTful, GORM e PostgreSQL.

**URL base:** `http://localhost:8080`

**Formato de comunicação:** JSON

## 2. Entidade Tarefa

| Campo | Tipo | Descrição |
|---|---|---|
| id | Long | Identificador gerado pelo GORM |
| nome | String | Nome da tarefa |
| descricao | String | Descrição da tarefa |
| dataTermino | LocalDate | Prazo de conclusão |
| prioridade | Integer | Prioridade entre 1 e 5 |
| categoria | Categoria | Categoria associada |
| status | Enum | TODO, DOING ou DONE |
| horarioAlarme | LocalDateTime | Horário do alarme |

O status inicial de uma nova tarefa é `TODO`.

A categoria é opcional e, quando informada, deve corresponder a uma categoria existente.

## 3. Endpoints

### 3.1 Listar tarefas

**Método:** GET

**Endpoint:** `/api/tarefas`

**Resposta:** 200 OK

Retorna a lista de tarefas cadastradas.

### 3.2 Consultar tarefa

**Método:** GET

**Endpoint:** `/api/tarefas/{id}`

**Resposta:** 200 OK

Retorna os dados da tarefa identificada pelo ID.

**Erro:** 404 Not Found, quando a tarefa não existe.

### 3.3 Criar tarefa

**Método:** POST

**Endpoint:** `/api/tarefas`

**Content-Type:** application/json

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

**Resposta:** 201 Created

Retorna a tarefa criada.

**Erro:** 422 Unprocessable Entity, quando os dados não passam pelas validações.

### 3.4 Atualizar tarefa

**Método:** PUT

**Endpoint:** `/api/tarefas/{id}`

**Content-Type:** application/json

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

**Resposta:** 200 OK

Retorna os dados atualizados.

**Erros:**
- 404 Not Found: tarefa inexistente.
- 422 Unprocessable Entity: dados inválidos.

A implementação atual atualiza as propriedades informadas no JSON, preservando as propriedades omitidas.

### 3.5 Excluir tarefa

**Método:** DELETE

**Endpoint:** `/api/tarefas/{id}`

**Resposta:** 204 No Content

A tarefa é removida do banco de dados.

**Erro:** 404 Not Found, quando a tarefa não existe.

## 4. Regras de validação

| Regra | Comportamento |
|---|---|
| Prioridade | Obrigatória, entre 1 e 5 |
| Status | Não pode ser nulo |
| Status inicial | TODO |
| Categoria | Opcional, mas deve existir quando informada |
| Nome da categoria | Obrigatório e não vazio |

## 5. Códigos HTTP utilizados

| Código | Descrição |
|---|---|
| 200 | Operação realizada com sucesso |
| 201 | Recurso criado |
| 204 | Recurso excluído |
| 404 | Recurso não encontrado |
| 422 | Dados inválidos ou não processáveis |

## 6. Tecnologias utilizadas

- Grails Framework 8.0.0
- Groovy
- GORM
- Hibernate
- PostgreSQL 18.6
- Spock
- Gradle