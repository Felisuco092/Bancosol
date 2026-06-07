import { API_BASE_URL } from '../utils/constants';

function getToken() {
  return sessionStorage.getItem('token');
}

function authHeaders(headers = {}) {
  const token = getToken();
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }
  return { ...headers, 'Content-Type': 'application/json' };
}

export async function fetchData(ruta) {
  const res = await fetch(`${API_BASE_URL}/${ruta}`, {
    headers: authHeaders(),
  });
  if (!res.ok) throw new Error(`Error HTTP ${res.status}`);
  return res.json();
}

export async function postData(ruta, data) {
  const res = await fetch(`${API_BASE_URL}/${ruta}`, {
    method: 'POST',
    headers: authHeaders(),
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error(`Error HTTP ${res.status}`);
  return res.json();
}

export async function putData(ruta, data) {
  const res = await fetch(`${API_BASE_URL}/${ruta}`, {
    method: 'PUT',
    headers: authHeaders(),
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error(`Error HTTP ${res.status}`);
  return res.json();
}

export async function deleteData(ruta) {
  const res = await fetch(`${API_BASE_URL}/${ruta}`, {
    method: 'DELETE',
    headers: authHeaders(),
  });
  if (!res.ok) throw new Error(`Error HTTP ${res.status}`);
  return res.json();
}

export async function loginUser(usuario, password) {
  const res = await fetch(`${API_BASE_URL}/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ usuario, password }),
  });
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.error || 'Error al iniciar sesión');
  }
  return res.json();
}
