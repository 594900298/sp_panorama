<script setup lang="ts">
import { ElMessage, type FormInstance, type FormRules } from "element-plus";
import { ref, reactive, defineProps, onMounted } from "vue";
import { post,get } from "@/libs/request";
import uploadImageCase from '@/components/uploadImageCase.vue'
const props = defineProps(["close", "params"]);
const ruleForm = ref({
  bannerId:props.params.bannerId,
  bannerName: undefined,
  bannerImage:undefined,
  bannerLink:undefined,
  isShow:undefined,
});
const rules = reactive<FormRules>({
  bannerName: [{ required: true, message: "请填写轮播图名称", trigger: "blur" }],
  bannerImage: [{ required: true, message: "请先上传轮播图", trigger: "blur" }],
});
//表单提交
const ruleFormRef = ref<FormInstance>();
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      post("banner/edit", 
        ruleForm.value,
      ).then((res) => {
        if (res.errCode == 0) {
          ElMessage.success(res.errMsg);
          props.close();
        }
      });
    }
  });
};
//获取权限列表
const getDetail = async () => {
  const res = await get(`banner/detail/${props.params.bannerId}`);
  if (res.errCode == 0) {
    ruleForm.value = res.data;
  }else{
    ElMessage.error(res.errMsg);
  }
};
onMounted(() => {
  getDetail();
});
</script>
<template>
  <div class="page">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item @click="props.close()">轮播图列表</el-breadcrumb-item>
      <el-breadcrumb-item>修改轮播图</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="main">
      <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width="120"
      >
        <el-form-item label="轮播图片" prop="bannerImage">
          <upload-image-case v-model="ruleForm.bannerImage" action="common/uploadPic" name="img"></upload-image-case>
        </el-form-item>
        <el-form-item label="轮播图名称" prop="bannerName">
          <el-input v-model="ruleForm.bannerName" placeholder="请输入轮播图名称" />
        </el-form-item>
        <el-form-item label="跳转连接" prop="bannerLink">
          <el-input v-model="ruleForm.bannerLink" placeholder="请输入跳转连接" />
        </el-form-item>
        <el-form-item label="显示状态" prop="isShow">
          <el-switch v-model="ruleForm.isShow"></el-switch>
        </el-form-item>
        <el-form-item class="active">
          <el-button type="primary" @click="submitForm(ruleFormRef)">
            提交
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>