<script setup>
import { inject, ref, watch } from 'vue'

const profile = inject('profile')

const isAdmin = ref(false)

watch(
  profile,
  (newValue) => {
    if (newValue === null) return

    isAdmin.value = newValue.role === 'ROLE_ADMIN'
  },
  { immediate: true },
)
</script>

<template>
  <div class="footer-container">
    <RouterLink class="link" :to="{ name: 'asset' }">
      <img src="@/assets/icons/wallet.png" alt="자산" />
    </RouterLink>
    <RouterLink class="link" :to="{ name: 'home' }">
      <img src="@/assets/icons/home.png" alt="홈" />
    </RouterLink>
    <RouterLink class="link" :to="{ name: 'financial-ledger-list' }">
      <img src="@/assets/icons/calendar.png" alt="가계부" />
    </RouterLink>
    <RouterLink v-if="isAdmin" class="link" :to="{ name: 'admin' }">
      <img class="admin" src="@/assets/icons/admin.png" alt="관리자" />
    </RouterLink>
  </div>
</template>

<style scoped>
.footer-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 1.25rem;
  background-color: #74b7f8;
}

.link {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 1.875rem;
  height: 1.875rem;
}

.link img {
  width: 100%;
  height: 100%;
}

.link .admin {
  width: 85%;
  height: 85%;
}
</style>
