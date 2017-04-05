/*
 * #{copyright}#
 */

package com.hand.hap.account.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * InvalidAccountException.
 * 
 * <p> Created on 15/12/30
 *
 * @author jessen
 */
public class InvalidAccountException extends BaseException {

    private static final long serialVersionUID = -2410004314859717665L;

    public InvalidAccountException(String code) {
        this(code, code, new Object[0]);
    }

    public InvalidAccountException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
}
