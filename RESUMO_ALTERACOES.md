# Resumo das Alterações — Integração Frontend ↔ Backend ↔ Banco

> Documento de contexto para colaboradores. Explica **o que existia antes** e **o que foi
> alterado** para fazer o sistema (login, peças, estoque, cadastro de cliente) funcionar de
> ponta a ponta. O contrato detalhado da API está em [`TODO.md`](TODO.md).

---

## TL;DR

O backend Java havia **divergido** do modelo de dados real. Existia um banco antigo `oficina`
(schema completo da oficina) e o backend apontava para um `db_reuso` **inexistente**, com um
modelo de peça polimórfico que **não batia** com os dados, login quebrado e **zero** integração
no frontend (tudo mockado).

Decisão tomada: **o banco rico (`oficina`) é a fonte da verdade** — clonamos seu schema para
`db_reuso` e **alinhamos o backend e o frontend a ele**. Hoje login, catálogo de peças, estoque
e cadastro de cliente funcionam de verdade, persistindo no MySQL.

---

## Antes (o que tínhamos)

### Banco
- Banco **`oficina`** existente (modelo rico e normalizado: `usuario, funcionario, cliente,
  peca, estoque, servico, acesso_funcionario, servico_cliente`).
- **`db_reuso` não existia** — mas o `application.yml` apontava para ele (com senha `1234`,
  sendo que o root do MySQL/XAMPP é **sem senha**).
- **Nenhum script SQL** versionado no repositório.

### Backend (Spring Boot)
- **Login** (`POST /auth/login`): `LoginRequest {email, password}`, `LoginResponse` **vazia**;
  o repositório consultava `usuario` por `email`/`password = MD5(...)` — colunas que **não
  existem** naquela tabela.
- **Peças** (`/api/pecas`): modelo **polimórfico** `AbstractPeca` + `Parafuso/Porca/Arroela` +
  `PecaFactory`, com campo `quantidade`, lendo de uma tabela `pecas` inexistente. **POST/PUT só
  faziam "echo"** e **DELETE era no-op** — nada persistia.
- Sem CORS para o `/auth/login`.

### Frontend (HTML/CSS/JS)
- Páginas **100% estáticas**, com dados mockados nas tabelas.
- Formulários com `name`/`id` inconsistentes e **sem nenhuma chamada à API**.
- Não existia camada de comunicação JS.

---

## O que foi feito

### 1. Banco de dados (`database/`)
- **Backup** do `oficina` → `database/oficina_backup.sql`.
- **`db_reuso` criado como clone** do `oficina` (schema rico + dados) → script versionado em
  `database/db_reuso.sql`.
- Coluna **`telefone`** adicionada em `cliente`.
- Coluna **`email`** adicionada em `usuario` (**UNIQUE, NOT NULL**) — login passou a ser por e-mail.
- Senhas migradas de texto puro para **hash BCrypt**.
- `application.yml`: senha do datasource corrigida (`1234` → **vazia**), alinhada ao XAMPP.

### 2. Backend
- **CORS global** (`WebConfig`) — frontend (XAMPP :80) ↔ backend (:8080).
- **Login** reescrito: autentica por **`email` + `senha`**; resposta `{nome, classe, keywords}`.
  - `classe`: 0 = Funcionário, 1 = Cliente.
  - `keywords` = serviços acessíveis (funcionário via `acesso_funcionario`; cliente via `servico_cliente`).
- **Peça** virou modelo **flat** `Peca {id, nome, preco}` (tabela `peca`), com **CRUD persistente**
  (INSERT/UPDATE/DELETE reais). Exclusão é **em cascata** (remove o `estoque` vinculado, transacional).
- **Inventário**: novo `EstoqueItem` + `InventarioController` → **`GET /api/estoque`** (join `estoque`+`peca`).
- **Cadastro de cliente**: novo **`POST /api/clientes`** — cria `usuario` (classe 1) + `cliente`
  numa transação; senha hasheada; valida e-mail duplicado.
