package com.api.common.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对接中国商品信息http
 **/
public class BarcodeUtil {

    public static void main(String[] args) {
        //http://webapi.chinatrace.org/api/getProductData?productCode=6921734976505&mac=91151BCA45D5F9248A2A082AF8C2311A7C6CF7DB17A9E4E180D220DE0FAF6698
        String paramString = "http://webapi.chinatrace.org/api/getProductData?productCode=6921734976505";
        int k = paramString.indexOf("/api/");
        int m = paramString.length();
        String str2 = paramString.substring(k, m);
        String url = paramString + "&mac=" + getMac("V7N3Xpm4jpRon/WsZ8X/63G8oMeGdUkA8Luxs1CenTY=", str2) ;
        System.out.println(url);
    }

    /**
     * get请求
     *
     * @param paramString 请求路径+参数
     * @return string
     * @author xuzilong
     * @date 2020年02月27日
     **/
    public static String getUrl(String paramString) {
        int k = paramString.indexOf("/api/");
        int m = paramString.length();
        String str2 = paramString.substring(k, m);
        return  paramString + "&mac=" + getMac("V7N3Xpm4jpRon/WsZ8X/63G8oMeGdUkA8Luxs1CenTY=", str2);
    }

    private static String getMac(String paramString1, String paramString2) {
        byte[] arrayOfByte1 = new byte[32];
        arrayOfByte1[0] = 87;
        arrayOfByte1[1] = -77;
        arrayOfByte1[2] = 119;
        arrayOfByte1[3] = 94;
        arrayOfByte1[4] = -103;
        arrayOfByte1[5] = -72;
        arrayOfByte1[6] = -114;
        arrayOfByte1[7] = -108;
        arrayOfByte1[8] = 104;
        arrayOfByte1[9] = -97;
        arrayOfByte1[10] = -11;
        arrayOfByte1[11] = -84;
        arrayOfByte1[12] = 103;
        arrayOfByte1[13] = -59;
        arrayOfByte1[14] = -1;
        arrayOfByte1[15] = -21;
        arrayOfByte1[16] = 113;
        arrayOfByte1[17] = -68;
        arrayOfByte1[18] = -96;
        arrayOfByte1[19] = -57;
        arrayOfByte1[20] = -122;
        arrayOfByte1[21] = 117;
        arrayOfByte1[22] = 73;
        arrayOfByte1[24] = -16;
        arrayOfByte1[25] = -69;
        arrayOfByte1[26] = -79;
        arrayOfByte1[27] = -77;
        arrayOfByte1[28] = 80;
        arrayOfByte1[29] = -98;
        arrayOfByte1[30] = -99;
        arrayOfByte1[31] = 54;
        String str1 = "";
        try {
            Mac localMac = Mac.getInstance("HmacSHA256");
            byte[] arrayOfByte2 = paramString2.getBytes("ASCII");
            localMac.init(new SecretKeySpec(arrayOfByte1, "HMACSHA256"));
            str1 = toHex(localMac.doFinal(arrayOfByte2));
            return str1.toUpperCase();
        }
        catch (Exception localException) {
            localException.printStackTrace();
        }
        return str1;
    }

    public static String toHex(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer1 = new StringBuffer(2 * paramArrayOfByte.length);
        StringBuffer localStringBuffer2 = new StringBuffer(paramArrayOfByte.length);
        for (int i = 0; ; i++)
        {
            if (i >= paramArrayOfByte.length){
                return localStringBuffer1.toString();
            }
            localStringBuffer1.append(Character.forDigit((0xF0 & paramArrayOfByte[i]) >> 4, 16));
            localStringBuffer1.append(Character.forDigit(0xF & paramArrayOfByte[i], 16));
            localStringBuffer2.append(paramArrayOfByte[i]);
        }
    }
}
