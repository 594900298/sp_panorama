<script setup lang="ts">
import { onMounted, ref } from "vue";
import { post,get } from "@/libs/request";
import { useDialogCase } from "@/libs/dialog";
import NoData from "@/views/other/noData.vue";
import { ElMessage } from "element-plus";
import paginationCase from "@/components/paginationCase.vue";
const tableData = ref([]);
const pageData = ref({
  pageIndex: 1,
  pageSize: 30,
  total: 0,
});
//加载分页数据
const getPaginateDate = async (reload = true) => {
  const res = await post("admin/paginate",{}, {
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
  const res = await post("admin/editSort", {
    userId: row.userId,
    sort: row.sort,
  });
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg)
    getPaginateDate();
  }else{
    ElMessage.error(res.errMsg);
  }
};
//修改显示状态
const handleChangeShow = async (row: any) => {
  const res = await get(`admin/editStatus/${row.userId}`);
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg)
    getPaginateDate();
  } else {
    ElMessage.error(res.errMsg);
  }
};
//点击删除
const deleteRow = async (row: any) => {
  const res = await get(`admin/delete/${row.userId}`);
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg)
    getPaginateDate();
  }else{
    ElMessage.error(res.errMsg);
  }
};
// 点击添加
const addRow = () => {
  const dialog = useDialogCase();
  dialog.show({
    path: "admin/add",
    callback: () => {
      getPaginateDate();
    },
  });
};
//点击编辑
const editRow = (row: any) => {
  const dialog = useDialogCase();
  dialog.show({
    path: "admin/edit",
    params: {
      userId: row.userId,
    },
    callback: () => {
      getPaginateDate();
    },
  });
};
//点击修改密码
const editPassWord = (row: any) => {
  const dialog = useDialogCase();
  dialog.show({
    path: "admin/editPassWord",
    title:"修改密码",
    width:"30%",
    params: {
      userId: row.userId,
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
    <div class="title">管理员列表</div>
    <div class="toolbar">
      <el-button class="float-right" type="primary" @click="addRow()">新增管理员</el-button>
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
        <el-table-column prop="username" label="管理员名称" />
        <el-table-column prop="realName" label="真实姓名">
          <template #default="scope">{{
            scope.row.realName ?? "-"
          }}</template>
        </el-table-column>
        <el-table-column prop="mobile" label="手机号" />
        <el-table-column label="是否主账号">
          <template #default="scope">{{
            scope.row.isMain ? "是" : "否"
          }}</template>
        </el-table-column>
        <el-table-column label="状态">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="true"
              :inactive-value="false"
              :disabled="scope.row.isMain"
              @change="handleChangeShow(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="220">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="editPassWord(scope.row)"
              >修改密码</el-button
            >
            <el-button
              type="primary"
              size="small"
              :disabled="scope.row.isMain"
              @click="editRow(scope.row)"
              >编辑</el-button
            >
            <el-button
              type="danger"
              size="small"
              :disabled="scope.row.isMain"
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