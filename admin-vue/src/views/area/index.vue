<script setup lang="ts">
import { onMounted, ref, h , watch} from "vue";
import { get, post } from "@/libs/request";
import { useDialogCase } from "@/libs/dialog";
import { ElMessage, ElInput, ElButton, ElSwitch } from "element-plus";
const tableData = ref([]);
const page = ref();
const columns = [
  {
    key: "areaName",
    dataKey: "areaName",
    title: "名称",
    width: 400,
  },
  {
    key: "isShow",
    dataKey: "isShow",
    title: "显示状态",
    width: 120,
    cellRenderer: ({ rowData }) =>
      h(ElSwitch, { 
        "model-value":rowData.isShow,
        onChange:()=>{editIsShow(rowData)}
      }),
  },
  {
    dataKey: "areaId",
    title: "操作",
    width: 200,
    cellRenderer: ({rowData}) =>{
      let arr = [
        h(ElButton,{
          "type":"primary",
          "size":"small",
          onclick:()=>{editRow(rowData)}
        },"编辑"),
        h(ElButton,{
          "type":"danger",
          "size":"small",
          onclick:()=>{deleteRow(rowData)}
        },"删除")
      ];
      if(rowData.areaType!=2){
        arr.unshift(h(ElButton,{
          "type":"primary",
          "size":"small",
          onclick:()=>{addRow(rowData.areaId,rowData.areaType+1)}
        },"添加"))
      }
      return h("div",arr)
    },
  },
];
function addChildren(arr){
  return arr.map((item)=>{
    return {
      ...item,
      id:item.areaId,
      parentId:item.pid,
      children:item.children?addChildren(item.children):[]
    }
  })
}
//加载分页数据
const getList = async () => {
  const res = await post("area/getTree");
  if (res.errCode == 0) {
    tableData.value = res.data.map((item)=>{
      return {
        ...item,
        id:item.areaId,
        parentId:item.pid,
        children:item.children?addChildren(item.children):[]
      };
    })
  }
};
/**
 * 添加
 * @param pid
 * @param areaType
 */
const addRow = (pid,areaType) => {
  const dialog = useDialogCase();
  dialog.show({
    path: "area/add",
    title: "添加区域",
    width: "30%",
    params: {
      pid: pid,
      areaType: areaType,
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
    path: "area/edit",
    title: "编辑区域",
    width: "30%",
    params: {
      areaId: row.areaId,
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
  const res = await get(`area/delete/${row.areaId}`);
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg);
    getList();
  }
};
/**
 * 删除
 * @param row
 */
const editIsShow = async (row: any) => {
  const res = await get(`area/editIsShow/${row.areaId}`);
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg);
    getList();
  }
};
/**
 * 修改排序
 */
const editSort = async (row: any) => {
  const res = await post(`area/editSort`,{
    areaId:row.areaId,
    sort:row.sort
  });
  if (res.errCode == 0) {
    ElMessage.success(res.errMsg);
    getList();
  }
};
onMounted(() => {
  getList();
});
</script>
<template>
  <div class="page" ref="page">
    <div class="title">区域管理</div>
    <div class="toolbar">
      <el-button class="float-right" type="primary" @click="addRow(0,0)"
        >添加区域</el-button
      >
    </div>
    <div class="main" >
      <el-table-v2
        v-if="page"
        v-model:expanded-row-keys="children"
        expand-column-key="areaName"
        :columns="columns"
        :data="tableData"
        :width="page.offsetWidth"
        :height="page.offsetHeight"
      />
    </div>
  </div>
</template>
<style style="scss" scoped>
.page{
  height: calc( 100% - 186px);
}
</style>
