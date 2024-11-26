<script setup>
import { computed, inject, ref } from 'vue'
import { useRoute } from 'vue-router'

import ChatbotModal from './ChatbotModal.vue'

import router from '@/router'

const route = useRoute()

const hasLogo = computed(() => route.meta.hasLogo)
const hasSearch = computed(() => route.meta.hasSearch)

const isChatbotModalShown = inject('isChatbotModalShown')

const chatbotConversation = ref([
  { type: 'bot', modalType: 'text', msg: '안녕하세요. 저는 당신의 AI 비서 Teemo라고 해요!' },
  { type: 'bot', modalType: 'text', msg: '원하시는 자산 분석 서비스를 선택해주세요.' },
  { type: 'bot', modalType: 'select' },
])

const handleBackClick = () => {
  router.go(-1)
}

const handleOpenChatbotModal = () => {
  isChatbotModalShown.value = true
}

const handleCloseChatbotModal = () => {
  isChatbotModalShown.value = false
}
</script>

<template>
  <div class="header-container">
    <div v-if="hasLogo">
      <RouterLink :to="{ name: 'home' }">
        <img class="main-logo" src="@/assets/images/TmT-logo.webp" alt="메인로고" />
      </RouterLink>
    </div>
    <div v-else class="arrow-container">
      <img
        @click="handleBackClick"
        class="arrow-left"
        src="@/assets/icons/arrow-left.png"
        alt="화살표"
      />
    </div>

    <nav>
      <img v-if="hasSearch" class="nav-icon" src="@/assets/icons/search.png" alt="검색" />
      <!-- <img class="nav-icon" src="@/assets/icons/bell.png" alt="알림" /> -->
      <img
        @click="handleOpenChatbotModal"
        class="bot-icon"
        src="@/assets/icons/chatbot.png"
        alt="챗봇"
      />
      <!-- <img class="nav-icon" src="@/assets/icons/hamburger.png" alt="메뉴" /> -->
    </nav>
  </div>

  <Transition>
    <div @click.self="handleCloseChatbotModal" v-if="isChatbotModalShown" class="modal-wrapper">
      <ChatbotModal v-model="chatbotConversation" />
    </div>
  </Transition>
</template>

<style scoped>
.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.25rem;
  background-color: #f2f2f2;
  box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.25);
}

.main-logo {
  height: 2.25rem;
}

.arrow-container {
  display: flex;
  align-items: center;
  height: 2.25rem;
}

.arrow-left {
  height: 1.25rem;
}

nav {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.nav-icon {
  width: 1.5625rem;
  height: 1.5625rem;
}

.bot-icon {
  height: 1.5rem;
  margin-left: -0.25rem;
}

.v-enter-active,
.v-leave-active {
  transition: opacity 0.5s ease;
}

.v-enter-from {
  opacity: 0;
}

.v-leave-to {
  opacity: 0;
}
</style>
