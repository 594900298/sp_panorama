<div align="center">

# 半夏全景

</div>
<div align="center" >
    <a href="javascript:;">
        <img src="https://img.shields.io/:license-MIT-blue.svg" />
    </a>
    <a href='https://gitee.com/jiang_zhenhua/sp_panorama/stargazers'>
        <img src='https://gitee.com/jiang_zhenhua/sp_panorama/badge/star.svg?theme=dark' alt='star'></img>
    </a>
    <a href='https://gitee.com/jiang_zhenhua/sp_panorama/members'>
        <img src='https://gitee.com/jiang_zhenhua/sp_panorama/badge/fork.svg?theme=dark' alt='fork'></img>
    </a>
    <a href='https://gitcode.com/Truth___/sp_panorama/stargazers'>
        <img src='https://gitcode.com/Truth___/sp_panorama/star/badge.svg?theme=dark' alt='star'>
    </a>
</div>
<div align="center">

[comment]: <> ([宽屏预览]&#40;https://gitee.com/jiang_zhenhua/sp_panorama/blob/master/README.md&#41;)

</div>
<div align="center" >
    <a href="https://gitee.com/jiang_zhenhua/sp_panorama/blob/master/README.md">宽 屏 预 览 文 档</a>
</div>
<div align="center" >
    <a href="https://panorama.banxia.website/">效 果 展 示</a>
    &nbsp
    <a href="https://panorama.banxia.website/space/5">内 页 展 示</a>
</div>
<div align="center">
    如果对您有帮助，您可以点右上角 "Star" ❤️ 支持一下 谢谢！
</div>

### 📖 简介：

半夏全景商城系统Java版，基于Java+Vue+TS开发，在微信公众号、小程序、H5移动端都能使用，代码全开源无加密，独立部署，二开很方便，还支持免费商用，能满足企业新零售多种经营需求，自用、做二开项目都很合适。

### 💻 运行环境及框架：
~~~
1. 后台服务 Java SpringBoot + Mybatis-plus + Mysql + redis + RabbitMQ + MongoDB
2. 运行环境 linux和windows等都支持,只要有Java环境和对应的数据库 redis
3. 运行条件 Java 1.8 Mysql5.7 node18.20.2
4. Api对应的接口文档也可以部署项目后查看    域名/doc.html 查看【knife4j生成】
~~~

### 🔧 Java项目框架 和 项目运行环境
#### 后端依赖
~~~
*   SpringBoot 2.7.13 
*   Mybatis Plus LATEST
*   knife4j
*   Redis
*   SLF4j+log4j
*   Redis
*   Fastjson
*   Lombok
*   knife4j
*   krpano-1.19 
~~~
#### 前端依赖
~~~
*   Vue3      
*   ElementPlusUi 
*   TypeScript
*   NUXT
~~~

---

### 🧭 项目代码包介绍
~~~
├─application               应用目录
|  ├─krpano                 krpano
|  ├─stitic                 静态素材
│  ├─scene                  全景素材
│  ├─sp_common              公共模块
│  ├─sp_admin               管理员模块【默认端口8080】
│  └─sp_api                 用户模块 【默认端口8081】
├─admin-vue                 管理后台(PC端管理端 VUE + ElementUi)
│  ├─src                    核心包
│  │  ├─libs                公告模块
|  |  |  ├─app.config.ts    配置文件              
├─front-vue                 使用nuxt 开发的用户端
│  ├─src                    核心包
│  │  ├─libs                公告模块
|  |  |  ├─app.config.js    配置文件   
└─mysql                     数据库备份文件
~~~

### 📝 使用说明：
* 后端
    * 1. 导入数据库
    * 2. 修改数据库连接信息
    * 3. 导入application文件夹到IDEA
    * 4. 安装Maven相关依赖【一版情况下IDEA会完成扫描】
    * 5. DEBUG启动
* 前端
    * admin-vue和front-vue都是前端项目,npm install 后直接npm run dev启动


### 🎬 系统演示：
* 后台账号： admin/123123【默认密码】【线上预览密码不提供,慎防被攻击~抱歉】

### 📷 项目截图：
![](demo/demo.png)
### Nginx部署参考构建目录
![](demo/dir.jpg)
### Nginx部署配置
![](demo/nginx.png)
### 激活码
* <a href="https://blog.csdn.net/qq_31708763/article/details/86561952">激活方法</a>
* krpano-1.19-pr16 【仅供学习使用】
    * FXsqTqaGNSZER5dSETEm+VzQEh9sWSa5DZMFsSmMxYV9GcXs8W3R8A/mWXrGNUceXvrihmh28hfRF1ivrW0HMzEychPvNiD8B/4/ZzDaUE9Rh6Ig22aKJGDbja1/kYIqmc/VKfItRE2RTSOIbIroxOtsz626NIpxWksAAifwhpNwuPXqDQpz2sRUMBzoPqZktpkItoSenN2mKd8Klfx7pOuB6CIK3e1CDXgyndqOt2mWybLZcU/wfJVAecfxk15ghiqrzaDsbqrdABDowg==
### 提供一些用于测试全景图片
* 如果这些图片侵权了，请联系我删除
![](demo/example-1.jpg)
![](demo/example-2.jpg)
![](demo/example-2.jpg)
### 更新记录
* 增加小行星配置，移除废弃代码，修复已知bug
    * 新增sql
~~~
ALTER TABLE `sp_panorama`.`sp_space` 
ADD COLUMN `littleplanetintro` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否显示小行星介绍 0-否 1-是' AFTER `is_show`;
~~~
