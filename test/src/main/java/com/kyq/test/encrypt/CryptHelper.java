package com.kyq.test.encrypt;



import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Description:  认证方法帮助类
 *
 * @author kuangyongqiang
 * @version 1.0
 */
public class CryptHelper {
    //获取TOP的密钥
    private static final String TOTP = "NBCWYTDPK5XVE3CEEE";

    //获取TOTP的口令KNRUO42KIRUU4VDFKJTECY2FEE
    private static final String OTP_PASS_KEY = "KNRUO42KIRUU4VDFKJTECY2FEE";

    private static final String base32Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final int[] base32Lookup = {
            0xFF, 0xFF, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F, // '0', '1', '2', '3', '4', '5', '6', '7'
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, // '8', '9', ':',';', '<', '=', '>', '?'
            0xFF, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, // '@', 'A', 'B',  'C', 'D', 'E', 'F', 'G'
            0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, // 'H', 'I', 'J',  'K', 'L', 'M', 'N', 'O'
            0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, // 'P', 'Q', 'R',  'S', 'T', 'U','V', 'W'
            0x17, 0x18, 0x19, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, // 'X', 'Y', 'Z',  '[', '', ']', '^', '_'
            0xFF, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, // '`', 'a', 'b',  'c', 'd', 'e', 'f', 'g'
            0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, // 'h', 'i', 'j',  'k', 'l', 'm', 'n', 'o'
            0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, // 'p', 'q', 'r',  's', 't', 'u', 'v', 'w'
            0x17, 0x18, 0x19, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF // 'x', 'y', 'z',   '{', '|', '}', '~', 'DEL'
    };

