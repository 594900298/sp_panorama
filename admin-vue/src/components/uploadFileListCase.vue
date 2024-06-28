<!-- 
  多文件上传
    引入
    import uploadFileListCase from '@/components/uploadFileListCase.vue'

    使用
    <el-form-item label="文件列表:">
        <upload-file-list-case v-model="ruleForm.fileList" action="base/upload" name="upload" :limit="1"></upload-file-list-case>
    </el-form-item>
    action:上传路径
    name:上传字段名称
    accept:上传文件类型
    limit:最大上传数量
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
  limit:10,
})
//实现双向数据绑定
const emit = defineEmits(["update:modelValue"]);
const value = computed({
  get() {
    return props.modelValue??[];
  },
  set(value) {
    emit("update:modelValue", value);
  },
});
//设置请求头部
const headers = {
  [accessTokenName]: storage.get(accessTokenName),
};
//图片上传路径
const uploadUrl = computed(() => {
  return webServiceUrl + props.action;
});
//禁用状态
const disabled = computed(() => {
  return props.limit && value.value.length >= props.limit ? true : false;
});
//上传回调
const handleAvatarSuccess = (res: any, file: any) => {
  if (res.errCode == 0) {
    //将路径设置到v-model,相对路径dev模式需要配置代理
    value.value.push({
      name: res.data.fileName,
      url: res.data.fileUrl,
    });
  } else {
    ElMessage.error(res.errMsg);
  }
};
//移除
const handleRemove = (file: any, fileList: any) => {
  value.value = fileList;
};
</script>
<template>
  <div class="upload-file-list">
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
      :file-list="value"
      :accept="accept"
      :headers="headers"
      :on-success="handleAvatarSuccess"
      :on-remove="handleRemove"
      :limit="limit"
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