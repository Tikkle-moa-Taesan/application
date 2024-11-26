<script setup>
import { computed, inject, ref } from 'vue'

const emits = defineEmits(['closeModal', 'selectDate'])

const profile = inject('profile')

const createdAt = computed(() => profile.value.createdAt)

const selectedIdx = ref(0)

const startYear = computed(() => new Date(createdAt.value).getFullYear())
const startMonth = computed(() => new Date(createdAt.value).getMonth() + 1)

const generatedMonthList = computed(() => {
  let monthList = []

  let year = new Date().getFullYear()
  let month = new Date().getMonth() + 1

  while (year > startYear.value || (year === startYear.value && month >= startMonth.value)) {
    monthList.push([year, month])

    month -= 1

    if (month < 1) {
      month = 12
      year -= 1
    }
  }

  return monthList
})

const handleCloseIconClick = () => {
  emits('closeModal')
}

const handleDateClick = (date, idx) => {
  selectedIdx.value = idx
}

const handleSettingBtnClick = () => {
  emits('selectDate', generatedMonthList.value[selectedIdx.value])
}
</script>

<template>
  <div class="modal-container">
    <div class="title">
      <span>날짜 선택</span>
      <img
        @click="handleCloseIconClick"
        class="close-icon"
        src="@/assets/icons/close.png"
        alt="닫기"
      />
    </div>

    <div class="date-list-wrapper">
      <div class="date-list">
        <div
          @click="handleDateClick(date, idx)"
          class="date-container"
          :class="{ 'is-selected': selectedIdx === idx }"
          v-for="(date, idx) in generatedMonthList"
          :key="date"
        >
          <span>{{ date[0] }}년</span>
          <span>{{ date[1] }}월</span>
        </div>
      </div>
    </div>

    <button @click="handleSettingBtnClick">설정</button>
  </div>
</template>

<style scoped>
.title {
  position: relative;
  font-weight: 600;
  text-align: center;
}

.close-icon {
  position: absolute;
  bottom: 0.2rem;
  right: 0;
  width: 1rem;
}

.close-icon:hover {
  cursor: pointer;
}

.date-list-wrapper {
  max-height: 12rem;
  display: flex;
  justify-content: center;
  padding: 1.5rem 1rem;
  border-radius: 10px;
  background-color: #f2f2f2;
}

.date-list {
  width: 100%;
  display: flex;
  flex-direction: column;
  overflow-y: scroll;
}

.date-container {
  display: flex;
  gap: 0.75rem;
  padding: 0.8rem 0.5rem;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.date-container:hover {
  cursor: pointer;
}

.is-selected {
  font-weight: 500;
  background-color: #daedff;
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
