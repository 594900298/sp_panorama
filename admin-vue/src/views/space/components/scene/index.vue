<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { post, get } from "@/libs/request";
import { useDialogCase } from "@/libs/dialog";
import NoData from "@/views/other/noData.vue";
import { ElMessage } from "element-plus";
import paginationCase from "@/components/paginationCase.vue";
import { debounce } from "@/libs/util";
import { Search } from '@element-plus/icons-vue'
import { useRouter } from "vue-router";
const router = useRouter();
const props = defineProps(["modelValue"]);
const tableData = ref([]);
const pageData = ref({
  pageIndex: 1,
  pageSize: 30,
  total: 0,
});
const params = ref<{
  sceneName: String;
  spaceId: number;
}>({
  sceneName: "",
  spaceId: 0,
});
watch(
  props,
  () => {
    debounce(() => {
      params.value.spaceId = props.modelValue;
      getPaginateDate();
    });
  }
);
//加载分页数据
const getPaginateDate = async (reload = true) => {
  const res = await post(
    "scene/paginate",
    {
      ...params.value
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
  const res = await post("scene/editSort", {
    sceneId: row.sceneId,
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
  const res = await get(`scene/editIsShow/${row.sceneId}`);
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg);
    getPaginateDate();
  } else {
    ElMessage.error(res.errMsg);
  }
};
//点击删除
const deleteRow = async (row: any) => {
  const res = await get(`scene/delete/${row.sceneId}`);
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
    path: "space/components/scene/add",
    callback: () => {
      getPaginateDate();
    },
  });
};
//点击编辑
const editRow = (row: any) => {
  router.push({ path: "/scene", query: { sceneId: row.sceneId }  });
};
onMounted(() => {
  getPaginateDate();
});
</script>
<template>
  <div class="page">
    <div class="title font-bold">场景列表</div>
    <div class="toolbar">
      <el-input v-model="params.sceneName" style="max-width: 300px" placeholder="请输入场景名称" class="input-with-select">
        <template #append>
          <el-button :icon="Search" @click="getPaginateDate()" />
        </template>
      </el-input>
      <el-button type="primary" @click="addRow()" class="float-right">添加场景</el-button>
    </div>
    <div class="main">
      <el-table :data="tableData" style="width: 100%" border>
        <template #empty>
          <NoData />
        </template>
        <el-table-column label="排序" align="center" width="80">
          <template #default="scope">
            <el-input-number :controls="false" size="small" v-model="scope.row.sort" :min="0" :max="999"
              @change="handleChangeSort(scope.row)" />
          </template>
        </el-table-column>
        <el-table-column prop="sceneName" label="场景名称" />
        <el-table-column prop="limitview" label="视角类型" />
        <el-table-column prop="hlookatmin" label="最小水平视角范围" align="center" width="140" />
        <el-table-column prop="hlookatmax" label="最大水平视角范围" align="center" width="140" />
        <el-table-column prop="vlookatmin" label="最小垂直视角范围" align="center" width="140" />
        <el-table-column prop="vlookatmax" label="最大垂直视角范围" align="center" width="140" />
        <el-table-column label="状态">
          <template #default="scope">
            <el-switch v-model="scope.row.isShow" :active-value="true" :inactive-value="false"
              @change="handleChangeShow(scope.row)" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="220" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="editRow(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteRow(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <paginationCase v-model="pageData" @update:modelValue="getPaginateDate(false)" />
    </div>
  </div>
</template>
