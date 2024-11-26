<script setup>
import { computed, onMounted, ref, watch } from 'vue'

import router from '@/router'

const props = defineProps({
  totalBudgetInfo: Object,
})

const thisMonthRest = computed(() => {
  return props.totalBudgetInfo.thisMonthBudget - props.totalBudgetInfo.thisMonthExpense
})

const progress = ref(0)

const animateProgress = (rate) => {
  let currRate = 0

  const interval = setInterval(() => {
    if (currRate >= rate) {
      clearInterval(interval)
    } else {
      currRate++
      progress.value = currRate
    }
  }, 10)
}

const handleModifyBtnClick = () => {
  router.push({ name: 'total-budget-set' })
}

onMounted(() => {
  animateProgress(props.totalBudgetInfo.rate)
})
</script>

<template>
  <div class="budget-info-container">
    <div class="budget-bar-container">
      <div
        class="expense-bar"
        :style="{ width: progress + '%' }"
        :class="{ 'is-red': progress >= 90, 'is-green': progress >= 80, 'is-blue': progress >= 20 }"
      ></div>
      <span class="expense-ratio">{{ progress }} %</span>
    </div>

    <div class="budget-value-container">
      <div class="value-container">
        <span>총 예산</span>
        <span>{{ totalBudgetInfo.thisMonthBudget.toLocaleString() }} 원</span>
      </div>
      <div class="value-container last">
        <span>현재까지의 지출</span>
        <span>{{ totalBudgetInfo.thisMonthExpense.toLocaleString() }} 원</span>
      </div>

      <div class="value-container">
        <span>남은 예산</span>
        <span class="rest-text">{{ thisMonthRest.toLocaleString() }} 원</span>
      </div>
    </div>

    <button @click="handleModifyBtnClick" class="modify-btn" type="button">예산 수정</button>
  </div>
</template>

<style scoped>
.budget-info-container {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
}

.budget-bar-container {
  position: relative;
  height: 1.5rem;
  border-radius: 999px;
  background-color: #d9d9d9;
  overflow: hidden;
}

.expense-bar {
  height: 100%;
  background-color: #a5a5a5;
  transition: width 0.3s ease-out;
}

.expense-ratio {
  position: absolute;
  top: 50%;
  left: 50%;
  font-size: 0.875rem;
  color: white;
  transform: translate(-50%, -50%);
}

.budget-value-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.value-container {
  display: flex;
  justify-content: space-between;
  font-size: 0.875rem;
}

.rest-text {
  font-weight: 700;
}

.last {
  padding-bottom: 0.75rem;
  border-bottom: 2px dashed #b0afaf;
}

.modify-btn {
  margin-top: 0.5rem;
  padding: 0.5rem;
  border: 1px solid #646464;
  border-radius: 10px;
  background-color: white;
  font-weight: 700;
  color: #646464;
}

.is-blue {
  background-color: #3396f4;
}

.is-green {
  background-color: #97b788;
}

.is-red {
  background-color: #ff5858;
}
</style>
