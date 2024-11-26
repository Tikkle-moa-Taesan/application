<script setup></script>

<template>
  <div class="loading-container">
    <span class="main-msg">NOT FOUND</span>
    <div class="img-container">
      <RouterLink :to="{ name: 'home' }">
        <img class="ghost-img" src="@/assets/images/ghost.webp" alt="유령" />
      </RouterLink>
      <span class="msg">유령을 클릭하면 홈으로 돌아갈 수 있어요</span>
    </div>
  </div>
</template>

<style scoped>
.loading-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 6rem;
  background-color: #f2f2f2;
}

.main-msg {
  font-size: 1.5rem;
  font-weight: 700;
}

.img-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 2rem;
}

.ghost-img {
  width: 13rem;
  animation: float 3s ease-in-out infinite;
}

.msg {
  color: #646464;
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
