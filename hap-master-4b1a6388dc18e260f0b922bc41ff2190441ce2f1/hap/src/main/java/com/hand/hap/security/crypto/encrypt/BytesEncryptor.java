/*
 * #{copyright}#
 */
package com.hand.hap.security.crypto.encrypt;

/**
 * @author shiliyan
 *
 */
public interface BytesEncryptor {
    byte[] encrypt(byte[] paramArrayOfByte);

    byte[] decrypt(byte[] paramArrayOfByte);
}
