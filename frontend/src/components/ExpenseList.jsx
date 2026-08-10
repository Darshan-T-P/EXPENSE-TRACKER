// The table that shows every expense.
// Props:
//   expenses   -> list of expense objects from the backend
//   onDelete(id) -> called when the user clicks Delete
//   onEdit(expense) -> called when the user clicks Edit
export default function ExpenseList({ expenses, onDelete, onEdit }) {
  if (expenses.length === 0) {
    return <p className="empty">No expenses recorded yet.</p>
  }

  return (
    <div className="expense-list">
      <h2>All Expenses</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Type</th>
            <th>Date</th>
            <th>Description</th>
            <th>Amount</th>
            <th>Payment</th>
            <th>Category / Purpose</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {/* .map() turns every expense object into one table row */}
          {expenses.map((e) => (
            <tr key={e.id}>
              <td>{e.id}</td>
              <td><span className={`badge badge-${e.type.toLowerCase()}`}>{e.type}</span></td>
              <td>{e.date}</td>
              <td>{e.description}</td>
              <td>${e.amount.toFixed(2)}</td>
              <td>{e.paymentMethod}</td>
              {/* Personal expenses have a category, business ones have a purpose */}
              <td>{e.category || e.businessPurpose}</td>
              <td className="actions">
                <button className="btn btn-small" onClick={() => onEdit(e)}>Edit</button>
                <button className="btn btn-small btn-danger" onClick={() => onDelete(e.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
