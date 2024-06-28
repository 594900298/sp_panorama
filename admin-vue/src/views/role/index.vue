<script setup lang="ts">
import { onMounted, ref } from "vue";
import { post,get } from "@/libs/request";
import { useDialogCase } from "@/libs/dialog";
import { ElMessage } from "element-plus";
import NoData from "@/views/other/noData.vue";
import paginationCase from "@/components/paginationCase.vue";
const tableData = ref([]);
const pageData = ref({
  pageIndex: 1,
  pageSize: 30,
  total: 0,
});
//加载分页数据
const getPaginateDate = async (reload = true) => {
  const res = await post("role/paginate",{}, {
    pageIndex: reload ? 1 : pageData.value.pageIndex,
    pageSize: pageData.value.pageSize,
  });
  if (res.errCode == 0) {
    pageData.value = res.page;
    tableData.value = res.data;
  }
};
//修改排序
const handleChangeSort = async (row: any) => {
  const res = await post("role/editSort", {
    roleId: row.roleId,
    sort: row.sort,
  });
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg)
    getPaginateDate();
  }
};
//点击删除
const deleteRow = async (row: any) => {
  const res = await get(`role/delete/${row.roleId}`);
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg)
    getPaginateDate();
  }
};
// 点击添加
const addRow = () => {
  const dialog = useDialogCase();
  dialog.show({
    path: "role/add",
    callback: () => {
      getPaginateDate();
    },
  });
};
//点击编辑
const editRow = (row: any) => {
  const dialog = useDialogCase();
  dialog.show({
    path: "role/edit",
    params: {
      roleId: row.roleId,
    },
    callback: () => {
      getPaginateDate();
    },
  });
};
onMounted(() => {
  getPaginateDate();
});
</script>
<template>
  <div class="page">
    <div class="title">角色列表</div>
    <div class="toolbar">
      <el-button class="float-right" type="primary" @click="addRow()">新增角色</el-button>
    </div>
    <div class="main">
      <el-table :data="tableData" style="width: 100%" border>
        <template #empty>
          <NoData />
        </template>
        <el-table-column label="排序" align="center" width="80">
          <template #default="scope">
            <el-input-number
              :controls="false"
              size="small"
              v-model="scope.row.sort"
              :min="0"
              :max="999"
              @change="handleChangeSort(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="roleName" label="角色名称" />
        <el-table-column label="操作" align="center" width="200">
          <template #default="scope">
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
      <paginationCase v-model="pageData" @update:modelValue="getPaginateDate(false)" />
    </div>
  </div>
</template>