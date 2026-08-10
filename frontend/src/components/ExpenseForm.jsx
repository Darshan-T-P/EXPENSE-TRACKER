import { useState } from 'react'

// The starting values for a new expense form.
const emptyForm = {
  type: 'PERSONAL',
  date: new Date().toISOString().slice(0, 10), // today's date as YYYY-MM-DD
  description: '',
  amount: '',
  paymentMethod: '',
  category: '',
  businessPurpose: '',
}

// The "Add Expense" form.
// Props:
//   onSubmit(formData) -> called with the filled-in form when the user clicks Add
export default function ExpenseForm({ onSubmit }) {
  // useState keeps the current value of every input field.
  const [form, setForm] = useState(emptyForm)

  // set('date') returns a function that updates that one field.
  const set = (key) => (e) => setForm({ ...form, [key]: e.target.value })

  function handleSubmit(e) {
    e.preventDefault() // stop the page from reloading on submit
    onSubmit({
      ...form,             // keep all fields as they are
      amount: Number(form.amount), // but turn the amount into a real number
    })
    setForm(emptyForm)     // clear the form after saving
  }

  return (
    <form onSubmit={handleSubmit} className="expense-form">
      {/* The title changes depending on the selected type */}
      <h2>{form.type === 'PERSONAL' ? 'Add Personal Expense' : 'Add Business Expense'}</h2>

      <div className="field">
        <label htmlFor="type">Type</label>
        <select id="type" value={form.type} onChange={set('type')}>
          <option value="PERSONAL">Personal</option>
          <option value="BUSINESS">Business</option>
        </select>
      </div>

      <div className="field">
        <label htmlFor="date">Date</label>
        <input id="date" type="date" value={form.date} onChange={set('date')} required />
      </div>

      <div className="field">
        <label htmlFor="description">Description</label>
        <input id="description" value={form.description} onChange={set('description')} required />
      </div>

      <div className="field">
        <label htmlFor="amount">Amount</label>
        <input id="amount" type="number" min="0.01" step="0.01" value={form.amount} onChange={set('amount')} required />
      </div>

      <div className="field">
        <label htmlFor="paymentMethod">Payment Method</label>
        <input id="paymentMethod" value={form.paymentMethod} onChange={set('paymentMethod')} required />
      </div>

      {/* Show the right extra field depending on the type */}
      {form.type === 'PERSONAL' ? (
        <div className="field">
          <label htmlFor="category">Category</label>
          <input id="category" value={form.category} onChange={set('category')} required />
        </div>
      ) : (
        <div className="field">
          <label htmlFor="businessPurpose">Business Purpose</label>
          <input id="businessPurpose" value={form.businessPurpose} onChange={set('businessPurpose')} required />
        </div>
      )}

      <button type="submit" className="btn btn-primary">Add Expense</button>
    </form>
  )
}
