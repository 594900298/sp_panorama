<template>
    <div class="home-container">
        <div class="header-wrapper">
            <img src="../public/images/logo.png">
            <div class="nav-wrapper">
                <nuxt-link to="/">首页</nuxt-link>
                <nuxt-link to="/">关于我们</nuxt-link>
                <nuxt-link to="/">产品中心</nuxt-link>
                <nuxt-link to="/">新闻资讯</nuxt-link>
                <nuxt-link to="/">联系我们</nuxt-link>
            </div>
        </div>
        <div class="main-wrapper">
            <UCarousel v-slot="{ item }" :items="list" :ui="{
                item: 'basis-full',
                container: 'rounded-lg'
            }" :prev-button="{
                color: 'gray',
                icon: 'i-heroicons-arrow-left-20-solid',
                class: '-start-12'
            }" :next-button="{
                color: 'gray',
                icon: 'i-heroicons-arrow-right-20-solid',
                class: '-end-12'
            }" arrows class="w-1/2 mx-auto">
                <nuxt-link :to="`/space/${item.spaceId}`">
                    <img :src="item.spaceThumb" class="w-full" draggable="false">
                </nuxt-link>
            </UCarousel>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { post } from "~/src/libs/request";
import { ElMessage } from "element-plus";
useSeoMeta({
    title: '半夏全景VR全景官网-3DVR全景拍摄制作软件平台',
    ogTitle: '半夏全景VR全景官网-3DVR全景拍摄制作软件平台',
    description: '半夏全景是技术领先的3DVR全景软件网站，为创作者和企业提供360度VR全景拍摄、VR全景制作、VR全景地图、VR全景图片、VR全景视频等展示、分发、技术支持、内容定制的一站式服务，助力商企提升品牌宣传和营销转化效果。',
    ogDescription: '半夏全景是技术领先的3DVR全景软件网站，为创作者和企业提供360度VR全景拍摄、VR全景制作、VR全景地图、VR全景图片、VR全景视频等展示、分发、技术支持、内容定制的一站式服务，助力商企提升品牌宣传和营销转化效果。',
})
const list = ref([]);
async function getList() {
    const res = await post(`space/getList`);
    if (res.errCode === 0) {
        list.value = res.data;
    } else {
        ElMessage.error(res.errMsg)
    }
}
onMounted(() => {
    getList();
})
</script>

<style scoped lang="less">
.home-container {
    background: #fefaee;
    height: 100vh;
    overflow: auto;

    .header-wrapper {
        display: flex;
        align-items: center;
        width: 100%;
        padding: 0 30px;
        height: 120px;
        background: #fefaee;
        border-bottom: 20px solid #ffffff;
        box-sizing: border-box;

        img {
            width: 140px;
        }

        .nav-wrapper {
            display: flex;
            align-items: center;
            margin-left: 60px;

            a {
                display: inline-block;
                padding: 0 30px;
                line-height: 60px;
                color: #333333;
                font-size: 18px;
                font-weight: 600;
                text-decoration: none;
            }
        }
    }

    .main-wrapper {
        display: flex;
        align-items: center;
        height: calc(100vh - 120px);
    }
}
</style>