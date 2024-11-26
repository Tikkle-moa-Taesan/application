<script setup>
import { computed, ref } from 'vue'

import { Doughnut } from 'vue-chartjs'
import { ArcElement, Chart, Legend, Tooltip } from 'chart.js'

const props = defineProps({
  categoryExpenses: Object,
  centerText: String,
})

Chart.register(ArcElement, Tooltip, Legend)

const chartLabels = ref([
  '식비',
  '생활',
  '주거/통신',
  '금융',
  '교통',
  '육아',
  '문화/여가',
  '반려동물',
  '경조/선물',
])
const chartDatasets = computed(() => {
  if (!props.categoryExpenses) return []

  return Object.values(props.categoryExpenses)
})

const chartData = computed(() => {
  return {
    labels: chartLabels.value,
    datasets: [
      {
        data: chartDatasets.value,
        backgroundColor: [
          '#4BC0C0',
          '#36A2EB',
          '#FF6384',
          '#FF9F40',
          '#FFCD56',
          '#C9CBCF',
          '#9966FF',
          '#FF9F40',
          '#FF6384',
        ],
        hoverBackgroundColor: [
          '#4BC0C0',
          '#36A2EB',
          '#FF6384',
          '#FF9F40',
          '#FFCD56',
          '#C9CBCF',
          '#9966FF',
          '#FF9F40',
          '#FF6384',
        ],
      },
    ],
  }
})

const chartOptions = ref({
  maintainAspectRatio: false,
  radius: 100,
  plugins: {
    legend: {
      position: 'bottom',
      labels: {
        usePointStyle: true,
        pointStyle: 'circle',
      },
    },
    centerText: {
      text: props.centerText,
    },
  },
})

const centerTextPlugin = {
  id: 'centerText',
  beforeDraw: (chart) => {
    const { ctx, chartArea, config } = chart

    ctx.restore()

    ctx.font = `1rem Pretendard-Regular`
    ctx.fillStyle = '#646464'
    ctx.textAlign = 'center'
    ctx.textBaseline = 'middle'

    const text = config.options.plugins.centerText.text
    const textX = (chartArea.left + chartArea.right) / 2
    const textY = (chartArea.top + chartArea.bottom) / 2

    ctx.fillText(text, textX, textY)
    ctx.save()
  },
}

Chart.register(centerTextPlugin)
</script>

<template>
  <div class="doughnut-container">
    <Doughnut :data="chartData" :options="chartOptions" />
  </div>
</template>

<style scoped>
.doughnut-container {
  background-color: #f2f2f2;
  border-radius: 10px;
}
</style>
