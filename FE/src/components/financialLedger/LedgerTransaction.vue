<script setup>
import { computed } from 'vue'

import LedgerTransactionDay from './LedgerTransactionDay.vue'

import { formatDate } from '@/utils/formatDate'

const props = defineProps({
  transactions: Array,
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
  return groupingByDate(props.transactions)
})
</script>

<template>
  <div class="ledger-transaction-container">
    <LedgerTransactionDay
      v-if="transactions.length > 0"
      v-for="(transactions, date) in groupedTransaction"
      :key="date"
      :date="date"
      :transactions="transactions"
    />
  </div>
</template>

<style scoped>
.ledger-transaction-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
}
</style>
