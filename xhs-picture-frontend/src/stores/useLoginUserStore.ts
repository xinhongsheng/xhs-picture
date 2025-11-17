// Vue 和 Pinia 的函数现在会自动导入，无需手动 import

import { getLoginUserUsingGet } from "@/api/userController";
import { defineStore } from 'pinia'
import { ref } from 'vue'
// 定义登录用户的 Pinia Store
export const useLoginUserStore = defineStore('loginUser', () => {
  const loginUser = ref<API.LoginUserVO>({
    userName: '未登录',
  })
  /** 获取登录用户信息 */
  async function fetchLoginUser() {
    const res = await getLoginUserUsingGet();
    if (res.data.code === 0 && res.data.data) {
      loginUser.value = res.data.data;
    }
  }

  function setLoginUser(newLoginUser: any) {
    loginUser.value = newLoginUser
  }

  return { loginUser, setLoginUser, fetchLoginUser }
})
