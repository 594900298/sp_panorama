<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from "vue-router";
import { get, post } from "@/libs/request";
import { ElMessage, ElMessageBox } from "element-plus";
import { Grid, More } from '@element-plus/icons-vue'
const router = useRouter();
const route = useRoute();
const lMenuList = Object.freeze([
  { title: "基础", icon: Grid },
  { title: "热点", icon: More },
])
const viewTypeList = Object.freeze([
  { label: "自动限制", value: "auto" },
  { label: "限制查看", value: "lookat" },
  { label: "只有在这个范围内,允许观察", value: "range" },
  { label: "但允许放大看到整个图像", value: "fullrage" },
  { label: "不以任何方式限制缩放", value: "offrange" },
])
const controlTypeList = Object.freeze([
  { label: "拖动全景图像", value: "drag" },
  { label: "移动全景图像", value: "moveto" },
])
const spotTypeList = Object.freeze([
  { label: "切换场景热点", value: 0 },
  { label: "货架热点", value: 1 },
  { label: "视频热点", value: 2 },
  { label: "热门热点", value: 3 },
])
const styleImgList = Object.freeze([
  {
    label: "skin_prod_list",
    value: "@/assets/image/skin_prod_list.gif",
  },
  {
    label: "skin_hotspotstyle_turn_left",
    value: "@/assets/image/skin_hotspotstyle_turn_left.gif",
  },
  {
    label: "skin_hotspotstyle_turn_right",
    value: "@/assets/image/skin_hotspotstyle_turn_right.gif",
  },
  {
    label: "skin_hotspotstyle_turn_left_back",
    value: "@/assets/image/skin_hotspotstyle_turn_left_back.gif",
  },
  {
    label: "skin_hotspotstyle_turn_right_back",
    value: "@/assets/image/skin_hotspotstyle_turn_right_back.gif",
  },
  {
    label: "skin_hotspotstyle_arrow",
    value: "@/assets/image/skin_hotspotstyle_arrow.gif",
  },
  {
    label: "skin_layerstyle_tip",
    value: "@/assets/image/skin_layerstyle_tip.png",
  },
  {
    label: "skin_hotspotstyle",
    value: "@/assets/image/skin_hotspotstyle.png",
  },
  {
    label: "skin_hotspotstyle_hotspot",
    value: "@/assets/image/skin_hotspotstyle_hotspot.gif",
  },
  {
    label: "skin_hotspotstyle_inspiration",
    value: "@/assets/image/skin_hotspotstyle_inspiration.gif",
  },
  {
    label: "skin_hotspotstyle_video",
    value: "@/assets/image/skin_hotspotstyle_video.gif",
  },
  {
    label: "skin_hotspotstyle_point",
    value: "@/assets/image/skin_hotspotstyle_point.jpg",
  },
  {
    label: "skin_hotspotstyle_door",
    value: "@/assets/image/skin_hotspotstyle_door.jpg",
  },
])
const ruleFormRef = ref(null);
const hotRuleFormRef = ref(null);
const loading = ref<boolean>(true);
const krpanoUtils = ref<any>();
const krpanoUtils2 = ref<any>();
const hlookat = ref<Array<number>>([-360, 360]);
const vlookat = ref<Array<number>>([-90, 90]);
const componentKey = ref<number>(0);
const componentKey2 = ref<number>(0);
const ruleForm = ref<any>({});
const tempScene = ref<any>({});
const rules = ref({
  sceneName: [
    { required: true, message: "请输入场景名称", trigger: "blur" },
  ],
  spaceId: [
    { required: true, message: "请选择空间", trigger: "blur" },
  ],
});
const hotRuleForm = ref({
  hotspotId: null,
  hotspotName: "",
  sceneId: route.params.sceneId,
  hotspotStyle: "skin_prod_list",
  hotspotType: null,
  ath: "",
  atv: "",
  toSceneId: "",
  shelfId: "",
  videoLink: "",
  pdfLink: [],
  width: 0,
  height: 0,
});
const hotRules = ref({
  hotspotType: [
    { required: true, message: "请选择热点类型", trigger: "blur" },
  ],
  toSceneId: [
    { required: true, message: "请选择切换的场景", trigger: "blur" },
  ],
  shelfId: [{ required: true, message: "请选择货架", trigger: "blur" }],
  videoLink: [
    { required: true, message: "请上传视频地址", trigger: "blur" },
  ],
  pdfLink: [{ required: true, message: "请上传PDF", trigger: "blur" }],
});
const isActive = ref<number>(0);
const spaceList = ref<Array<any>>([]);
const hotspotList = ref<Array<any>>([]);
const sceneList = ref<Array<any>>([]);
const shelfList = ref<Array<any>>([]);
const isSpotEdit = ref<string>("0");
const isAddHotSpot = ref<boolean>(false);
const isSelectScene = ref<boolean>(false);
const addSpotName = ref<string>("");
const isSpotActive = ref<string>("");
const timeout = ref<any>(null);
/**
 * @description: 获取展厅分类
 */
