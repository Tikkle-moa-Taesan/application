<script setup>
import { computed, inject, onMounted, onUnmounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'

import AccountInfo from '@/components/asset/AccountInfo.vue'
import AccountTransaction from '@/components/asset/AccountTransaction.vue'
import FilterModal from '@/components/financialLedger/FilterModal.vue'

import { postFreeAccount, postSavingAccount } from '@/utils/api'

const isLoading = ref(true)

const route = useRoute()
const accountType = ref(route.params.type)
const accountId = ref(route.params.id)

const isModalShown = inject('isModalShown')

const account = ref({ accountDetail: {}, transactions: [] })

const filterCondition = ref({
  period: 0,
  transactionType: null,
})

const periodText = computed(() => {
  switch (filterCondition.value.period) {
    case 0:
      return '이번 달'
    case 3:
      return '3개월'
    case 6:
      return '6개월'
  }
})

const transactionTypeText = computed(() => {
  switch (filterCondition.value.transactionType) {
    case null:
      return '입출금 전체'
    case 'expense':
      return '출금만'
    case 'income':
      return '입금만'
  }
})

const page = ref(0)

const isAvailableScroll = ref(false)
const hasMoreData = ref(true)

const handleInfiniteScroll = async (fetchFunction) => {
  if (isAvailableScroll.value || !hasMoreData.value) return

  isAvailableScroll.value = true

  try {
    const res = await fetchFunction(accountId.value, page.value, filterCondition.value)

    if (page.value === 0) account.value.accountDetail = res.accountDetail

    if (res.transactions.length === 0) {
      hasMoreData.value = false
      return
    }

    account.value.transactions.push(...res.transactions)

    page.value++
  } catch (error) {
    console.error(error)
  } finally {
    isAvailableScroll.value = false
  }
}

const fetchAccount = async () => {
  const fetchFunction = accountType.value === 'free' ? postFreeAccount : postSavingAccount

  await handleInfiniteScroll(fetchFunction)
}

const onScroll = () => {
  const scrollPosition = scrollY + innerHeight
  const bottomPosition = document.documentElement.scrollHeight

  if (scrollPosition >= bottomPosition - 100 && !isAvailableScroll.value) fetchAccount()
}

const handleSettingClick = () => {
  isModalShown.value = true
}

const handleSettingBtnClick = (condition) => {
  filterCondition.value = condition

  if (condition.transactionType === 'null') filterCondition.value.transactionType = null

  isModalShown.value = false
}

const handleCloseModal = () => {
  isModalShown.value = false
}

onMounted(async () => {
  await fetchAccount()

  isLoading.value = false

  addEventListener('scroll', onScroll)
})

watch(filterCondition, async () => {
  page.value = 0
  account.value.transactions = []
  hasMoreData.value = true

  await fetchAccount()
})

onUnmounted(() => {
  removeEventListener('scroll', onScroll)
})
</script>

<template>
  <div class="flex-one">
    <div v-if="!isLoading && account" class="page-container">
      <AccountInfo :account-info="account.accountDetail" :account-type="accountType" />

      <div class="transactions-container">
        <div class="filter-container">
          <div class="filter-text">{{ transactionTypeText }} | {{ periodText }}</div>
          <img
            alt="설정"
            class="setting-icon"
            src="@/assets/icons/setting.png"
            @click="handleSettingClick"
          />
        </div>
        <AccountTransaction :account-transaction="account.transactions" />
      </div>
      <div v-if="isAvailableScroll" class="is-loading">loading...</div>
    </div>

    <Transition>
      <div v-if="isModalShown" @click.self="handleCloseModal" class="modal-wrapper">
        <FilterModal ref="modalRef" @click="handleSettingBtnClick" @closeModal="handleCloseModal" />
      </div>
    </Transition>
  </div>
</template>

<style scoped>
.flex-one {
  display: flex;
}

.is-loading {
  margin: 0.5rem 0;
  color: #646464;
  text-align: center;
}

.transactions-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.filter-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.filter-text {
  font-size: 14px;
  color: #646464;
}

.setting-icon {
  width: 1.25rem;
}

.v-enter-active,
.v-leave-active {
  transition: opacity 0.5s ease;
}

.v-enter-from {
  opacity: 0;
}

.v-leave-to {
  opacity: 0;
}
</style>
