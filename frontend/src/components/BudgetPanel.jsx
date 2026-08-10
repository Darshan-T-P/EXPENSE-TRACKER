import { useState } from 'react'

// The budget box.
// Props:
//   budgetStatus -> info from GET /api/budget/check (may be null before first load)
//   onSetBudget(budget) -> called with the new budget when the user clicks Set
export default function BudgetPanel({ budgetStatus, onSetBudget }) {
  const [value, setValue] = useState('') // what the user typed

  function handleSubmit(e) {
    e.preventDefault() // stop the page from reloading
    if (!value) return  // ignore an empty input
    onSetBudget(Number(value))
    setValue('')        // clear the input after saving
  }

  return (
    <div className="card budget-panel">
      <h3>Budget</h3>

      {/* Show a green "ok" box or a red "over budget" box */}
      {budgetStatus && (
        <div className={`budget-status ${budgetStatus.overBudget ? 'over' : 'ok'}`}>
          {budgetStatus.overBudget ? (
            <p>Warning: Exceeded budget by ${budgetStatus.exceededBy.toFixed(2)}</p>
          ) : (
            <p>Within budget. Remaining: ${budgetStatus.remaining.toFixed(2)}</p>
          )}
          <p className="muted">
            Budget ${budgetStatus.budget.toFixed(2)} · Spent ${budgetStatus.totalExpenses.toFixed(2)}
          </p>
        </div>
      )}

      <form onSubmit={handleSubmit} className="budget-form">
        <input
          type="number"
          min="0.01"
          step="0.01"
          placeholder="Set budget amount"
          value={value}
          onChange={(e) => setValue(e.target.value)}
        />
        <button type="submit" className="btn btn-primary">Set Budget</button>
      </form>
    </div>
  )
}
