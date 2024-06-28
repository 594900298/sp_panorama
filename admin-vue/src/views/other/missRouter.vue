<script setup lang="ts">
let msg = "访问的页面不存在";
</script>
<template>
  <el-result title="404" :sub-title="msg">
    <template #icon>
      <img src="@/assets/images/empty-page.png" class="width400" />
    </template>
  </el-result>
</template>
