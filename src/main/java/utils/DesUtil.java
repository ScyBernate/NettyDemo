package utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 自定义的DES加密和解密工具,可以对字符串进行加密和解密操作 。
 */
public class DesUtil {

    /**
     * 字符串默认键值
     */
    public static String strDefaultKey = "s2_I$-!#eu_3y2*4D-8^2{A3R_E}I5%&U#I@-O;!";

    public static String ivKey = "abcdefgh";

    /**
     * 加密工具
     */
    public static Cipher encryptCipher = null;

    /**
     * 解密工具
     */
    public static Cipher decryptCipher = null;

    static {
        if (encryptCipher == null || decryptCipher == null) {
            try {
                DESKeySpec desKeySpec = new DESKeySpec(strDefaultKey.getBytes("UTF-8"));
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
                IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes("UTF-8"));

                encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     */
    public static String encrypt(String strIn) throws Exception {
        byte[] retByte = encryptCipher.doFinal(strIn.getBytes());
        return byteArr2HexStr(retByte);
    }


    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String strIn) throws Exception {
        byte[] retByte = decryptCipher.doFinal(hexStr2ByteArr(strIn));
        return new String(retByte);
    }

    public static void main(String[] args) throws Exception {
        String token = "这是一个下发的胜多负少东方是防守打法斯蒂芬是防守打法斯蒂芬斯蒂芬斯蒂芬斯蒂芬斯蒂芬是";
        System.out.println("加密字符：" + token);
        String tokenAfter = DesUtil.encrypt(token);
        System.out.println("加密后的字符：" + tokenAfter.length());
        System.out.println("解密后的字符：" + DesUtil.decrypt("7945313874b734b776c2e5c7629d5214781bbeefbaa7e380"));
    }

}