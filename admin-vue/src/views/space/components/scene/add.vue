<script setup lang="ts">
import { ElMessage, type FormInstance, type FormRules } from "element-plus";
import { ref, reactive, defineProps, onMounted } from "vue";
import { post, get } from "@/libs/request";
import uploadImageCase from '@/components/uploadImageCase.vue'
const props = defineProps(["close", "params"]);
const ruleForm = reactive({
  spaceId: undefined,
  sceneName: undefined,
  panoramicImage: '',
});
const rules = reactive<FormRules>({
  spaceId: [{ required: true, message: "请选择所属空间", trigger: "blur" }],
  sceneName: [{ required: true, message: "请输入场景名称", trigger: "blur" }],
  panoramicImage: [{ required: true, message: "请先上传全景图片", trigger: "blur" }],
});
const dataList = ref();
//表单提交
const ruleFormRef = ref<FormInstance>({});
const getList = async () => {
  const res = await get("space/getList");
  if (res.errCode == 0) {
    dataList.value = res.data.map(item => {
      return{
        value:item.spaceId,
        label:item.spaceName,
      }
    });
  }
};
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      post("scene/add", {
        ...ruleForm,
      }).then((res) => {
        if (res.errCode == 0) {
          ElMessage.success(res.errMsg);
          props.close();
        }
      });
    }
  });
};
onMounted(() => {
  getList();
});
</script>
<template>
  <div class="page">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item @click="props.close()">场景列表</el-breadcrumb-item>
      <el-breadcrumb-item>新增场景</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="main">
      <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="100">
        <el-form-item label="所属空间" prop="spaceId">
          <el-select v-model="ruleForm.spaceId" placeholder="请选择所属空间" size="large" style="width: 240px" >
            <el-option v-for="item in dataList" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="场景名称" prop="sceneName">
          <el-input v-model="ruleForm.sceneName" placeholder="请输入场景名称" />
        </el-form-item>
        <el-form-item label="全景图" prop="panoramicImage">
          <upload-image-case v-model="ruleForm.panoramicImage" action="common/uploadPic" name="img"></upload-image-case>
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