- **Segurança**: `spring-security-crypto` (BCrypt) + `PasswordEncoder`; `PasswordMigrationRunner`
  converte senhas antigas no boot (idempotente).
- **Tratamento de erro**: `GlobalExceptionHandler` retorna mensagem limpa (HTTP 409) em violações
  de integridade, sem vazar SQL.
- Os artefatos de **padrões de projeto** (`AbstractPeca`/`PecaFactory`, `Motor*`, `Pintura*`,
  `template/*`) foram **preservados**, mas **não estão ligados à API** (o modelo exposto é o `Peca` flat).

### 3. Frontend
- **`frontend/js/api.js`** (novo): camada de comunicação assíncrona — `fetch` async/await,
  serialização `FormData → JSON`, tratamento de erro (corpo em texto puro), sessão em
  `sessionStorage`, helpers `bindForm` e `notify` (toast).
- **`login.html`**: login real por e-mail; redireciona por `classe`
  (funcionário → `dashboard-usuario.html`, cliente → `dashboard-cliente.html`).
- **`pecas-lista.html`**: lista o catálogo do banco, cria e exclui peças.
- **`pecas-estoque.html`**: lista o inventário via `/api/estoque`.
- **`cadastro-cliente.html`**: cadastro de cliente real (cria conta que já loga por e-mail).

---

## Endpoints ativos hoje

| Verbo | Rota | Função |
|---|---|---|
| POST | `/auth/login` | Login por e-mail/senha → `{nome, classe, keywords}` |
| GET | `/api/pecas` | Catálogo de peças (`id, nome, preco`) |
| GET | `/api/pecas/{id}` | Uma peça |
| POST | `/api/pecas` | Cria peça (persiste) |
| PUT | `/api/pecas/{id}` | Atualiza peça |
| DELETE | `/api/pecas/{id}` | Exclui peça (cascata no estoque) |
| GET | `/api/estoque` | Inventário (`id, pecaId, pecaNome, quantidade`) |
| POST | `/api/clientes` | Cadastro de cliente (usuario classe 1 + cliente) |

---

## Como rodar e testar

1. **MySQL (XAMPP) ligado.** Se o banco `db_reuso` não existir, importe `database/db_reuso.sql`
   (phpMyAdmin → Importar). As senhas-semente em texto puro são convertidas para BCrypt no
   primeiro boot do backend.
2. **Backend**: na pasta `backend/`, rode `./mvnw spring-boot:run` (sobe em `:8080`).
3. **Frontend**: abra via XAMPP →
   `http://localhost/api-reuso/frontend/comum/login.html`.

**Credenciais de teste:**

| E-mail | Senha | Perfil |
|---|---|---|
| `admin@oficina.com` | `1234` | Funcionário (dashboard-usuario) |
| `lucas@exemplo.com` | `1234` | Cliente (dashboard-cliente) |

> Se o backend não estiver em `localhost:8080`, defina `window.API_BASE_URL` antes do `api.js`.

---

## Pendências / próximos passos

- **Sem controllers ainda** para `funcionario` e `servico`. As páginas abaixo seguem com dados
  **mockados** até esses endpoints existirem:
  `usuarios-master.html`, `servicos-lista.html`, `cliente-perfil.html`, `dashboard-*`,
  `servicos-andamento.html`, `servicos-historico.html`, `cliente-andamento.html`, `cliente-historico.html`.
- Edição de peça (PUT) está no backend, mas o botão "Editar" do frontend ainda não foi ligado.

---

## Onde olhar
- **Contrato da API (fonte da verdade):** [`TODO.md`](TODO.md)
- **Schema + seed do banco:** `database/db_reuso.sql`
- **Backup do banco original:** `database/oficina_backup.sql`
- **Camada de comunicação do front:** `frontend/js/api.js`
