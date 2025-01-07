import { ref } from 'vue'
import { defineStore } from 'pinia'
/**
 * 用户名
 */
export const useCommonStore = defineStore('common', () => {
  const loading = ref<boolean>(false)

  return {
    loading,
  }
})
