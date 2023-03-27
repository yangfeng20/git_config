package com.maple.git.config.listener;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.maple.git.config.notify.LinkNotificationAction;
import com.maple.git.config.notify.Notifier;
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
 * 版本控制系统克隆监听器
 */
public class VcsCloneListener implements ProjectManagerListener {

    private final static String NOT_SHOW_KEY = "NoLongerShow";

    @Override
    public void projectOpened(@NotNull Project project) {
        try {
            VirtualFile virtualFile = ProjectUtil.guessProjectDir(project);
            if (Objects.isNull(virtualFile)) {
                return;
            }

            // 获取项目空间路径
            String projectPath = virtualFile.getPath();
            Path projectSpacePath = Paths.get(projectPath).getParent();
            String projectSpacePathStr = projectSpacePath.toString();

            if (AbsProjectSpaces.notProjectSpace(projectSpacePathStr)) {
                return;
            }

            DefaultProjectSpace projectSpace = new DefaultProjectSpace(projectSpacePathStr);
            DefaultGitProject gitProject = new DefaultGitProject(projectPath);

            // 项目配置和项目空间配置相同
            AbsProjectSpaces projectSpaces = new AccountProjectSpaces();
            if (projectSpaces.equalsProjectSpaceConfig(projectSpace, gitProject)) {
                return;
            }

            // 不再显示
            if (PropertiesComponent.getInstance().getBoolean(projectPath + NOT_SHOW_KEY)) {
                return;
            }

            // 通知提示是否同步配置
            SyncNotification syncNotification = new SyncNotification("GitConfig", "sync project space git config", "syncProjectGitConfig");
            syncNotification.addAction(new LinkNotificationAction("sync", (event, notification) -> {
                // 同步项目空间的配置到当前项目
                projectSpaces.syncProjectSpaceConfig(projectSpace, gitProject);

            }));

            syncNotification.addAction(new LinkNotificationAction("No longer show", (event, notification) ->
                    PropertiesComponent.getInstance().setValue(projectPath + NOT_SHOW_KEY, Boolean.TRUE)));

            syncNotification.end().showNotification(project);
        } catch (Exception e) {
            e.printStackTrace();
            Notifier.notifyError("git Config" + e.getMessage(), project);
        }
    }
}
