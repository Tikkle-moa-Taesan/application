<script setup>
import AccountTransactionDayDetail from './AccountTransactionDayDetail.vue'

defineProps({
  date: String,
  transactions: Array,
})
</script>

<template>
  <div class="day-transaction-container">
    <span class="date">{{ date }}</span>

    <div class="day-transaction-list">
      <AccountTransactionDayDetail
        v-for="transaction in transactions"
        :key="transaction.transactionDatetime"
        :transaction="transaction"
      />
    </div>
  </div>
</template>

<style scoped>
.day-transaction-container {
  display: flex;
  flex-direction: column;
}

.date {
  color: #646464;
  padding-bottom: 0.5rem;
  border-bottom: 2px dashed #f2f2f2;
}

.day-transaction-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  padding: 1rem 0;
}
</style>
