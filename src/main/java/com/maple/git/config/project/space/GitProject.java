package com.maple.git.config.project.space;

import com.maple.git.config.entity.GitConfig;

/**
 * @author yangfeng
 * @date : 2023/3/26 21:18
 * desc:
 */

public interface GitProject {

    /**
     * 获取项目路径
     *
     * @return {@link String}
     */
    String getProjectPath();

    /**
     * 获取当前项目配置
     *
     * @return {@link GitConfig}
     */
    GitConfig getProjectConfig();
}
