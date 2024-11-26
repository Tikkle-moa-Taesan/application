<script setup>
import { postMock } from '@/utils/api'
import { ref } from 'vue'

const isMethodClicked = ref(false)

const handleMethodClick = () => {
  isMethodClicked.value = !isMethodClicked.value
}

const handleFormSubmit = async () => {
  await postMock()

  isMethodClicked.value = false
}
</script>

<template>
  <div class="method-container">
    <div @click="handleMethodClick" class="title-container">
      <span class="type">POST</span>
      <h2>목데이터 추가</h2>
    </div>

    <Transition>
      <button v-if="isMethodClicked" @click.prevent="handleFormSubmit" class="submit-btn">
        데이터 추가
      </button>
    </Transition>
  </div>
</template>

<style scoped>
.method-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
  font-size: 14px;
}

.title-container {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.type {
  padding: 0.5rem 1rem;
  background-color: #49cc90;
  border-radius: 5px;
  color: white;
}

h2 {
  font-size: 1rem;
}

.submit-btn {
  padding: 0.5rem;
  border: none;
  border-radius: 5px;
  background-color: #3396f4;
  color: white;
}

.v-enter-active {
  transition: opacity 0.5s ease;
}

.v-enter-from {
  opacity: 0;
}
</style>
