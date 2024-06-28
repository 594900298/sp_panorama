<script setup lang="ts">
import Space from "@/views/space/components/space/index.vue"
import Scene from "@/views/space/components/scene/index.vue"
import { ref } from "vue";
const spaceId = ref(0);
</script>
<template>
  <div class="page">
    <div class="title">空间管理</div>
    <div class="main">
      <el-row>
        <el-col :span="10">
          <Space v-model="spaceId"></Space>
        </el-col>
        <el-col :span="14">
          <Scene v-model="spaceId"></Scene>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