    /**
     *  生成口令的方法
     * */
    public static String getTOTP(String secret, String userId){
        if(OTP_PASS_KEY.equals(secret)){
            try {
                return encode(getOTP(encode(userId.getBytes("UTF8"))).getBytes("UTF8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        return "";
    }

    /**
     *  生成口令的方法
     * */
    public static String getTOTP(){
        return getOTP(TOTP);
    }

    public static String dec2hex(long s){
        return (s < 15.5 ? "0" : "") + Integer.toHexString(Math.round(s));
    }
    public static Long hex2dec(String s){
        return Long.parseLong(s, 16);
    }
    public static String leftpad(String s,int l,String p){
        if (l + 1 >= s.length()) {
            StringBuffer sb = new StringBuffer();
            int len = l - s.length();
            for(int i=0;i<len;i++){
                sb.append(p);
            }
            s = sb.toString()+s;
        }
        return s;
    }
    public static String base32tohex(String base32){
        String base32chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
        String bits = "";
        String hex = "";
        int base32Len = base32.length();
        for(int i = 0;i<base32Len;i++){
            Integer val = base32chars.indexOf(String.valueOf(base32.charAt(i)).toUpperCase());
            bits += leftpad(Integer.toBinaryString(val),5,"0");
        }

        int bitsLen = bits.length();
        for(int i=0; i + 4 <= bitsLen; i += 4) {
            hex = hex + Integer.toHexString(Integer.valueOf(bits.substring(i,i+4),2));
        }
        return hex;
    }
    public static String getOTP(String secret){
        long epoch = Math.round(new Date().getTime()/1000.0);
        String time = leftpad(dec2hex(Math.round(Math.floor(epoch / 30))),16,"0");
//        String time = leftpad(dec2hex(Math.round(Math.floor(epoch / 120))),16,"0");
        String hmac = getHMACSHA1ByHex(time,base32tohex(secret));
        long offset = hex2dec(hmac.substring(hmac.length()-1));
        Integer offsetInt = Math.toIntExact(offset);
        int cutLen = offsetInt * 2+8>hmac.length()?hmac.length():offsetInt * 2+8;
        String otp = (hex2dec(hmac.substring(offsetInt * 2, cutLen)) & hex2dec("7fffffff")) + "";
        otp = otp.substring(otp.length()-6);
        return otp;
    }
    public static String getHMACSHA1ByHex(String data,String key){
        try {
            SecretKeySpec signingKey = new SecretKeySpec(hexToBytes(key), "HmacSHA1");
            Mac mac  = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(hexToBytes(data)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }


    //获取签名
    public static String getSign(String actionName,String postdata,String user_token){
        String  data = actionName + "\n", key = user_token;
        data += postdata;
        key =key==null ? "SCDP" : key;
        return getHmacSHA256(data, key);
    }

    public static String getHmacSHA256(String data,String key){
        try {
                try {
                    SecretKeySpec  signingKey = new SecretKeySpec(key.getBytes("utf-8"), "HmacSHA256");
                    Mac mac  = Mac.getInstance("HmacSHA256");
                    mac.init(signingKey);
                    return byte2hex(mac.doFinal(hexToBytes(data)));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] hexToBytes(String hexStr) {
        int len = hexStr.length();
        hexStr = hexStr.toUpperCase();
        byte[] des;
        if (len % 2 != 0 || len == 0) {
            return null;
        } else {
            int halfLen = len / 2;
            des = new byte[halfLen];
            char[] tempChars = hexStr.toCharArray();
            for (int i = 0; i < halfLen; ++i) {
                char c1 = tempChars[i * 2];
                char c2 = tempChars[i * 2 + 1];
                int tempI = 0;
                if (c1 >= '0' && c1 <= '9') {
                    tempI += ((c1 - '0') << 4);
                } else if (c1 >= 'A' && c1 <= 'F') {
                    tempI += (c1 - 'A' + 10) << 4;
                } else {
                    return null;
                }
                if (c2 >= '0' && c2 <= '9') {
                    tempI += (c2 - '0');
                } else if (c2 >= 'A' && c2 <= 'F') {
                    tempI += (c2 - 'A' + 10);
                } else {
                    return null;
                }
                des[i] = (byte) tempI;
            }
            return des;
        }
    }
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString();
    }

    /**
     * Encodes byte array to Base32 String.
     *
     * @param bytes
     *            Bytes to encode.
     * @return Encoded byte array <code>bytes</code> as a String.
     *
     */
    public static String encode(final byte[] bytes) {
        int i = 0, index = 0, digit = 0;
        int currByte, nextByte;
        StringBuffer base32 = new StringBuffer((bytes.length + 7) * 8 / 5);
        while (i < bytes.length) {
            currByte = (bytes[i] >= 0) ? bytes[i] : (bytes[i] + 256); // unsign
            /* Is the current digit going to span a byte boundary? */
            if (index > 3) {
                if ((i + 1) < bytes.length) {
                    nextByte = (bytes[i + 1] >= 0) ? bytes[i + 1]
                            : (bytes[i + 1] + 256);
                } else {
                    nextByte = 0;
                }
                digit = currByte & (0xFF >> index);
                index = (index + 5) % 8;
                digit <<= index;
                digit |= nextByte >> (8 - index);
                i++;
            } else {
                digit = (currByte >> (8 - (index + 5))) & 0x1F;
                index = (index + 5) % 8;
                if (index == 0)
                    i++;
            }
            base32.append(base32Chars.charAt(digit));
        }
        return base32.toString();
    }

    /**
     * Decodes the given Base32 String to a raw byte array.
     *
     * @param base32
     * @return Decoded <code>base32</code> String as a raw byte array.
     */
    public static byte[] decode(final String base32) {
        int i, index, lookup, offset, digit;
        byte[] bytes = new byte[base32.length() * 5 / 8];
        for (i = 0, index = 0, offset = 0; i < base32.length(); i++) {
            lookup = base32.charAt(i) - '0';
            /* Skip chars outside the lookup table */
            if (lookup < 0 || lookup >= base32Lookup.length) {
                continue;
            }
            digit = base32Lookup[lookup];
            /* If this digit is not in the table, ignore it */
            if (digit == 0xFF) {
                continue;
            }
            if (index <= 3) {
                index = (index + 5) % 8;
                if (index == 0) {
                    bytes[offset] |= digit;
                    offset++;
                    if (offset >= bytes.length)
                        break;
                } else {
                    bytes[offset] |= digit << (8 - index);
                }
            } else {
                index = (index + 5) % 8;
                bytes[offset] |= (digit >>> index);
                offset++;
                if (offset >= bytes.length) {
                    break;
                }
                bytes[offset] |= digit << (8 - index);
            }
        }
        return bytes;
    }

    /**
     * 密码加密算法
     * @param s 原始密码
     * @return  加密后的密码
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String encrySHA1(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        sha.update(s.getBytes());
        byte[] rlt = sha.digest();

        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        int j = rlt.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = rlt[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        String rltStr = new String(str);
        rltStr = rltStr.toLowerCase();
        return rltStr;
    }

    public static boolean verifyTOTP(String userId, String userOtp){
        try {
            String base32UserId = encode(userId.getBytes("UTF8"));

//            com.warrenstrange.googleauth.GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder builder = new com.warrenstrange.googleauth.GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder();
//            builder.setTimeStepSizeInMillis(300000L);
//            com.warrenstrange.googleauth.GoogleAuthenticatorConfig config = builder.build();
//            com.warrenstrange.googleauth.GoogleAuthenticator gauth = new com.warrenstrange.googleauth.GoogleAuthenticator(config);

            com.warrenstrange.googleauth.GoogleAuthenticator gauth = new com.warrenstrange.googleauth.GoogleAuthenticator();

            String decodeUserOtp = new String(decode(userOtp),"UTF8");
            return gauth.authorize(base32UserId, Integer.parseInt(decodeUserOtp));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