const getspaceList = async () => {
  const res = await get("space/getList");
  if (res.errCode === 0) {
    spaceList.value = res.data.map((itme) => {
      return {
        label: itme.spaceName,
        value: itme.spaceId
      };
    });
  }
}
/**
     * @description: 获取场景详情
     */
const getDetail = async () => {
  const res = await get(`scene/detail/${route.query.sceneId}`);
  if (res.errCode === 0) {
    ruleForm.value = res.data;
    res.data.hlookatmin ? "" : (ruleForm.value.hlookatmin = -360);
    res.data.hlookatmax ? "" : (ruleForm.value.hlookatmax = 360);
    res.data.vlookatmin ? "" : (ruleForm.value.vlookatmin = 90);
    res.data.vlookatmax ? "" : (ruleForm.value.vlookatmax = -90);
    hlookat.value = [ruleForm.value.hlookatmin, ruleForm.value.hlookatmax];
    vlookat.value = [ruleForm.value.vlookatmin, ruleForm.value.vlookatmax];
    embedpano({
      swf: `/static/scene/tour.swf`,
      xml: `${res.data.xmlPath}`,
      target: "pano",
      html5: "auto",
      mobilescale: 1.0,
      passQueryParameters: true,
    });
    krpanoUtils.value = new KrpanoUtilsClass();
    setTimeout(() => {
      // 初始化 全景
      // 设置场景移动方式
      krpanoUtils.value.setControlMouse(ruleForm.value.control);
      // 设置视角
      krpanoUtils.value.setViewLookat(
        ruleForm.value.hlookat,
        ruleForm.value.vlookat
      );
      // 设置锁定视角
      krpanoUtils.value.setViewLoock(
        ruleForm.value.hlookatmin,
        ruleForm.value.hlookatmax,
        ruleForm.value.vlookatmin,
        ruleForm.value.vlookatmax,
        ruleForm.value.limitview
      );
      // 设置当前缩放
      krpanoUtils.value.setViewFov(ruleForm.value.fov);
      initTwoPano();
    }, 500);
  }
}
/**
 * 设置当前初始视角模块
 */
const initTwoPano = () => {
  embedpano({
    swf: `/static/scene/tour.swf`,
    xml: `${ruleForm.value.xmlPath}`,
    target: "panoTwo",
    html5: "auto",
    mobilescale: 1.0,
    passQueryParameters: true,
  });
  krpanoUtils2.value = new KrpanoUtilsClass("krpanoSWFObject2");
  setTimeout(() => {
    // 设置视角
    krpanoUtils2.value.setViewLookat(
      ruleForm.value.hlookat,
      ruleForm.value.vlookat
    );
    // 设置锁定视角
    krpanoUtils2.value.setViewLoock(
      ruleForm.value.hlookatmin,
      ruleForm.value.hlookatmax,
      ruleForm.value.vlookatmin,
      ruleForm.value.vlookatmax,
      ruleForm.value.limitview
    );
    // 设置当前缩放
    krpanoUtils2.value.setViewFov(ruleForm.value.fov);
    loading.value = false;
  }, 500);
}
/**
 * 选择选择类型
 * @param e 
 */
