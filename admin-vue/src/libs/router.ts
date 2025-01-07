/**
 * 路由配置文件
 */
import type { RouteRecordRaw } from "vue-router";
const routers: RouteRecordRaw[] = [
    // 登陆界面
    {
        path: '/',
        meta: {
            showContainer: false,
            notAuth: true,
        },
        component: () => import('@/views/login/index.vue')
    },
    // 路由规则
    {
        path: '/rules',
        component: () => import('@/views/rules/index.vue')
    },
    // 角色
    {
        path: '/role',
        component: () => import('@/views/role/index.vue')
    },
    // 管理员
    {
        path: '/admin',
        component: () => import('@/views/admin/index.vue')
    },
    // 商品管理
    {
        path: '/space',
        component: () => import('@/views/space/index.vue')
    },
    {
        path: '/scene',
        meta: {
            showContainer: false,
        },
        component: () => import('@/views/space/components/scene/edit.vue')
    },
    // 区域管理
    {
        path: '/area',
        component: () => import('@/views/area/index.vue')
    },
    // 轮播管理
    {
        path: '/banner',
        component: () => import('@/views/banner/index.vue')
    },
];
//最后推入一个miss路由
routers.push(
    {
        path: "/:w+",
        component: () => import("@/views/other/missRouter.vue"),
    }
);
export { routers }