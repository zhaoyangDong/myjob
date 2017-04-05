/*
 * Copyright Hand China Co.,Ltd.
 */

package com.hand.hap.core.impl;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hand.hap.core.ILanguageProvider;
import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.TopicMonitor;
import com.hand.hap.system.dto.Language;

/**
 * @author shengyang.zhou@hand-china.com
 */
@TopicMonitor(channel = { "cache.language", "topic:cache:reloaded" })
public class LanguageProviderImpl implements ILanguageProvider, InitializingBean, IMessageConsumer<String> {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    private List<Language> languages = Collections.emptyList();

    private Logger logger = LoggerFactory.getLogger(LanguageProviderImpl.class);

    @Override
    public List<Language> getSupportedLanguages() {
        return languages;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        reload();
    }

    private void reload() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.afterPropertiesSet();
        RowMapper<Language> rowMapper = new BeanPropertyRowMapper<>(Language.class);

        languages = jdbcTemplate.query("select * from sys_lang_b", rowMapper);
    }

    @Override
    public void onMessage(String message, String pattern) {
        if ("cache.language".equals(pattern)) {
            if (logger.isDebugEnabled()) {
                logger.debug("language changed, now reload cache.", message);
            }
            reload();
        } else if ("topic:cache:reloaded".equals(pattern) && "language".equals(message)) {
            if (logger.isDebugEnabled()) {
                logger.debug("language reloaded, now reload cache.");
            }
            reload();
        }
    }
}
