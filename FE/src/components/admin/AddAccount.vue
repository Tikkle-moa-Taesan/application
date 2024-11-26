<script setup>
import { ref } from 'vue'

import { postNewAccount } from '@/utils/api'

const INPUT_CONTENT = [
  {
    model: 'accountNumber',
    label: '계좌번호',
    type: 'text',
    placeholder: '111-11-111111',
  },
  {
    model: 'accountName',
    label: '계좌이름',
    type: 'text',
    placeholder: '자유입출금통장 1',
  },
  {
    model: 'bankName',
    label: '은행',
    type: 'text',
    placeholder: '하나은행',
  },
  {
    model: 'interestRate',
    label: '이자율',
    type: 'number',
    placeholder: '0',
  },
  {
    model: 'maturityDate',
    label: '만기일',
    type: 'date',
  },
]

const isMethodClicked = ref(false)

const inputValue = ref({
  accountType: 'free',
  accountNumber: null,
  accountName: null,
  bankName: null,
  interestRate: 0,
  maturityDate: null,
})

const handleMethodClick = () => {
  isMethodClicked.value = !isMethodClicked.value
}

// FIX: 자유입출금계좌 maturityDate를 null값으로 보내면 에러 발생
const handleFormSubmit = async () => {
  inputValue.value.transactionDatetime = new Date(inputValue.value.transactionDatetime)

  await postNewAccount(inputValue.value)

  isMethodClicked.value = false
}
</script>

<template>
  <div class="method-container">
    <div @click.stop="handleMethodClick" class="title-container">
      <span class="type">POST</span>
      <h2>계좌 추가</h2>
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
            <label for="accountType">계좌종류</label>
            <select v-model="inputValue.accountType" class="input" id="accountType">
              <option value="free">자유입출금</option>
              <option value="saving">적금</option>
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
