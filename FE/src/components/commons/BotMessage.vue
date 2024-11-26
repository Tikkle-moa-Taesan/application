<script setup>
const model = defineModel()

defineProps({
  modalType: String,
  msg: [String, null],
})
</script>

<template>
  <div class="message-container">
    <div v-if="modalType === 'text'" class="text" v-html="msg"></div>

    <div v-else class="option-container">
      <div>
        <input v-model="model" type="radio" id="latest" value="간편 자산 분석" />
        <label for="latest">간편 자산 분석</label>
      </div>
      <div>
        <input v-model="model" type="radio" id="whole" value="정밀 자산 분석" />
        <label class="whole" for="whole">정밀 자산 분석 (유료)</label>
      </div>
    </div>

    <div class="img-container">
      <img src="@/assets/images/ghost.webp" alt="유령" />
    </div>
  </div>
</template>

<style scoped>
.message-container {
  display: flex;
  align-items: flex-start;
  gap: 1rem;
}

.text {
  flex: 1;
  padding: 1rem;
  border-radius: 10px;
  background-color: rgba(255, 255, 255, 0.6);
  color: black;
  line-height: 1.25rem;
  font-size: 0.875rem;
}

.option-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 1rem;
  border-radius: 10px;
  background-color: rgba(255, 255, 255, 0.6);
  color: black;
  font-size: 0.875rem;
}

.option-container input {
  display: none;
}

.option-container label {
  display: block;
  width: 100%;
  padding: 0.75rem;
  border: none;
  border-radius: 5px;
  background-color: #74b7f8;
  color: white;
}

.option-container .whole {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.img-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 2.75rem;
  height: 2.75rem;
  border-radius: 999px;
  background-color: rgba(255, 255, 255, 0.6);
  overflow: hidden;
}

.img-container img {
  width: 70%;
}
</style>

<style>
.ai-response {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.ai-response ul {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  margin-left: 1.5rem;
}
</style>
