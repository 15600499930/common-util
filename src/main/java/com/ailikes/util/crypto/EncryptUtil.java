package com.ailikes.util.crypto;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 功能描述:加密工具类,支持MD5、SHA
 * @author 徐大伟
 *
 */
public class EncryptUtil {
    /**
     * MD5加密
     * @param inputText
     * @return
     */
    public static String getMd5(String inputText) {
        return encrypt(inputText, "md5");
    }

    /**
     * sha加密 
     * @param inputText
     * @return
     */
    public static String getSha(String inputText) {
        return encrypt(inputText, "sha-1");
    }

    /** 
     * md5或者sha-1加密 
     *  
     * @param inputText 
     *            要加密的内容 
     * @param algorithmName 
     *            加密算法名称：md5或者sha-1，不区分大小写 
     * @return 
     */
    private static String encrypt(String inputText,
                                  String algorithmName) {
        if (inputText == null || "".equals(inputText.trim())) {
            throw new IllegalArgumentException("请输入要加密的内容");
        }
        if (algorithmName == null || "".equals(algorithmName.trim())) {
            algorithmName = "md5";
        }
        String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(inputText.getBytes("UTF8"));
            byte s[] = m.digest();
            // m.digest(inputText.getBytes("UTF8"));
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptText;
    }

    // 返回十六进制字符串
    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
    
    /**
     * 
     * 功能描述:校验订单请求签名是否正确 
     * 
     * @return boolean
     * @version 1.0.0
     * @author 孔垂云 
     */
    public static boolean checkOrderRequestSign(String order_num, String order_name,String order_amount,String order_date, String source, String payer,String receipt,String sign,String key) {
        String newSign = EncryptUtil.getMd5(EncryptUtil.getMd5(order_num +order_name+order_amount + order_date + source + payer + receipt) + key);
        if(null != sign && !"".equals(sign) && sign.equals(newSign)){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtil.getMd5(EncryptUtil.getMd5("19216811" + "15101515776") + "sfVC9u6256GM"));
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
}