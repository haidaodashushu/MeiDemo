package com.example.meidemo.CommonUtils;
public class MD5 {

	public static String getMD5(String instr) {
		String s = null;
		// 鐢ㄦ潵灏嗗瓧鑺傝浆鎹㈡垚 16 杩涘埗琛ㄧず鐨勫瓧绗?
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(instr.getBytes());
			byte tmp[] = md.digest(); // MD5 鐨勮绠楃粨鏋滄槸涓?釜 128 浣嶇殑闀挎暣鏁帮紝
										// 鐢ㄥ瓧鑺傝〃绀哄氨鏄?16 涓瓧鑺?
			char str[] = new char[16 * 2]; // 姣忎釜瀛楄妭鐢?16 杩涘埗琛ㄧず鐨勮瘽锛屼娇鐢ㄤ袱涓瓧绗︼紝
											// 鎵?互琛ㄧず鎴?16 杩涘埗闇? 32 涓瓧绗?
			int k = 0; // 琛ㄧず杞崲缁撴灉涓搴旂殑瀛楃浣嶇疆
			for (int i = 0; i < 16; i++) { // 浠庣涓?釜瀛楄妭寮?锛屽 MD5 鐨勬瘡涓?釜瀛楄妭
											// 杞崲鎴?16 杩涘埗瀛楃鐨勮浆鎹?
				byte byte0 = tmp[i]; // 鍙栫 i 涓瓧鑺?
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 鍙栧瓧鑺備腑楂?4 浣嶇殑鏁板瓧杞崲,
															// >>>
															// 涓洪?杈戝彸绉伙紝灏嗙鍙蜂綅涓?捣鍙崇Щ
				str[k++] = hexDigits[byte0 & 0xf]; // 鍙栧瓧鑺備腑浣?4 浣嶇殑鏁板瓧杞崲
			}
			s = new String(str).toUpperCase(); // 鎹㈠悗鐨勭粨鏋滆浆鎹负瀛楃涓?

		} catch (Exception e) {

		}
		return s;
	}

}
