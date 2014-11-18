package com.example.meidemo.data;

public class GlobalData {
//	public static String ip = "http://192.168.171.161/test/typedata/gb_interface/index.php/";// 测试服务器
	public static final String ip = "http://tuantest.meizhou.com/interface/index.php/";
	// http://192.168.171.161/test/typedata/gb_interface/index.php/tuan/team/filmTopList
	/** 租房 */
	public static String House_Rent = "house/Rent/index";
	/** 猜你喜欢 */
	public static String Tuan_INDEXTEAM = "Tuan/Team/indexTeam?";

	// http://192.168.171.161/test/typedata/gb_interface/index.php/tuan/Team/teamlist?group=536&pg=1&ps=20&sort=0
	// 支持排序 sort 0是销量，1是价格升，2 价格降，3 发布时间
	/**
	 * 团购 536:美食
	 * */
	public static String FOODCATEGORY = "Tuan/Team/teamlist?";
	
	/** 电影顶部推荐列表 */
	public static String FILMTOPLIST = "Tuan/team/filmTopList";
	
//	http://192.168.171.161/test/typedata/gb_interface/index.php/tuan/team/filmlist?group=538
	public static String FILECATEGORY  = "Tuan/team/filmlist";
	/** 团购界面顶部分类*/
//	public static String TOPCLASSIFY = "/tuan/Category/stypeList";
	public static String TOPCLASSIFY = "Tuan/Category/ftypeList";
	/**团购详情 tuan/team/index?id=293*/
	public static String DETAILCATEGORY = "Tuan/team/index";
	/**
	 * 获取评论内容
	 */
	public static String COMMENT = "Tuan/pinglun/pinglunList";
	/**
	 * 获取指定商家的信息
	 */
	public static String BUSINESS_INFO = "Tuan/partner/getById";
	/**下订单*/
	public static String ORDER = "tuan/order/order";
	/**注册*/
	public static String REGISTER = "com/User/phoneregister";
	/**登录*/
	public static String LOGIN = "com/User/login";
	
//	order/orderpay?sid=&money=&orderid=  支付订单
	public static String PAYORDER = "tuan/order/orderpay";
	/**银联请求*/
	//callback=1&trade={"orderid":12,memberid":181818,"amount":12,"clientid":1,"clientty":2,"appversion":"1.0.3","descripe":"订单描述"}
	public static String YINLIANPAY = "tuan/Pay/topay?";
	/** 客户端编号 **/
	public static String clientID = "1";
	public static String appVersion = "1.0";// 版本号
	public static String sid = "";//sid
	public static String clientty = "";//clientty
	public static boolean isLogin = false;
	public static String city;
	public static String cityId = "1";
	/** IMEI **/
	public static String imei = "";
	/** 渠道号 **/
	public static String channel = "12";

	public static final String BAIDU_AK = "LlCemaNAKibOl8gQsPky8Mnc";
	
	public static final String SPINFOTOKEN = "user_token";
	public static final String SPINFOPHONE = "user_phone";
	
	public static final int LOING_RESULT_OK = 10;
	public static final int REGISTER_RESULT_OK = 11;
	public static final int UNLOING_RESULT_OK = 12;
	
	public static final int RESULT_OK = 1;
}
