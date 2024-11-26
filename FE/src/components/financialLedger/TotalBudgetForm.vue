<script setup>
import { ref, watch } from 'vue'

import router from '@/router'
import { postBudgetCreate, putBudget } from '@/utils/api'

const props = defineProps({
  previousBudget: Number,
})

const totalBudgetString = ref(props.previousBudget.toLocaleString())
const totalBudgetNumber = ref()

const isSamePrevious = ref(true)

const handleInput = (e) => {
  totalBudgetNumber.value = e.target.value.replace(/,/g, '')

  totalBudgetString.value = Number(totalBudgetNumber.value).toLocaleString()
}

const fetchBudgetCreate = async () => {
  if (props.previousBudget === 0) await postBudgetCreate(totalBudgetNumber.value)
  else await putBudget(Number(totalBudgetNumber.value))

  router.replace({ name: 'category-budget-set' })
}

watch(
  () => props.previousBudget,
  () => {
    totalBudgetNumber.value = props.previousBudget
  },
  { immediate: true },
)

watch(totalBudgetNumber, (newValue) => {
  isSamePrevious.value = newValue == props.previousBudget
})
</script>

<template>
  <div class="form-container">
    <form class="budget-set-form" @submit.prevent="fetchBudgetCreate">
      <div class="input-container">
        <label for="budget">이번 달 예산을 설정해주세요</label>
        <div class="input">
          <input
            @input="handleInput"
            :value="totalBudgetString"
            type="text"
            id="budget"
            placeholder="0"
          />원
        </div>
      </div>

      <div class="btn-container">
        <button
          class="submit-btn"
          type="submit"
          :disabled="isSamePrevious || totalBudgetNumber == 0"
        >
          예산 설정
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
.form-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 4rem;
  background-color: white;
  border-radius: 10px;
}

.budget-set-form {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  gap: 4rem;
}

.input-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3rem;
}

.input-container > label {
  font-weight: 700;
}

.input-container .input {
  display: flex;
  gap: 0.5rem;
  align-items: center;
  width: fit-content;
  border-bottom: 1px solid black;
}

.input-container input {
  border: none;
  font-size: 1rem;
  font-weight: 700;
  text-align: end;
}

.input-container input:focus {
  outline: none;
}

.btn-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.btn-container > button {
  border-radius: 10px;
  padding: 0.75rem;
  font-weight: 700;
  text-align: center;
}

.submit-btn {
  border: none;
  background-color: #3396f4;
  color: white;
}

.submit-btn:disabled {
  background-color: #a5a5a5;
}
</style>
