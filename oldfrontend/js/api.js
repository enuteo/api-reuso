// URL base do seu servidor Backend (Spring Boot)
const API_BASE_URL = "http://localhost:8080"; 

// ---------------------------------------------------------
// GATILHO AUTOMÁTICO (Ao carregar a página)
// ---------------------------------------------------------
// Esse bloco verifica qual tela o usuário abriu e chama a função correta
// para buscar os dados do banco de dados imediatamente.
document.addEventListener("DOMContentLoaded", () => {
    const paginaAtual = window.location.pathname;

    if (paginaAtual.includes("dashboard.html")) {
        carregarDashboard();
    } else if (paginaAtual.includes("estoque.html")) {
        carregarEstoque();
    } else if (paginaAtual.includes("servicos.html")) {
        carregarServicos();
    }
});


// =========================================================
// MÓDULO 1: LEITURA DE DADOS (Método GET - Puxar do Banco)
// =========================================================

// INTEGRAÇÃO: Puxar Peças (Tabela: pecas)
async function carregarEstoque() {
    try {
        // Faz a requisição GET para o endpoint de peças
        const response = await fetch(`${API_BASE_URL}/api/pecas`);
        if (response.ok) {
            const pecas = await response.json(); // Converte a resposta do banco para JSON
            const tbody = document.getElementById('tbodyEstoque');
            
            // Limpa os dados falsos (Mockups) do HTML
            tbody.innerHTML = ''; 

            // Varre a lista de peças vindas do banco e cria as linhas da tabela
            pecas.forEach(peca => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>#${peca.id}</td>
                    <td>${peca.nome}</td>
                    <td>${peca.quantidade} un</td>
                    <td><span class="badge badge-success">No Banco</span></td>
                    <td><a href="#" style="color: var(--primary);">Editar</a></td>
                `;
                tbody.appendChild(tr);
            });
        }
    } catch (error) {
        console.error("Erro ao puxar estoque do banco:", error);
    }
}

// INTEGRAÇÃO: Puxar Serviços (Tabelas: servicos)
async function carregarServicos() {
    try {
        const response = await fetch(`${API_BASE_URL}/api/servicos`);
        if (response.ok) {
            const servicos = await response.json();
            const tbody = document.getElementById('tbodyServicos');
            tbody.innerHTML = ''; // Limpa os dados falsos

            servicos.forEach(servico => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>OS-${servico.id}</td>
                    <td>${servico.nome}</td>
                    <td>R$ ${servico.precos.toFixed(2)}</td>
                    <td><span class="badge badge-warning">Registrado</span></td>
                `;
                tbody.appendChild(tr);
            });
        }
    } catch (error) {
        console.error("Erro ao puxar serviços do banco:", error);
    }
}

// INTEGRAÇÃO: Puxar KPIs para o Dashboard
async function carregarDashboard() {
    try {
        // Exemplo: Puxando o total de clientes
        const responseClientes = await fetch(`${API_BASE_URL}/api/clientes/total`);
        if (responseClientes.ok) {
            const total = await responseClientes.json();
            // Substitui o "89" fixo pelo valor real do banco
            document.getElementById('kpiClientes').innerText = total; 
        }
        
        // (Você pode replicar a lógica acima para as peças e serviços, 
        // desde que o Spring Boot tenha rotas como '/api/pecas/total')
    } catch (error) {
        console.error("Erro ao carregar Dashboard:", error);
    }
}


// =========================================================
// MÓDULO 2: GRAVAÇÃO DE DADOS (Método POST - Enviar pro Banco)
// =========================================================

// INTEGRAÇÃO: Autenticação de Login
async function fazerLogin() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const payload = { email: email, senha: senha };

    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert("Acesso liberado!");
            window.location.href = "dashboard.html";
        } else {
            alert("Credenciais inválidas.");
        }
    } catch (error) {
        console.error("Erro de conexão:", error);
        alert("Erro de conexão. O Spring Boot está rodando?");
    }
}

// INTEGRAÇÃO: Criação de Novo Usuário/Operador
async function criarUsuario() {
    const payload = {
        nome: document.getElementById('usuarioNome').value,
        email: document.getElementById('usuarioEmail').value,
        master: parseInt(document.getElementById('usuarioNivel').value)
    };

    try {
        const response = await fetch(`${API_BASE_URL}/api/usuarios`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert("Operador cadastrado com sucesso!");
            document.getElementById('formUsuario').reset();
        } else {
            alert("Erro ao cadastrar operador.");
        }
    } catch (error) {
        console.error("Erro de conexão:", error);
    }
}

// INTEGRAÇÃO: Criação de Novo Cliente
async function criarCliente() {
    const payload = {
        nome: document.getElementById('clienteNome').value,
        documento: document.getElementById('clienteDocumento').value,
        telefone: document.getElementById('clienteTelefone').value,
        email: document.getElementById('clienteEmail').value
    };

    try {
        const response = await fetch(`${API_BASE_URL}/api/clientes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            alert("Cliente salvo no banco de dados!");
            document.getElementById('formCliente').reset();
        } else {
            alert("Falha ao registrar cliente.");
        }
    } catch (error) {
        console.error("Erro de conexão:", error);
    }
}