//package com.wyiwei.aa_commons.base64;
//
//import java.security.Key;
//
//import javax.crypto.Cipher;
//import javax.crypto.SecretKeyFactory;
//import javax.crypto.spec.DESedeKeySpec;
//import javax.crypto.spec.SecretKeySpec;
//
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//
//public class DESCoder {
//
//    private static BASE64Encoder base64 = new BASE64Encoder();
//    //Aes 
//
//    // 密钥
//    public static String strKey = "MjA4MjUxODM1MjUwMzA2MjQxNDAzNzM3"; // "Mjc2OTU4NDI0NTIxNjQzMTYzNTcyMDk1";//
//                                                                      // "NDIzNDUwODI2OTQ3MzEzNzU1NTYxMjYx";
//
//    public static String desEncrypt(String input) throws Exception {
//        return desEncrypt(input, strKey);
//    }
//
//    public static String desEncrypt(String input, String keyValue)
//            throws Exception {
//        BASE64Decoder base64d = new BASE64Decoder();
//        DESedeKeySpec p8ksp = null;
//        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(keyValue));
//        Key key = null;
//        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);
//
//        byte[] plainBytes = (byte[]) null;
//        Cipher cipher = null;
//        byte[] cipherText = (byte[]) null;
//        // “算法/模式/填充”
//        plainBytes = input.getBytes("UTF8");
//        cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
//        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
//        // IvParameterSpec ivspec = new IvParameterSpec(myIV);
//        cipher.init(1, myKey);
//        cipherText = cipher.doFinal(plainBytes);
//        return removeBR(base64.encode(cipherText));
//    }
//
//    public static String desDecrypt(String cipherText) throws Exception {
//        return desDecrypt(cipherText, strKey);
//    }
//
//    public static void main(String[] args) throws Exception {
//    	
////    	System.out.println(DESCoder.desEncrypt("fengshiwan@126.com"));
////    	System.out.println(DESCoder.desEncrypt("Fsw198611"));
////    	System.out.println(DESCoder.desEncrypt("fengshiwan@126.com"));
////    	System.out.println(DESCoder.desEncrypt("30D"));
////    	System.out.println(DESCoder.desEncrypt("3men.net"));
////    	System.out.println(DESCoder. desEncrypt("jdbc:mysql://192.168.1.101:3306/smso?useUnicode=true&characterEncoding=UTF-8"));
////    	System.out.println("getItemBySourceandItem".toUpperCase());
////    	System.out.println(DESCoder. desEncrypt("jdbc:mysql://127.0.0.1:3306/doa_test?useUnicode=true&characterEncoding=UTF-8"));
////    	System.out.println(DESCoder. desEncrypt("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8"));
////    	System.out.println(DESCoder. desEncrypt("jdbc:mysql://127.0.0.1:3306/smso?useUnicode=true&characterEncoding=UTF-8"));
//    	System.out.println(DESCoder. desEncrypt("jdbc:mysql://localhost:3306/doa?useUnicode=true&characterEncoding=gbk&autoReconnect=true"));
////    	System.out.println(DESCoder.desDecrypt("eA4aobBdVKA="));
//////    	System.out.println(DESCoder. desEncrypt("cdzxjypt"));
//    	System.out.println(DESCoder. desEncrypt("root"));
//    	System.out.println(DESCoder. desEncrypt("123456"));
////    	System.out.println(DESCoder.desDecrypt ("CPaAFYRJy8M="));
////    	System.out.println(DESCoder.desDecrypt ("ZQjvvRjN6i9f/Ood10wS1JuLpS5Naq37LSE4imLQ5trWsdRue4DXptBabL1cs/v8AL5KiG4DX95BC4MOG+xcu5njV2ddykv5PqaSy/o4v9FpFk7f+LYdiQ=="));
////    	System.out.println(DESCoder.desDecrypt ("CTYBmcoQGp8="));
////    	System.out.println(DESCoder.desDecrypt ("YgTa6dQ0J7leTlPF8RjvsA=="));
////    	System.out.println(DESCoder. desEncrypt("ljcandly!@#$"));
////    	System.out.println(DESCoder.desDecrypt ("ZQjvvRjN6i8Kn/uj/wz5vNWT+vIkJILHIqMnW4EVdzGpzij5GzDA4aUPoibntF38v8KGgzqiQzpVniWKt2/eSX0oRfoIJexI3pwmwbjatUs="));
////    	System.out.println(DESCoder.desDecrypt ("CTYBmcoQGp8="));
////    	System.out.println(DESCoder.desDecrypt ("TQ48jT1h7SM="));
//        //System.out.println(desDecrypt("kohIZoeYanooechPlmXpdXgOGqGwXVSg"));
//       // System.out.println("sdfasdf".split(",").length);
//        
////        Client c = new Client(new URL("http://localhost/services/DrcService?wsdl"));
////		Object[] re = c.invoke("service",new Object[]{"17","1",""});
//    	
////    	Client c = new Client(new URL("http://localhost/services/DataService?wsdl"));
////		Object[] re = c.invoke("data",new Object[]{"student","12","/766JN97NZA="});
////    	
////		System.out.println(re[0]);
//		
//		
//    }
//
//    public static String desDecrypt(String cipherText, String keyValue)
//            throws Exception {
//
//        BASE64Decoder base64d = new BASE64Decoder();
//        DESedeKeySpec p8ksp = null;
//        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(keyValue));
//        Key key = null;
//        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);
//
//        Cipher cipher = null;
//        byte[] inPut = base64d.decodeBuffer(cipherText);
//        // “算法/模式/填充”
//        cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
//        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
//        // IvParameterSpec ivspec = new IvParameterSpec(myIV);
//        cipher.init(2, myKey);
//        byte[] output = cipher.doFinal(inPut);
//        return new String(output, "gb2312");
//    }
//
//    private static String removeBR(String str) {
//        StringBuffer sf = new StringBuffer(str);
//
//        for (int i = 0; i < sf.length(); ++i) {
//            if (sf.charAt(i) == '\n') {
//                sf = sf.deleteCharAt(i);
//            }
//        }
//        for (int i = 0; i < sf.length(); ++i)
//            if (sf.charAt(i) == '\r') {
//                sf = sf.deleteCharAt(i);
//            }
//
//        return sf.toString();
//    }
//}
