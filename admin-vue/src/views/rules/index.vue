<script setup lang="ts">
import { onMounted, ref } from "vue";
import { get } from "@/libs/request";
import { useDialogCase } from "@/libs/dialog";
import { ElMessage } from "element-plus";
const tableData = ref([]);
//加载分页数据
const getList = async () => {
  const res = await get("rule/getTree");
  if (res.errCode == 0) {
    tableData.value = res.data;
  }
};
/**
 * 添加
 * @param pid
 */
const addRow = (pid: number = 0) => {
  const dialog = useDialogCase();
  dialog.show({
    path: "rules/add",
    title: "添加规则",
    width: "30%",
    params: {
      pid: pid,
    },
    callback: () => {
      getList();
    },
  });
};
/**
 * 编辑
 * @param row
 */
const editRow = (row: any) => {
  const dialog = useDialogCase();
  dialog.show({
    path: "rules/edit",
    title: "编辑规则",
    width: "30%",
    params: {
      ruleId: row.ruleId,
    },
    callback: () => {
      getList();
    },
  });
};
/**
 * 删除
 * @param row 
 */
const deleteRow = async (row: any) => {
  const res = await get(`rule/delete/${row.ruleId}`);
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg)
    getList();
  } else {
    ElMessage.error(res.errMsg);
  }
};
onMounted(() => {
  getList();
});
</script>
<template>
  <div class="page">
    <div class="title">路由规则</div>
    <div class="toolbar">
      <el-button class="float-right" type="primary" @click="addRow()">添加规则</el-button>
    </div>
    <div class="main">
      <el-table
        :data="tableData"
        style="width: 100%"
        row-key="ruleId"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        border
        default-expand-all
      >
        <template #empty>
          <NoData />
        </template>
        <el-table-column prop="name" label="路由名称" />
        <el-table-column prop="ruleUrl" label="路由地址" />
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              :disabled="scope.row.isSystem == 1"
              @click="addRow(scope.row.ruleId)"
              >添加</el-button
            >
            <el-button
              type="primary"
              size="small"
              :disabled="scope.row.isSystem == 1"
              @click="editRow(scope.row)"
              >编辑</el-button
            >
            <el-button
              type="danger"
              size="small"
              :disabled="scope.row.isSystem == 1"
              @click="deleteRow(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>