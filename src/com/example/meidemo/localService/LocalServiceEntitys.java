package com.example.meidemo.localService;

import java.util.ArrayList;

public class LocalServiceEntitys {
	public String state;
	public ArrayList<LocalServiceEntity> result;
	@Override
	public String toString() {
		return "LocalServiceEntitys [state=" + state + ", result=" + result.toString()
				+ "]";
	}
	
	
}

class LocalServiceEntity {
	public String id; // 信息id
	public String type; // 0为租赁；1为求租
	public String pubtype; // 出租或者销售类型 0整套出租 1.单间出租 2.床位出租
	public String persontype; // 0是个人，1是经纪人
	public String zonename; // 地区
	public String address; // 地址
	public String title; // 标题
	public String hosenote; // 备注，房屋描述
	public String hoseimages; // 房屋图片
	public String contactname; // 联系人
	public String phonenumber; // 联系电话
	public String hidephone; // 是否隐藏号码（暂时没有用）
	public String hall; // 大厅
	public String room; // 房间，室
	public String toilet; // 卫生间
	public String price; // 价格
	public String area; // 面积（平米）
	public String mating; // 配置，格式：0000 0000 床，热水器，宽带，空调，洗衣机，冰箱，电视，暖气
	public String floor; // 第几层
	public String totalfloor; // 总共几层
	public String jindu; // 经度
	public String weidu; // 纬度
	public String geostr; // 经纬度换算字符串
	public String publish; // 发布时间
	public String updatetime; // 更新时间
	public String pubflag; // 推广标识
	public String ishide; // 信息是否隐藏，0为不隐藏，1为隐藏
	public String state; // 信息状态，-2为（审核未通过）系统删除，-1为（用户）已删除，0为已发布，1为草稿
	public String thumbfile; // 缩略图，数组地址，以逗号隔开
	public String agency; // 中介公司
	public String count;
	public String tempdu;
	public String distance;
	/**记录点击事件
	 */
	public boolean click;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return phonenumber;
	}
}