const selectType = (e) => {
  if (e == "auto") {
    ruleForm.value.hlookatmin = -360;
    ruleForm.value.hlookatmax = 360;
    ruleForm.value.vlookatmin = 90;
    ruleForm.value.vlookatmax = -90;
    hlookat.value = [-360, 360];
    vlookat.value = [-90, 90];
  }
  krpanoUtils.value.setViewLoock(
    -360,
    360,
    -90,
    90,
    ruleForm.value.limitview
  );
  krpanoUtils.value.krpano.call("updatescreen()");
  let param = krpanoUtils.value.getViewLookat();
  ruleForm.value.hlookat = param.hlookat.toFixed(2);
  ruleForm.value.vlookat = param.vlookat.toFixed(2);
}
/**
     * @description: 切换功能
     * @param index 索引 0 基础 1 热点
     */
const changeMenu = (index) => {
  isActive.value = index;
  if (index == 1) {
    getSceneList();
    getHotspotList();
    getShelfList();
  } else {
    krpanoUtils.value.deleteHotspot(addSpotName.value);
    hotspotList.value.map((item) => {
      krpanoUtils.value.deleteHotspot(item.random_string);
    });
    initSpot();
    isAddHotSpot.value = false;
    isSpotEdit.value = "0";
    hotRuleFormRef.value.clearValidate();
  }
}
/**
 * @description: 设置控制方式
 * @param $event 选中参数
 */
const selectControl = (e) => {
  krpanoUtils.value.setControlMouse(e);
}
/**
 * @description: 获取热点列表
 */
const getHotspotList = async () => {
  const res = await post("Hotspot/getList", {
    sceneId: route.query.sceneId,
  });
  if (res.errCode === 0) {
    hotspotList.value = res.data;
    res.data.map((item) => {
      krpanoUtils.value.addHotspots(
        item.random_string,
        item.hotspot_style,
        item.hotspot_name,
        item.ath,
        item.atv
      );
      if (item.width > 0 && item.height > 0) {
        krpanoUtils.value.registercontentsize(
          item.random_string,
          item.width,
          item.height
        );
      }
    });
  }
}
/**
 * @description: 获取场景列表
 */
const getSceneList = async () => {
  const res = await post("VrScene/getList", {
    roomId: ruleForm.value.roomId,
    sceneId: ruleForm.value.sceneId,
  });
  if (res.errCode === 0) {
    sceneList.value = res.data;
  }
}
/**
 * @description: 获取货架列表
 */
const getShelfList = async () => {
  const res = await post("Shelf/getList", {
    roomId: ruleForm.value.roomId,
  });
  if (res.errCode === 0) {
    shelfList.value = res.data;
  }
}
/**
 * @description: 设置当前视角参数
 */
const setLockView = () => {
  let param = krpanoUtils.value.getViewLookat();
  let { fov } = krpanoUtils.value.getViewFov();
  ruleForm.value.hlookat = param.hlookat.toFixed(2);
  ruleForm.value.vlookat = param.vlookat.toFixed(2);
  ruleForm.value.fov = fov.toFixed(2);
  krpanoUtils.value.setViewLookat(param.hlookat, param.vlookat);
  krpanoUtils2.value.setViewLookat(param.hlookat, param.vlookat);
  krpanoUtils.value.setViewFov(fov);
  krpanoUtils2.value.setViewFov(fov);
}
/**
 * @description: 选择水平视角参数
 */
const changeHLookAt = () => {
  componentKey.value += 1;
  ruleForm.value.hlookatmin = hlookat.value[0];
  ruleForm.value.hlookatmax = hlookat.value[1];
  krpanoUtils.value.setViewLoock(
    hlookat.value[0],
    hlookat.value[1],
    ruleForm.value.vlookatmin,
    ruleForm.value.vlookatmax,
    ruleForm.value.limitview
  );
  krpanoUtils.value.krpano.call("updatescreen()");
  let param = krpanoUtils.value.getViewLookat();
  ruleForm.value.hlookat = param.hlookat.toFixed(2);
  ruleForm.value.vlookat = param.vlookat.toFixed(2);
}
/**
 * @description: 选择垂直视角参数
 */
