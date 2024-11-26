<script setup>
import { computed, provide, ref, watch } from 'vue'
import { useRoute } from 'vue-router'

import FloatingNav from '@/components/commons/FloatingNav.vue'
import MonthlySummary from '@/components/financialLedger/MonthlySummary.vue'
import Loading from '@/components/commons/Loading.vue'

import { getFinancialLedgerId, postBudgetUpdate } from '@/utils/api'
import router from '@/router'

const isLoading = ref(true)

const hasModifyContent = ref(false)
provide('hasModifyContent', hasModifyContent)

const route = useRoute()

const isBudget = computed(() => route.meta.isBudget == true)

const date = ref([new Date().getFullYear(), new Date().getMonth() + 1])

const financialLedgerInfo = ref(null)

const fetchFinancialLedgerInfo = async () => {
  const res = await getFinancialLedgerId(
    `${date.value[0]}${date.value[1].toString().padStart(2, '0')}`,
  )

  if (res === -1) {
    router.push({ name: 'total-budget-set' })
    return
  }

  if (res === 'empty') {
    financialLedgerInfo.value = null
    return
  }

  financialLedgerInfo.value = res
}

watch(
  date,
  () => {
    fetchFinancialLedgerInfo()
    isLoading.value = false
  },
  { immediate: true },
)

watch(hasModifyContent, async (newValue) => {
  if (!newValue) return

  await postBudgetUpdate()
  await fetchFinancialLedgerInfo()
})
</script>

<template>
  <div class="flex-one">
    <div v-if="!isLoading" class="page-container">
      <MonthlySummary
        v-if="!isBudget"
        v-model="date"
        :financial-ledger-info="financialLedgerInfo"
      />

      <div v-if="isBudget || financialLedgerInfo != null">
        <RouterView v-slot="{ Component }">
          <Transition>
            <component
              :is="Component"
              :displayed-date="date"
              :financial-ledger-info="financialLedgerInfo"
            />
          </Transition>
        </RouterView>
      </div>
      <div v-else class="empty-container">
        <Loading msg="가계부 내역이 존재하지 않아요." />
      </div>

      <FloatingNav class="floating-nav" />
    </div>
  </div>
</template>

<style scoped>
.flex-one {
  display: flex;
  flex-direction: column;
}

.page-container {
  padding-bottom: 4rem;
}

.floating-nav {
  position: fixed;
  bottom: 5rem;
  left: 50%;
  transform: translate(-50%, 0);
  width: 90%;
  max-width: calc(430px - 3rem);
}

.empty-container {
  flex: 1;
  justify-content: center;
  display: flex;
  flex-direction: column;
  gap: 2rem;
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
  transform: translateY(1%);
}
</style>
