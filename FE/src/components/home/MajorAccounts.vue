<script setup>
import { computed, ref } from 'vue'
import router from '@/router'

import MajorAccountDetail from './MajorAccountDetail.vue'

import formatNumber from '@/utils/formatNumber'
import { postMock } from '@/utils/api'

const props = defineProps({
  freeAccountList: Array,
})

const emits = defineEmits(['addBtnClick'])

const majorAccountList = computed(() => {
  return props.freeAccountList ? props.freeAccountList.slice(0, 3) : []
})

const restAccountList = computed(() => {
  return props.freeAccountList ? props.freeAccountList.slice(3) : []
})

const restTotalBalance = computed(() =>
  restAccountList.value.reduce((sum, account) => sum + account.balance, 0),
)

const isAddBtnClicked = ref(false)

const handleEtcClick = () => {
  router.push({ name: 'asset' })
}

const handleAddBtnClick = async () => {
  isAddBtnClicked.value = true

  await postMock()

  emits('addBtnClick')
}
</script>

<template>
  <div class="major-accounts-container">
    <h2>자유 입출금 계좌</h2>

    <span v-if="freeAccountList.length > 0">전날 대비</span>

    <div v-if="freeAccountList.length > 0" class="account-list">
      <MajorAccountDetail
        v-for="account in majorAccountList"
        :key="account.accountId"
        :account="account"
      />

      <div v-if="restAccountList.length > 0" @click="handleEtcClick" class="rest-info-container">
        <img class="etc-img" src="@/assets/images/etc.webp" alt="기타" />
        <div class="rest-info">
          <span>{{ formatNumber(restTotalBalance) }} 원</span>
          <span class="rest-count">그 외 {{ restAccountList.length }}개</span>
        </div>
      </div>
    </div>

    <div v-if="freeAccountList.length === 0" class="empty-container">
      <button
        v-show="!isAddBtnClicked"
        class="mock-btn"
        @click.stop="handleAddBtnClick"
        type="button"
      >
        데이터 추가하기
      </button>
    </div>
  </div>
</template>

<style scoped>
.major-accounts-container {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  padding: 1rem;
  background-color: white;
  border-radius: 10px;
}

.major-accounts-container > h2 {
  font-weight: 600;
}

.major-accounts-container > span {
  text-align: end;
  font-size: 0.875rem;
  color: #646464;
}

.account-list {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  font-size: 0.875rem;
}

.rest-info-container {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.rest-info {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.rest-info .rest-count {
  color: #646464;
}

.etc-img {
  width: 2.75rem;
  height: 2.75rem;
}

.empty-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 16rem;
  padding: 2rem;
  background-image: url('/src/assets/images/no-account.webp');
  background-size: contain;
  background-position: center;
  background-repeat: no-repeat;
}

.mock-btn {
  padding: 0.75rem 1.25rem;
  border: none;
  border-radius: 5px;
  background-color: #3396f4;
  color: white;
  font-weight: 700;
}
</style>
