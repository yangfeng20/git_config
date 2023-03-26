package com.maple.git.config.project.space;

import com.maple.git.config.entity.GitConfig;

/**
 * @author yangfeng
 * @date : 2023/3/26 21:05
 * desc:
 */

public interface ProjectSpace {


    /**
     * 获取项目空间路径
     *
     * @return {@link String}
     */
    String getProjectSpacePath();

    /**
     * 设置项目空间路径
     *
     * @param projectSpacePath 工作空间路径
     */
    void setProjectSpacePath(String projectSpacePath);

    /**
     * 获取项目空间配置
     *
     * @return {@link GitConfig}
     */
    GitConfig getProjectSpaceConfig();
}
