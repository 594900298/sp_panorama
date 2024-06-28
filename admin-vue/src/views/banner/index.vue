<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { post,get } from "@/libs/request";
import { useDialogCase } from "@/libs/dialog";
import NoData from "@/views/other/noData.vue";
import { ElMessage } from "element-plus";
import { debounce } from "@/libs/util";
import paginationCase from "@/components/paginationCase.vue";
const tableData = ref([]);
const pageData = ref({
  pageIndex: 1,
  pageSize: 30,
  total: 0,
});
const params = ref<{
  isShow: Boolean;
}>({
  isShow: undefined,
});
//加载分页数据
const getPaginateDate = async (reload = true) => {
  const res = await post("banner/paginate",
    params.value, 
  {
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
  const res = await post("banner/editSort", {
    bannerId: row.bannerId,
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
  const res = await get(`banner/editIsShow/${row.bannerId}`);
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg)
    getPaginateDate();
  } else {
    ElMessage.error(res.errMsg);
  }
};
//点击删除
const deleteRow = async (row: any) => {
  const res = await get(`banner/delete/${row.bannerId}`);
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
    path: "banner/add",
    callback: () => {
      getPaginateDate();
    },
  });
};
//点击编辑
const editRow = (row: any) => {
  const dialog = useDialogCase();
  dialog.show({
    path: "banner/edit",
    params: {
      bannerId: row.bannerId,
    },
    callback: () => {
      getPaginateDate();
    },
  });
};
watch(
  params.value,
  () => {
    debounce(() => {
      pageData.pageIndex = 1;
      getPaginateDate();
    });
  }
);
onMounted(() => {
  getPaginateDate();
});
</script>
<template>
  <div class="page">
    <div class="title">轮播图列表</div>
    <div class="toolbar">
      <div class="query">
        <el-radio-group v-model="params.isShow">
          <el-radio-button :label="undefined">全部</el-radio-button>
          <el-radio-button :label="true">显示</el-radio-button>
          <el-radio-button :label="false">隐藏</el-radio-button>
        </el-radio-group>
      </div>
      <el-button class="float-right" type="primary" @click="addRow()">新增轮播图</el-button>
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
        <el-table-column label="轮播图">
          <template #default="scope">
            <el-image style="width: 100px; height: 100px" :src="scope.row.bannerImage" />
          </template>
        </el-table-column>
        <el-table-column prop="bannerName" label="轮播图名称" />
        <el-table-column prop="bannerLink" label="轮播图连接" />
        <el-table-column label="显示状态" :width="80">
          <template #default="scope">
            <el-switch
              v-model="scope.row.isShow"
              :active-value="true"
              :inactive-value="false"
              @change="handleChangeShow(scope.row)"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="220">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="editRow(scope.row)"
              >编辑</el-button
            >
            <el-button
              type="danger"
              size="small"
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