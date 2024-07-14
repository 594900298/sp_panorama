/**
* krpano 助手类
*/
export class KrpanoUtilsClass {
    krpano;
    /**
     * 构造方法
     * @param {*} id 
     */
    constructor(id = 'krpanoSWFObject') {
        this.krpano = document.getElementById(id)
    }

    /**
     * 获取当前视角中心点
     * @returns 
     */
    getCenter() {
        let hlookat = this.krpano.get('view.hlookat');
        let vlookat = this.krpano.get('view.vlookat');
        return {
            "hlookat": hlookat,
            "vlookat": vlookat,
        };
    }

    /**
     * 添加可以拖拽的热点
     * 需要xml的拖拽action支持
     * @param {*} spotname 不能数字开头
     * @param {*} style [
     * 
     * skin_prod_list
     * skin_hotspotstyle_turn_left
     * skin_hotspotstyle_turn_right
     * skin_hotspotstyle_turn_left_back
     * skin_hotspotstyle_turn_right_back
     * skin_hotspotstyle_arrow,
     * skin_layerstyle_tip,
     * skin_hotspotstyle
     * skin_hotspotstyle_hotspot
     * skin_hotspotstyle_inspiration
     * ]
     * @param {*} tiptext 
     * @param {*} dragable 
     * 
     <!--    拖拽-->
     <action name="draghotspot">
            spheretoscreen(ath, atv, hotspotcenterx, hotspotcentery, calc(mouse.stagex LT stagewidth/2 ? 'l' : 'r'));
            sub(drag_adjustx, mouse.stagex, hotspotcenterx);
            sub(drag_adjusty, mouse.stagey, hotspotcentery);
            asyncloop(pressed,
                sub(dx, mouse.stagex, drag_adjustx);
                sub(dy, mouse.stagey, drag_adjusty);
                screentosphere(dx, dy, ath, atv);
                js(listenerDragHotSpot(get('xml.scene'),get('name'),get(ath),get(atv)))
              );
        </action>
 
    <!--    隐藏热点文字提示-->
    <action name="hide_tooltip">
        txtadd(tooltipname, 'tooltip_', get(name));
        set(plugin[get(tooltipname)].alpha,0);
    </action>
 
    <!--    显示热点文字提示 注意swf位置-->
    <action name="show_tooltip">
        txtadd(tooltipname, 'tooltip_', get(name));
        addplugin(get(tooltipname));
        txtadd(plugin[get(tooltipname)].parent, 'hotspot[', get(name), ']');
        set(plugin[get(tooltipname)].url,'../plugins/textfield.swf');
        set(plugin[get(tooltipname)].alpha,1);
        set(plugin[get(tooltipname)].align,top);
        set(plugin[get(tooltipname)].edge,bottom);
        set(plugin[get(tooltipname)].x,0);
        set(plugin[get(tooltipname)].y,-20);
        set(plugin[get(tooltipname)].autowidth,true);
        set(plugin[get(tooltipname)].autoheight,true);
        set(plugin[get(tooltipname)].vcenter,true);
        set(plugin[get(tooltipname)].background,true);
        set(plugin[get(tooltipname)].backgroundcolor,0x000000);
        set(plugin[get(tooltipname)].roundedge,5);
        set(plugin[get(tooltipname)].backgroundalpha,0.65);
        set(plugin[get(tooltipname)].css,'text-align:center; padding: 5px 10px; color:#FFFFFF;
        font-family:MicrosoftYahei;
        font-size:16px;');
        copy(plugin[get(tooltipname)].html,tiptext)
    </action>        
 
     */
    addHotspots(spotname, style = "skin_prod_list", tiptext = '', hlookat = '', vlookat = '', dragable = true) {
        if (!hlookat.length) {
            hlookat = this.krpano.get('view.hlookat');
        }
        if (!vlookat.length) {
            vlookat = this.krpano.get('view.vlookat');
        }
        this.krpano.call("addhotspot(" + spotname + ");");
        this.krpano.call("set(hotspot[" + spotname + "].ath," + hlookat + ");");
        this.krpano.call("set(hotspot[" + spotname + "].atv," + vlookat + ");");
        if (dragable) {
            this.krpano.call("set(hotspot[" + spotname + "].ondown,draghotspot(););");
        }
        if (tiptext.length) {
            this.krpano.call("set(hotspot[" + spotname + "].tiptext," + tiptext + ");");
            this.krpano.call("set(hotspot[" + spotname + "].onover,show_tooltip(););");
            this.krpano.call("set(hotspot[" + spotname + "].onout,hide_tooltip(););");
        }
        this.krpano.call("hotspot[" + spotname + "].loadstyle(" + style + ");");
    }

