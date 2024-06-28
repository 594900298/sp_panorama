<!--
    Tree 树形控件
    * 支持双向数据绑定回显
    * 支持无限级递归

    ---------------------------
    Author: 594900298@qq.com
    山内有樱名为良，树本无名只待春

    引入
      import treeCase from "@/components/treeCase.vue"

    使用
    <tree-case v-model="ruleForm.rules" :data="dataList" :props="{label: 'name',children: 'children'}" node-key="rule_id" />
    
    v-model 绑定参数
    data 数据源
    props 配置【参考interface】
    node-key 唯一标识
    select-father-node 是否需要选中父元素
    unselect-father-node 是否需要取消选中父元素
    select-children-node 是否需要选中后代
    unselect-children-node 是否需要取消选中后代
-->
<script setup lang="ts">
import { defineProps, defineEmits, computed } from "vue";
import type { PropType } from "vue";
//props 接口
interface propsInterface {
  //指定节点标签为节点对象的某个属性值
  label: String;
  //指定子树为节点对象的某个属性值
  children: String;
  //指定节点选择框是否禁用为节点对象的某个属性值
  disabled?: String;
  //自定义节点类名
  class?: String;
}
const props = defineProps({
  //数据源
  data: {
    type: Array,
    required: true,
  },
  //绑定值
  modelValue: {
    type: Array,
    required: true,
  },
  //props 参数
  props: {
    type: Object as PropType<propsInterface>,
    required: true,
  },
  //唯一标识
  nodeKey: {
    type: String,
    required: true,
  },
  //父节点
  father: {
    type: Array,
    default: [],
  },
  //是否需要选中祖先
  selectFatherNode: {
    type: Boolean,
    default: true,
  },
  //是否需要取消选中祖先
  unselectFatherNode: {
    type: Boolean,
    default: false,
  },
  //是否需要选中后辈
  selectChildreNode: {
    type: Boolean,
    default: true,
  },
  //是否需要取消选中后辈
  unselectChildreNode: {
    type: Boolean,
    default: true,
  },
});
//实现双向数据绑定
const emit = defineEmits(["update:modelValue"]);
const value = computed({
  get() {
    return props.modelValue;
  },
  set(value) {
    emit("update:modelValue", value);
  },
});
//祖先节点
const fatherValue = (nodeKey: string | number) => {
  let key = nodeKey;
  return [...props.father, key];
};
//判断是选中
const checkBoxModel = (nodeKey: string | number): boolean => {
  let check = nodeKey;
  return value.value.indexOf(check) != -1;
};
/**
 * 递归获取所有nodeKey
 * 将number 转成了 string
 * @param row
 */
const getNodeKeyList = (row: any) => {
  let container: any[] = [];
  row.map((v: any) => {
    //先加入自己
    container.push(v[props.nodeKey]);
    //判断有没子层
    v[props.props.children] &&
      v[props.props.children].map((vv: any) => {
        //进入子层先加入自己
        container.push(vv[props.nodeKey]);
        //判断有没子层，递归自己
        vv[props.props.children] &&
          container.push(...getNodeKeyList(vv[props.props.children]));
      });
  });
  return container;
};
//change事件
const onChangeEvent = (e: any, row: any) => {
  let fatherNodeKeyList = (
    e.target.checked ? props.selectFatherNode : props.unselectFatherNode
  )
    ? props.father
    : [];
  /**
   * 判断是否需要子节点,要进入递归不需要则加入自身
   * 这个为false 的时候取消选择也不会影响后代
   */
  let nodeKeyList = (
    e.target.checked ? props.selectChildreNode : props.unselectChildreNode
  )
    ? getNodeKeyList([row])
    : [row[props.nodeKey]];
  //看是选中还是取消选中
  if (e.target.checked) {
    // 选中直接推进去
    value.value.push(...fatherNodeKeyList);
    value.value.push(...nodeKeyList);
  } else {
    //取消选中直接过滤掉,取消的时候只会取消自己和后代，不会取消父类
    value.value = value.value.filter((item) => {
      return props.unselectFatherNode
        ? fatherNodeKeyList.indexOf(item) == -1 &&
            nodeKeyList.indexOf(item) == -1
        : nodeKeyList.indexOf(item) == -1;
    });
  }
};
</script>
<template>
  <div
    v-for="item in props.data"
    :key="item[props.nodeKey]"
    :class="props.props.class"
    class="tree-node"
  >
    <div class="tree-node__content">
      <input
        type="checkbox"
        :checked="checkBoxModel(item[props.nodeKey])"
        :disabled="item[props.props.disabled]"
        class="tree-node-checkbox"
        @change="onChangeEvent($event, item)"
      />
      <div class="tree-node__label">{{ item[props.props.label] }}</div>
    </div>
    <div class="tree-node__children" v-if="item[props.props.children]">
      <tree-case
        v-model="value"
        :data="item[props.props.children]"
        :props="props.props"
        :node-key="props.nodeKey"
        :father="fatherValue(item[props.nodeKey])"
      />
    </div>
  </div>
</template>
<style scoped lang="scss">
.tree-node {
  display: flex;
  width: 100%;
  border-right: 1px solid #dcdfe6;
  .tree-node__content {
    flex: auto;
    padding-left: 5px;
    display: flex;
    align-items: center;
    border-left: 1px solid #dcdfe6;
    border-bottom: 1px solid #dcdfe6;
    .tree-node-checkbox {
      margin-right: 5px;
    }
  }
  .tree-node__children {
    flex: auto;
  }
}
.tree-node:first-child {
  border-top: 1px solid #dcdfe6;
}
.tree-node__children > .tree-node:first-child {
  border-top: none;
}
</style>