const changeVLookAt = () => {
  componentKey2.value += 1;
  ruleForm.value.vlookatmin = vlookat.value[1];
  ruleForm.value.vlookatmax = vlookat.value[0];
  krpanoUtils.value.setViewLoock(
    ruleForm.value.hlookatmin,
    ruleForm.value.hlookatmax,
    vlookat.value[1],
    vlookat.value[0],
    ruleForm.value.limitview
  );
  krpanoUtils.value.krpano.call("updatescreen()");
  let param = krpanoUtils.value.getViewLookat();
  ruleForm.value.hlookat = param.hlookat.toFixed(2);
  ruleForm.value.vlookat = param.vlookat.toFixed(2);
}
/**
 * @description: 保存场景编辑
 */
const saveSet = () => {
  ruleFormRef.value.validate(async (valid) => {
    if (valid) {
      const res = await post("scene/edit", ruleForm.value);
      if (res.errCode === 0) {
        ElMessage.success("保存成功");
      }
    }
  });
}
/**
 * @description: 新增热点
 */
const addHotSpot = () => {
  if (hotRuleForm.value.hotspotId)
    return ElMessage.error("请先保存当前热点");
  if (isAddHotSpot.value) return;
  initSpot();
  isAddHotSpot.value = true;
  isSpotEdit.value = "1";
  addSpotName.value = "hotSpot_" + new Date().getTime();
  krpanoUtils.value.addHotspots(
    addSpotName.value,
    hotRuleForm.value.hotspotStyle,
    hotRuleForm.value.hotspotName,
    hotRuleForm.value.ath,
    hotRuleForm.value.atv
  );
}
/**
 * @description: 切换热点样式
 * @param label 样式名称
 */
const changeHotSpot = (item) => {
  let param = krpanoUtils.value.getHotspots(addSpotName.value);
  hotRuleForm.value.ath = param.hlookat.toFixed(2);
  hotRuleForm.value.atv = param.vlookat.toFixed(2);
  hotRuleForm.value.hotspotStyle = item.label;
  krpanoUtils.value.deleteHotspot(addSpotName.value);
  addSpotName.value = "hotSpot_" + new Date().getTime();
  krpanoUtils.value.addHotspots(
    addSpotName.value,
    hotRuleForm.value.hotspotStyle,
    hotRuleForm.value.hotspotName,
    hotRuleForm.value.ath,
    hotRuleForm.value.atv
  );
  hotRuleForm.value.width = 0;
  hotRuleForm.value.height = 0;
}
/**
 * @description: 设置hotspot_name名称
 */
const setHotSpot_name = () => {
  krpanoUtils.value.setHotspotsText(
    addSpotName.value,
    hotRuleForm.value.hotspotName
  );
}
/**
 * @description: 获取选择场景id、封面图
 */
const selectScenData = (e) => {
  hotRuleForm.value.toSceneId = e.sceneId;
  tempScene.value = e;
  isSelectScene.value = false;
}
/**
 * @description: 切换热点类型
 * @param e 类型索引
 *  { label: '切换场景热点', value: 0 }
    { label: '货架热点', value: 1 }
    { label: '视频热点', value: 2 }
    { label: '热门热点', value: 3 }
 */
const changeSpotType = (e) => {
  hotRuleFormRef.value.clearValidate();
  switch (e) {
    case 0:
      hotRuleForm.value.shelfId = '';
      hotRuleForm.value.videoLink = '';
      hotRuleForm.value.pdfLink = [];
      break;
    case 1:
      hotRuleForm.value.toSceneId = '';
      hotRuleForm.value.videoLink = '';
      hotRuleForm.value.pdfLink = [];
      break;
    case 2:
      hotRuleForm.value.shelfId = '';
      hotRuleForm.value.toSceneId = '';
      hotRuleForm.value.pdfLink = [];
      break;
    case 3:
      hotRuleForm.value.shelfId = '';
      hotRuleForm.value.videoLink = '';
      hotRuleForm.value.toSceneId = '';
      break;
  }
}
/**
 * @description: 上传热点pdf回调
 * @return {*} 上传文件数据
 */
