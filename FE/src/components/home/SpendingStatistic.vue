<script setup>
import { computed } from 'vue'

import DoughnutGraphSpending from './DoughnutGraphSpending.vue'
import DoughnutGraphBudget from './DoughnutGraphBudget.vue'

import formatNumber from '@/utils/formatNumber'
import router from '@/router'

const props = defineProps({
  monthlySpending: [Object, Number],
  monthlyBudget: [Object, Number],
})

const monthlyDifference = computed(() => {
  if (!props.monthlySpending) return 0

  return props.monthlySpending.lastMonthExpense - props.monthlySpending.thisMonthExpense
})

const weeklyDifference = computed(() => {
  if (!props.monthlySpending) return 0

  return props.monthlySpending.lastWeekExpense - props.monthlySpending.thisWeekExpense
})

const differenceString = (diff) => {
  return diff > 0 ? '적게' : '많이'
}

const hasStatisticData = computed(() => {
  return props.monthlySpending !== -1 && props.monthlyBudget !== -1
})

const handleRegisterBtnClick = () => {
  router.push({ name: 'total-budget-set' })
}
</script>

<template>
  <div class="component-container">
    <h2>지출 통계</h2>

    <div v-if="hasStatisticData" class="spending-statistic-container">
      <div class="difference-container">
        <p class="difference-info">
          지난 달보다
          <span class="difference" :class="monthlyDifference > 0 ? 'text-blue' : 'text-red'">
            {{ formatNumber(monthlyDifference) }} </span
          >원 <span>{{ differenceString(monthlyDifference) }}</span> 사용했어요.
        </p>
        <p class="difference-info">
          지난 주보다
          <span class="difference" :class="weeklyDifference > 0 ? 'text-blue' : 'text-red'">
            {{ formatNumber(weeklyDifference) }} </span
          >원 <span>{{ differenceString(weeklyDifference) }}</span> 사용했어요.
        </p>
      </div>

      <div class="graph-container">
        <DoughnutGraphSpending
          :category-expenses="monthlySpending?.categoryExpense"
          center-text="지출"
        />
        <DoughnutGraphBudget :monthly-budget="monthlyBudget" center-text="예산" />
      </div>
    </div>

    <div v-else class="no-spending-statistic">
      <div class="register-box">
        <div class="msg">
          <span>가계부를 등록하고</span>
          <span>통계 데이터를 확인해보세요</span>
        </div>
        <button @click="handleRegisterBtnClick">가계부 등록하기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
h2 {
  font-weight: 600;
}

.component-container {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
}

.spending-statistic-container {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.difference-container {
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
  font-size: 0.875rem;
}

.difference-info {
  color: #646464;
}

.difference {
  font-weight: 700;
}

.graph-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.no-spending-statistic {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  border-radius: 10px;

  aspect-ratio: 3 / 2;
  background-image: url('/src/assets/images/no-statistic.webp');
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}

.register-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  padding: 1rem 2rem;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.no-spending-statistic .msg {
  display: flex;
  flex-direction: column;
  align-self: center;
  gap: 0.5rem;
  font-size: 0.875rem;
  text-align: center;
  color: #646464;
}

.no-spending-statistic button {
  width: 100%;
  margin-top: 0.75rem;
  padding: 0.75rem 0;
  border: none;
  border-radius: 5px;
  background-color: #3396f4;
  color: white;
  font-weight: 600;
}
</style>
