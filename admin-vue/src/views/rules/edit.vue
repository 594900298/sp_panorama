<script setup lang="ts">
import type { FormInstance, FormRules } from "element-plus";
import {ElMessage} from "element-plus";
import { ref, reactive, defineProps, onMounted } from "vue";
import { post,get } from "@/libs/request";
const props = defineProps(["close", "params"]);
const ruleForm = ref({
  ruleId: props.params.ruleId,
  pid: undefined,
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
/**
 * 获取详情
 */
const getDetail = async () => {
  const res = await get(`rule/detail/${props.params.ruleId}`);
  if (res.errCode == 0) {
    ruleForm.value = res.data;
  }
};
//表单提交
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      post("rule/edit", {
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
onMounted(() => {
  getDetail();
});
const ruleFormRef = ref<FormInstance>();
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