<script setup>
import { inject, ref, watch } from 'vue'

import { formatDateForLedger } from '@/utils/formatDate'

import CATEGORY_CODE from '@/constants/categoryCode'
import { deleteTransaction, modifyTransaction } from '@/utils/api'

const props = defineProps({
  transaction: Object,
})

const emits = defineEmits(['closeModal'])

const hasModifyContent = inject('hasModifyContent')

const isSamePrevious = ref(true)

const previousContent = ref({
  transactionType: props.transaction.transactionType,
  categoryCode: CATEGORY_CODE[props.transaction.categoryName],
  merchantName: props.transaction.merchantName,
  amount: props.transaction.amount,
})

const modifiedContent = ref({
  transactionType: props.transaction.transactionType,
  categoryCode: CATEGORY_CODE[props.transaction.categoryName],
  merchantName: props.transaction.merchantName,
  amount: props.transaction.amount,
})

const isCategoryClicked = ref(false)
const selectedCategoryName = ref(props.transaction.categoryName)

watch(
  modifiedContent,
  (newValue) => {
    isSamePrevious.value = JSON.stringify(previousContent.value) === JSON.stringify(newValue)
  },
  { deep: true },
)

const handleCloseIconClick = () => {
  emits('closeModal')
}

const handleCategoryClick = () => {
  isCategoryClicked.value = true
}

const handleCategoryInput = (category) => {
  selectedCategoryName.value = category
  isCategoryClicked.value = false
}

const handleDeleteBtnClick = async () => {
  await deleteTransaction(props.transaction.budgetTransactionId)

  hasModifyContent.value = true
  emits('closeModal')
}

const handleModifyBtnClick = async () => {
  await modifyTransaction(props.transaction.budgetTransactionId, modifiedContent.value)

  hasModifyContent.value = true
  emits('closeModal')
}
</script>

<template>
  <div class="modal-container">
    <div class="fix-region">
      <div class="transaction-info">
        <div class="major-info">
          <span>{{ transaction.merchantName }}</span>
          <span class="spending">{{ transaction.amount.toLocaleString() }}원</span>
        </div>
        <div class="detail-info">
          <span>{{ transaction.accountName }}</span>
          <span>{{ formatDateForLedger(transaction.transactionDatetime) }}</span>
        </div>
      </div>
      <img
        @click.stop="handleCloseIconClick"
        class="close-icon"
        src="@/assets/icons/close.png"
        alt="닫기"
      />
    </div>

    <div class="modify-region">
      <div class="item-container">
        <span class="label">분류</span>
        <div class="item">
          <label
            class="item-label"
            :class="{ 'is-selected': modifiedContent.transactionType === 'income' }"
            for="income"
            >수입</label
          >
          <input
            class="ratio-input"
            v-model="modifiedContent.transactionType"
            type="radio"
            id="income"
            value="income"
          />
          <label
            class="item-label"
            :class="{ 'is-selected': modifiedContent.transactionType === 'expense' }"
            for="expense"
            >지출</label
          >
          <input
            class="ratio-input"
            v-model="modifiedContent.transactionType"
            type="radio"
            id="expense"
            value="expense"
          />
        </div>
      </div>

      <div class="item-container">
        <span>카테고리</span>
        <div class="item category-wrapper">
          <div v-if="!isCategoryClicked" @click.stop="handleCategoryClick" class="category-input">
            <span>{{ selectedCategoryName }}</span>
            <img src="@/assets/icons/pencil.png" alt="수정" />
          </div>
          <div v-else class="category-container">
            <div class="category-selection" v-for="(code, category) in CATEGORY_CODE">
              <input
                v-model="modifiedContent.categoryCode"
                type="radio"
                @input.stop="handleCategoryInput(category)"
                :id="category"
                :value="code"
              />
              <label :for="category">{{ category }}</label>
            </div>
          </div>
        </div>
      </div>

      <div class="item-container">
        <label for="merchantName">거래명</label>
        <div class="item">
          <input type="text" id="merchantName" v-model="modifiedContent.merchantName" />
          <img src="@/assets/icons/pencil.png" alt="수정" />
        </div>
      </div>

      <div class="item-container">
        <label for="amount">거래 금액</label>
        <div class="item">
          <input type="number" id="item" v-model="modifiedContent.amount" />
          <img src="@/assets/icons/pencil.png" alt="수정" />
        </div>
      </div>
    </div>

    <div class="btn-container">
      <button class="delete-btn" @click.stop="handleDeleteBtnClick" type="button">삭제</button>
      <button
        class="modify-btn"
        @click.stop="handleModifyBtnClick"
        :disabled="isSamePrevious"
        type="submit"
      >
        저장
      </button>
    </div>
  </div>
</template>

<style scoped>
.fix-region {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding-bottom: 1rem;
  border-bottom: 2px dashed #f2f2f2;
}

.close-icon {
  width: 1rem;
}

.close-icon:hover {
  cursor: pointer;
}

.transaction-info {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.major-info {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.spending {
  font-size: 1.125rem;
  font-weight: 700;
}

.detail-info {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  font-size: 14px;
  color: #646464;
}

.modify-region {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
  margin-top: -0.5rem;
}

.item-container {
  min-height: 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #646464;
}

.item-container .item {
  width: 65%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
}

.item-container .category-wrapper {
  flex-direction: column;
  justify-content: flex-start;
}

.item-label {
  flex: 1;
  padding: 0.5rem 0.75rem;
  border: 1px solid #646464;
  border-radius: 5px;
  text-align: center;
}

.item input {
  flex: 1;
  width: inherit;
  padding: 0;
  border: none;
  font-size: 1rem;
  color: #646464;
}

.item input:focus {
  outline: none;
  color: black;
}

.item .ratio-input {
  display: none;
}

.item img {
  height: 1rem;
}

.category-input {
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.category-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.category-selection {
  display: flex;
  gap: 0.5rem;
}

.category-selection input {
  flex: none;
}

.btn-container {
  display: flex;
  gap: 1rem;
}

.btn-container button {
  padding: 0.75rem 1rem;
  border-radius: 5px;
  font-weight: 700;
}

.delete-btn {
  flex: 1;
  border: 1px solid #646464;
  color: #646464;
}

.modify-btn {
  flex: 3;
  border: none;
  background-color: #3396f4;
  color: white;
}

.modify-btn:disabled {
  background-color: #a5a5a5;
}

.is-selected {
  border-color: #3396f4;
  color: #3396f4;
}
</style>
