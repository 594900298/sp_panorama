<script setup lang="ts">
import type { FormInstance, FormRules } from "element-plus";
import {ElMessage} from "element-plus";
import { ref, reactive, defineProps ,onMounted } from "vue";
import { get ,post } from "@/libs/request";
const props = defineProps(["close", "params"]);
const ruleForm = ref({
  areaId: props.params.areaId,
  pid: undefined,
  areaName: undefined,
  areaKey: undefined,
  areaType: undefined,
});
const rules = reactive<FormRules>({
  areaName: [{ required: true, message: "请输入区域名称", trigger: "blur" }],
  areaKey: [{ required: true, message: "请输入区域首字母", trigger: "blur" }],
});
//表单提交
const ruleFormRef = ref<FormInstance>();
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      post("area/edit", 
        ruleForm.value,
      ).then((res) => {
        if (res.errCode == 0) {
          ElMessage.success(res.errMsg)
          props.close();
        }
      });
    }
  });
};
/**
 * 获取详情
 */
 const getDetail = async () => {
  const res = await get(`area/detail/${props.params.areaId}`);
  if (res.errCode == 0) {
    ruleForm.value = res.data;
  } else {
    ElMessage.error(res.errMsg);
  }
};
onMounted(() => {
  getDetail();
});
</script>
<template>
  <div class="page">
    <div class="main">
      <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width="100"
      >
        <el-form-item label="区域名称" prop="areaName">
          <el-input v-model="ruleForm.areaName" placeholder="请输入区域名称" />
        </el-form-item>
        <el-form-item label="区域首字母" prop="areaKey">
          <el-input v-model="ruleForm.areaKey" placeholder="请输入区域首字母" />
        </el-form-item>
        <el-form-item label="级别标志" prop="areaType">
          <el-radio-group v-model="ruleForm.areaType" >
            <el-radio-button :label="0" disabled>省级</el-radio-button>
            <el-radio-button :label="1" disabled>市级</el-radio-button>
            <el-radio-button :label="2" disabled>区级</el-radio-button>
          </el-radio-group>
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