    /**
     * 修改热点大小
     * @param {*} spotname 
     * @param {*} imagewidth 
     * @param {*} imageheight 
     */
    registercontentsize(spotname, imagewidth, imageheight) {
        this.krpano.call("hotspot[" + spotname + "].registercontentsize(" + imagewidth + "," + imageheight + ");");
    }

    /**
     * 重置热点大小  
     * @param {*} spotname 
     */
    resetsize(spotname) {
        this.krpano.call("hotspot[" + spotname + "].resetsize();");
    }

    /**
     * 设置热点悬浮文字
     * @param {*} spotname 
     * @param {*} tiptext 
     */
    setHotspotsText(spotname, tiptext) {
        this.krpano.set("hotspot[" + spotname + "].tiptext", tiptext);
        if (tiptext.length) {
            this.krpano.set("hotspot[" + spotname + "].onover", "show_tooltip();");
            this.krpano.set("hotspot[" + spotname + "].onout", "hide_tooltip();");
        } else {
            this.krpano.set("hotspot[" + spotname + "].onover", "");
            this.krpano.set("hotspot[" + spotname + "].onout", "");
        }
    }

    /**
         * 切换样式热点
         * skin_prod_list
         * skin_hotspotstyle_turn_left
         * skin_hotspotstyle_turn_right
         * skin_hotspotstyle_turn_left_back
         * skin_hotspotstyle_turn_right_back
         * skin_hotspotstyle_arrow,
         * skin_layerstyle_tip,
         * skin_hotspotstyle
     * @param {*} spotname 
     * @param {*} style 
     */
    setHotspotsLoadstyle(spotname, style) {
        this.krpano.call("hotspot[" + spotname + "].loadstyle(" + style + ");")
    }

    /**
     * 获取热点坐标
     * @param {*} spotname 
     * @returns 
     */
    getHotspots(spotname) {
        let hlookat = this.krpano.get("hotspot[" + spotname + "].ath") //全景中球体坐标的水平位置
        let vlookat = this.krpano.get("hotspot[" + spotname + "].atv") //全景中球体坐标的垂直位置
        return {
            "hlookat": hlookat,
            "vlookat": vlookat,
        };
    }

    /**
     * 删除热点
     * @param {*} spotname 
     */
    deleteHotspot(spotname) {
        this.krpano.call("removehotspot(" + spotname + ");")
    }

    /**
     * 锁定视角
     * @param {*} hlookatmin 
     * @param {*} hlookatmax 
     * @param {*} vlookatmin 
     * @param {*} vlookatmax 
     * @param {*} limitview 
     */
    setViewLoock(hlookatmin, hlookatmax, vlookatmin, vlookatmax, limitview = 'lookat') {
        this.krpano.set("view.limitview", limitview); //视角限制类型
        this.krpano.set("view.hlookatmin", hlookatmin); //水平最小 默认 -180
        this.krpano.set("view.hlookatmax", hlookatmax); //水平最大 默认  180
        this.krpano.set("view.vlookatmin", vlookatmin); //垂直最小 默认  90
        this.krpano.set("view.vlookatmax", vlookatmax); //垂直最大 默认 -90
    }

    /**
     * 获取当前视角
     * 可以用于获取当前视角帮助设定起始视角
     */
    getViewLookat() {
        let hlookat = this.krpano.get('view.hlookat');
        let vlookat = this.krpano.get('view.vlookat');
        return {
            "hlookat": hlookat,
            "vlookat": vlookat,
        };
    }

    /**
     * 设置视角
     * @param {*} hlookat 
     * @param {*} vlookat 
     */
    setViewLookat(hlookat, vlookat) {
        this.krpano.set('view.hlookat', hlookat);
        this.krpano.set('view.vlookat', vlookat);
    }

    /**
     * 切换场景
     * @param {*} scenename 
     */
    loadscene(scenename) {
        this.krpano.call("loadscene(" + scenename + ")")
    }

    /**
     * 切换全屏
     * 还有别的方式
     * this.krpano.set(fullscreen,true);
     */
    toggleFullscreen() {
        this.krpano.call("toggle(fullscreen);");
    }

    /**
     * 选择是拖动全景图像还是移动全景图像
     * drag - drag the pano image. 【默认】
     * moveto - move the pano image.
     * @param {*} mouse 
     */
    setControlMouse(mouse = "drag") {
        this.krpano.set("control.mouse", mouse);
        this.krpano.set("control.touch", mouse);
    }

    /**
     * 开启自动旋转
     */
    autorotateStart() {
        this.krpano.call("autorotate.start()");
    }

    /**
     * 关闭自动旋转
     */
    autorotateStop() {
        this.krpano.call("autorotate.stop()");
    }

    /**
     * 获取当前缩放大小
     * @returns 
     */
    getViewFov() {
        return this.krpano.get("view.fov");
    }

    /**
     * 设置当前缩放大小
     * @param {*} fov 
     */
    setViewFov(fov) {
        this.krpano.set("view.fov", fov);
    }
}