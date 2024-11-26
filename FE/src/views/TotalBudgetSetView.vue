<script setup>
import { onMounted, ref } from 'vue'

import LastMonthSummary from '@/components/financialLedger/LastMonthSummary.vue'
import TotalBudgetForm from '@/components/financialLedger/TotalBudgetForm.vue'
import { getBudgetStatistic } from '@/utils/api'

const isLoading = ref(true)

const previousBudget = ref(0)

onMounted(async () => {
  const res = await getBudgetStatistic()

  if (res !== -1) previousBudget.value = res.thisMonthBudget

  isLoading.value = false
})
</script>

<template>
  <div class="flex-one">
    <div v-if="!isLoading" class="page-container">
      <LastMonthSummary />
      <TotalBudgetForm :previous-budget="previousBudget" />
    </div>
  </div>
</template>

<style scoped>
.flex-one {
  display: flex;
  flex-direction: column;
}
</style>
