<script setup>
import { computed } from 'vue'

import AccountTransactionDay from './AccountTransactionDay.vue'
import Loading from '../commons/Loading.vue'

import { formatDate } from '@/utils/formatDate'

const props = defineProps({
  accountTransaction: Array,
})

const groupingByDate = (transactions) => {
  return transactions.reduce((acc, transactions) => {
    const date = formatDate(transactions.transactionDatetime)

    if (!acc[date]) acc[date] = []

    acc[date].push(transactions)

    return acc
  }, {})
}

const groupedTransaction = computed(() => {
  return groupingByDate(props.accountTransaction)
})
</script>

<template>
  <div
    class="account-transaction-container"
    :class="{ 'no-content': accountTransaction.length == 0 }"
  >
    <AccountTransactionDay
      v-if="accountTransaction.length > 0"
      v-for="(transactions, date) in groupedTransaction"
      :key="date"
      :date="date"
      :transactions="transactions"
    />

    <div v-else>
      <Loading msg="아직 거래 내역이 없어요." />
    </div>
  </div>
</template>

<style scoped>
.account-transaction-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
}

.no-content {
  flex: 1;
  justify-content: center;
}
</style>
