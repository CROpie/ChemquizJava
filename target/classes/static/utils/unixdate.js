export function formatDate(dateInUnix) {
    const prettyDate = new Date(dateInUnix)
    return prettyDate.toLocaleDateString('en-US', {
      weekday: 'short',
      month: 'short',
      day: '2-digit',
      year: 'numeric',
    })
  }