<script setup>
import { ref } from 'vue'

import OptionSelection from '../commons/OptionSelection.vue'

const selectedCondition = ref({
  transactionType: 'null',
  period: 0,
})

const transactionOptions = [
  { label: '전체', value: 'null' },
  { label: '입금', value: 'income' },
  { label: '출금', value: 'expense' },
]

const periodOptions = [
  { label: '이번 달', value: 0 },
  { label: '3개월', value: 3 },
  { label: '6개월', value: 6 },
]

const emits = defineEmits(['click', 'closeModal'])

const handleSettingBtnClick = () => {
  emits('click', selectedCondition.value)
}

const handleCloseIconClick = () => {
  emits('closeModal')
}
</script>

<template>
  <div class="modal-container">
    <div class="title">
      <span>조건 설정</span>
      <img
        @click="handleCloseIconClick"
        class="close-icon"
        src="@/assets/icons/close.png"
        alt="닫기"
      />
    </div>

    <OptionSelection
      title="거래구분"
      :options="transactionOptions"
      v-model="selectedCondition.transactionType"
    />

    <OptionSelection title="조회기간" :options="periodOptions" v-model="selectedCondition.period" />

    <button @click="handleSettingBtnClick" type="button">설정</button>
  </div>
</template>

<style scoped>
.title {
  display: flex;
  justify-content: space-between;
  font-weight: 600;
}

.close-icon {
  width: 1rem;
}

.close-icon:hover {
  cursor: pointer;
}

button {
  margin-top: 0.75rem;
  padding: 0.75rem 0;
  border: none;
  border-radius: 5px;
  background-color: #3396f4;
  color: white;
  font-weight: 600;
}
</style>
