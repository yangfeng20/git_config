package com.maple.git.config.project.space;

import com.maple.git.config.entity.GitConfig;
import org.jetbrains.annotations.NotNull;

/**
 * @author yangfeng
 * @date : 2023/3/26 10:49
 * desc:
 */

public class DefaultProjectSpace implements ProjectSpace {

    @NotNull
    private String projectSpacePath;

    public DefaultProjectSpace(@NotNull String projectSpacePath) {
        this.projectSpacePath = projectSpacePath;
    }


    @Override
    public @NotNull String getProjectSpacePath() {
        return projectSpacePath;
    }

    @Override
    public void setProjectSpacePath(@NotNull String projectSpacePath) {
        this.projectSpacePath = projectSpacePath;
    }

    @Override
    public GitConfig getProjectSpaceConfig() {
        return null;
    }
}
