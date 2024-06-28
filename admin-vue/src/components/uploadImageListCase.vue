<!-- 
  多图上传组件
    引入
    import uploadImageListCase from "@/components/uploadImageListCase.vue";

    使用
    <el-form-item label="图片列表:">
        <upload-image-list-case v-model="ruleForm.imageList" action="base/upload" name="upload" list-type="picture-card"></upload-image-list-case>
    </el-form-item>

    aciton:上传地址
    name:上传字段名称
    list-type:文件列表的类型
    accept：文件类型
    limit:数量限制
 -->
<script setup lang="ts">
import appConfig from "@/libs/app.config";
import { ref, computed, onMounted } from "vue";
import { storage } from "@/libs/storage";
import { ElMessage } from "element-plus";
import { Plus } from "@element-plus/icons-vue";
onMounted(()=>{
  console.log(props,'--props');
})
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
  listType:'picture-card',
  limit:10,
})
//图片上传路径
const uploadUrl = computed(() => {
  return webServiceUrl + props.action;
});
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
//弹出层状态
const dialogVisible = ref(false);
//弹出照片url
const dialogImageUrl = ref("");
//上传回调
const handleSuccess = (res: any, file: any) => {
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
//移除图片
const handleRemove = (file: any, fileList: any) => {
  value.value = fileList;
};
//放大图片
const handlePictureCardPreview = (file: any) => {
  dialogImageUrl.value = file.url;
  dialogVisible.value = true;
};
</script>
<template>
  <div class="upload-image-list">
    <el-upload
      :action="uploadUrl"
      :list-type="props.listType"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :on-success="handleSuccess"
      :headers="headers"
      :name="props.name"
      :limit="props.limit"
      :file-list="value"
      :accept="props.accept"
    >
      <el-icon class="avatar-uploader-icon"><Plus /></el-icon>
    </el-upload>
    <el-dialog v-model="dialogVisible" :append-to-body="true">
      <div class="dialog-wrapper">
        <img :src="dialogImageUrl" />
      </div>
    </el-dialog>
  </div>
</template>
<style>
.dialog-wrapper img {
  width: 100%;
}
</style>
