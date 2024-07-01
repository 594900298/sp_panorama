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
</div>
<div align="center">

[comment]: <> ([宽屏预览]&#40;https://gitee.com/jiang_zhenhua/sp_panorama/blob/master/README.md&#41;)

</div>
<div align="center" >
    <a href="https://gitee.com/jiang_zhenhua/sp_panorama/blob/master/README.md">宽 屏 预 览</a>
</div>
<div align="center">
    如果对您有帮助，您可以点右上角 "Star" ❤️ 支持一下 谢谢！
</div>

### 📖 简介：

半夏全景商城系统Java版，基于Java+Vue+Uni-app开发，在微信公众号、小程序、H5移动端都能使用，代码全开源无加密，独立部署，二开很方便，还支持免费商用，能满足企业新零售多种经营需求，自用、做二开项目都很合适。

### 💻 运行环境及框架：
~~~
1. 接口端已经构建了dockerfile 和 docker-compose 建议使用docker-compose进行部署方便快捷
2. 后台服务 Java SpringBoot + Mybatis-plus + Mysql + redis + RabbitMQ + MongoDB
3. 运行环境 linux和windows等都支持,只要有Java环境和对应的数据库 redis
4. 运行条件 Java 1.8 Mysql5.7 node18
5. Api对应的接口文档也可以部署项目后查看    域名/doc.html 查看【knife4j生成】
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
~~~
#### 前端依赖
~~~
*   Vue3      
*   ElementPlusUi 
*   TypeScript
~~~

---

### 🧭 项目代码包介绍
~~~
├─application               应用目录
│  ├─sp_common              公共模块
│  ├─sp_admin               管理员模块【默认端口8080】
│  └─sp_api                 用户模块 【默认端口8081】
├─admin-vue                 管理后台(PC端管理端 VUE + ElementUi)
│  ├─src                    核心包
│  │  ├─libs                公告模块
|  |  |  ├─app.config.ts    配置文件              
├─mini-program              小程序(UniApp标准开发(H5 + 微信小程序))
│  ├─src                    核心包
│  │  ├─libs                公告模块
|  |  |  ├─app.config.js    配置文件   
├─baseXml                   baseXML
├─krpano                    krpano
├─stitic                    静态素材
│  ├─scene                  全景素材
└─mysql                     数据库备份文件
~~~

### 🎬 系统演示：
后台账号： admin/123123

### 📷 项目截图：
![](demo/demo.png)