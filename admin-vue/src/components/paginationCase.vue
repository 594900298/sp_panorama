<!-- 
  分页组件
    引入
    import paginationCase from "@/components/paginationCase.vue";
    
    使用
    <pagination-case v-model="pageData" @update:modelValue="getPaginateDate(false)" />
-->
<script setup lang="ts">
//双向数据绑定
const props = defineProps(["modelValue"]);
const emit = defineEmits(["update:modelValue"]);
//检测值改变，通知父元素
const handleCurrentChange = (value: number) => {
  emit("update:modelValue",{
    pageIndex: value,
    pageSize: props.modelValue.pageSize,
    total: props.modelValue.total,
  });
};
const handleSizeChange = (value: number) => {
  emit("update:modelValue",{
    pageIndex: props.modelValue.pageIndex,
    pageSize: value,
    total: props.modelValue.total,
  });
};
</script>
<template>
  <div class="pagiante-case">
    <el-pagination @current-change="handleCurrentChange" @size-change="handleSizeChange" :current-page="props.modelValue.pageIndex"
      :page-size="props.modelValue.pageSize" :total="props.modelValue.total" layout="total, prev, pager, next, jumper"
      background>
    </el-pagination>
  </div>
</template>
<style scoped>
.pagiante-case {
  padding: 15px 0;
}
</style>
