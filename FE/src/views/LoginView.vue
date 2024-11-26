<script setup>
const BASE_FE_URL = import.meta.env.VITE_BASE_FE_URL

const KAKAO_KEY = import.meta.env.VITE_KAKAO_KEY
const GOOGLE_CLIENT_ID = import.meta.env.VITE_GOOGLE_CLIENT_ID

const kakaoRedirectUri = `${BASE_FE_URL}/login/kakao/code`
const googleRedirectUri = `${BASE_FE_URL}/login/google/code`

const handleKakaoBtnClick = () => {
  location.href = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${KAKAO_KEY}&redirect_uri=${kakaoRedirectUri}&scope=openid`
}

const handleGoogleBtnClick = () => {
  location.href = `https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${GOOGLE_CLIENT_ID}&scope=openid email profile&redirect_uri=${googleRedirectUri}`
}
</script>

<template>
  <div class="container">
    <div class="img-container">
      <img class="logo" src="@/assets/images/TmT.webp" alt="logo" />
      <img class="ghost" src="@/assets/images/ghost.webp" alt="유령" />
    </div>

    <div class="btn-container">
      <button @click="handleKakaoBtnClick" class="login-btn kakao-btn">
        <img src="@/assets/images/kakao-logo.webp" alt="카카오" />
        <span>카카오 로그인</span>
      </button>
      <button @click="handleGoogleBtnClick" class="login-btn google-btn">
        <img src="@/assets/images/google-logo.webp" alt="구글" />
        <span>구글 로그인</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
.container {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 4rem;
  height: 100vh;
  background-image: url('@/assets/images/login-background.webp');
  background-size: cover;
}

.img-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;
}

.img-container .logo {
  width: 10rem;
}

.img-container .ghost {
  width: 15rem;
  animation: float 3s ease-in-out infinite;
}

.btn-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.login-btn {
  display: flex;
  align-items: center;
  width: 18rem;
  height: 3.75rem;
  padding: 1.25rem;
  border: none;
  border-radius: 10px;
  font-size: 1rem;
  font-weight: 500;
}

.login-btn > img {
  width: 1.5rem;
  height: 1.5rem;
}

.login-btn > span {
  width: 100%;
}

.kakao-btn {
  background-color: #fee500;
}

.google-btn {
  border: 1px solid #867e75;
  background-color: white;
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
