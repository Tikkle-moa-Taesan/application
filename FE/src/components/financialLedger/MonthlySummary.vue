<script setup>
import { inject } from 'vue'

import formatNumber from '@/utils/formatNumber'
import SelectMonthModal from './SelectMonthModal.vue'

defineProps({
  financialLedgerInfo: Object,
})

const model = defineModel()

const isModalShown = inject('isModalShown')

const currYear = new Date().getFullYear()

const handleOpenModal = () => {
  isModalShown.value = true
}

const handleCloseModal = () => {
  isModalShown.value = false
}

const handleSelectDate = (date) => {
  model.value = date

  isModalShown.value = false
}
</script>

<template>
  <div class="summary-container">
    <div @click="handleOpenModal" class="title">
      <div>
        <span v-if="currYear !== model[0]">{{ model[0] }}년</span> {{ model[1] }}월 내역
      </div>
      <img class="arrow-img" src="@/assets/icons/arrow-down.png" alt="화살표" />
    </div>

    <div class="income-and-expense">
      <div class="value-container">
        <span>지출</span>
        <span>
          <span class="expense-value">
            {{ formatNumber(financialLedgerInfo?.monthExpense || 0) }}
          </span>
          원
        </span>
      </div>
      <div class="value-container">
        <span>수입</span>
        <span>
          <span class="income-value">
            {{ formatNumber(financialLedgerInfo?.monthIncome || 0) }}
          </span>
          원
        </span>
      </div>
    </div>
  </div>

  <Transition>
    <div @click.self="handleCloseModal" v-if="isModalShown" class="modal-wrapper">
      <SelectMonthModal @close-modal="handleCloseModal" @select-date="handleSelectDate" />
    </div>
  </Transition>
</template>

<style scoped>
.summary-container {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.summary-container .title {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  font-size: 1.25rem;
  font-weight: 600;
}

.summary-container .arrow-img {
  width: 1rem;
  height: 0.7rem;
}

.income-and-expense {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  font-weight: 500;
}

.value-container {
  display: flex;
  gap: 1rem;
}

.value-container .expense-value {
  color: #ff3939;
}

.value-container .income-value {
  color: #3396f4;
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
