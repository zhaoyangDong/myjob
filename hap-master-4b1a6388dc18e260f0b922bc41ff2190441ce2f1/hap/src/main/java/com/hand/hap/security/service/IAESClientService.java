package com.hand.hap.security.service;

/**
 * @author shengyang.zhou@hand-china.com
 */
public interface IAESClientService {
    String encrypt(String password);

    String decrypt(String password);
}
