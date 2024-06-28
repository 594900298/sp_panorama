<script setup lang="ts">
import { ref, reactive } from "vue";
import { post } from "@/libs/request";
import { useRouter } from "vue-router";
import { storage } from "@/libs/storage";
import { ElMessage } from "element-plus";
import type { FormInstance, FormRules } from "element-plus";
const props = defineProps(["close"]);
const router = useRouter();
//表单内容
const ruleForm = reactive({
  oldPassword: "",
  password: "",
  confirmPassword: "",
});
const ruleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  oldPassword: [
    { required: true, message: "请输入旧密码", trigger: "oldPassword" },
  ],
  password: [{ required: true, message: "请输入新密码", trigger: "password" }],
  confirmPassword: [
    {
      required: true,
      message: "请输入确认新密码",
      trigger: "confirmPassword",
    },
  ],
});
//点击提交
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      post("admin/modifyPassword", {
        ...ruleForm,
      }).then((res) => {
        if (res.errCode == 0) {
          ElMessage.success(res.errMsg);
          props.close();
          storage.clear();
          router.push({ path: "/" });
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
        label-width="120px"
        class="demo-ruleForm"
      >
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input
            type="password"
            v-model="ruleForm.oldPassword"
            placeholder="请输入旧密码"
          />
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input
            type="password"
            v-model="ruleForm.password"
            placeholder="请输入新密码"
          />
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input
            type="password"
            v-model="ruleForm.confirmPassword"
            placeholder="请输入确认新密码"
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
