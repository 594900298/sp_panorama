<!-- 
  图片上传组件
    引入
    import uploadImageCase from '@/components/uploadImageCase.vue'

    使用
    <el-form-item label="图片:">
        <upload-image-case v-model="ruleForm.image" action="base/upload" name="upload"></upload-image-case>
    </el-form-item>

    action:上传地址
    name:上传字段
    accept:文件类型
 -->
 <script setup lang="ts">
 import appConfig from "@/libs/app.config";
 import { ref, computed } from "vue";
 import { storage } from "@/libs/storage";
 import { ElMessage } from "element-plus";
 import { Plus, ZoomIn, Delete } from "@element-plus/icons-vue";
 //访问令牌名称
 let { accessTokenName } = appConfig.storageConfig;
 //服务器地址
 let { webServiceUrl } = appConfig.requestConfig;
 //声明props
 const props = withDefaults(defineProps<{
   // 双向绑定
   modelValue?:string,
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
   modelValue: "",
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
 //弹出层状态
 const dialogVisible = ref(false);
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
 //放大镜
 const handlePictureCardPreview = () => {
   dialogVisible.value = true;
 };
 </script>
 <template>
   <div class="upload-image">
     <el-upload
       class="cover-uploader"
       :action="uploadUrl"
       :show-file-list="false"
       :on-success="handleSuccess"
       :headers="headers"
       :name="name"
       :disabled="disabled"
       :accept="accept"
     >
       <!-- 操作控件 -->
       <div v-if="value" class="cover-box">
         <img :src="value" class="cover" />
         <span class="el-upload-list__item-actions">
           <span
             class="el-upload-list__item-preview"
             @click="handlePictureCardPreview()"
           >
             <el-icon>
               <ZoomIn />
             </el-icon>
           </span>
           <span
             class="el-upload-list__item-preview"
             @click.stop="handleRemove()"
           >
             <el-icon>
               <Delete />
             </el-icon>
           </span>
         </span>
       </div>
       <!-- 添加图标 -->
       <el-icon class="cover-uploader-icon" v-else><Plus /></el-icon>
     </el-upload>
     <!-- 放大 -->
     <el-dialog v-model="dialogVisible" :append-to-body="true">
       <div class="dialog-wrapper">
         <img :src="value" />
       </div>
     </el-dialog>
   </div>
 </template>
 
 <style>
 .el-upload-list__item-preview {
   padding: 5px;
 }
 .el-upload-list__item-actions span {
   cursor: pointer;
 }
 .upload-image .cover-uploader{
   width: 100%;
   height: 100%;
 }
 .upload-image .cover-box:hover .el-upload-list__item-actions {
   opacity: 1;
 }
 
 .upload-image .el-upload-list__item-actions {
   position: absolute;
   width: 100%;
   height: 100%;
   left: 0;
   top: 0;
   cursor: default;
   text-align: center;
   color: #fff;
   opacity: 0;
   font-size: 20px;
   background-color: rgba(0, 0, 0, 0.5);
   transition: opacity 0.3s;
   display: flex;
   align-items: center;
   justify-content: center;
 }
 .upload-image .cover-uploader .el-upload {
   width: 100%;
   height: 100%;
   border: 1px dashed #d9d9d9;
   border-radius: 6px;
   cursor: pointer;
   position: relative;
   overflow: hidden;
 }
 .upload-image .cover-uploader .el-upload:hover {
   border-color: #409eff;
 }
 .upload-image .cover-uploader-icon {
   width: 100%;
   height: 100%;
   font-size: 28px;
   color: #8c939d;
   width: 178px;
   height: 178px;
   line-height: 178px;
   text-align: center;
 }
 .upload-image .cover {
   width: 178px;
   height: 178px;
   display: block;
   object-fit: contain;
 }
 .dialog-wrapper img {
   width: 100%;
 }
 </style>