<script setup>
import { useRoute } from 'vue-router'

const route = useRoute()

const isSelected = (path) => route.path.endsWith(path)
</script>

<template>
  <div class="floating-nav-container">
    <RouterLink class="link" :to="{ name: 'financial-ledger-list' }">
      <div class="text" :class="{ selected: isSelected('list') }">내역</div>
    </RouterLink>
    <RouterLink class="link" :to="{ name: 'financial-ledger-calendar' }">
      <div class="text" :class="{ selected: isSelected('calendar') }">달력</div>
    </RouterLink>
    <RouterLink class="link" :to="{ name: 'financial-ledger-budget' }">
      <div class="text" :class="{ selected: isSelected('budget') }">예산</div>
    </RouterLink>
  </div>
</template>

<style scoped>
.floating-nav-container {
  display: flex;
  justify-content: space-around;
  padding: 0.1rem;
  border-radius: 40px;
  background-color: #f2f2f2;
  box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.25);
}

.floating-nav-container .link {
  flex: 1;
  text-decoration: none;
  color: #a5a5a5;
  font-weight: 600;
}

.floating-nav-container .text {
  padding: 0.75rem;
  border-radius: 40px;
  text-align: center;
}

.selected {
  color: white;
  background-color: #74b7f8;
}
</style>
