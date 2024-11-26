<script setup>
import { computed, ref, watch } from 'vue'

import Calendar from '@/components/financialLedger/Calendar.vue'
import LedgerTransactionDay from '@/components/financialLedger/LedgerTransactionDay.vue'

import { getAllFinancialLedger } from '@/utils/api'
import { formatDate } from '@/utils/formatDate'

const props = defineProps({
  displayedDate: Array,
  financialLedgerInfo: Object,
})

const allTransactions = ref([])

const selectedDate = ref(null)

const fetchAllFinancialLedger = async () => {
  if (!props.financialLedgerInfo.budgetId) return

  const res = await getAllFinancialLedger(props.financialLedgerInfo.budgetId)
  allTransactions.value = res.budgetTransactions
}

const groupingByDate = (transactions) => {
  return transactions.reduce((acc, transactions) => {
    const date = formatDate(transactions.transactionDatetime)

    if (!acc[date]) acc[date] = []

    acc[date].push(transactions)

    return acc
  }, {})
}

const calculatingByDate = (transactions) => {
  return transactions.reduce((acc, transaction) => {
    const date = new Date(transaction.transactionDatetime).getDate()

    if (!acc[date]) acc[date] = { income: 0, expense: 0 }

    if (transaction.transactionType === 'income') acc[date].income += transaction.amount
    else if (transaction.transactionType === 'expense') acc[date].expense += transaction.amount

    return acc
  }, {})
}

const groupedTransaction = computed(() => {
  return groupingByDate(allTransactions?.value || [])
})

const financialData = computed(() => {
  return calculatingByDate(allTransactions?.value || [])
})

const handleDateClick = (date) => {
  selectedDate.value = date
}

watch(
  props,
  () => {
    fetchAllFinancialLedger()
  },
  { immediate: true },
)
</script>

<template>
  <div class="calendar-page-container">
    <Calendar
      @click-date="handleDateClick"
      :displayed-date="displayedDate"
      :financial-data="financialData"
    />

    <Transition>
      <div v-if="selectedDate" :key="selectedDate" class="transition-container">
        <LedgerTransactionDay
          :date="selectedDate"
          :transactions="groupedTransaction[selectedDate]"
        />
      </div>
    </Transition>
  </div>
</template>

<style scoped>
.calendar-page-container {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.transition-container {
  display: flex;
  flex-direction: column;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
}

.v-enter-active {
  transition:
    opacity 0.5s ease,
    transform 0.5s ease;
}

.v-enter-from {
  opacity: 0;
  transform: translateY(5%);
}
</style>
