package com.maple.git.config.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.maple.git.config.project.space.AbsProjectSpaces;
import com.maple.git.config.project.space.AccountProjectSpaces;
import com.maple.git.config.project.space.DefaultProjectSpace;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author yangfeng
 * @date : 2023/3/27 1:19
 * desc:
 */

public class SyncProjectSpaceAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (Objects.isNull(project)) {
            return;
        }

        String projectPath = project.getBasePath();
        if (StringUtils.isBlank(projectPath)) {
            return;
        }
        Path projectSpacePath = Paths.get(projectPath).getParent();
        String projectSpacePathStr = projectSpacePath.toString();

        if (AbsProjectSpaces.notProjectSpace(projectSpacePathStr)) {
            return;
        }

        // 同步项目空间下所有项目的配置
        DefaultProjectSpace projectSpace = new DefaultProjectSpace(projectSpacePathStr);
        AbsProjectSpaces projectSpaces = new AccountProjectSpaces();
        projectSpaces.syncProjectSpaceAllProjectConfig(projectSpace);
    }
}
