<!-- 
  文件上传组件
    引入
    import uploadFileCase from '@/components/uploadFileCase.vue'

    使用
    <el-form-item label="文件上传:">
        <upload-file-case v-model="ruleForm.file" action="base/upload" name="upload"></upload-file-case>
    </el-form-item>

    aciton:上传地址
    name:上传字段名称
    accept：文件类型
 -->
<script setup lang="ts">
import { computed } from "vue";
import appConfig from "@/libs/app.config";
import { ElMessage } from "element-plus";
import { storage } from "@/libs/storage";
//访问令牌名称
let { accessTokenName } = appConfig.storageConfig;
//服务器地址
let { webServiceUrl } = appConfig.requestConfig;
//声明props
const props = withDefaults(defineProps<{
  // 双向绑定
  modelValue?:Array<any>,
  // 上传路径
  action?:string
  // 字段名
  name?:string,
  //文件类型
  accept?:string
  // 文件列表的类型 text/picture/picture-card
  listType?:string,
   // 最大上传数量
  limit?:number
}>(), {
  modelValue: ()=>[],
  action:"common/uploadPic",
  name:"img",
  accept:".png, .jpg, .jpeg",
})
//实现双向数据绑定
const emit = defineEmits(["update:modelValue"]);
const value = computed({
  get() {
    return props.modelValue;
  },
  set(value) {
    emit("update:modelValue", value);
  },
});
//禁用状态
const disabled = computed(() => {
  return value.value ? true : false;
});
//设置请求头部
const headers = {
  [accessTokenName]: storage.get(accessTokenName),
};
//图片上传路径
const uploadUrl = computed(() => {
  return webServiceUrl + props.action;
});
//文件列表
const fileList = computed(() => {
  return value.value
    ? [
        {
          name: value.value.substring(
            value.value.lastIndexOf("/") + 1,
            value.value.length
          ),
          url: value.value,
        },
      ]
    : [];
});
//上传回调
const handleSuccess = (res: any, file: any) => {
  if (res.errCode == 0) {
    //将路径设置到v-model,相对路径dev模式需要配置代理
    value.value = res.data.fileUrl;
  } else {
    ElMessage.error(res.errMsg);
  }
};
//移除图片
const handleRemove = () => {
  value.value = "";
};
</script>
<template>
  <div class="upload-file">
    <el-button
      size="small"
      type="primary"
      :disabled="disabled"
      v-if="disabled"
      class="disabled-button"
      >点击上传</el-button
    >
    <el-upload
      class="upload-demo"
      :action="uploadUrl"
      :name="name"
      :file-list="fileList"
      :accept="accept"
      :headers="headers"
      :on-success="handleSuccess"
      :on-remove="handleRemove"
    >
      <el-button size="small" type="primary" v-if="!disabled"
        >点击上传</el-button
      >
    </el-upload>
  </div>
</template>
<style>
.el-upload-list__item-file-name{
  width: 90px;
}
</style>
