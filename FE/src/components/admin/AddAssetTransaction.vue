<script setup>
import { ref } from 'vue'

import CATEGORY_CODE from '@/constants/categoryCode'
import { postNewTransaction } from '@/utils/api'

const INPUT_CONTENT = [
  {
    model: 'accountId',
    label: '계좌 ID',
    type: 'number',
    placeholder: '1',
  },
  {
    model: 'transactionDatetime',
    label: '결제시간',
    type: 'datetime',
    placeholder: '2024-10-11 09:30:00',
  },
  {
    model: 'amount',
    label: '결제금액',
    type: 'number',
    placeholder: '10000',
  },
  {
    model: 'merchantName',
    label: '결제명',
    type: 'text',
    placeholder: '바나프레소',
  },
]

const isMethodClicked = ref(false)

const inputValue = ref({
  accountId: 2,
  categoryCode: 4,
  transactionDatetime: new Date(),
  amount: 500000,
  merchantName: '정기적금',
  transactionType: 'expense',
})

const handleMethodClick = () => {
  isMethodClicked.value = !isMethodClicked.value
}

const handleFormSubmit = async () => {
  inputValue.value.transactionDatetime = new Date(inputValue.value.transactionDatetime)

  await postNewTransaction(inputValue.value)

  isMethodClicked.value = false
}
</script>

<template>
  <div class="method-container">
    <div @click="handleMethodClick" class="title-container">
      <span class="type">POST</span>
      <h2>거래 내역 추가</h2>
    </div>

    <Transition>
      <form v-if="isMethodClicked" @submit.prevent="handleFormSubmit">
        <div class="input-wrapper">
          <div class="input-container" v-for="(content, idx) in INPUT_CONTENT" :key="idx">
            <label :for="content.model">{{ content.label }}</label>
            <input
              class="input"
              v-model="inputValue[content.model]"
              :type="content.type"
              :for="content.model"
              :placeholder="content.placeholder"
            />
          </div>

          <div class="input-container">
            <label for="transactionType">거래종류</label>
            <select v-model="inputValue.transactionType" class="input" id="transactionType">
              <option value="income">수입</option>
              <option value="expense">지출</option>
            </select>
          </div>

          <div class="input-container">
            <label for="categoryCode">카테고리</label>
            <select v-model="inputValue.categoryCode" class="input" id="categoryCode">
              <option v-for="(code, category) in CATEGORY_CODE" :value="code">
                {{ category }}
              </option>
            </select>
          </div>
        </div>

        <button class="submit-btn">거래내역 추가</button>
      </form>
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

form {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.input-wrapper {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.input-container {
  height: 1.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.input {
  width: 60%;
  color: #646464;
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
