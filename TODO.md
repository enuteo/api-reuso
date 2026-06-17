# Contratos da API — api-reuso

> Single Source of Truth dos contratos. Alinhado ao banco **`db_reuso`** (clone do
> schema original `oficina`) e ao backend Spring Boot. Backend em `:8080`,
> frontend servido pelo XAMPP (`:80`). CORS liberado globalmente (`WebConfig`).

---

# API AUTH

## POST /auth/login

Autentica por `email` + `senha` (tabela `usuario`). O front redireciona por `classe`
(0 → `dashboard-usuario.html` | 1 → `dashboard-cliente.html`).

- Request body:
```json
{
  "email": "string",
  "senha": "string"
}
```
- Response 200:
```json
{
  "nome": "string",
  "classe": "integer (0 = Funcionário | 1 = Cliente)",
  "keywords": ["string"]
}
```
> `keywords` = serviços acessíveis ao usuário: funcionário via `acesso_funcionario`→`servico.keyword`; cliente via `servico_cliente`→`servico.keyword`.

- Response 400: corpo em **texto puro** `Usuário ou senha inválidos` (via `GlobalExceptionHandler`).

> 🔒 **Segurança:** a `senha` é armazenada com **hash BCrypt** (`spring-security-crypto`). O cadastro grava o hash; o login valida com `PasswordEncoder.matches`. Senhas antigas em texto puro são migradas automaticamente no boot (`PasswordMigrationRunner`).

---

# API PEÇAS (catálogo — tabela `peca`)

## GET /api/pecas
- Sem body. Retorna o catálogo.
- Response 200:
```json
[
  { "id": "integer", "nome": "string", "preco": "number" }
]
```

## GET /api/pecas/{id}
- Path param `id` (integer). Response 200: `{ "id", "nome", "preco" }`.

## POST /api/pecas
- Request body:
```json
{ "nome": "string", "preco": "number" }
```
- Response 200: peça criada, com `id` gerado (`INSERT INTO peca`).

## PUT /api/pecas/{id}
- Request body: `{ "nome": "string", "preco": "number" }`.
- Response 200: peça atualizada (`UPDATE peca SET nome, preco WHERE id`).

## DELETE /api/pecas/{id}
- Sem body. Response 204 (`DELETE FROM peca WHERE id`).

---

# API ESTOQUE (inventário — tabela `estoque` JOIN `peca`)

## GET /api/estoque
- Sem body. Retorna quantidades por peça.
- Response 200:
```json
[
  { "id": "integer", "pecaId": "integer", "pecaNome": "string", "quantidade": "integer" }
]
```

---

# API CLIENTES

## POST /api/clientes

Cadastro de cliente. Cria, em uma transação, um `usuario` (`classe = 1`) **e** um `cliente` vinculado. O login posterior é por `email` + `senha`.

- Request body:
```json
{
  "nome": "string",
  "telefone": "string",
  "email": "string",
  "senha": "string"
}
```
- Response 201:
```json
{ "id": "integer", "nome": "string", "email": "string", "telefone": "string" }
```
- Response 400 (texto puro): `Já existe uma conta com esse e-mail.`

---

# Modelo de dados (`db_reuso`)

Schema completo clonado de `oficina`. Scripts versionados em `database/`:
- `database/db_reuso.sql` — schema + seed do `db_reuso`
- `database/oficina_backup.sql` — backup do banco original

| Tabela | Colunas principais |
|---|---|
| `usuario` | `id, nome, email (UNIQUE, NOT NULL), senha (hash BCrypt), classe` (0=func/1=cliente) — login por `email` |
| `funcionario` | `id, nome, cargo, id_usuario→usuario` |
| `cliente` | `id, nome, email, telefone, id_usuario→usuario` |
| `peca` | `id, nome, preco` |
| `estoque` | `id, id_peca→peca, quantidade` |
| `servico` | `id, descricao, preco, keyword` |
| `acesso_funcionario` | `id, funcionario_id→funcionario, servico_id→servico` |
| `servico_cliente` | `id, cliente_id→cliente, servico_id→servico` |

---

# Tabelas SEM controller (endpoints futuros)

O banco já modela todo o domínio, mas ainda **não há controllers** para:
`funcionario`, `servico`, `servico_cliente`, `acesso_funcionario`.

As páginas do frontend abaixo dependem desses endpoints futuros e seguem com dados estáticos/mock:
`usuarios-master.html`, `servicos-lista.html`, `cliente-perfil.html`,
`dashboard-*`, `servicos-andamento.html`, `servicos-historico.html`, `cliente-andamento.html`, `cliente-historico.html`.

---

# Notas de arquitetura

- As classes `AbstractPeca` / `Parafuso` / `Porca` / `Arroela` / `PecaFactory`, além de `Motor*` e `Pintura*` e os `template/*`, são **artefatos de padrões de projeto** (Factory, Template Method) e **não estão ligados à API** — a peça exposta é o modelo flat `Peca`.
- Configuração do banco em `application.yml`: `db_reuso`, usuário `root`, **senha vazia** (alinhado ao MySQL do XAMPP).
