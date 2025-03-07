package com.example.common.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后台管理员
 *
 * @TableName sp_admin
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AdminPO implements Serializable {
    @ApiModelProperty(value = "用户id")
    @TableId(type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "账号状态 1-正常 0-禁用")
    private Boolean status;

    @ApiModelProperty(value = "是否为主账号 1-主账号")
    private Boolean isMain;

    @ApiModelProperty(value = "登录ip")
    private String loginIp;

    @ApiModelProperty(value = "登录时间")
    private Integer loginTime;

    @ApiModelProperty(value = "登录次数")
    private Integer loginNum;

    private String uuid;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否系统 1-是 0-否")
    private Boolean isSystem;

    @ApiModelProperty(value = "删除时间 不为0则软删")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleteTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Integer createTime;

    private static final long serialVersionUID = 1L;

    /**
     * 场景表
     * @TableName sp_scene
     */
    @TableName(value ="sp_scene")
    @Data
    public static class Scene implements Serializable {
        /**
         * 场景id
         */
        @TableId(type = IdType.AUTO)
        private Integer sceneId;

        /**
         * 空间id
         */
        private Integer spaceId;

        /**
         * 场景名称
         */
        private String sceneName;

        /**
         * 起始水平视角
         */
        private String hlookat;

        /**
         * 起始垂直视角
         */
        private String vlookat;

        /**
         * 默认视角的缩放
         */
        private String fov;

        /**
         * 视角类型
         */
        private String limitview;

        /**
         * 最小水平视角范围
         */
        private String hlookatmin;

        /**
         * 最大水平视角范围
         */
        private String hlookatmax;

        /**
         * 最小垂直视角范围
         */
        private String vlookatmin;

        /**
         * 最大垂直视角范围
         */
        private String vlookatmax;

        /**
         * 控制方式 drag[默认]、moveto
         */
        private String control;

        /**
         * 随机生成的字符串
         */
        private String randomString;

        /**
         * 素材路径
         */
        private String panosPath;

        /**
         * xml路径
         */
        private String xmlPath;

        /**
         * 素材文件名
         */
        private String materialFileName;

        /**
         * xml level配置
         */
        private String levelConfig;

        /**
         * 1-显示 2-隐藏
         */
        private Integer isShow;

        /**
         * 排序
         */
        private Integer sort;

        /**
         * 创建时间
         */
        private Integer createTime;

        /**
         * 软删
         */
        private Integer deleteTime;

        @TableField(exist = false)
        private static final long serialVersionUID = 1L;

        @Override
        public boolean equals(Object that) {
            if (this == that) {
                return true;
            }
            if (that == null) {
                return false;
            }
            if (getClass() != that.getClass()) {
                return false;
            }
            Scene other = (Scene) that;
            return (this.getSceneId() == null ? other.getSceneId() == null : this.getSceneId().equals(other.getSceneId()))
                && (this.getSpaceId() == null ? other.getSpaceId() == null : this.getSpaceId().equals(other.getSpaceId()))
                && (this.getSceneName() == null ? other.getSceneName() == null : this.getSceneName().equals(other.getSceneName()))
                && (this.getHlookat() == null ? other.getHlookat() == null : this.getHlookat().equals(other.getHlookat()))
                && (this.getVlookat() == null ? other.getVlookat() == null : this.getVlookat().equals(other.getVlookat()))
                && (this.getFov() == null ? other.getFov() == null : this.getFov().equals(other.getFov()))
                && (this.getLimitview() == null ? other.getLimitview() == null : this.getLimitview().equals(other.getLimitview()))
                && (this.getHlookatmin() == null ? other.getHlookatmin() == null : this.getHlookatmin().equals(other.getHlookatmin()))
                && (this.getHlookatmax() == null ? other.getHlookatmax() == null : this.getHlookatmax().equals(other.getHlookatmax()))
                && (this.getVlookatmin() == null ? other.getVlookatmin() == null : this.getVlookatmin().equals(other.getVlookatmin()))
                && (this.getVlookatmax() == null ? other.getVlookatmax() == null : this.getVlookatmax().equals(other.getVlookatmax()))
                && (this.getControl() == null ? other.getControl() == null : this.getControl().equals(other.getControl()))
                && (this.getRandomString() == null ? other.getRandomString() == null : this.getRandomString().equals(other.getRandomString()))
                && (this.getPanosPath() == null ? other.getPanosPath() == null : this.getPanosPath().equals(other.getPanosPath()))
                && (this.getXmlPath() == null ? other.getXmlPath() == null : this.getXmlPath().equals(other.getXmlPath()))
                && (this.getMaterialFileName() == null ? other.getMaterialFileName() == null : this.getMaterialFileName().equals(other.getMaterialFileName()))
                && (this.getLevelConfig() == null ? other.getLevelConfig() == null : this.getLevelConfig().equals(other.getLevelConfig()))
                && (this.getIsShow() == null ? other.getIsShow() == null : this.getIsShow().equals(other.getIsShow()))
                && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()));
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((getSceneId() == null) ? 0 : getSceneId().hashCode());
            result = prime * result + ((getSpaceId() == null) ? 0 : getSpaceId().hashCode());
            result = prime * result + ((getSceneName() == null) ? 0 : getSceneName().hashCode());
            result = prime * result + ((getHlookat() == null) ? 0 : getHlookat().hashCode());
            result = prime * result + ((getVlookat() == null) ? 0 : getVlookat().hashCode());
            result = prime * result + ((getFov() == null) ? 0 : getFov().hashCode());
            result = prime * result + ((getLimitview() == null) ? 0 : getLimitview().hashCode());
            result = prime * result + ((getHlookatmin() == null) ? 0 : getHlookatmin().hashCode());
            result = prime * result + ((getHlookatmax() == null) ? 0 : getHlookatmax().hashCode());
            result = prime * result + ((getVlookatmin() == null) ? 0 : getVlookatmin().hashCode());
            result = prime * result + ((getVlookatmax() == null) ? 0 : getVlookatmax().hashCode());
            result = prime * result + ((getControl() == null) ? 0 : getControl().hashCode());
            result = prime * result + ((getRandomString() == null) ? 0 : getRandomString().hashCode());
            result = prime * result + ((getPanosPath() == null) ? 0 : getPanosPath().hashCode());
            result = prime * result + ((getXmlPath() == null) ? 0 : getXmlPath().hashCode());
            result = prime * result + ((getMaterialFileName() == null) ? 0 : getMaterialFileName().hashCode());
            result = prime * result + ((getLevelConfig() == null) ? 0 : getLevelConfig().hashCode());
            result = prime * result + ((getIsShow() == null) ? 0 : getIsShow().hashCode());
            result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
            result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
            result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
            return result;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getSimpleName());
            sb.append(" [");
            sb.append("Hash = ").append(hashCode());
            sb.append(", sceneId=").append(sceneId);
            sb.append(", spaceId=").append(spaceId);
            sb.append(", sceneName=").append(sceneName);
            sb.append(", hlookat=").append(hlookat);
            sb.append(", vlookat=").append(vlookat);
            sb.append(", fov=").append(fov);
            sb.append(", limitview=").append(limitview);
            sb.append(", hlookatmin=").append(hlookatmin);
            sb.append(", hlookatmax=").append(hlookatmax);
            sb.append(", vlookatmin=").append(vlookatmin);
            sb.append(", vlookatmax=").append(vlookatmax);
            sb.append(", control=").append(control);
            sb.append(", randomString=").append(randomString);
            sb.append(", panosPath=").append(panosPath);
            sb.append(", xmlPath=").append(xmlPath);
            sb.append(", materialFileName=").append(materialFileName);
            sb.append(", levelConfig=").append(levelConfig);
            sb.append(", isShow=").append(isShow);
            sb.append(", sort=").append(sort);
            sb.append(", createTime=").append(createTime);
            sb.append(", deleteTime=").append(deleteTime);
            sb.append(", serialVersionUID=").append(serialVersionUID);
            sb.append("]");
            return sb.toString();
        }
    }
}

