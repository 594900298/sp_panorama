<!-- 
  富文本编辑器组件
    引入
    import EditorCase from '@/components/editorCase.vue'

    使用
    <el-form-item label="详情:">
        <editor-case v-model="ruleForm.content"></editor-case>
    </el-form-item>
-->
<script setup lang="ts">
import { computed } from "vue";
import { QuillEditor } from "@vueup/vue-quill";
import { storage } from "@/libs/storage";
import { ElMessage } from "element-plus";
import BlotFormatter from "quill-blot-formatter";
import ImageUploader from "quill-image-uploader";
import appConfig from "@/libs/app.config";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import axios from "axios";
//图片参数名,服务器地址
let { name, action } = appConfig.editorConfig;
//访问令牌名称
let { accessTokenName } = appConfig.storageConfig;
//声明props
const props = defineProps(["modelValue"]);
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
const modules = [
  //用于调整和重新对齐图像和 iframe 视频的模板
  {
    name: "blotFormatter",
    module: BlotFormatter,
    options: {},
  },
  //允许将图像上传到服务器而不是进行base64编码【需要安装axios】
  {
    name: "imageUploader",
    module: ImageUploader,
    options: {
      upload: (file: any) => {
        return new Promise((resolve, reject) => {
          const formData = new FormData();
          formData.append(name, file);
          //不要忘记全局axios配置的影响
          axios
            .post(action, formData, {
              //设置请求头
              headers: {
                accessTokenName: storage.get(accessTokenName),
              },
            })
            .then((res: any) => {
              //这里resolve返回服务器返回的图片地址就可以了，这样出来的就是img标签src路径
              resolve(res.data.data.fileUrl);
            })
            .catch((err: any) => {
              reject("Upload failed");
              ElMessage.error(err);
            });
        });
      },
    },
  },
];
</script>
<template>
  <div class="editor-case">
    <QuillEditor
      toolbar="full"
      v-model:content="value"
      contentType="html"
      :modules="modules"
    />
  </div>
</template>
<style lang="scss">
.ql-editor{
  min-height: 150px;
}
</style>