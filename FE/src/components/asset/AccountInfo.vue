<script setup>
import formatNumber from '@/utils/formatNumber'

defineProps({
  accountType: String,
  accountInfo: Object,
})
</script>

<template>
  <div class="account-info-container">
    <div class="account-main-info">
      <span class="account-name">{{ accountInfo.accountName }}</span>
      <span class="account-balance">{{ formatNumber(accountInfo.balance) }}원</span>
    </div>

    <span class="account-detail">
      <span>
        {{ accountInfo.bankName }}
      </span>
      <span>
        {{ accountInfo.accountNumber }}
      </span>
    </span>

    <div v-if="accountType === 'saving'" class="saving-account-detail">
      <div class="content">
        <span class="label">적용 금리</span>
        <span class="value">{{ accountInfo.interestRate }}%</span>
      </div>
      <div class="content">
        <span class="label">만기일</span>
        <span class="value">{{ accountInfo.maturityDate }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.account-info-container {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.account-main-info {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.account-name {
  font-weight: 700;
  color: #646464;
}

.account-balance {
  font-size: 1.25rem;
  font-weight: 700;
}

.account-detail {
  display: flex;
  gap: 0.5rem;
  font-size: 14px;
  color: #646464;
}

.saving-account-detail {
  display: flex;
  gap: 3.75rem;
  padding: 0.75rem 0.5rem;
  border-radius: 5px;
  background-color: #f2f2f2;
}

.saving-account-detail .content {
  display: flex;
  gap: 0.5rem;
  font-size: 0.875rem;
}

.saving-account-detail .label {
  color: #646464;
}
</style>
