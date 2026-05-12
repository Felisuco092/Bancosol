const API_BASE = 'http://localhost:3001';

export async function fetchData(ruta) {
  const res = await fetch(`${API_BASE}/${ruta}`);
  if (!res.ok) throw new Error(`Error HTTP ${res.status}`);
  return res.json();
}

export async function postData(ruta, data) {
  const res = await fetch(`${API_BASE}/${ruta}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error(`Error HTTP ${res.status}`);
  return res.json();
}
