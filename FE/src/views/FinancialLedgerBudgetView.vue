<script setup>
import { onMounted, ref } from 'vue'

import CategoryBudgetInfo from '@/components/financialLedger/CategoryBudgetInfo.vue'
import LineGraphBudget from '@/components/financialLedger/LineGraphBudget.vue'
import TotalBudgetInfo from '@/components/financialLedger/TotalBudgetInfo.vue'

import {
  getBudgetForSixMonths,
  getBudgetStatistic,
  getCategoryBudget,
  getExpenseStatistic,
} from '@/utils/api'

const isLoading = ref(true)

const totalBudgetInfo = ref({})

const budgetForSixMonths = ref({})

const categoryBudget = ref({})
const categoryExpense = ref({})

onMounted(async () => {
  totalBudgetInfo.value = await getBudgetStatistic()

  budgetForSixMonths.value = await getBudgetForSixMonths()

  categoryBudget.value = await getCategoryBudget()

  const expenseData = await getExpenseStatistic()
  categoryExpense.value = expenseData.categoryExpense

  isLoading.value = false
})
</script>

<template>
  <div class="flex-one">
    <div v-if="!isLoading" class="budget-page-container">
      <LineGraphBudget :budget-for-six-months="budgetForSixMonths" center-text="" />

      <TotalBudgetInfo :total-budget-info="totalBudgetInfo" />

      <CategoryBudgetInfo :category-budget="categoryBudget" :category-expense="categoryExpense" />
    </div>
  </div>
</template>

<style scoped>
.budget-page-container {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}
</style>
