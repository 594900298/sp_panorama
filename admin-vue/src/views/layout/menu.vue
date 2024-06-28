<script setup lang="ts">
import appConfig from "@/libs/app.config";
import {defineProps} from "vue";
import { useRouter } from "vue-router";
const menuList = appConfig.menuList;
const props = defineProps({
    collapse:Boolean
})
const router = useRouter();
//点击导航跳转
const goNav = (path:string)=>{
    router.push({ path: path });
}
</script>
<template>
  <div class="menu">
    <el-menu class="el-menu-vertical-demo" background-color="#1e222d" text-color="#fff" active-text-color="#fff" :collapse="props.collapse">
        <template v-for="(item,index) in menuList" :key="index">
            <!-- 有二级 -->
            <el-sub-menu :index="index" v-if="item.children">
                <template #title>
                    <i :class="item.icon"></i>
                    <span>{{item.name}}</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item :index="index+'-'+i" v-for="(v,i) in item.children" :key="i" @click="goNav(v.path)">{{v.name}}</el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>
            <!-- 无二级 -->
            <el-menu-item v-else>
                <template #title >
                    <div @click="goNav(item.path)">
                        <i :class="item.icon"></i>
                        {{item.name}}
                    </div>
                </template>
            </el-menu-item>
        </template>
    </el-menu>
  </div>
</template>


