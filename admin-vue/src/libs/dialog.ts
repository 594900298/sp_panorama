
// 弹出层组件接口
export interface dialogOptionsType {
  //打开文件路径
  path: string;
  //标题
  title?: string
  //宽度
  width?: string;
  //参数
  params?: object;
  //是否显示
  isShow?: any;
  //是否全屏
  fullscreen?: boolean;
  //是否显示关闭按钮
  showClose?: boolean;
  //组件
  asyncComp?: any;
  //Dialog 打开的回调
  open?: () => void;
  //Dialog 打开动画结束时的回调
  opened?: () => void;
  //Dialog 关闭的回调【这里必定会触发callback,除非自己重写】
  close?: () => void;
  //Dialog 关闭动画结束时的回调
  closed?: () => void;
  //关闭后回调方法
  callback?: (params: object) => void;
}
//弹出层容器,容器了为了存放便于所有组件调用
let container: any;
//设置弹出层容器
export const setDialogCase = (ref: any) => {
  container = ref;
};
//获取弹出层容器
export const useDialogCase = () => {
  return container;
};

