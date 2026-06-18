/**
 * api.js — Camada de comunicação assíncrona (Oficina System)
 * --------------------------------------------------------------
 * Cliente HTTP modular sobre a Fetch API.
 * Rotas mapeadas no TODO.md (Single Source of Truth).
 *
 * Convenções respeitadas a partir do backend:
 *  - Payloads enviados como JSON estrito (Content-Type: application/json).
 *  - Erros do GlobalExceptionHandler chegam como TEXTO PURO no body
 *    (ex.: "Usuário ou senha inválidos"), não como objeto JSON.
 *  - InvalidUser -> HTTP 400 | PecaInvalida -> HTTP 500 (sem handler dedicado).
 *
 * Uso: <script src="../js/api.js"></script>  ->  window.API
 */
(function (global) {
  'use strict';

  // Base configurável: defina window.API_BASE_URL antes deste script para sobrescrever.
  // Backend Spring Boot roda, por padrão, em :8080 (origem distinta do XAMPP -> exige CORS).
  const BASE_URL = (global.API_BASE_URL || 'http://localhost:8080').replace(/\/+$/, '');

  const SESSION_KEY = 'oficina.auth';

  /** Erro de domínio normalizado, carregando o status HTTP e o body original. */
  class ApiError extends Error {
    constructor(message, status, body) {
      super(message);
      this.name = 'ApiError';
      this.status = status;
      this.body = body;
    }
  }

  /* ------------------------------------------------------------------ */
  /* Núcleo HTTP                                                         */
  /* ------------------------------------------------------------------ */

  /** Faz o parse do body como JSON; se não for JSON, devolve o texto cru. */
  function parseBody(text) {
    if (!text) return null;
    try {
      return JSON.parse(text);
    } catch {
      return text;
    }
  }

  /** Normaliza a resposta: trata 204, sucesso e erros (texto ou JSON). */
  async function handleResponse(res) {
    if (res.status === 204) return null; // DELETE /api/pecas/{id}

    const data = parseBody(await res.text());

    if (!res.ok) {
      const message =
        typeof data === 'string' && data
          ? data
          : (data && (data.message || data.error)) || `Erro ${res.status}`;
      throw new ApiError(message, res.status, data);
    }
    return data;
  }

  /**
   * Requisição genérica.
   * @param {string} path  Caminho relativo (ex.: '/api/pecas').
   * @param {{method?: string, body?: any}} [options]
   */
  async function request(path, { method = 'GET', body } = {}) {
    const init = { method, headers: {} };

    if (body !== undefined) {
      init.headers['Content-Type'] = 'application/json';
      init.body = JSON.stringify(body);
    }

    let res;
    try {
      res = await fetch(`${BASE_URL}${path}`, init);
    } catch (networkErr) {
      // Falha de rede/CORS não chega a virar resposta HTTP.
      throw new ApiError(
        'Falha de comunicação com o servidor. Verifique a conexão ou o CORS do backend.',
        0,
        networkErr
      );
    }
    return handleResponse(res);
  }

  /* ------------------------------------------------------------------ */
  /* Serialização de formulários (FormData -> JSON estrito)              */
  /* ------------------------------------------------------------------ */

  /**
   * Extrai os dados de um <form> via FormData e serializa em objeto JSON.
   * - Coage <input type="number"> para Number.
   * - Omite campos vazios (ex.: newPassword opcional no perfil).
   * @param {HTMLFormElement} form
   */
  function serializeForm(form) {
    if (!(form instanceof HTMLFormElement)) {
      throw new TypeError('serializeForm requer um HTMLFormElement.');
    }
    const payload = {};
    for (const [name, value] of new FormData(form).entries()) {
      if (value === '') continue; // não enviar chaves vazias
      const field = form.elements.namedItem(name);
      payload[name] = field && field.type === 'number' ? Number(value) : value;
    }
    return payload;
  }

  /* ------------------------------------------------------------------ */
  /* Sessão do browser                                                   */
  /* ------------------------------------------------------------------ */

  const session = {
    save(data) {
      sessionStorage.setItem(SESSION_KEY, JSON.stringify(data));
    },
    get() {
      const raw = sessionStorage.getItem(SESSION_KEY);
      return raw ? JSON.parse(raw) : null;
    },
    clear() {
      sessionStorage.removeItem(SESSION_KEY);
    },
    isAuthenticated() {
      return sessionStorage.getItem(SESSION_KEY) !== null;
    },
  };

  /* ------------------------------------------------------------------ */
  /* Endpoints REAIS (existem no backend)                                */
  /* ------------------------------------------------------------------ */

  /** POST /auth/login — autentica e persiste a sessão. */
  async function login(form) {
    const credentials = serializeForm(form); // { email, senha }
    const data = await request('/auth/login', { method: 'POST', body: credentials });

    // LoginResponse = { nome, classe, keywords }. Persistimos + o e-mail usado.
    const authData = { email: credentials.email, ...(data || {}) };
    session.save(authData);
    return authData;
  }

  /** Encerra a sessão local. */
  function logout() {
    session.clear();
  }

  /** GET /api/pecas — catálogo completo (id, nome, preco). */
  const listarPecas = () => request('/api/pecas');

  /** GET /api/pecas/{id} — busca uma peça do catálogo. */
  const buscarPeca = (id) => request(`/api/pecas/${encodeURIComponent(id)}`);

  /** GET /api/estoque — inventário (id, pecaId, pecaNome, quantidade). */
  const listarEstoque = () => request('/api/estoque');

  /** POST /api/pecas — cria a partir do form (nome, preco). */
  const criarPeca = (form) =>
    request('/api/pecas', { method: 'POST', body: serializeForm(form) });

  /** PUT /api/pecas/{id} — atualiza a partir do form. */
  const atualizarPeca = (id, form) =>
    request(`/api/pecas/${encodeURIComponent(id)}`, {
      method: 'PUT',
      body: serializeForm(form),
    });

  /** DELETE /api/pecas/{id} — remove (204 No Content). */
  const excluirPeca = (id) =>
    request(`/api/pecas/${encodeURIComponent(id)}`, { method: 'DELETE' });

  /* ------------------------------------------------------------------ */
  /* Cliente (reais)                                                     */
  /* ------------------------------------------------------------------ */

  /** POST /api/clientes — cadastro de cliente. */
  const cadastrarCliente = (form) =>
    request('/api/clientes', { method: 'POST', body: serializeForm(form) });

  /** GET /api/clientes/usuario/{id} — perfil do cliente logado. */
  const getPerfilCliente = (usuarioId) =>
    request(`/api/clientes/usuario/${encodeURIComponent(usuarioId)}`);

  /** GET /api/clientes/usuario/{id}/servicos — serviços contratados. */
  const getServicosCliente = (usuarioId) =>
    request(`/api/clientes/usuario/${encodeURIComponent(usuarioId)}/servicos`);

  /** PUT /api/clientes/usuario/{id} — atualiza perfil (e senha, opcional). */
  const atualizarPerfilCliente = (usuarioId, form) =>
    request(`/api/clientes/usuario/${encodeURIComponent(usuarioId)}`, {
      method: 'PUT',
      body: serializeForm(form),
    });

  /* ------------------------------------------------------------------ */
  /* Endpoints PROPOSTOS (WIP — controllers ainda inexistentes)          */
  /* ------------------------------------------------------------------ */

  const cadastrarUsuario = (form) =>
    request('/api/usuarios', { method: 'POST', body: serializeForm(form) });

  const cadastrarServico = (form) =>
    request('/api/servicos', { method: 'POST', body: serializeForm(form) });

  /* ------------------------------------------------------------------ */
  /* Feedback de UI                                                      */
  /* ------------------------------------------------------------------ */

  /**
   * Exibe mensagem ao utilizador. Injeta num #api-feedback se existir;
   * caso contrário, cria um toast flutuante. Fallback final: alert().
   * @param {string} message
   * @param {'error'|'success'} [type]
   */
  function notify(message, type = 'error') {
    const slot = document.getElementById('api-feedback');
    if (slot) {
      slot.textContent = message;
      slot.dataset.type = type;
      slot.style.display = 'block';
      return;
    }
    if (typeof document === 'undefined' || !document.body) {
      alert(message);
      return;
    }
    const toast = document.createElement('div');
    toast.textContent = message;
    toast.setAttribute('role', 'alert');
    Object.assign(toast.style, {
      position: 'fixed',
      top: '1rem',
      right: '1rem',
      zIndex: '99999',
      maxWidth: '360px',
      padding: '0.85rem 1.1rem',
      borderRadius: '8px',
      color: '#fff',
      fontFamily: 'system-ui, sans-serif',
      fontSize: '0.9rem',
      boxShadow: '0 10px 25px rgba(0,0,0,.25)',
      background: type === 'success' ? '#16a34a' : '#dc2626',
    });
    document.body.appendChild(toast);
    setTimeout(() => toast.remove(), 4000);
  }

  /**
   * Liga um <form> a uma função da API, tratando submit/erros de forma uniforme.
   * @param {string|HTMLFormElement} formOrSelector
   * @param {(form: HTMLFormElement) => Promise<any>} apiFn
   * @param {{onSuccess?: (data:any, form:HTMLFormElement)=>void, successMessage?: string}} [opts]
   */
  function bindForm(formOrSelector, apiFn, opts = {}) {
    const form =
      typeof formOrSelector === 'string'
        ? document.querySelector(formOrSelector)
        : formOrSelector;
    if (!form) return;

    form.addEventListener('submit', async (event) => {
      event.preventDefault();
      const submitBtn = form.querySelector('[type="submit"]');
      if (submitBtn) submitBtn.disabled = true;
      try {
        const data = await apiFn(form);
        if (opts.successMessage) notify(opts.successMessage, 'success');
        opts.onSuccess?.(data, form);
      } catch (err) {
        // ApiError carrega a mensagem de domínio (texto puro vindo do backend).
        notify(err instanceof ApiError ? err.message : 'Erro inesperado. Tente novamente.');
        console.error(err);
      } finally {
        if (submitBtn) submitBtn.disabled = false;
      }
    });
  }

  /* ------------------------------------------------------------------ */
  /* API pública                                                         */
  /* ------------------------------------------------------------------ */

  global.API = {
    // núcleo
    request,
    serializeForm,
    ApiError,
    session,
    // auth
    login,
    logout,
    // peças (reais)
    listarPecas,
    buscarPeca,
    listarEstoque,
    criarPeca,
    atualizarPeca,
    excluirPeca,
    // cliente (reais)
    cadastrarCliente,
    getPerfilCliente,
    getServicosCliente,
    atualizarPerfilCliente,
    // propostos (WIP)
    cadastrarUsuario,
    cadastrarServico,
    // UI
    notify,
    bindForm,
  };
})(window);
