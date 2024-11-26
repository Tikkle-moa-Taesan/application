<script setup>
import formatNumber from '@/utils/formatNumber'

import BANK from '@/constants/bank'
import ghostFace from '@/assets/images/ghost-face.webp'

const props = defineProps({
  account: Object,
})
</script>

<template>
  <div class="account-container">
    <div class="account-info-container">
      <div class="bank-img-container">
        <img :src="BANK[account.bankName] || ghostFace" alt="은행아이콘" />
      </div>
      <span class="account-name">{{ account.accountName }}</span>
    </div>

    <span> {{ formatNumber(account.balance) }}원 </span>
  </div>
</template>

<style scoped>
.account-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.account-info-container {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.bank-img-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 2.75rem;
  height: 2.75rem;
  border-radius: 999px;
  background-color: #e8e8e8;
}

.bank-img-container img {
  width: 60%;
}

.account-name {
  color: #646464;
}
</style>
