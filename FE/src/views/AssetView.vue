<script setup>
import { onMounted, ref } from 'vue'

import Account from '@/components/asset/Accounts.vue'
import TotalBalance from '@/components/home/TotalBalance.vue'

import { getFreeAccountList, getSavingAccountList, getTotalBalance } from '@/utils/api'

const isLoading = ref(true)

const totalBalance = ref(null)

const freeAccountList = ref()
const savingAccountList = ref()

onMounted(async () => {
  const balance = await getTotalBalance()
  totalBalance.value = balance.total

  freeAccountList.value = await getFreeAccountList()
  savingAccountList.value = await getSavingAccountList()

  isLoading.value = false
})
</script>

<template>
  <div class="flex-one">
    <div v-if="!isLoading" class="page-container">
      <TotalBalance :total-balance="totalBalance" />
      <Account title="자유 입출금 계좌" :accounts="freeAccountList" />
      <Account title="적금 계좌" :accounts="savingAccountList" />
    </div>
  </div>
</template>
