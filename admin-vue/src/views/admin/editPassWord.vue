<script setup lang="ts">
import type { FormInstance, FormRules } from "element-plus";
import {ElMessage} from "element-plus";
import { ref, reactive, defineProps } from "vue";
import { post } from "@/libs/request";
const props = defineProps(["close", "params"]);
const ruleForm = ref({
  userId: props.params.userId,
  password: undefined,
});
const rules = reactive<FormRules>({
  password: [{ required: true, message: "请输入新密码", trigger: "blur" }],
});
//表单提交
const ruleFormRef = ref<FormInstance>();
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      post("admin/editPassword", {
        ...ruleForm.value,
      }).then((res) => {
        if (res.errCode == 0) {
          ElMessage.success(res.errMsg);
          props.close();
        }else{
          ElMessage.error(res.errMsg);
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
        label-width="80"
      >
        <el-form-item label="新密码" prop="password">
          <el-input
            v-model="ruleForm.password"
            placeholder="请输入新密码"
            show-password
          />
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