<template>
  <div>
    <NuxtPage />
  </div>
</template>
<script setup>
useHead({
  script: [
    { src: '/js/tour.js', body: true },
  ]
})
useSeoMeta({
    title: '半夏全景VR全景官网-3DVR全景拍摄制作软件平台',
    ogTitle: '半夏全景VR全景官网-3DVR全景拍摄制作软件平台',
    description: '半夏全景是技术领先的3DVR全景软件网站，为创作者和企业提供360度VR全景拍摄、VR全景制作、VR全景地图、VR全景图片、VR全景视频等展示、分发、技术支持、内容定制的一站式服务，助力商企提升品牌宣传和营销转化效果。',
    ogDescription: '半夏全景是技术领先的3DVR全景软件网站，为创作者和企业提供360度VR全景拍摄、VR全景制作、VR全景地图、VR全景图片、VR全景视频等展示、分发、技术支持、内容定制的一站式服务，助力商企提升品牌宣传和营销转化效果。',
})
</script>
