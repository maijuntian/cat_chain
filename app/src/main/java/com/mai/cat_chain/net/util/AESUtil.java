package com.mai.cat_chain.net.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
/**
 * 自定义AES实现，随机填充元数据
 * @author zhengliming
 *
 */
public class AESUtil {
	//调用加密算法，并用Base64Encoder再次加密
	public static String encode(String content, String password) throws Exception {
		String cont = strUTF8(content);//转成utf-8的字符串
		String pwd = strUTF8(password);//转成utf-8的字符串
		String encodeStr = strUTF8(doEnFinal(cont, pwd));
		return parseByte2HexStr(encodeStr.getBytes("utf-8"));
	}
	//先调用Base64Decoder解密再用AES解密算法进行解密
	public static String decrypt(String content, String password) throws Exception {
		String cont = new String(parseHexStr2Byte(content),"utf-8");
		String pwd = strUTF8(password);
		return strUTF8(doDeFinal(cont, pwd));
	}
	//加密
	private static String doEnFinal(String cont, String pwd) throws Exception {
		String sumStr = "";
		String xh = "";
		for (int i = 0; i < cont.length(); i++) {
			char c = cont.charAt(i);
			Random random = new Random();
			int r = random.nextInt(pwd.length());
			char p = pwd.charAt(r);
			int sum=(int)c+(int)p;
			sumStr += sum+"-";
			xh += "-" + r;
		}
		sumStr=sumStr.substring(0, sumStr.length()-1);
		return  sumStr+ xh + "-" + sumStr.length();
	}
	//解密
	private static String doDeFinal(String cont, String pwd) throws Exception {
		String encodeContent=getEnCodeContent(cont);
		int[] pwdIndex=getPWDIndexArrays(cont);
		String deCodeStr="";
		String[] enCodeArrays=encodeContent.split("-");
		for(int i=0;i<pwdIndex.length;i++) {
			int e=Integer.parseInt(enCodeArrays[i]);
			int p= pwd.charAt(pwdIndex[i]);
			deCodeStr+=(char)(e-p);
		}
		return deCodeStr;
	}
	//转成utf-8x
	private static String strUTF8(String str) throws Exception {
		String utf8Str = new String(str.getBytes(), "utf-8");
		return utf8Str;
	}
	//获取原加密内容
	private static String getEnCodeContent(String ec) {
		int lenIndex = ec.lastIndexOf("-");
		int enCodeContentSize = Integer.parseInt(ec.substring(lenIndex + 1));
		return ec.substring(0, enCodeContentSize);
	}
	//获取密码下标集合
	private static int[] getPWDIndexArrays(String ec) {
		int lenIndex = ec.lastIndexOf("-");
		int enCodeContentSize = Integer.parseInt(ec.substring(lenIndex + 1));
		String pwdIndexStr = ec.substring(enCodeContentSize+1,lenIndex);
		String[] array = pwdIndexStr.split("-");
		return stringArraysToInt(array);
	}
	//string数组转成int数组
	private static int[] stringArraysToInt(String[] arrays) {
		int[] intArrays = new int[arrays.length];
		for (int i = 0; i < arrays.length; i++) {
			intArrays[i] = Integer.parseInt(arrays[i]);
		}
		return intArrays;
	}
	 /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
	public static String createAESKey() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
}