<script setup>
import formatNumber from '@/utils/formatNumber'

import defaultProfileImg from '@/assets/images/ghost.webp'
import { inject } from 'vue'

defineProps({
  totalBalance: Number,
})

const profile = inject('profile')
</script>

<template>
  <div class="total-balance-container">
    <div class="info-container">
      <span class="title">{{ profile?.memberName }}님의 총 자산</span>
      <span class="total-balance">{{ formatNumber(totalBalance) }} 원</span>
    </div>

    <div class="img-container">
      <img class="profile-img" :src="profile?.picture || defaultProfileImg" alt="프로필 사진" />
    </div>
  </div>
</template>

<style scoped>
.total-balance-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background-color: #74b7f8;
  border-radius: 10px;
}

.info-container {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.info-container > span {
  font-weight: 700;
  color: white;
}

.info-container .total-balance {
  font-size: 1.25rem;
}

.img-container {
  width: 3.25rem;
  height: 3.25rem;
  border-radius: 999px;
  background-color: white;
  overflow: hidden;
}

.profile-img {
  width: 100%;
}
</style>
