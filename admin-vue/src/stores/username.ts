import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
/**
 * 用户名
 */
export const useUserNameStore = defineStore('username', () => {
  const username = ref("")
  //获取器
  const getUserName = computed(() => {
    return username.value
  })
  //修改器
  function setUserName(name: string) {
    username.value = name
  }

  return { 
    username, 
    getUserName, 
    setUserName 
  }
}, {
  persist: {
    enabled: true
  }
})
