<script setup>
import { computed, onMounted, ref, watch } from 'vue'

import formatNumber from '@/utils/formatNumber'
import CATEGORIES from '@/constants/category'

const props = defineProps({
  categoryBudget: Object,
  categoryExpense: Object,
})

const categoryRest = computed(() => {
  return CATEGORIES.reduce((acc, { eng }) => {
    acc[eng] = props.categoryBudget[`${eng}Budget`] - props.categoryExpense[`${eng}Expense`]

    return acc
  }, {})
})

const categoryExpenseRatio = computed(() => {
  return CATEGORIES.reduce((acc, { eng }) => {
    let budget = props.categoryBudget[`${eng}Budget`]
    const expense = props.categoryExpense[`${eng}Expense`]

    if (budget === 0) acc[eng] = 100
    else acc[eng] = Math.round(Math.min((expense / budget) * 100, 100))

    return acc
  }, {})
})

const progress = ref(
  CATEGORIES.reduce((acc, { eng }) => {
    acc[eng] = 0

    return acc
  }, {}),
)

const animateProgress = (key, rate) => {
  let currRate = 0

  const interval = setInterval(() => {
    if (currRate >= rate) {
      clearInterval(interval)
    } else {
      currRate++
      progress.value[key] = currRate
    }
  }, 15)
}

watch(
  categoryExpenseRatio,
  (newValue) => {
    CATEGORIES.forEach(({ eng }) => {
      animateProgress(eng, newValue[eng])
    })
  },
  { immediate: true },
)
</script>

<template>
  <div class="budget-info-container">
    <span class="title">카테고리 별 사용 예산</span>

    <div class="expense-ratio-container">
      <div class="category-container" v-for="{ kor, eng } in CATEGORIES" :key="eng">
        <div class="info-container">
          <div class="category-info">
            <span>{{ kor }}</span>
            <div class="rest-value">
              {{ formatNumber(categoryRest[eng]) }}원
              <span v-if="categoryRest[eng] >= 0">남음</span>
              <span v-else>초과</span>
            </div>
          </div>
          <span>{{ categoryExpenseRatio[eng] }}%</span>
        </div>
        <div class="budget-bar-container">
          <div
            class="expense-bar"
            :style="{ width: progress[eng] + '%' }"
            :class="{
              'is-red': progress[eng] >= 90,
              'is-green': progress[eng] >= 80,
              'is-blue': progress[eng] >= 20,
            }"
          ></div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.budget-info-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
}

.title {
  font-weight: 700;
}

.expense-ratio-container {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
  font-size: 0.875rem;
}

.category-container {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.info-container {
  display: flex;
  justify-content: space-between;
}

.category-info {
  display: flex;
  gap: 0.5rem;
}

.rest-value {
  color: #646464;
}

.budget-bar-container {
  position: relative;
  height: 0.75rem;
  border-radius: 999px;
  background-color: #d9d9d9;
  overflow: hidden;
}

.expense-bar {
  height: 100%;
  background-color: #a5a5a5;
  transition: width 0.3s ease-out;
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
