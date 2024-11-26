<script setup>
defineProps({
  msg: String,
})
</script>

<template>
  <div class="loading-container">
    <img class="ghost-img" src="@/assets/images/ghost.webp" alt="유령" />
    <span class="msg">{{ msg }}</span>
  </div>
</template>

<style scoped>
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;
}

.msg {
  color: #646464;
}

.ghost-img {
  width: 11rem;
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
