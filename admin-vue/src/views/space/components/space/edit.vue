<script setup lang="ts">
import { ElMessage, type FormInstance, type FormRules } from "element-plus";
import { ref, reactive, defineProps, onMounted } from "vue";
import { post, get } from "@/libs/request";
import uploadImageCase from '@/components/uploadImageCase.vue'
import uploadFileCase from '@/components/uploadFileCase.vue'
const props = defineProps(["close", "params"]);
const ruleForm = ref({
  spaceId: undefined,
  spaceName: undefined,
  spaceThumb: undefined,
  backgroundMusic: undefined,
  isShow: undefined,
  sort: undefined,
});
const rules = reactive<FormRules>({
  spaceName: [{ required: true, message: "请输入空间名称", trigger: "blur" }],
  isShow: [{ required: true, message: "请选择是否显示", trigger: "blur" }],
  sort: [{ required: true, message: "请输入排序", trigger: "blur" }],
});
const ruleFormRef = ref<FormInstance>();
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      post("space/edit", {
        ...ruleForm.value,
      }).then((res) => {
        if (res.errCode == 0) {
          ElMessage.success(res.errMsg);
          props.close();
        }
      });
    }
  });
};
//详情
const getDetail = async () => {
  const res = await get(`space/detail/${props.params.spaceId}`);
  if (res.errCode == 0) {
    ruleForm.value = res.data;
  } else {
    ElMessage.error(res.errMsg);
  }
};
onMounted(async () => {
  await getDetail();
})
</script>
<template>
  <div class="page">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item @click="props.close()">空间列表</el-breadcrumb-item>
      <el-breadcrumb-item>编辑空间</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="main">
      <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="100">
        <el-form-item label="商品名称" prop="spaceName">
          <el-input v-model="ruleForm.spaceName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="封面图" prop="spaceThumb">
          <upload-image-case v-model="ruleForm.spaceThumb" action="common/uploadPic" name="img"></upload-image-case>
        </el-form-item>
        <el-form-item label="背景音乐" prop="backgroundMusic">
          <upload-file-case v-model="ruleForm.backgroundMusic" action="common/uploadMusic"
            name="file" accept=".ogg,.mp3,.wav,.flac"></upload-file-case>
        </el-form-item>
        <el-form-item label="状态" prop="isShow">
          <el-switch v-model="ruleForm.isShow" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="ruleForm.sort" placeholder="请输入排序" />
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