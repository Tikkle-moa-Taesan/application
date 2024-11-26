<script setup>
import { inject, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'

import router from '@/router'
import { getProfile, postKaKaoLogin } from '@/utils/api'

const route = useRoute()
const code = ref(route.query.code)

const profile = inject('profile')

onMounted(async () => {
  await postKaKaoLogin(code.value)

  profile.value = await getProfile()

  setTimeout(() => {
    router.push({ name: 'home' })
  }, 1000)
})
</script>

<template>
  <div class="login-loading-container">
    <img class="ghost-img" src="@/assets/images/ghost.webp" alt="유령" />
    <span class="loading-msg">잠시만 기다려주세요</span>
  </div>
</template>

<style scoped>
.login-loading-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 3rem;
  height: 100vh;
  background-color: #f2f2f2;
}

.loading-msg {
  font-weight: 600;
}

.ghost-img {
  width: 13rem;
  animation: float 3s ease-in-out infinite;
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-15px);
  }
}
</style>
