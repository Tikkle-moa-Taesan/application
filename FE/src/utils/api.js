import instance from './instance'

export const postKaKaoLogin = async (code) => {
  try {
    const response = await instance.post('/api/oauth/kakao/login', {
      authorizationCode: code,
    })

    return response.data
  } catch (error) {}
}

export const postGoogleLogin = async (code) => {
  try {
    const response = await instance.post('/api/oauth/google/login', {
      authorizationCode: code,
    })

    return response.data
  } catch (error) {}
}

export const getProfile = async () => {
  try {
    const response = await instance.get('/api/member/profile')

    return response.data
  } catch (error) {}
}

export const getTotalBalance = async () => {
  try {
    const response = await instance.get('/api/account/balance')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

export const getFreeAccountList = async () => {
  try {
    const response = await instance.get('/api/account/free')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

export const postFreeAccount = async (accountId, page, condition) => {
  try {
    const response = await instance.post(`/api/account/free/${accountId}?page=${page}`, condition)

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

export const getSavingAccountList = async () => {
  try {
    const response = await instance.get('/api/account/savings')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

export const postSavingAccount = async (accountId, page, condition) => {
  try {
    const response = await instance.post(
      `/api/account/savings/${accountId}?page=${page}`,
      condition,
    )

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

export const getExpenseStatistic = async () => {
  try {
    const response = await instance.get('/api/budget/expense')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
    else if (error.response.data.code === 'B001') return -1
  }
}

/**
 * 거래내역 수정
 * @param {Number} budgetTransactionId
 * @param {Object} modifyContent
 * @returns {Promise<String>}
 */
export const modifyTransaction = async (budgetTransactionId, modifyContent) => {
  try {
    const response = await instance.put(
      `/api/member/transaction/${budgetTransactionId}`,
      modifyContent,
    )

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

/**
 * 거래내역 삭제
 * @param {Number} budgetTransactionId - 계좌 ID
 * @returns {Promise<String>}
 */
export const deleteTransaction = async (budgetTransactionId) => {
  try {
    const response = await instance.delete(`/api/member/transaction/${budgetTransactionId}`)

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

export const getBudgetStatistic = async () => {
  try {
    const response = await instance.get('/api/budget/rate')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
    else if (error.response.data.code === 'B001') return -1
  }
}

export const postBudgetCreate = async (monthBudget) => {
  try {
    await instance.post('/api/budget/create', {
      monthBudget: monthBudget,
    })
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

/**
 * 총 예산 수정
 * @param {Number} totalBudget
 * @returns {Promise<Object>}
 */
export const putBudget = async (totalBudget) => {
  try {
    const response = await instance.put('/api/budget', {
      budgetAmount: totalBudget,
    })

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

export const postBudgetUpdate = async () => {
  try {
    const response = await instance.post('/api/budget/download')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
    else if (error.response.data.code === 'B001') return -1

    console.log(error)
  }
}

export const getCategoryBudget = async () => {
  try {
    const response = await instance.get('/api/budget/category')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'

    console.log(error)
  }
}

export const putCategoryBudget = async (budget) => {
  try {
    const response = await instance.put('/api/budget/category', budget)

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'

    console.log(error)
  }
}

export const getBudgetForSixMonths = async () => {
  try {
    const response = await instance.get('/api/budget/graph')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'

    console.log(error)
  }
}

export const getFinancialLedgerId = async (date) => {
  const currDate = Number(`${new Date().getFullYear()}${new Date().getMonth() + 1}`)
  const inputDate = Number(date)

  try {
    const response = await instance.get(`/api/budget/date/${date}`)

    return response.data
  } catch (error) {
    if (inputDate < currDate && error.status == 404) return 'empty'

    if (error.status == 403) location.href = '/login'
    else if (error.response.data.code === 'B001') return -1
  }
}

export const getFinancialLedger = async (budgetId, page) => {
  try {
    const response = await instance.get(`/api/budget/${budgetId}?page=${page}`)

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

export const getAllFinancialLedger = async (budgetId) => {
  try {
    const response = await instance.get(`/api/budget/${budgetId}/all`)

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

export const getChatbotForLatest = async () => {
  try {
    const response = await instance.get('/api/openAI/preview')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
    else if (error.response.data.code === 'B001') return -1
  }
}

export const getChatbotForWhole = async () => {
  try {
    const response = await instance.get('/api/openAI/data')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
    else if (error.response.data.code === 'B001') return -1
  }
}

/**
 * 관리자 권한으로 목데이터 추가
 * @returns {Promise<String>}
 */
export const postMock = async () => {
  try {
    const response = await instance.post('/api/mock/insert')

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

/**
 * 관리자 권한으로 새로운 거래내역 생성
 * @returns {Promise<Object>}
 */
export const postNewTransaction = async (transactionData) => {
  try {
    const response = await instance.post('/api/admin/transaction', transactionData)

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}

/**
 * 관리자 권한으로 새로운 계좌 생성
 * @param {Object} accountData
 * @returns {Promise<String>}
 */
export const postNewAccount = async (accountData) => {
  try {
    const response = await instance.post('/api/admin/account', accountData)

    return response.data
  } catch (error) {
    if (error.status == 403) location.href = '/login'
  }
}