const setFileUrl = (e) => {
  hotRuleForm.value.pdfLink = [e.name, e.url];
}
/**
 * @description: 保存热点新增
 */
const saveAddSpot = () => {
  let param = krpanoUtils.value.getHotspots(addSpotName.value);
  hotRuleForm.value.ath = param.hlookat.toFixed(2);
  hotRuleForm.value.atv = param.vlookat.toFixed(2);
  hotRuleFormRef.value.validate(async (valid) => {
    if (valid) {
      var res;
      // 新增
      if (!hotRuleForm.value.hotspotId) {
        res = await post("Hotspot/add", hotRuleForm);
      }
      // 编辑
      else {
        res = await post("Hotspot/edit", hotRuleForm);
      }
      if (res.errCode === 0) {
        krpanoUtils.value.deleteHotspot(addSpotName.value);
        ElMessage.success("操作成功");
        hotRuleFormRef.value.resetFields();
        initSpot();
        getHotspotList();
        isAddHotSpot.value = false;
        isSpotEdit.value = "0";
      }
    }
  });
}
/**
 * @description: 编辑热点
 */
const editSpot = (item) => {
  if (isAddHotSpot.value) {
    isSpotEdit.value = "1";
    return ElMessage.error("请先保存当前热点设置");
  }
  // 删除所有热点
  hotspotList.value.map((item) => {
    krpanoUtils.value.deleteHotspot(item.random_string);
  });
  // 添加当前编辑标签
  krpanoUtils.value.addHotspots(
    item.random_string,
    item.hotspot_style,
    item.hotspot_name,
    item.ath,
    item.atv
  );
  // 设置当前编辑标签大小
  if (item.width > 0 && item.height > 0) {
    krpanoUtils.value.registercontentsize(
      item.random_string,
      item.width,
      item.height
    );
  }
  isSpotActive.value = item.hotspotId;
  krpanoUtils.value.setViewLookat(item.ath, item.atv);
  hotRuleForm.value = item;
  addSpotName.value = item.random_string;
  if (item.toSceneId) {
    sceneList.value.map((data) => {
      if (data.sceneId == item.toSceneId) {
        tempScene.value = data;
      }
    });
  }
  isSpotEdit.value = "1";
  isAddHotSpot.value = true;
}
/**
 * @description: 重置热点,返回热点列表
 */
const initSpot = () => {
  tempScene.value = {};
  hotRuleForm.value = {
    hotspotName: "",
    sceneId: String(route.query.sceneId),
    hotspotStyle: "skin_prod_list",
    hotspotType: null,
    ath: "",
    atv: "",
    toSceneId: "",
    shelfId: "",
    videoLink: "",
    pdfLink: [],
    width: 0,
    height: 0,
  };
  addSpotName.value = "";
  isSpotActive.value = "";
}
/**
 * @description: 删除热点
 */
const deleteSpot = () => {
  ElMessageBox.confirm("是否删除该热点?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(async () => {
    if (hotRuleForm.value.hotspotId) {
      const res = await post("Hotspot/delete", {
        hotspotId: hotRuleForm.value.hotspotId,
      });
    }
    krpanoUtils.value.deleteHotspot(addSpotName.value);
    initSpot();
    getHotspotList();
    isAddHotSpot.value = false;
    isSpotEdit.value = "0";
  });
}
/**
 * @description: 设置热点位置
 * @param name 热点名称
 * @param ath
 * @param atv
 */
const setHotSpotPosition = async (name, ath, atv) => {
  let num = 0;
  hotspotList.value.map((item, index) => {
    if (item.random_string == name) {
      item.ath = ath.toFixed(2);
      item.atv = atv.toFixed(2);
      num = index;
    }
  });
  const res = await post("Hotspot/edit", hotspotList.value[num]);
  krpanoUtils.value.deleteHotspot(hotspotList.value[num].random_string);
  krpanoUtils.value.addHotspots(
    hotspotList.value[num].random_string,
    hotspotList.value[num].hotspot_style,
    hotspotList.value[num].hotspot_name,
    hotspotList.value[num].ath,
    hotspotList.value[num].atv
  );
  if (hotspotList.value[num].width > 0 && hotspotList.value[num].height > 0) {
    krpanoUtils.value.registercontentsize(
      hotspotList.value[num].random_string,
      hotspotList.value[num].width,
      hotspotList.value[num].height
    );
  }
}
/**
 * @description: 切换热点大小
 */
