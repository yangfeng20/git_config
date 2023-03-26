package com.maple.git.config.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.maple.git.config.project.space.AccountProjectSpaces;
import com.maple.git.config.project.space.DefaultGitProject;
import com.maple.git.config.project.space.DefaultProjectSpace;
import com.maple.git.config.project.space.AbsProjectSpaces;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author maple
 */
public class VcsCloneListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        VirtualFile virtualFile = ProjectUtil.guessProjectDir(project);
        if (Objects.isNull(virtualFile)) {
            return;
        }

        // 获取项目工具空间路径
        Path projectSpacePath = Paths.get(virtualFile.getPath()).getParent();
        String projectSpacePathStr = projectSpacePath.toString();

        if (!AbsProjectSpaces.hasProjectSpace(projectSpacePathStr)) {
            return;
        }

        DefaultProjectSpace projectSpace = new DefaultProjectSpace(projectSpacePathStr);
        DefaultGitProject gitProject = new DefaultGitProject(virtualFile.getPath());

        // 项目配置和项目空间配置相同
        AbsProjectSpaces projectSpaces = new AccountProjectSpaces();
        if (projectSpaces.equalsProjectSpaceConfig(projectSpace, gitProject)) {
            return;
        }

        // todo 提示是否同步项目[提示弹框]；同步所有项目是，遍历项目空间下的所有项目。

        // 同步项目空间的配置到当前项目
        projectSpaces.syncProjectSpaceConfig(projectSpace, gitProject);
    }
}
