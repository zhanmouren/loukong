package com.koron.inwlms.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
    /**
     * RSA非对称加密，对于长度超出117的字符串做了分段加密处理
     * @author Administrator
     *
     */
    public class RSAUtil {

        private final static Logger logger = LoggerFactory.getLogger(RSAUtil.class);

        /**
         * 加密
         * @param message 需要加密的字符串
         * @param publicKey 公钥
         * @return
         */
        public static String encrypt(final String message, final String publicKey ) {
            //base64编码的公钥
            try {
                final byte[] decoded = Base64.decodeBase64(publicKey);
                final RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
                //RSA加密
                final Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, pubKey);

                final byte[] bytes = message.getBytes("UTF-8");
                final int len = bytes.length;//字符串长度
                int offset = 0;//偏移量
                int i = 0;//所分的段数
                final ByteArrayOutputStream bos = new ByteArrayOutputStream();

                while (len > offset) {
                    byte[] cache;
                    if (len - offset > 117) {
                        cache = cipher.doFinal(bytes, offset, 117);
                    } else {
                        cache = cipher.doFinal(bytes, offset, len - offset);
                    }
                    bos.write(cache);
                    i++;
                    offset = 117 * i;
                }
                bos.close();

                final String encryptMessage = Base64.encodeBase64String(bos.toByteArray());
                return encryptMessage;
            } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException
                    | IllegalBlockSizeException | BadPaddingException | IOException e) {
                logger.error("使用公钥对数据加密异常", e);
            }
            return null;
        }

        /**
         * 解密
         * @param message 需要解密的密文
         * @param privateKey 私钥
         * @return
         */
        public static String decrypt(String message, final String privateKey) {
            try {
                logger.info("message1 = " + message);
                if (message.contains(" ")) {
                    logger.info("解码前的字符串包含空格");
                    message = message.replaceAll(" ", "+");
                }
                logger.info("message2 = " + message);
                //base64编码的私钥
                final byte[] decoded = Base64.decodeBase64(privateKey);
                RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, priKey);
                //64位解码加密后的字符串
                final byte[] inputByte = Base64.decodeBase64(message.getBytes("UTF-8"));

                final int len = inputByte.length;//密文
                int offset = 0;//偏移量
                int i = 0;//段数
                final ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while (len - offset > 0) {
                    byte[] cache;
                    if (len - offset > 128) {
                        cache = cipher.doFinal(inputByte, offset, 128);
                    } else {
                        cache = cipher.doFinal(inputByte, offset, len - offset);
                    }
                    bos.write(cache);
                    i++;
                    offset = 128 * i;
                }
                bos.close();
                return new String(bos.toByteArray(), "UTF-8");
            } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException
                    | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | IOException e) {
                logger.error("使用私钥对数据解密异常", e);
            }
            return null;
        }
}