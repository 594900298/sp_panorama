<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import type { FormRules, FormInstance } from "element-plus";
import { ElMessage } from "element-plus";
import { User, Lock } from "@element-plus/icons-vue";
import { post,get } from "@/libs/request";
import { useRouter } from "vue-router";
import appConfig from "@/libs/app.config";
import { storage } from "@/libs/storage";
import { isLogin } from "@/libs/util";
import { useUserNameStore } from "@/stores/username";
const router = useRouter();
//访问令牌名称,刷新token名称
let { accessTokenName, refreshTokenName } = appConfig.storageConfig;
// 表单数据
const ruleForm = reactive({
  username: "",
  password: "",
  codeSign: "",
  code: "",
});
//验证码地址
const imgUrl = ref("");
//验证规则
const rules = reactive<FormRules>({
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
  code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
});
const ruleFormRef = ref<FormInstance>();
//验证表单
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      post("login/code", {
        ...ruleForm,
      }).then((res) => {
        if (res.errCode === 0) {
          ElMessage.success(res.errMsg)
          storage.set(accessTokenName, res.data.token, res.data.expire);
          storage.set(refreshTokenName, res.data.refreshToken);
          useUserNameStore().setUserName(res.data.username);
          router.push({ path: "/admin" });
        } else {
          ElMessage.error(res.errMsg);
          getVerify();
        }
      });
    }
  });
};
//请求验证码
const getVerify = async () => {
  const res = await get("captcha/getCaptcha", {
    codeSign: ruleForm.codeSign,
  });
  if (res.errCode == 0) {
    ruleForm.codeSign = res.data.codeSign;
    imgUrl.value =  res.data.base64CodeImg;
  }
};
onMounted(() => {
  //处于登陆状态
  if (isLogin()) {
    router.push({ path: "/admin" });
  }
  getVerify();
});
</script>

<template>
  <div class="login-page">
    <div class="main-continaer">
      <el-avatar :size="50" src="/src/assets/images/avatar.png" />
      <p>后台管理系统</p>
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleFormRef"
        @keyup.enter="submitForm(ruleFormRef)"
      >
        <el-form-item prop="username">
          <el-input
            v-model="ruleForm.username"
            :prefix-icon="User"
            placeholder="请输入用户名"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="ruleForm.password"
            :prefix-icon="Lock"
            show-password
            placeholder="请输入密码"
          />
        </el-form-item>
        <el-form-item prop="code" class="code-wrapper">
          <el-input v-model="ruleForm.code" placeholder="请输入验证码" />
          <img :src="imgUrl" alt="验证码" @click="getVerify()" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleFormRef)"
            >登陆</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
