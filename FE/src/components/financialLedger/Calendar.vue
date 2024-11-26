<script setup>
import { ref, watch } from 'vue'

import formatNumber from '@/utils/formatNumber'
import { formatDate } from '@/utils/formatDate'

const props = defineProps({
  displayedDate: Array,
  financialData: Object,
})

const emits = defineEmits(['clickDate'])

const daysOfWeek = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']

const selectedYear = ref(new Date().getFullYear())
const selectedMonth = ref(new Date().getMonth())
const dates = ref([])

const clickedDate = ref(null)

const updateCalendar = () => {
  const year = selectedYear.value
  const month = selectedMonth.value

  const firstDay = new Date(year, month, 1).getDay() // 당월의 첫 번째 요일
  const lastDate = new Date(year, month + 1, 0).getDate() // 당월의 마지막 날짜

  // 달력에 빈칸 추가
  dates.value = Array.from({ length: firstDay }, () => null).concat(
    Array.from({ length: lastDate }, (v, k) => k + 1),
  )
}

const handleDateClick = (date) => {
  clickedDate.value = date

  if (date == null) {
    emits('clickDate')
    return
  }

  const formattedDate = formatDate(
    new Date(`${selectedYear.value}-${selectedMonth.value + 1}-${date}`),
  )

  emits('clickDate', formattedDate)
}

watch(
  () => props.displayedDate,
  () => {
    selectedYear.value = props.displayedDate[0]
    selectedMonth.value = props.displayedDate[1] - 1

    updateCalendar()
  },
  { immediate: true, deep: true },
)

watch([selectedYear, selectedMonth], updateCalendar)
</script>

<template>
  <div class="calendar-container">
    <div class="days">
      <div class="day" v-for="day in daysOfWeek" :key="day">{{ day }}</div>
    </div>
    <div class="dates">
      <div
        @click="handleDateClick(date)"
        class="date"
        :class="{ 'is-selected': date != null && date == clickedDate }"
        v-for="date in dates"
        :key="date"
      >
        <span class="date-value">{{ date }}</span>
        <div class="income-and-expense">
          <span v-if="financialData[date] && financialData[date].expense != 0" class="expense"
            >-{{ formatNumber(financialData[date].expense) }}</span
          >
          <span v-if="financialData[date] && financialData[date].income != 0" class="income"
            >+{{ formatNumber(financialData[date].income) }}</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.calendar-container {
  padding-top: 0.5rem;
  border-radius: 10px;
  background-color: white;
}

.days {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  margin-bottom: 0.5rem;
}

.day {
  font-size: 0.875rem;
  text-align: center;
  color: #646464;
}

.dates {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
}

.date {
  display: flex;
  flex-direction: column;
  height: 4rem;
  transition:
    background-color 0.5s ease,
    border 0.5s ease;
}

.date .date-value {
  padding: 0.25rem 0.5rem;
  font-size: 10px;
  text-align: end;
}

.income-and-expense {
  display: flex;
  flex-direction: column;
  align-items: end;
  gap: 0.5rem;
  padding: 0.5rem 0.25rem;
}

.expense {
  color: #ff4a4a;
  font-size: 0.5rem;
}

.income {
  color: #3396f4;
  font-size: 0.5rem;
}

.date .null {
  visibility: hidden;
}

.is-selected {
  background-color: #f2f2f2;
  border: 1px solid #646464;
  border-radius: 5px;
  transition:
    background-color 0.5s ease,
    border 0.5s ease;
}
</style>
