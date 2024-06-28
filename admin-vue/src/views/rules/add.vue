<script setup lang="ts">
import type { FormInstance, FormRules } from "element-plus";
import {ElMessage} from "element-plus";
import { ref, reactive, defineProps } from "vue";
import { post } from "@/libs/request";
const props = defineProps(["close", "params"]);
const ruleForm = reactive({
  pid: props.params.pid,
  name: undefined,
  ruleUrl: undefined,
  sort: 0,
  isMenu: 0,
});
const rules = reactive<FormRules>({
  name: [{ required: true, message: "请输入路由名称", trigger: "blur" }],
  ruleUrl: [{ required: true, message: "请输入规则地址", trigger: "blur" }],
  sort: [{ type: "number", message: "排序只能为数字", trigger: "blur" }],
});
//表单提交
const ruleFormRef = ref<FormInstance>();
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      post("rule/add", {
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
        label-width="80"
      >
        <el-form-item label="路由名称" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入路由名称" />
        </el-form-item>
        <el-form-item label="规则地址" prop="ruleUrl">
          <el-input v-model="ruleForm.ruleUrl" placeholder="请输入规则地址正则" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model.number="ruleForm.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="是否菜单" prop="isMenu">
          <el-switch
            v-model="ruleForm.isMenu"
            class="ml-2"
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