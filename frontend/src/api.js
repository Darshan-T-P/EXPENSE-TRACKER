// This file is the ONLY place the frontend talks to the backend.
// Every function below just calls one of the REST endpoints
// that our Spring Boot app exposes.

// Sends a request to the backend and returns the JSON response.
// path = "/expenses", "/budget", ...
// options = { method, body } for POST/PUT/DELETE
async function request(path, options = {}) {
  const res = await fetch(`/api${path}`, {
    headers: { 'Content-Type': 'application/json' }, // we always send JSON
    ...options,
  })

  // If the backend replied with an error status (400, 404, ...),
  // grab the message from the JSON body and throw it as a normal error.
  if (!res.ok) {
    let message = `Request failed (${res.status})`
    try {
      const body = await res.json()
      if (typeof body === 'object' && body !== null) {
        message = Object.values(body).join(', ')
      } else {
        message = body
      }
    } catch {
      // no JSON body, keep the simple message
    }
    throw new Error(message)
  }

  // 204 = "No Content" (used for DELETE), nothing to return.
  if (res.status === 204) return null
  return res.json()
}

// The "api" object groups all the endpoints we use.
// Each method maps to one REST endpoint of the backend.
export const api = {
  getExpenses: () => request('/expenses'),
  addExpense: (data) => request('/expenses', {
    method: 'POST',
    body: JSON.stringify(data), // JSON.stringify turns the object into a string to send
  }),
  updateExpense: (id, data) => request(`/expenses/${id}`, {
    method: 'PUT',
    body: JSON.stringify(data),
  }),
  deleteExpense: (id) => request(`/expenses/${id}`, { method: 'DELETE' }),
  getTotals: () => request('/expenses/totals'),
  getByPaymentMethod: () => request('/expenses/payment-method'),
  getByCategory: () => request('/expenses/category'),
  setBudget: (budget) => request('/budget', {
    method: 'PUT',
    body: JSON.stringify({ budget }),
  }),
  checkBudget: () => request('/budget/check'),
}
