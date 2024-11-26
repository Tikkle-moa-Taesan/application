const DAYS_OF_WEEK = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']

export const formatDate = (dateString) => {
  const formattedDate = new Date(dateString)

  const year = formattedDate.getFullYear()
  const month = formattedDate.getMonth() + 1
  const date = formattedDate.getDate()
  const day = formattedDate.getDay()

  return `${year}.${month}.${date} ${DAYS_OF_WEEK[day]}`
}

export const formatDateForLedger = (dateString) => {
  const formattedDate = new Date(dateString)

  const year = formattedDate.getFullYear()
  const month = formattedDate.getMonth() + 1
  const date = formattedDate.getDate()

  const hour = formattedDate.getHours().toString().padStart(2, '0')
  const minute = formattedDate.getMinutes().toString().padStart(2, '0')

  return `${year}년 ${month}월 ${date}일 ${hour}:${minute}`
}

export const formatHour = (dateString) => {
  const formattedDate = new Date(dateString)

  const hour = formattedDate.getHours().toString().padStart(2, '0')
  const minute = formattedDate.getMinutes().toString().padStart(2, '0')

  return `${hour}:${minute}`
}
