<!-- 
  弹出层组件
   
    // App.vue挂载了组件并且，使用容器存放了这个组件

    import dialogCase from "./components/dialogCase.vue";
    import { setDialogCase } from "@/libs/dialog";
    const dialog = ref(null);
    <dialog-case ref="dialog" />
    //挂在全局弹出层组件
    onMounted(() => {
      dialog.value && setDialogCase(dialog.value);
    });



    使用组件
    const dialog = useDialogCase();
    如:
    onMounted(() => {
      dialog.show({
          path: "role/add",
          params: { abc: 123 },
          callback: () => {
            console.log("in");
          },
      });
    })
    
    被打开的组件里面的props 
    const props = defineProps(["close","params"])

    interface dialogOptionsType {
        //打开文件路径请按照项目实际文件路径修改
        path: string;
        //宽度
        width?: string;
        //参数
        params?: object;
        //是否显示
        isShow?: any;
        //是否全屏
        fullscreen?: boolean;
        //是否显示关闭按钮
        showClose?: boolean;
        //组件
        asyncComp?: any;
        //Dialog 打开的回调
        open?: () => void;
        //Dialog 打开动画结束时的回调
        opened?: () => void;
        //Dialog 关闭的回调【这里必定会触发callback,除非自己重写】
        close?: () => void;
        //Dialog 关闭动画结束时的回调
        closed?: () => void;
        //关闭后回调方法
        callback?: (params: object) => void;
      }
-->
<script setup lang="ts">
import { defineAsyncComponent, ref, computed } from "vue";
import type { dialogOptionsType } from "@/libs/dialog";
//存储弹出层
const dialogList = ref<dialogOptionsType[]>([]);
/**
 * 显示弹出层
 */
const show = (options: dialogOptionsType): number => {
  //初始化默认值
  options.width = options.width ?? "60%";
  options.fullscreen = options.fullscreen ?? false;
  options.showClose = options.showClose ?? true;
  options.isShow = ref(options.isShow ?? true);
  //动态加载异步组件,文件路径请按照项目实际路径修改
  options.asyncComp = computed(() => {
    let path = "../views/" + options.path + ".vue";
    let modules = import.meta.glob("../views/**/*.vue");
    try {
      return defineAsyncComponent(modules[path]);
    } catch (error) {
      console.log(
        `友好提示:打开失败，请确认${path}文件路径没问题[注意：严格区分大小写!!!]`
      );
      console.log(error);
      throw `打开失败，请确认${path}文件路径没问题[注意：严格区分大小写!!!]`;
    }
  });
  //关闭方法,编辑右上角关闭和组件中调用close 都会执行关闭
  // 这里需要将用户定义的close方法先赋值到变量上，否则同名的情况下会出现死循环，导致内存溢出
  let diyClose = options.close;
  options.close = (params = undefined) => {
    options.isShow.value = false;
    //判断是否有重新传入close方法
    if (diyClose) {
      diyClose(params);
    } else {
      //没有重写传入close方法的情况下，而且回调函数存在，则调用一次回调函数
      options.callback && options.callback(params);
    }
  };
  dialogList.value.push(options);
  //返回弹出层列表下标
  return dialogList.value.length - 1;
};
/**
 * 切换指定弹出层,显示/隐藏指定弹出层
 * Tips：关闭所有弹出层后，会导致无法正确关闭指定弹出层
 * @param index
 * @param showStatus
 */
const toggleShow = (index: number, showStatus: boolean): boolean => {
  if (dialogList.value[index]) {
    dialogList.value[index].isShow.value = showStatus;
    return true;
  } else {
    return true;
  }
};
/**
 * 关闭所有弹出层
 */
const closeAll = (): void => {
  //循环弹出层列表
  dialogList.value.forEach((item: dialogOptionsType) => {
    item.close();
  });
  //清空弹出层
  dialogList.value = [];
};
defineExpose({
  show,
  toggleShow,
  closeAll,
});
</script>
<template>
  <template v-for="(item, index) in dialogList" :key="index">
    <!-- 弹出层s -->
    <el-dialog
      v-model="item.isShow"
      :title="item.title"
      :width="item.width"
      :fullscreen="item.fullscreen"
      :show-close="item.showClose"
      @open="item.open"
      @opened="item.opened"
      @closed="item.closed"
      append-to-body
    >
      <!-- 异步组件 -->
      <component
        :is="item.asyncComp"
        :params="item.params"
        :close="item.close"
      />
    </el-dialog>
  </template>
</template>