package com.maple.git.config.entity;

/**
 * @author yangfeng
 * @date : 2023/3/26 21:12
 * desc:
 */

public interface GitConfig {

    String getUserName();

    void setUserName(String userName);

    String getUserEmail();

    void setUserEmail(String userEmail);

    /**
     * 转换为配置文本
     *
     * @return {@link String}
     */
    String toConfigText();
}
