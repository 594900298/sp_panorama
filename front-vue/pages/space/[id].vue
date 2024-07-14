<template>
    <div class="page">
        <div id="pano">
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
        <div class="left-menu">
            <div @click="router.push('/')">
                <img src="../../public/images/vr_home.png" alt="HOME">
                <p>HOME</p>
            </div>
            <div>
                <img src="../../public/images/vr_back.png" alt="BACK">
                <p>BACK</p>
            </div>
        </div>
        <div class="right-menu">
            <div>
                <img src="../../public/images/vr_music.png" alt="MUSIC">
                <p>MUSIC</p>
            </div>
            <div>
                <img src="../../public/images/vr_viewPort.png" alt="VIEWPORT">
                <p>VIEWPORT</p>
            </div>
        </div>
        <Transition>
            <div class="retract-buttom-menu" v-if="isShowButtomMenu">
                <img src="../../public/images/vr_arrow.png" alt="ARROW" @click="isShowButtomMenu = !isShowButtomMenu">
                <Swiper class="swiper-wrapper" :slides-per-view="'auto'" :centerInsufficientSlides="true">
                    <swiper-slide v-for="(value, index) in detail.sceneListVO" :key="index" class="item"
                        @click="linkedsceneClick(value.randomString)"
                        :class="{ 'active': value.randomString == curRandomString }">
                        <img :src="value.thumb" :alt="value.sceneName" />
                        <div class="text">{{ value.sceneName }}</div>
                    </swiper-slide>
                </Swiper>
            </div>
            <div class="expand-buttom-menu" v-else @click="isShowButtomMenu = !isShowButtomMenu">
                <img src="../../public/images/vr_arrow.png" alt="ARROW">
                <div>场景选择</div>
            </div>
        </Transition>
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { get } from "~/src/libs/request";
import { ElMessage } from "element-plus";
import { useRouter, useRoute } from "vue-router";
import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
import { KrpanoUtilsClass } from "~/public/js/krpanoUtils1.1";
import 'swiper/css'
let krpanoUtils: any = null;
const isShowButtomMenu = ref<boolean>(true);
const detail = ref<{
    sceneListVO: Array<{
        sceneName: string,
        thumb: string,
        randomString: string,
    }>
}>({
    sceneListVO: []
});
const route = useRoute();
const router = useRouter();
const curRandomString = ref<String>("");
/**
 * 获取详情
 */
async function getDeatil() {
    const res = await get(`space/detail/${route.params.id}`);
    if (res.errCode === 0) {
        detail.value = res.data;
        embedpano({
            swf: `/static/scene/tour.swf`,
            xml: `http://localhost:8080/api/space/getXml/${route.params.id}`,
            target: "pano",
            html5: "auto",
            mobilescale: 1.0,
            passQueryParameters: true,
        });
        nextTick(() => {
            krpanoUtils = new KrpanoUtilsClass();
            window.linkedsceneClick = linkedsceneClick
            if (detail.value.sceneListVO.length) {
                linkedsceneClick(detail.value.sceneListVO[0].randomString);
            }
        })
    } else {
        ElMessage.error(res.errMsg)
    }
}
/**
 * 点击热点跳转
 * @param randomString 
 */
function linkedsceneClick(randomString: String) {
    curRandomString.value = randomString;
    krpanoUtils.loadscene(randomString);
}
onMounted(() => {
    getDeatil();
})
</script>

<style scoped lang="less">
.page {
    position: relative;

    #pano {
        width: 100%;
        height: 100vh;
    }

    .left-menu {
        position: absolute;
        top: 16px;
        left: 22px;
        display: flex;
        align-items: center;
        padding: 10px 0;
        border-radius: 10px;
        background: rgba(0, 0, 0, 0.3);

        div {
            cursor: pointer;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 0 20px;
            color: #FFFFFF;

            img {
                width: 30px;
                height: 30px;
            }
        }
    }

    .right-menu {
        position: absolute;
        top: 16px;
        right: 22px;
        display: flex;
        align-items: center;
        padding: 10px;
        border-radius: 10px;
        background: rgba(0, 0, 0, 0.3);

        div {
            cursor: pointer;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 0 20px;
            color: #FFFFFF;

            img {
                width: 30px;
                height: 30px;
            }
        }
    }

    .retract-buttom-menu {
        position: absolute;
        bottom: 0;
        display: flex;
        align-items: center;
        flex-direction: column;
        width: 100%;
        padding: 10px;
        background: rgba(0, 0, 0, 0.3);
        color: #FFFFFF;

        >img {
            cursor: pointer;
            position: absolute;
            top: -30px;
            width: 40px;
            height: 20px;
            transform: rotate(180deg);
        }

        .swiper-wrapper {
            width: 80%;
            display: flex;
            justify-content: center;
            overflow-x: scroll;

            .item {
                width: 100px;
                height: 100px;
                flex-shrink: 0;
                position: relative;
                border-radius: 5px;
                overflow: hidden;
                display: flex;
                flex-direction: column;
                align-items: center;
                margin: 0 10px;
                cursor: pointer;
                box-sizing: border-box;
                border: 2px solid #fff;

                .text {
                    background-color: rgba(0, 0, 0, .5);
                    bottom: 0;
                    height: 18px;
                    left: 0;
                    position: absolute;
                    width: 100%;
                    text-align: center;
                    line-height: normal;
                    white-space: nowrap;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    font-size: 12px;
                }
            }

            .active {
                border-color: rgb(40, 110, 250);
            }
        }

        .swiper-wrapper::-webkit-scrollbar {
            width: 0 !important
        }
    }
}

.expand-buttom-menu {
    position: absolute;
    bottom: 0;
    display: flex;
    align-items: center;
    flex-direction: column;
    width: 100%;
    padding: 10px;
    background: rgba(0, 0, 0, 0.3);
    color: #FFFFFF;

    >img {
        cursor: pointer;
        width: 40px;
        height: 20px;
    }
}
</style>