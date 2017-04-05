/*
 * #{copyright}#
 */
package com.hand.hap.core.i18n;

import java.text.MessageFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractMessageSource;

import com.hand.hap.cache.impl.HashStringRedisCache;

/**
 * CacheMessageSource.
 * 
 * @author shengyang.zhou@hand-china.com
 */
public class CacheMessageSource extends AbstractMessageSource {
    private static final String SINGLE_QUOTES_REPLACEMENT = "&#39;";
    private static final String DOUBLE_QUOTES_REPLACEMENT = "&#34;";

    @Autowired
    private ApplicationContext applicationContext;

    private HashStringRedisCache<String> promptCache;

    public CacheMessageSource() {
        reload();
    }

    public void reload() {
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        return createMessageFormat(resolveCodeWithoutArguments(code, locale), locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        if (promptCache == null) {
            promptCache = (HashStringRedisCache<String>) applicationContext.getBean("promptCache");
        }
        String code2 = StringUtils.lowerCase(code);
        String pmt = promptCache.getValue(code2 + "." + locale);
        if (pmt == null) {
            return code;
        }
        return replaceQuote(pmt);
    }

    private String replaceQuote(String str) {
        int idx = -1;
        char c = 0;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == '\'' || c == '"') {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            return str;
        }
        StrBuilder sb = new StrBuilder(str.length() + 32);
        sb.append(str.substring(0, idx));
        if (c == '"') {
            sb.append(DOUBLE_QUOTES_REPLACEMENT);
        } else {
            sb.append(SINGLE_QUOTES_REPLACEMENT);
        }
        for (int i = idx + 1; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == '"') {
                sb.append(DOUBLE_QUOTES_REPLACEMENT);
            } else if (c == '\'') {
                sb.append(SINGLE_QUOTES_REPLACEMENT);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
