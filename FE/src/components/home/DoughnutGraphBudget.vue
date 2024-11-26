<script setup>
import { Doughnut } from 'vue-chartjs'
import { ArcElement, Chart, Legend, Tooltip } from 'chart.js'
import { computed, ref } from 'vue'

const props = defineProps({
  monthlyBudget: Object,
  centerText: String,
})

const restBudget = computed(() => {
  if (!props.monthlyBudget) return 0

  return props.monthlyBudget.thisMonthBudget - props.monthlyBudget.thisMonthExpense
})

Chart.register(ArcElement, Tooltip, Legend)

const chartLabels = ref(['남은 예산', '지출'])

const chartData = computed(() => {
  return {
    labels: chartLabels.value,
    datasets: [
      {
        data: [restBudget.value, props.monthlyBudget?.thisMonthExpense || 0],
        backgroundColor: ['#36A2EB', '#646464'],
        hoverBackgroundColor: ['#36A2EB', '#646464'],
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
