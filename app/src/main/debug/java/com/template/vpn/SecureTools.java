package com.template.vpn;

import androidx.annotation.NonNull;

public class SecureTools {

    static {
        System.loadLibrary("stools");
    }

    private static final String TAG = "SecureTools";
    private static final String AES = "AES";

    public static String decrypt(String enData) {
        return null;
    }

    public static String encrypt(String srcData) {
        return null;
    }

    public static native byte[] nEncrypt(@NonNull String origin);

    public static native byte[] nDecrypt(@NonNull byte[] data);

    public static native String nGetCert();

    public static native String nGetConfig();

    /**
     * 先压缩再加密
     *
     * @param origin 原始数据
     */
    private static byte[] encrypt(@NonNull String sign, @NonNull String origin, @NonNull String strKey) {
        return null;
    }

    /**
     * 先解密再解压
     */
    private static byte[] decrypt(@NonNull byte[] data, @NonNull String strKey) {
        return null;
    }

}
