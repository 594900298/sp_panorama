<script setup lang="ts">
import type { FormInstance, FormRules } from "element-plus";
import {ElMessage} from "element-plus";
import { ref, reactive, defineProps } from "vue";
import { post } from "@/libs/request";
const props = defineProps(["close", "params"]);
const ruleForm = reactive({
  pid: props.params.pid,
  areaName: undefined,
  areaKey: undefined,
  areaType: props.params.areaType,
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
      post("area/add", {
        ...ruleForm,
      }).then((res) => {
        if (res.errCode == 0) {
          ElMessage.success(res.errMsg)
          props.close();
        }
      });
    }
  });
};
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
        <el-form-item label="地区名称" prop="areaName">
          <el-input v-model="ruleForm.areaName" placeholder="请输入地区名称" />
        </el-form-item>
        <el-form-item label="地区首字母" prop="areaKey">
          <el-input v-model="ruleForm.areaKey" placeholder="请输入地区首字母" />
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