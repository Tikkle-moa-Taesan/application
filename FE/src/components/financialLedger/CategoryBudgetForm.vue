<script setup>
import { computed, onMounted, ref, watch } from 'vue'

import Loading from '../commons/Loading.vue'
import CATEGORIES from '@/constants/category'

const model = defineModel()

const props = defineProps({
  totalBudget: Number,
})

const emits = defineEmits(['submitForm'])

const isLoading = ref(true)

const previousBudget = ref({})

const budgetString = ref({})

const sumOfInput = computed(() => {
  return Object.values(model.value || {}).reduce((sum, budget) => sum + budget, 0)
})

const isSame = ref(true)
const isBigger = computed(() => {
  return sumOfInput.value > props.totalBudget
})

const handleInput = (e, key) => {
  model.value[key] = Number(e.target.value.replace(/,/g, ''))

  budgetString.value[key] = Number(model.value[key]).toLocaleString()
}

const handleBudgetFormSubmit = () => {
  if (isSame.value || isBigger.value) return

  emits('submitForm')
}

watch(
  model,
  (newValue, oldValue) => {
    if (isLoading.value && newValue != oldValue) {
      previousBudget.value = newValue

      Object.keys(newValue).forEach((key) => {
        budgetString.value[key] = newValue[key].toLocaleString()
      })

      isLoading.value = false

      return
    }

    isSame.value = JSON.stringify(newValue) === previousBudget.value
  },
  { deep: true },
)

onMounted(() => {
  Object.keys(model.value).forEach((key) => {
    budgetString.value[key] = Number(model.value[key]).toLocaleString()
  })
})
</script>

<template>
  <div v-if="!isLoading" class="form-container">
    <h2>카테고리 별 예산을 설정해주세요.</h2>

    <form class="category-budget-form" @submit.prevent="handleBudgetFormSubmit">
      <div v-for="{ eng, kor, src } in CATEGORIES" class="category">
        <div class="label-container">
          <div class="img-container">
            <img :src="src" alt="카테고리" />
          </div>
          <label :for="eng">{{ kor }}</label>
        </div>
        <div class="input-container">
          <input
            :id="eng"
            :value="budgetString[`${eng}Budget`]"
            placeholder="0"
            type="text"
            @input="handleInput($event, `${eng}Budget`)"
          />원
        </div>
      </div>

      <button
        class="submit-btn"
        :class="{ 'is-same': isSame, 'is-bigger': isBigger }"
        type="submit"
      >
        <div v-if="!isBigger">
          <span>예산 확정</span> (남은 예산: {{ (totalBudget - sumOfInput).toLocaleString() }})
        </div>
        <div v-else>
          <span>예산 초과</span> (초과 금액: {{ (sumOfInput - totalBudget).toLocaleString() }})
        </div>
      </button>
    </form>
  </div>

  <div v-else class="loading-container">
    <Loading msg="정보를 불러오고 있어요" />
  </div>
</template>

<style scoped>
.form-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4rem;
  padding: 2rem 1.5rem;
  background-color: white;
  border-radius: 10px;
}

h2 {
  font-weight: 600;
}

.category-budget-form {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.category {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.label-container {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.img-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 2rem;
  height: 2rem;
  border-radius: 999px;
  background-color: #d9d9d9;
  overflow: hidden;
}

.img-container img {
  width: 70%;
}

.category .input-container {
  display: flex;
  gap: 0.5rem;
  color: #646464;
  border-bottom: 1px solid #646464;
}

.category input {
  border: none;
  text-align: end;
  color: #646464;
  width: 6rem;
}

.category input:focus {
  outline: none;
  color: black;
  font-weight: 600;
}

.category input:focus + span {
  color: black;
}

.category input:focus ~ .input-container {
  border-color: black;
}

.category .input-container:focus-within {
  color: black;
  border-color: black;
}

.submit-btn {
  margin-top: 2rem;
  padding: 0.75rem;
  border: none;
  border-radius: 5px;
  background-color: #3396f4;
  color: white;
}

.submit-btn span {
  font-weight: 700;
}

.is-same {
  background-color: #a5a5a5;
}

.is-bigger {
  background-color: #ff4a4a;
}

.loading-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem 1.5rem;
  background-color: white;
  border-radius: 10px;
}
</style>
