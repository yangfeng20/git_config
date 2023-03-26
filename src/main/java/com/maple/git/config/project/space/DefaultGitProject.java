package com.maple.git.config.project.space;

import com.maple.git.config.entity.GitConfig;
import org.jetbrains.annotations.NotNull;

/**
 * @author yangfeng
 * @date : 2023/3/26 21:19
 * desc:
 */

public class DefaultGitProject implements GitProject {


    @NotNull
    private final String projectPath;

    public DefaultGitProject(@NotNull String projectPath) {
        this.projectPath = projectPath;
    }

    @Override
    public String getProjectPath() {
        return projectPath;
    }

    @Override
    public GitConfig getProjectConfig() {
        return null;
    }
}
