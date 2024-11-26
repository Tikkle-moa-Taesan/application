<script setup>
import { onMounted, ref } from 'vue'

import CategoryBudgetForm from '@/components/financialLedger/CategoryBudgetForm.vue'

import router from '@/router'
import { getBudgetStatistic, getCategoryBudget, putCategoryBudget } from '@/utils/api'

const totalBudget = ref(0)

const categoryBudget = ref({
  foodBudget: 0,
  livingBudget: 0,
  housingCommunicationBudget: 0,
  financeBudget: 0,
  transportationBudget: 0,
  childcareBudget: 0,
  leisureCultureBudget: 0,
  petBudget: 0,
  eventGiftBudget: 0,
})

const fetchTotalBudget = async () => {
  try {
    const res = await getBudgetStatistic()

    totalBudget.value = res.thisMonthBudget
  } catch (error) {
    console.error('총 예산을 불러오는 데 실패하였습니다.', error)
  }
}

const fetchCategoryBudget = async () => {
  try {
    categoryBudget.value = await getCategoryBudget()
  } catch (error) {
    console.error('카테고리 별 예산 정보를 불러오는 데 실패하였습니다.', error)
  }
}

const handleBudgetFormSubmit = async () => {
  try {
    await putCategoryBudget(categoryBudget.value)

    router.push({ name: 'financial-ledger-budget' })
  } catch (error) {
    console.error('카테고리 별 예산을 업데이트 하는 데 실패하였습니다.', error)
  }
}

onMounted(async () => {
  try {
    await fetchTotalBudget()
    await fetchCategoryBudget()
  } catch (error) {
    console.error('이번 달 예산 금액을 불러오는 데 실패하였습니다.', error)
  }
})
</script>

<template>
  <div class="page-container">
    <CategoryBudgetForm
      v-model="categoryBudget"
      :total-budget="totalBudget"
      @submit-form="handleBudgetFormSubmit"
    />
  </div>
</template>

<style scoped></style>
