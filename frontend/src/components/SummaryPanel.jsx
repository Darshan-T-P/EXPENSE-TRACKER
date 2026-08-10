// One small card that shows a title and a list of "name -> amount" pairs,
// e.g. "Totals" -> { PERSONAL: 100, BUSINESS: 50, OVERALL: 150 }.
function SummaryCard({ title, entries }) {
  // Object.entries turns {"Food": 10} into [["Food", 10]]
  const list = Object.entries(entries)
  if (list.length === 0) return null // nothing to show yet

  return (
    <div className="card">
      <h3>{title}</h3>
      <ul className="summary-list">
        {list.map(([key, value]) => (
          <li key={key}>
            <span>{key}</span>
            <span className={key === 'OVERALL' ? 'total' : ''}>${value.toFixed(2)}</span>
          </li>
        ))}
      </ul>
    </div>
  )
}

// Groups the three summary cards side by side.
export default function SummaryPanel({ totals, byPayment, byCategory }) {
  return (
    <div className="summary-panel">
      <SummaryCard title="Totals" entries={totals} />
      <SummaryCard title="By Payment Method" entries={byPayment} />
      <SummaryCard title="By Category" entries={byCategory} />
    </div>
  )
}
