<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter, useRoute } from "vue-router";
import { get, post } from "@/libs/request";
import { ElMessage, ElMessageBox } from "element-plus";
import { Grid, More } from '@element-plus/icons-vue'
import selectSceneVue from "@/views/space/components/scene/selectScene.vue"
const router = useRouter();
const route = useRoute();
const lMenuList = Object.freeze([
  { title: "基础", icon: Grid },
  { title: "热点", icon: More },
])
const viewTypeList = Object.freeze([
  { value: "自动限制" },
  { value: "限制查看" },
  { value: "只有在这个范围内,允许观察" },
  { value: "但允许放大看到整个图像" },
  { value: "不以任何方式限制缩放" },
])
const controlTypeList = Object.freeze([
  { label: "拖动全景图像", value: "drag" },
  { label: "移动全景图像", value: "moveto" },
])
const spotTypeList = Object.freeze([
  { label: "切换场景热点", value: "CHANGE_SCENE" },
  { label: "视频热点", value: "VIDEO_HOTSPOT" },
])
const styleImgList = Object.freeze([
  {
    label: "skin_prod_list",
    value: new URL("/src/assets/images/skin_prod_list.gif", import.meta.url),
  },
  {
    label: "skin_hotspotstyle_turn_left",
    value: new URL("/src/assets/images/skin_hotspotstyle_turn_left.gif", import.meta.url),
  },
  {
    label: "skin_hotspotstyle_turn_right",
    value: new URL("/src/assets/images/skin_hotspotstyle_turn_right.gif", import.meta.url),
  },
  {
    label: "skin_hotspotstyle_turn_left_back",
    value: new URL("/src/assets/images/skin_hotspotstyle_turn_left_back.gif", import.meta.url),
  },
  {
    label: "skin_hotspotstyle_turn_right_back",
    value: new URL("/src/assets/images/skin_hotspotstyle_turn_right_back.gif", import.meta.url),
  },
  {
    label: "skin_hotspotstyle_arrow",
    value: new URL("/src/assets/images/skin_hotspotstyle_arrow.gif", import.meta.url),
  },
  {
    label: "skin_layerstyle_tip",
    value: new URL("/src/assets/images/skin_layerstyle_tip.png", import.meta.url),
  },
  {
    label: "skin_hotspotstyle",
    value: new URL("/src/assets/images/skin_hotspotstyle.png", import.meta.url),
  },
  {
    label: "skin_hotspotstyle_hotspot",
    value: new URL("/src/assets/images/skin_hotspotstyle_hotspot.gif", import.meta.url),
  },
  {
    label: "skin_hotspotstyle_inspiration",
    value: new URL("/src/assets/images/skin_hotspotstyle_inspiration.gif", import.meta.url),
  },
  {
    label: "skin_hotspotstyle_video",
    value: new URL("/src/assets/images/skin_hotspotstyle_video.gif", import.meta.url),
  },
  {
    label: "skin_hotspotstyle_point",
    value: new URL("/src/assets/images/skin_hotspotstyle_point.jpg", import.meta.url),
  },
  {
    label: "skin_hotspotstyle_door",
    value: new URL("/src/assets/images/skin_hotspotstyle_door.jpg", import.meta.url),
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
  hotspotValue: null,
  ath: "",
  atv: "",
  width: 0,
  height: 0,
});
const hotRules = ref({
  hotspotType: [
    { required: true, message: "请选择热点类型", trigger: "blur" },
  ],
  hotspotValue: [
    { required: true, message: "请选择或填入值", trigger: "blur" },
  ],
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
      xml: `/admin/scene/getXml/${route.query.sceneId}`,
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
    xml: `/admin/scene/getXml/${route.query.sceneId}`,
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
  } else {
    krpanoUtils.value.deleteHotspot(addSpotName.value);
    hotspotList.value.map((item) => {
      krpanoUtils.value.deleteHotspot(item.randomString);
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
  const res = await post("hotspot/getList", {
    sceneId: route.query.sceneId,
  });
  if (res.errCode === 0) {
    hotspotList.value = res.data;
    res.data.map((item) => {
      krpanoUtils.value.addHotspots(
        item.randomString,
        item.hotspotStyle,
        item.hotspotName,
        item.ath,
        item.atv
      );
      if (item.width > 0 && item.height > 0) {
        krpanoUtils.value.registercontentsize(
          item.randomString,
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
  const res = await post("scene/getList", {
    spaceId: ruleForm.value.spaceId,
    sceneId: ruleForm.value.sceneId,
  });
  if (res.errCode === 0) {
    sceneList.value = res.data;
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
  ruleFormRef.value?.validate(async (valid) => {
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
 * @description: 设置hotspotName名称
 */
const setHotSpotName = () => {
  krpanoUtils.value.setHotspotsText(
    addSpotName.value,
    hotRuleForm.value.hotspotName
  );
}
/**
 * @description: 获取选择场景id、封面图
 */
const selectScenData = (e) => {
  hotRuleForm.value.hotspotValue = e.sceneId;
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
  hotRuleFormRef.value?.clearValidate();
  hotRuleForm.value.hotspotValue
}
/**
 * @description: 保存热点新增
 */
const saveAddSpot = () => {
  let param = krpanoUtils.value.getHotspots(addSpotName.value);
  hotRuleForm.value.ath = param.hlookat.toFixed(2);
  hotRuleForm.value.atv = param.vlookat.toFixed(2);
  hotRuleFormRef.value?.validate(async (valid) => {
    if (valid) {
      var res;
      // 新增
      if (!hotRuleForm.value.hotspotId) {
        res = await post("hotspot/add", hotRuleForm.value);
      }
      // 编辑
      else {
        res = await post("hotspot/edit", hotRuleForm.value);
      }
      if (res.errCode === 0) {
        krpanoUtils.value.deleteHotspot(addSpotName.value);
        ElMessage.success("操作成功");
        hotRuleFormRef.value?.resetFields();
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
    krpanoUtils.value.deleteHotspot(item.randomString);
  });
  // 添加当前编辑标签
  krpanoUtils.value.addHotspots(
    item.randomString,
    item.hotspotStyle,
    item.hotspotName,
    item.ath,
    item.atv
  );
  // 设置当前编辑标签大小
  if (item.width > 0 && item.height > 0) {
    krpanoUtils.value.registercontentsize(
      item.randomString,
      item.width,
      item.height
    );
  }
  isSpotActive.value = item.hotspotId;
  krpanoUtils.value.setViewLookat(item.ath, item.atv);
  hotRuleForm.value = item;
  addSpotName.value = item.randomString;
  if (item.hotspotValue) {
    sceneList.value.map((data) => {
      if (data.sceneId == item.hotspotValue) {
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
    hotspotId: null,
    hotspotName: "",
    sceneId: String(route.query.sceneId),
    hotspotStyle: "skin_prod_list",
    hotspotType: null,
    hotspotValue: null,
    ath: "",
    atv: "",
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
    if (item.randomString == name) {
      item.ath = ath.toFixed(2);
      item.atv = atv.toFixed(2);
      num = index;
    }
  });
  const res = await post("Hotspot/edit", hotspotList.value[num]);
  krpanoUtils.value.deleteHotspot(hotspotList.value[num].randomString);
  krpanoUtils.value.addHotspots(
    hotspotList.value[num].randomString,
    hotspotList.value[num].hotspotStyle,
    hotspotList.value[num].hotspotName,
    hotspotList.value[num].ath,
    hotspotList.value[num].atv
  );
  if (hotspotList.value[num].width > 0 && hotspotList.value[num].height > 0) {
    krpanoUtils.value.registercontentsize(
      hotspotList.value[num].randomString,
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
const getHotSoptImg = (item) => {
  return styleImgList.find((value) => {
    return item.hotspotStyle == value.label
  })?.value
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
    let item = hotspotList.value.filter((item) => item.randomString == e);
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
        <div class="back itemBtn" @click="router.back()">
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
                <el-select v-model="ruleForm.spaceId" placeholder="请选择空间分类">
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
                  <el-option v-for="(item, key) in viewTypeList" :key="key" :value="item.value" />
                </el-select>
              </el-form-item>
              <div v-if="ruleForm.limitview !== '自动限制'" :class="ruleForm.limitview === '自动限制' ? '' : 'mb90'">
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
                <template #title>
                  <div class="spot_title">
                    热点列表
                    <el-button class="ml20" size="mini" type="primary" @click.stop.prevent="addHotSpot"
                      :disabled="isAddHotSpot && !hotRuleForm.hotspotId">新增热点</el-button>
                  </div>
                </template>
                <div v-if="hotspotList.length < 1" class="flex justify-center">
                  <el-empty description="暂无热点" :image-size="50"></el-empty>
                </div>
                <div class="hotSpot_list" :class="isSpotActive == item.hotspotId ? 'isSpotActive' : ''"
                  v-for="item in hotspotList" :key="item.hotspotId" @click.prevent="editSpot(item)">
                  <div style="height: 100%" class="flex justify-center items-center">
                    <img :src="getHotSoptImg(item)" />
                    <div class="spot_name">{{ item.hotspotName }}</div>
                    <div class="spot_style">
                      {{
                        spotTypeList[item.hotspotType] &&
                        spotTypeList[item.hotspotType].label
                      }}
                    </div>
                  </div>
                </div>
              </el-collapse-item>
              <el-collapse-item :disabled="!isAddHotSpot" title="热点编辑" name="1">
                <el-form label-position="top" ref="hotRuleFormRef" label-width="80px" :model="hotRuleForm"
                  :rules="hotRules" @submit.native.prevent>
                  <el-form-item label="热点样式" prop="hotspotStyle">
                    <div class="styleList_box">
                      <img :class="item.label == hotRuleForm.hotspotStyle
                        ? 'isActiveImg'
                        : ''
                        " :src="item.value" v-for="(item, index) in styleImgList" :key="index"
                        @click="changeHotSpot(item)" />
                    </div>
                  </el-form-item>
                  <el-form-item label="热点名称" prop="hotspotName">
                    <el-input size="mini" v-model="hotRuleForm.hotspotName" @change="setHotSpotName"></el-input>
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
                    <el-select style="width: 100%" size="mini" v-model="hotRuleForm.hotspotType"
                      @change="changeSpotType">
                      <el-option v-for="(item, key) in spotTypeList" :key="key" :label="item.label"
                        :value="item.value" />
                    </el-select>
                  </el-form-item>
                  <el-form-item v-if="hotRuleForm.hotspotType == 'CHANGE_SCENE'" label="目标场景" prop="hotspotValue">
                    <div class="selectScene_tip" v-if="!hotRuleForm.hotspotValue" @click="isSelectScene = true">
                      请选择目标场景
                    </div>
                    <img class="scene_img" v-else :src="tempScene.thumb" alt="" @click="isSelectScene = true" />
                    <div class="flex justify-center align-center">{{
                      tempScene.scenename
                    }}</div>
                  </el-form-item>
                  <el-form-item v-if="hotRuleForm.hotspotType == 'VIDEO_HOTSPOT'" label="视频链接" prop="hotspotValue">
                    <el-input size="mini" type="textarea" :rows="3" v-model="hotRuleForm.hotspotValue"></el-input>
                  </el-form-item>
                </el-form>
                <el-divider></el-divider>
                <div class="mb25 flex justify-center align-center">
                  <el-button class="mr20" size="small" type="primary" @click="saveAddSpot" v-points>
                    保存设置
                  </el-button>
                  <el-button size="small" type="danger" @click.prevent="deleteSpot">
                    删除热点
                  </el-button>
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
        </div>
      </el-aside>
    </el-container>
    <!-- 选择场景 -->
    <el-dialog title="选择场景" v-model="isSelectScene" width="50%">
      <selectSceneVue v-if="isAddHotSpot && isSelectScene" :sceneList="sceneList"
        :hotspotValue="hotRuleForm.hotspotValue" @selectScenData="selectScenData"></selectSceneVue>
    </el-dialog>
  </div>
</template>
<style scoped lang="less"></style>