<script setup lang="ts">
import { ref, computed } from "vue";
import { useUserNameStore } from "@/stores/username";
import { storage } from "@/libs/storage";
import { useRouter } from "vue-router";
import { Lock, Back, DArrowLeft, DArrowRight } from "@element-plus/icons-vue";
import { useDialogCase } from "@/libs/dialog";
import { storeToRefs } from 'pinia'
//判断是否全屏
const fullScreen = ref(false);
const router = useRouter();
//双向数据绑定
const props = defineProps(['modelValue'])
const emit = defineEmits(['update:modelValue'])
const value = computed({
  get() {
    return props.modelValue
  },
  set(value) {
    emit('update:modelValue', value)
  }
})
//使用计算属性获取值
const {username} = storeToRefs(useUserNameStore());

//退出登录按钮
const logout = () => {
  storage.clear();
  router.push({ path: "/" });
};
//点击全屏
const changeFullScreen = () => {
  fullScreen.value = !fullScreen.value;
  if (fullScreen.value) {
    if (document.documentElement.RequestFullScreen) {
      document.documentElement.RequestFullScreen();
    }
    //兼容火狐
    if (document.documentElement.mozRequestFullScreen) {
      document.documentElement.mozRequestFullScreen();
    }
    //兼容谷歌等可以webkitRequestFullScreen也可以webkitRequestFullscreen
    if (document.documentElement.webkitRequestFullScreen) {
      document.documentElement.webkitRequestFullScreen();
    }
    //兼容IE,只能写msRequestFullscreen
    if (document.documentElement.msRequestFullscreen) {
      document.documentElement.msRequestFullscreen();
    }
  } else {
    if (document.exitFullScreen) {
      document.exitFullscreen();
    }
    //兼容火狐
    if (document.mozCancelFullScreen) {
      document.mozCancelFullScreen();
    }
    //兼容谷歌等
    if (document.webkitExitFullscreen) {
      document.webkitExitFullscreen();
    }
    //兼容IE
    if (document.msExitFullscreen) {
      document.msExitFullscreen();
    }
  }
};
//点击修改密码
const modifyPassword = () => {
  const dialog = useDialogCase();
  dialog.show({
    title: "修改密码",
    width:"30%",
    path: "other/modifyPassword",
  });
}
</script>
<template>
  <div class="collapse-button-wrapper" @click="value = !value">
    <DArrowRight v-if="value" />
    <DArrowLeft v-else />
  </div>
  <div class="full-screen-wrapper">
    <i
      class="iconfont icon-quanping_o"
      @click="changeFullScreen()"
      v-if="!fullScreen"
    ></i>
    <i
      class="iconfont icon-quxiaoquanping"
      @click="changeFullScreen()"
      v-else
    ></i>
  </div>
  <div class="header-action-wrapper">
    <el-dropdown placement="bottom" trigger="click">
      <div>
        <el-avatar src="/src/assets/images/avatar.png" />
        <p>{{ username }}</p>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item>
            <div @click="modifyPassword()">
              <el-icon color="#409EFC"> <Lock /> </el-icon>变更密码
            </div>
          </el-dropdown-item>
          <el-dropdown-item>
            <div @click="logout()">
              <el-icon color="#409EFC"> <Back /> </el-icon>退出登录
            </div>
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

