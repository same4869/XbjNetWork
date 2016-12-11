package com.xbjnw.wangxun.xbjnetwork.web;

import android.util.Base64;

import com.xbjnw.wangxun.xbjnetwork.json.JSONFormatExcetion;
import com.xbjnw.wangxun.xbjnetwork.json.JSONToBeanHandler;

/**
 * 
 * @Author:Lijj
 * @Date:2014-4-17上午10:33:54
 * @Todo:TODO
 */
public class WenbaEncryptHandler {

	/**
	 * 数据源转化为 对象bean
	 * 
	 * @param data
	 * @param cls
	 * 
	 * @return
	 * @throws JSONFormatExcetion
	 */
	public static <T> T fromEncryptByteArray(byte[] data, Class<T> cls) throws JSONFormatExcetion {
		if (data == null) {
			return null;
		}

//		data = EncryptUtil.soDecryptValue(data);
		String dataString = new String(data);

		// bug compatiblity
		if (dataString.lastIndexOf("}") != dataString.length() - 1) {
			dataString = dataString.substring(0, dataString.lastIndexOf("}") + 1);
		}
		return JSONToBeanHandler.fromJsonString(dataString, cls);
	}

	/**
	 * 数据源转化为 对象bean
	 * 
	 * @param data
	 * @param cls
	 *            是否需要解密
	 * @return
	 * @throws JSONFormatExcetion
	 */
	public static <T> T fromEncryptString(String data, Class<T> cls) throws JSONFormatExcetion {
		byte[] dataBytes = Base64.decode(data, Base64.DEFAULT);

//		dataBytes = EncryptUtil.soDecryptValue(dataBytes);

		String dataString = new String(dataBytes);

		return JSONToBeanHandler.fromJsonString(dataString, cls);
	}

	/**
	 * 对象bean转化为加密json字符串
	 * 
	 * @param value
	 *            是否需要加密
	 * @return
	 * @throws JSONFormatExcetion
	 * @throws Exception
	 */
	public static String toEncryptString(Object value) throws JSONFormatExcetion, Exception {
		return Base64.encodeToString(toEncryptByteArray(value), Base64.DEFAULT);
	}

	/**
	 * 对象bean转化为json字符串并加密
	 * 
	 * @param value
	 * @return
	 * @throws JSONFormatExcetion
	 * @throws Exception
	 */
	public static byte[] toEncryptByteArray(Object value) throws JSONFormatExcetion, Exception {
		byte[] data = JSONToBeanHandler.toJsonString(value).getBytes();
//		if (data != null) {
//			return EncryptUtil.soEncryptValue(data);
//		}

		return data;
	}

}
