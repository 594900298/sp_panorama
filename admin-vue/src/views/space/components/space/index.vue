<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { post, get } from "@/libs/request";
import { useDialogCase } from "@/libs/dialog";
import NoData from "@/views/other/noData.vue";
import { ElMessage } from "element-plus";
import paginationCase from "@/components/paginationCase.vue";
const emits = defineEmits(["update:modelValue"])
const tableData = ref([]);
const pageData = ref({
  pageIndex: 1,
  pageSize: 30,
  total: 0,
});
const clickRow = ref();
//加载分页数据
const getPaginateDate = async (reload = true) => {
  const res = await post(
    "space/paginate",
    {
    },
    {
      pageIndex: reload ? 1 : pageData.value.pageIndex,
      pageSize: pageData.value.pageSize,
    }
  );
  if (res.errCode == 0) {
    pageData.value = res.page;
    tableData.value = res.data;
  }
};
//修改排序
const handleChangeSort = async (row: any) => {
  const res = await post("space/editSort", {
    spaceId: row.spaceId,
    sort: row.sort,
  });
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg);
    getPaginateDate();
  } else {
    ElMessage.error(res.errMsg);
  }
};
//修改显示状态
const handleChangeShow = async (row: any) => {
  const res = await get(`space/editIsShow/${row.spaceId}`);
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg);
    getPaginateDate();
  } else {
    ElMessage.error(res.errMsg);
  }
};
//点击删除
const deleteRow = async (row: any) => {
  const res = await get(`space/delete/${row.spaceId}`);
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg);
    getPaginateDate();
  } else {
    ElMessage.error(res.errMsg);
  }
};
// 点击添加
const addRow = () => {
  const dialog = useDialogCase();
  dialog.show({
    path: "space/components/space/add",
    callback: () => {
      getPaginateDate();
    },
  });
};
//点击编辑
const editRow = (row: any) => {
  const dialog = useDialogCase();
  dialog.show({
    path: "space/components/space/edit",
    params: {
      spaceId: row.spaceId,
    },
    callback: () => {
      getPaginateDate();
    },
  });
};
const handelRowClick = (row)=>{
  clickRow.value = row;
  emits("update:modelValue",row.spaceId)
}
const handleRow = ()=>{
  clickRow.value = null
  emits("update:modelValue",0)
}
onMounted(() => {
  getPaginateDate();
});
</script>
<template>
  <div class="page">
    <div class="title font-bold">空间列表</div>
    <div class="toolbar">
      <el-tag type="primary" closable @click="handleRow" v-if="clickRow">{{clickRow?.spaceName}}</el-tag>
      <el-button type="primary" @click="addRow()" class="float-right">添加空间</el-button>
    </div>
    <div class="main">
      <el-table :data="tableData" style="width: 100%" border @row-click="handelRowClick">
        <template #empty>
          <NoData />
        </template>
        <el-table-column label="排序" align="center" width="80">
          <template #default="scope">
            <el-input-number :controls="false" size="small" v-model="scope.row.sort" :min="0" :max="999"
              @change="handleChangeSort(scope.row)" />
          </template>
        </el-table-column>
        <el-table-column label="封面图" align="center">
          <template #default="scope">
            <el-image :src="scope.row.spaceCover" />
          </template>
        </el-table-column>
        <el-table-column prop="spaceName" label="空间名称" />
        <el-table-column label="状态">
          <template #default="scope">
            <el-switch v-model="scope.row.isShow" :active-value="true" :inactive-value="false"
              @change="handleChangeShow(scope.row)" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="220">
          <template #default="scope">
            <el-button type="primary" size="small" @click="editRow(scope.row)">预览</el-button>
            <el-button type="primary" size="small" @click="editRow(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteRow(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <paginationCase v-model="pageData" @update:modelValue="getPaginateDate(false)" />
    </div>
  </div>
</template>
