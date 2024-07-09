<template>
    <div id="pano">

    </div>
</template>

<script setup lang="ts">
import { get } from "~/src/libs/request";
import { ElMessage } from "element-plus";
const detail = ref({});
const route = useRoute();
async function getDeatil() {
    const res = await get(`space/detail/${route.params.id}`);
    if (res.errCode === 0) {
        detail.value = res.data;
        // embedpano({
        //     swf: "/scene/tour.swf",
        //     xml: `/scene/tour.xml?t=` + Date.parse(new Date()),
        //     target: "showPano",
        //     html5: "auto",
        //     mobilescale: 1.0,
        //     passQueryParameters: true,
        //     webglsettings: {
        //         preserveDrawingBuffer: true,
        //     },
        // });
    } else {
        ElMessage.error(res.errMsg)
    }
    console.log(res, "--res")
}
onMounted(() => {
    getDeatil();
})
</script>

<style scoped></style>