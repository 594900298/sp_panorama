<template>
  <div>
    <NuxtPage />
  </div>
</template>
<script setup>
useHead({
  script: [
    { src: '/js/tour.js', body: true },
    { src: '/js/krpanoUtils1.1.js', body: true },
  ]
})
</script>
