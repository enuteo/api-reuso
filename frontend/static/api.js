// Raw JS API client — points at a local backend running on your machine.
// Change this if your API runs elsewhere.
const API_BASE = "http://localhost:8080";

async function apiGet(path) {
  const res = await fetch(`${API_BASE}${path}`);
  if (!res.ok) throw new Error(`GET ${path} -> ${res.status}`);
  return res.json();
}

async function apiSend(path, method, body) {
  const res = await fetch(`${API_BASE}${path}`, {
    method,
    headers: { "Content-Type": "application/json" },
    body: body ? JSON.stringify(body) : undefined,
  });
  if (!res.ok) throw new Error(`${method} ${path} -> ${res.status}`);
  return res.status === 204 ? null : res.json();
}

const api = {
  login: (email, password) => apiSend("/auth/login", "POST", { email, password }),
  listStock: () => apiGet("/stock"),
  createPart: (part) => apiSend("/stock", "POST", part),
  listUsers: () => apiGet("/users"),
  inviteUser: (user) => apiSend("/users", "POST", user),
};

// --- helpers -------------------------------------------------------
function statusBadge(status) {
  const s = (status || "").toLowerCase();
  if (s === "out" || s === "inactive") return `<span class="badge bad">${status}</span>`;
  if (s === "low" || s === "on leave") return `<span class="badge warn">${status}</span>`;
  return `<span class="badge ok">${status || "Active"}</span>`;
}

function roleBadge(role) {
  const map = {
    Admin: "role-admin",
    Manager: "role-manager",
    Mechanic: "role-mech",
    Receptionist: "role-rec",
  };
  return `<span class="badge ${map[role] || "role-mech"}">${role}</span>`;
}

function showApiError(targetId, err) {
  const el = document.getElementById(targetId);
  if (!el) return;
  el.innerHTML = `<tr><td colspan="9" style="padding:24px;text-align:center;color:var(--danger)">
    Could not reach API at ${API_BASE} — ${err.message}
  </td></tr>`;
}
