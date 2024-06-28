<script setup lang="ts">
import type { FormInstance, FormRules } from "element-plus";
import {ElMessage} from "element-plus";
import { ref, reactive, defineProps, onMounted } from "vue";
import { post,get } from "@/libs/request";
import treeCase from "@/components/treeCase.vue"
import { arrayToString } from "@/libs/util"
const props = defineProps(["close", "params"]);
const dataList = ref({});
const ruleForm = ref({
  roleId: props.params.roleId,
  roleName: undefined,
  roleDesc: undefined,
  rules: [],
});
const rules = reactive<FormRules>({
  roleName: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
  rules: [{ required: true, message: "请选择角色权限", trigger: "blur" }],
});
//表单提交
const ruleFormRef = ref<FormInstance>();
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      post("role/edit", {
        ...ruleForm.value,
      }).then((res) => {
        if (res.errCode == 0) {
          ElMessage.success(res.errMsg)
          props.close();
        }
      });
    }
  });
};
//获取权限列表
const getList = async () => {
  const res = await get("rule/getTree");
  if (res.errCode == 0) {
    dataList.value = res.data;
  }
};
/**
 * 获取详情
 */
const getDetail = async () => {
  const res = await get(`role/detail/${props.params.roleId}`);
  if (res.errCode == 0) {
    ruleForm.value = res.data;
  }
};
onMounted(() => {
  getList();
  getDetail();
});
</script>
<template>
  <div class="page">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item @click="props.close()">角色列表</el-breadcrumb-item>
      <el-breadcrumb-item>编辑角色</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="main">
      <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width="80"
      >
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="ruleForm.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色描述" prop="name">
          <el-input v-model="ruleForm.roleDesc" placeholder="请输入角色描述" />
        </el-form-item>
        <el-form-item label="角色权限" prop="rules">
          <tree-case v-model="ruleForm.rules" :data="dataList" :props="{
              label: 'name',
              children: 'children',
            }" node-key="ruleId" :show-check-box="true"/>
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