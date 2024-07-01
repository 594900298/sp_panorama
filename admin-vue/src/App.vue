<script setup lang="ts">
import { ref, onMounted } from "vue";
import { RouterView } from "vue-router";
import { isLogin } from "./libs/util";
import { useRouter } from "vue-router";
import { setDialogCase } from "@/libs/dialog";
//挂载菜单栏
import Menu from "@/views/layout/menu.vue";
//挂载头部
import Header from "@/views/layout/header.vue";
//挂载全局弹出层组件
import dialogCase from "./components/dialogCase.vue";
//折叠导航
const collapse = ref(false);
//判断是否显示主容器
const showContainer = ref(false);
//全局弹窗组件
const dialog = ref(null);
//路由拦截
const router = useRouter();
router.beforeEach((to, from) => {
  showContainer.value = to.meta.showContainer as boolean;
  //验证页面是否需要登录
  if (to.meta.needAuth != false && !isLogin()) {
    router.push({ path: "/" });
  }
});
//挂在全局弹出层组件
onMounted(() => {
  dialog.value && setDialogCase(dialog.value);
});
</script>
<template>
  <dialog-case ref="dialog" />
  <template v-if="showContainer == false">
    <RouterView />
  </template>
  <template v-else>
    <div class="app">
      <el-container>
        <!-- 菜单s -->
        <div class="menu-container">
          <Menu :collapse="collapse" />
        </div>
        <!-- 菜单e -->
        <el-container>
          <!-- 头部s -->
          <el-header class="header-container">
            <Header v-model="collapse" />
          </el-header>
          <!-- 头部e -->
          <el-main>
            <RouterView />
          </el-main>
        </el-container>
      </el-container>
    </div>
  </template>
</template>
<!-- 清除默认样式 -->
<style>
@import "@/assets/css/normalize.css";
/* 引入tailwindcss */
@import "@/assets/css/tailwind.css";
@import "@/assets/iconfont/iconfont.css";
</style>
<!-- 引入主要样式 -->
<style lang="scss">
@import "@/assets/css/common.scss";
@import "@/assets/css/main.scss";
</style>