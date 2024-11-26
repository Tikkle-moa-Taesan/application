<script setup>
import { computed } from 'vue'

import { formatHour } from '@/utils/formatDate'
import formatNumber from '@/utils/formatNumber'

const props = defineProps({
  transaction: Object,
})

const type = computed(() => {
  return props.transaction.transactionType === 'expense' ? '-' : '+'
})
</script>

<template>
  <div>
    <div class="detail-container">
      <div class="description">
        <span>{{ transaction.merchantName }}</span>
        <span class="text-gray">{{ formatHour(transaction.transactionDatetime) }}</span>
      </div>
      <div class="money">
        <span class="spending-money" :class="{ 'text-blue': type === '+' }"
          >{{ type }}{{ formatNumber(transaction.amount) }}</span
        >
        <span class="text-gray">잔액 {{ transaction.balanceAfter.toLocaleString() }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.detail-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.description {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.money {
  display: flex;
  flex-direction: column;
  align-items: end;
  gap: 0.5rem;
}

.text-gray {
  font-size: 0.875rem;
  color: #646464;
}
</style>
