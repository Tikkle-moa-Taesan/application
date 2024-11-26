<script setup>
import router from '@/router'
import Account from './Account.vue'
import { computed } from 'vue'

const props = defineProps({
  title: String,
  accounts: Object,
})

const type = computed(() => (props.title === '자유 입출금 계좌' ? 'free' : 'saving'))

const handleAccountClick = (accountId) => {
  router.push({ name: 'asset-detail', params: { type: type.value, id: accountId } })
}
</script>

<template>
  <div class="free-accounts-container">
    <h2>{{ title }}</h2>

    <div class="account-list">
      <Account
        v-for="account in accounts"
        @click="handleAccountClick(account.accountId)"
        :key="account.accountId"
        :account="account"
      />
    </div>
  </div>
</template>

<style scoped>
.free-accounts-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
}

.free-accounts-container > h2 {
  font-weight: 600;
}

.account-list {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  font-size: 0.875rem;
}
</style>
