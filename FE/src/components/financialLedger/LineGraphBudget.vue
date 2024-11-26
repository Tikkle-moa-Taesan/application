<script setup>
import { computed, onMounted, ref, watch } from 'vue'

import { Line } from 'vue-chartjs'
import {
  CategoryScale,
  Chart,
  Legend,
  LinearScale,
  LineElement,
  PointElement,
  Title,
  Tooltip,
} from 'chart.js'

const props = defineProps({
  budgetForSixMonths: Object,
  centerText: String,
})

const labels = ref([])

const budget = ref([])
const expense = ref([])

const maxRange = ref(0)
const minRange = ref(0)
const stepSize = ref(0)

const getLastSixMonthsLabels = () => {
  const currentMonth = new Date().getMonth() + 1

  for (let month = currentMonth - 5; month <= currentMonth; month++) {
    labels.value.push(`${month}월`)
  }
}

const getLastSixMonthsData = () => {
  const tempBudget = []
  const tempExpenses = []

  Object.values(props.budgetForSixMonths).forEach((month) => {
    const thisBudget = month.thisMonthBudget
    const thisExpense = month.thisMonthExpense

    tempBudget.unshift(thisBudget > 0 ? thisBudget : null)
    tempExpenses.unshift(thisExpense > 0 ? thisExpense : null)
  })

  budget.value = tempBudget
  expense.value = tempExpenses
}

const getChartRange = () => {
  const allValues = [...budget.value, ...expense.value].filter((value) => value !== null)

  const maxValue = Math.max(...allValues)
  const minValue = Math.min(...allValues)

  maxRange.value = Math.round((maxValue * 1.1) / 10) * 10
  minRange.value = Math.round((minValue * 0.9) / 10) * 10

  stepSize.value = Math.round((maxRange.value - minRange.value) / 4)
}

onMounted(() => {
  getLastSixMonthsLabels()
  getLastSixMonthsData()

  getChartRange()
})

Chart.register(Title, Tooltip, Legend, LineElement, PointElement, CategoryScale, LinearScale)

const chartData = computed(() => {
  return {
    labels: labels.value,
    datasets: [
      {
        label: '예산',
        data: budget.value,
        borderColor: '#74b7f8',
        backgroundColor: '#74b7f8',
        fill: false,
        tension: 0.1,
      },
      {
        label: '지출',
        data: expense.value,
        borderColor: 'gray',
        backgroundColor: 'gray',
        fill: false,
        tension: 0.1,
      },
    ],
  }
})

const chartOptions = computed(() => {
  return {
    plugins: {
      legend: {
        position: 'top',
        labels: {
          boxWidth: 20,
          boxHeight: 0.5,
        },
      },
      centerText: {
        text: props.centerText,
      },
    },
    scales: {
      x: {
        grid: {
          display: false,
        },
      },
      y: {
        beginAtZero: true,
        min: minRange.value,
        max: maxRange.value,
        ticks: {
          stepSize: stepSize.value,
          callback: (value) => `${Math.round(value / 100000) * 10}만원`,
        },
      },
    },
  }
})
</script>

<template>
  <div class="line-graph-container">
    <Line :data="chartData" :options="chartOptions" />
  </div>
</template>

<style scoped>
.line-graph-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
}
</style>
