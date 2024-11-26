<script setup>
defineProps({
  title: String,
  options: Array,
  modelValue: [String, Number],
})

const emits = defineEmits(['update:modelValue'])

const handleRadioInput = (value) => {
  emits('update:modelValue', value)
}
</script>

<template>
  <div class="option-container">
    <div class="option">
      <span>{{ title }}</span>
      <div class="selections">
        <template v-for="option in options" :key="option.value">
          <input
            @input="handleRadioInput(option.value)"
            class="radio-input"
            type="radio"
            :id="option.value"
            :value="option.value"
            :checked="modelValue == option.value"
          />
          <label
            class="selection"
            :class="{ selected: modelValue == option.value }"
            :for="option.value"
            >{{ option.label }}
          </label>
        </template>
      </div>
    </div>
  </div>
</template>

<style scoped>
.option-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.option-container .option {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  color: #646464;
}

.selections {
  display: flex;
  justify-content: space-around;
  border: 1px solid #646464;
  border-radius: 5px;
}

.selections .radio-input {
  display: none;
}

.selections .selection {
  flex: 1;
  padding: 0.75rem 0;
  text-align: center;
}

.selections .selection:hover {
  cursor: pointer;
}

.selected {
  color: black;
  border: 1px solid black;
  border-radius: 5px;
  background-color: #d9d9d9;
}
</style>
