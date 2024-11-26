<script setup>
import CATEGORIES from '@/constants/category'
import formatNumber from '@/utils/formatNumber'
import { computed, ref } from 'vue'
import TransactionModal from '../commons/TransactionModal.vue'

const props = defineProps({
  transaction: Object,
})

const isModalShown = ref(false)

const type = computed(() => {
  return props.transaction.transactionType === 'expense' ? '-' : '+'
})

const findCategoryIcon = computed(() => {
  const categoryArr = CATEGORIES.find((elem) => elem.kor === props.transaction.categoryName)

  return categoryArr.src
})

const handleOpenModal = () => {
  isModalShown.value = true
}

const handleCloseModal = () => {
  isModalShown.value = false
}
</script>

<template>
  <div>
    <div @click="handleOpenModal" class="detail-container">
      <div class="description-container">
        <div class="img-container">
          <img :src="findCategoryIcon" alt="" />
        </div>
        <div class="description">
          <span>{{ transaction.merchantName }}</span>
          <span class="text-gray"
            >{{ transaction.categoryName }} | {{ transaction.accountName }}</span
          >
        </div>
      </div>
      <div class="money">
        <span class="spending-money" :class="{ 'text-blue': type === '+' }"
          >{{ type }}{{ formatNumber(transaction.amount) }}</span
        >
      </div>

      <Transition>
        <div v-if="isModalShown" @click.self.stop="handleCloseModal" class="modal-wrapper">
          <TransactionModal @close-modal="handleCloseModal" :transaction="transaction" />
        </div>
      </Transition>
    </div>
  </div>
</template>

<style scoped>
.detail-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.description-container {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.img-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 2.75rem;
  height: 2.75rem;
  background-color: #d9d9d9;
  border-radius: 999px;
  overflow: hidden;
}

.img-container img {
  width: 60%;
}

.description {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.money {
  display: flex;
  flex-direction: column;
  align-items: end;
  gap: 0.5rem;
}

.text-gray {
  font-size: 0.875rem;
  color: #646464;
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
