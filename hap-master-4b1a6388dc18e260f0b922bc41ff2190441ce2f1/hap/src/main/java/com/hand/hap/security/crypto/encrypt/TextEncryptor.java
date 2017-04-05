/*
 * #{copyright}#
 */
package com.hand.hap.security.crypto.encrypt;

/**
 * @author shiliyan
 *
 */
public interface TextEncryptor {
    String encrypt(String paramString);

    String decrypt(String paramString);
}
