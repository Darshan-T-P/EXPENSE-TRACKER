import { useEffect, useState } from 'react'
import { api } from './api'
import ExpenseForm from './components/ExpenseForm'
import EditExpenseForm from './components/EditExpenseForm'
import ExpenseList from './components/ExpenseList'
import SummaryPanel from './components/SummaryPanel'
import BudgetPanel from './components/BudgetPanel'
import './App.css'

// App is the "main" component. It:
//   1. loads data from the backend when it starts (useEffect)
//   2. keeps that data in "state" (useState)
//   3. passes the data down to the smaller components
//   4. handles the add / edit / delete actions
export default function App() {
  // ---- State: data the app remembers -------------------------------
  const [expenses, setExpenses] = useState([])     // list of expenses
  const [totals, setTotals] = useState({})         // PERSONAL/BUSINESS/OVERALL totals
  const [byPayment, setByPayment] = useState({})   // totals per payment method
  const [byCategory, setByCategory] = useState({}) // totals per category
  const [budgetStatus, setBudgetStatus] = useState(null) // over/under budget info
  const [editing, setEditing] = useState(null)     // expense being edited, null = none
  const [error, setError] = useState('')           // error message to show
  const [loading, setLoading] = useState(true)     // true while first load happens

  // ---- Loading data from the backend -------------------------------
  // Calls all the GET endpoints at once and stores the results.
  async function refresh() {
    const [expensesRes, totalsRes, byPaymentRes, byCategoryRes, budgetRes] = await Promise.all([
      api.getExpenses(),
      api.getTotals(),
      api.getByPaymentMethod(),
      api.getByCategory(),
      api.checkBudget(),
    ])
    setExpenses(expensesRes)
    setTotals(totalsRes)
    setByPayment(byPaymentRes)
    setByCategory(byCategoryRes)
    setBudgetStatus(budgetRes)
  }

  // useEffect runs once when the component first appears on the screen.
  useEffect(() => {
    refresh()
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
  }, [])

  // ---- Actions: what happens when the user clicks buttons ----------

  async function handleAdd(data) {
    try {
      setError('')
      await api.addExpense(data) // send to backend
      await refresh()            // reload everything so the list updates
    } catch (e) {
      setError(e.message)        // show the error from the backend
    }
  }

  async function handleUpdate(data) {
    try {
      setError('')
      await api.updateExpense(editing.id, data)
      setEditing(null)           // close the edit form
      await refresh()
    } catch (e) {
      setError(e.message)
    }
  }

  async function handleDelete(id) {
    if (!window.confirm('Delete this expense?')) return
    try {
      setError('')
      await api.deleteExpense(id)
      await refresh()
    } catch (e) {
      setError(e.message)
    }
  }

  async function handleSetBudget(budget) {
    try {
      setError('')
      await api.setBudget(budget)
      await refresh()
    } catch (e) {
      setError(e.message)
    }
  }

  // ---- What the user sees ------------------------------------------
  return (
    <div className="app">
      <header className="app-header">
        <h1>Expense Tracker</h1>
      </header>

      {/* Show an error message if anything went wrong */}
      {error && <div className="error-banner">{error}</div>}

      {loading ? (
        <p className="empty">Loading…</p>
      ) : (
        <>
          {/* Top row: the form to add an expense, and the budget box */}
          <div className="top-grid">
            <ExpenseForm onSubmit={handleAdd} />
            <BudgetPanel budgetStatus={budgetStatus} onSetBudget={handleSetBudget} />
          </div>

          {/* Middle row: the summary cards */}
          <SummaryPanel totals={totals} byPayment={byPayment} byCategory={byCategory} />

          {/* Bottom: the table of all expenses */}
          <ExpenseList expenses={expenses} onDelete={handleDelete} onEdit={setEditing} />
        </>
      )}

      {/* The popup form shown only while the user is editing */}
      {editing && (
        <EditExpenseForm
          expense={editing}
          onSubmit={handleUpdate}
          onCancel={() => setEditing(null)}
        />
      )}
    </div>
  )
}