const changeSpotSize = () => {
  if (hotRuleForm.value.width < 1 || hotRuleForm.value.height < 1) {
    krpanoUtils.value.resetsize(addSpotName.value);
  } else {
    krpanoUtils.value.registercontentsize(
      addSpotName.value,
      hotRuleForm.value.width,
      hotRuleForm.value.height
    );
  }
}
onMounted(() => {
  window.listenerDragHotSpot = function (_, name, ath, atv) {
    if (!isAddHotSpot.value) {
      if (timeout.value) {
        clearTimeout(timeout.value);
      }
      timeout.value = setTimeout(() => {
        setHotSpotPosition(name, ath, atv);
      }, 200);
    }
  };
  window.listen_hotspot = function (e) {
    if (isAddHotSpot.value) {
      isSpotEdit.value = "1";
      return
    }
    let item = hotspotList.value.filter((item) => item.random_string == e);
    if (item.length > 0) {
      editSpot(item[0]);
    }
  };
  getspaceList();
  getDetail();
})
</script>
<template>
  <div class="vrScene_box" v-loading="loading">
    <el-container>
      <el-aside class="box_l">
        <div class="itemBtn" :class="index == isActive ? 'isActiveItem' : ''" v-for="(item, index) in lMenuList"
          :key="index" @click="changeMenu(index)">
          <el-icon :size="30">
            <component :is="item.icon"></component>
          </el-icon>
          <div>{{ item.title }}</div>
        </div>
        <div class="back itemBtn" @click="$router.push({ path: '/vrScene' })">
          <i class="el-icon-back"></i>
          <div>返回</div>
        </div>
      </el-aside>
      <el-main class="box_mid">
        <div id="pano" ref="page">
          <noscript>
            <table style="width: 100%; height: 100%">
              <tr style="vertical-align: middle">
                <td>
                  <div style="text-align: center">
                    ERROR:
                    <br />
                    <br />Javascript not activated
                    <br />
                    <br />
                  </div>
                </td>
              </tr>
            </table>
          </noscript>
        </div>
        <div v-if="!loading && isActive == 0" class="view_frame"></div>
        <el-button v-if="!loading && isActive == 0" class="view_frame_btn" size="mini" type="primary"
          @click.prevent="setLockView">把当前视角设为初始视角</el-button>
      </el-main>
      <el-aside class="box_r">
        <!-- 基础设置 -->
        <div v-show="isActive == 0">
          <div class="title">基础设置</div>
          <div class="right_setting">
            <el-form label-position="top" ref="ruleFormRef" label-width="80px" :model="ruleForm" :rules="rules"
              @submit.native.prevent>
              <el-form-item label="场景名称" prop="sceneName">
                <el-input size="mini" v-model="ruleForm.sceneName"></el-input>
              </el-form-item>
              <el-form-item label="空间分类" prop="spaceId">
                <el-select v-model="ruleForm.spaceId" placeholder="请选择空间分类" >
                  <el-option v-for="item in spaceList" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
              </el-form-item>
              <el-form-item label="当前初始视角" prop="hlookat">
                <div id="panoTwo">
                  <div class="fixed"></div>
                </div>
              </el-form-item>
              <el-form-item class="mt10" label="控制方式" prop="control">
                <el-select style="width: 100%" size="mini" v-model="ruleForm.control" @change="selectControl">
                  <el-option v-for="(item, key) in controlTypeList" :key="key" :label="item.label"
                    :value="item.value" />
                </el-select>
              </el-form-item>
              <el-form-item label="视角类型" prop="limitview">
                <el-select style="width: 100%" size="mini" v-model="ruleForm.limitview" @change="selectType">
                  <el-option v-for="(item, key) in viewTypeList" :key="key" :label="item.label" :value="item.value" />
                </el-select>
              </el-form-item>
              <div v-if="ruleForm.limitview !== 'auto'" :class="ruleForm.limitview === 'auto' ? '' : 'mb90'">
                <el-form-item class="sceneSlider" label="水平视角限制" prop="hlookatmin">
                  <el-slider :key="`h_${componentKey}`" class="mr20 ml20" v-model="hlookat" range :min="-360" :max="360"
                    @change="changeHLookAt">
                  </el-slider>
                  <div class="flex justify-between w-full">
                    <div>
                      <div>
                        <el-input-number class="width50" size="mini" :controls="false" v-model="hlookat[0]" :min="-360"
                          :max="360" @change="changeHLookAt"></el-input-number>
                      </div>
                      <div class="sceneSlider_text">最低</div>
                    </div>
                    <div>
                      <div>
                        <el-input-number class="width50" size="mini" :controls="false" v-model="hlookat[1]" :min="-360"
                          :max="360" @change="changeHLookAt"></el-input-number>
                      </div>
                      <div class="sceneSlider_text">最高</div>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item class="mt80 sceneSlider" label="垂直视角限制" prop="vlookatmin">
                  <el-slider :key="`h_${componentKey2}`" class="mr20 ml20" v-model="vlookat" range :min="-90" :max="90"
                    @change="changeVLookAt">
                  </el-slider>
                  <div class="flex justify-between w-full">
                    <div>
                      <div>
                        <el-input-number class="width50" size="mini" :controls="false" v-model="vlookat[0]" :min="-90"
                          :max="90" @change="changeVLookAt"></el-input-number>
                      </div>
                      <div class="sceneSlider_text">最高</div>
                    </div>
                    <div>
                      <div>
                        <el-input-number class="width50" size="mini" :controls="false" v-model="vlookat[1]" :min="-90"
                          :max="90" @change="changeVLookAt"></el-input-number>
                      </div>
                      <div class="sceneSlider_text">最低</div>
                    </div>
                  </div>
                </el-form-item>
              </div>

              <el-divider></el-divider>
              <div class="flex justify-center">
                <el-button size="small" type="primary" v-points @click="saveSet">
                  保存设置
                </el-button>
              </div>
            </el-form>
          </div>
        </div>
        <!-- 热点设置 -->
        <div v-if="isActive == 1">
          <div class="title">热点设置</div>
          <div class="right_hotSpot">
            <el-collapse v-model="isSpotEdit" accordion>
              <el-collapse-item name="0">
                <template slot="title">
                  <div class="spot_title">
                    热点列表
                    <el-button class="ml20" size="mini" type="primary" @click.stop.prevent="addHotSpot"
                      :disabled="isAddHotSpot && !hotRuleForm.hotspotId">新增热点</el-button>
                  </div>
                </template>
                <flex v-if="hotspotList.length < 1" justify="center" align="center">
                  <el-empty description="暂无热点" :image-size="50"></el-empty>
                </flex>
                <div class="hotSpot_list" :class="isSpotActive == item.hotspotId ? 'isSpotActive' : ''"
                  v-for="item in hotspotList" :key="item.hotspotId" @click.prevent="editSpot(item)">
                  <flex style="height: 100%" align="center">
                    <img :src="getHotSoptImg(item)" />
                    <div class="spot_name">{{ item.hotspot_name }}</div>
                    <div class="spot_style">
                      {{
                        spotTypeList[item.hotspot_type] &&
                        spotTypeList[item.hotspot_type].label
                      }}
                    </div>
                  </flex>
                </div>
              </el-collapse-item>
              <el-collapse-item :disabled="!isAddHotSpot" title="热点编辑" name="1">
                <el-form label-position="top" ref="hotRuleFormRef" label-width="80px" :model="hotRuleForm"
                  :rules="hotRules" @submit.native.prevent>
                  <el-form-item label="热点样式" prop="hotspot_style">
                    <div class="styleList_box">
                      <img :class="item.label == hotRuleForm.hotspot_style
                        ? 'isActiveImg'
                        : ''
                        " :src="item.value" alt="" v-for="(item, index) in styleImgList" :key="index"
                        @click="changeHotSpot(item)" />
                    </div>
                  </el-form-item>
                  <el-form-item label="热点名称" prop="hotspot_name">
                    <el-input size="mini" v-model="hotRuleForm.hotspot_name" @change="setHotSpot_name"></el-input>
                  </el-form-item>
                  <el-form-item label="热点宽度">
                    <el-slider class="ml15 mr15 bsbb slideIp" style="width: 90%" v-model="hotRuleForm.width" :min="0"
                      :max="999" @change="changeSpotSize" show-input :show-input-controls="false"></el-slider>
                  </el-form-item>
                  <el-form-item label="热点高度">
                    <el-slider class="ml20 mr20 bsbb slideIp" style="width: 90%" v-model="hotRuleForm.height" :min="0"
                      :max="999" @change="changeSpotSize" show-input :show-input-controls="false"></el-slider>
                  </el-form-item>
                  <el-form-item label="热点类型" prop="hotspot_type">
                    <el-select style="width: 100%" size="mini" v-model="hotRuleForm.hotspot_type"
                      @change="changeSpotType">
                      <el-option v-for="(item, key) in spotTypeList" :key="key" :label="item.label"
                        :value="item.value" />
                    </el-select>
                  </el-form-item>
                  <el-form-item v-if="hotRuleForm.hotspot_type == 0" label="目标场景" prop="toSceneId">
                    <div class="selectScene_tip" v-if="!hotRuleForm.toSceneId" @click="isSelectScene = true">
                      请选择目标场景
                    </div>
                    <img class="scene_img" v-else :src="tempScene.thumb" alt="" @click="isSelectScene = true" />
                    <flex justify="center" align="center">{{
                      tempScene.scene_name
                    }}</flex>
                  </el-form-item>
                  <el-form-item v-if="hotRuleForm.hotspot_type == 1" label="货架热点" prop="shelfId">
                    <el-select style="width: 100%" size="mini" filterable placeholder="请选择货架热点"
                      v-model="hotRuleForm.shelfId">
                      <el-option v-for="item in shelfList" :key="item.shelfId" :label="item.mc" :value="item.shelfId" />
                    </el-select>
                  </el-form-item>
                  <el-form-item v-if="hotRuleForm.hotspot_type == 2" label="视频链接" prop="videoLink">
                    <el-input size="mini" type="textarea" :rows="3" v-model="hotRuleForm.videoLink"></el-input>
                  </el-form-item>
                  <el-form-item v-if="hotRuleForm.hotspot_type == 3" label="PDF文件" prop="pdfLink">
                    <uploadFile :otherUrl="'Hotspot/uploadPdf'" :fileList="[
                      {
                        name: hotRuleForm.pdfLink
                          ? hotRuleForm.pdfLink[0]
                          : '',
                        url: hotRuleForm.pdfLink
                          ? hotRuleForm.pdfLink[1]
                          : '',
                      },
                    ]" :type="'.pdf'" @setFileUrl="setFileUrl"></uploadFile>
                  </el-form-item>
                </el-form>
                <el-divider></el-divider>
                <flex class="mb25" justify="center" align="center">
                  <el-button class="mr20" size="small" type="primary" @click="saveAddSpot" v-points>
                    保存设置
                  </el-button>
                  <el-button size="small" type="danger" @click.prevent="deleteSpot">
                    删除热点
                  </el-button>
                </flex>
              </el-collapse-item>
            </el-collapse>
          </div>
        </div>
      </el-aside>
    </el-container>
    <!-- 选择场景 -->
    <el-dialog title="选择场景" :visible.sync="isSelectScene" width="50%">
      <selectSceneVue v-if="isAddHotSpot && isSelectScene" :sceneList="sceneList" :toSceneId="hotRuleForm.toSceneId"
        @selectScenData="selectScenData"></selectSceneVue>
    </el-dialog>
  </div>
</template>
<style scoped lang="less"></style>