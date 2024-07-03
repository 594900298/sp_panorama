<template>
    <div class="selectScene">
        <flex class="tips" v-if="sceneList && sceneList.length < 1" justify="center" align="center">暂无其他场景</flex>
        <div class="sceneImg mb10" @dblclick="saveSet" :class="isScene == index ? 'isSceneImg' : ''" v-else
            v-for="(itme, index) in sceneList" :key="index" @click="selectSceneFn(index)">
            <img :src="itme.thumb">
            <div class="scene_name">{{ itme.scene_name }}</div>
        </div>
        <el-divider></el-divider>
        <flex style="width: 100%" justify="end" align="center">
            <el-button class="mr20" size="small" type="primary" v-points @click="saveSet"> 保存设置 </el-button>
        </flex>
    </div>
</template>

<script setup lang="ts">
import { ref,onMounted } from 'vue';
const props = withDefaults(defineProps<{
    sceneList: Array,
    toSceneId: number | String,
}>(), {
    sceneList:[],
    toSceneId:undefined,
})
const emits = defineEmits(["selectScenData"]);
const isScene = ref(null);


/**
* @description: 选择场景
* @param index 索引
*/
function selectSceneFn(index) {
    isScene.value = index
}
/**
 * @description: 保存选择场景
 */
function saveSet() {
    emits('selectScenData', props.sceneList[isScene.value])
}
onMounted(() => {
    if (props.toSceneId && props.sceneList) {
        props.sceneList.map((item, index) => {
            if (item.scene_id == props.toSceneId) {
                isScene.value = index
            }
        })
    }
})
</script>

<style scoped>
.selectScene {
    display: flex;
    flex-wrap: wrap;
    border-top: 1px solid #ededed;
    padding: 20px 20px 0;
}

.tips {
    height: 200px;
}

.sceneImg {
    width: 100px;
    height: 130px;
    object-fit: contain;
    margin-right: 20px;
    text-align: center;
    cursor: pointer;
    border: 2px solid #fff;
    padding: 10px 10px 0;
}

.sceneImg .scene_name {
    height: 30px;
    line-height: 30px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.sceneImg img {
    /* display: block;
    margin: 0 auto; */
    width: 100%;
    height: 100px;
    object-fit: contain;
    vertical-align: middle;
}

.isSceneImg {
    border: 2px solid #409EFF;
}

.sceneImg:hover {
    border: 2px solid #409EFF;
}
</style>