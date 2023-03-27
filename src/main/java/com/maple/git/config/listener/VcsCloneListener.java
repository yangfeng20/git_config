package com.maple.git.config.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.maple.git.config.notify.LinkNotificationAction;
import com.maple.git.config.notify.SyncNotification;
import com.maple.git.config.project.space.AbsProjectSpaces;
import com.maple.git.config.project.space.AccountProjectSpaces;
import com.maple.git.config.project.space.DefaultGitProject;
import com.maple.git.config.project.space.DefaultProjectSpace;
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

        // 通知提示是否同步配置
        SyncNotification syncNotification = new SyncNotification("GitConfig", "sync project space git config", "syncProjectGitConfig");
        syncNotification.addAction(new LinkNotificationAction("sync", (event, notification) -> {
            System.out.println(event);
            // 同步项目空间的配置到当前项目
            projectSpaces.syncProjectSpaceConfig(projectSpace, gitProject);

        }));
        syncNotification.addAction(new LinkNotificationAction("No longer show", (event, notification) -> {
            System.out.println(event);
        }));

        syncNotification.end().showNotification(project);
    }
}
