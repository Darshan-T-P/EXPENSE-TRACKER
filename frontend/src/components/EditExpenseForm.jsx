import { useState } from 'react'

// The popup form shown when the user clicks "Edit".
// Props:
//   expense   -> the expense being edited (starts the form filled in)
//   onSubmit(formData) -> called with the new values when the user clicks Save
//   onCancel() -> called when the user clicks Cancel
export default function EditExpenseForm({ expense, onSubmit, onCancel }) {
  // Start the form with the expense's current values.
  const [form, setForm] = useState({
    type: expense.type,
    date: expense.date,
    description: expense.description,
    amount: expense.amount,
    paymentMethod: expense.paymentMethod,
    category: expense.category || '',
    businessPurpose: expense.businessPurpose || '',
  })

  const set = (key) => (e) => setForm({ ...form, [key]: e.target.value })

  function handleSubmit(e) {
    e.preventDefault() // stop the page from reloading
    onSubmit({
      ...form,
      amount: Number(form.amount),
    })
  }

  return (
    <div className="edit-overlay"> {/* dark background behind the popup */}
      <form onSubmit={handleSubmit} className="expense-form">
        <h2>Edit Expense #{expense.id}</h2>

        <div className="field">
          <label htmlFor="edit-type">Type</label>
          <select id="edit-type" value={form.type} onChange={set('type')}>
            <option value="PERSONAL">Personal</option>
            <option value="BUSINESS">Business</option>
          </select>
        </div>

        <div className="field">
          <label htmlFor="edit-date">Date</label>
          <input id="edit-date" type="date" value={form.date} onChange={set('date')} required />
        </div>

        <div className="field">
          <label htmlFor="edit-description">Description</label>
          <input id="edit-description" value={form.description} onChange={set('description')} required />
        </div>

        <div className="field">
          <label htmlFor="edit-amount">Amount</label>
          <input id="edit-amount" type="number" min="0.01" step="0.01" value={form.amount} onChange={set('amount')} required />
        </div>

        <div className="field">
          <label htmlFor="edit-paymentMethod">Payment Method</label>
          <input id="edit-paymentMethod" value={form.paymentMethod} onChange={set('paymentMethod')} required />
        </div>

        {form.type === 'PERSONAL' ? (
          <div className="field">
            <label htmlFor="edit-category">Category</label>
            <input id="edit-category" value={form.category} onChange={set('category')} required />
          </div>
        ) : (
          <div className="field">
            <label htmlFor="edit-businessPurpose">Business Purpose</label>
            <input id="edit-businessPurpose" value={form.businessPurpose} onChange={set('businessPurpose')} required />
          </div>
        )}

        <div className="form-actions">
          <button type="submit" className="btn btn-primary">Save</button>
          <button type="button" className="btn" onClick={onCancel}>Cancel</button>
        </div>
      </form>
    </div>
  )
}
