package com.maple.git.config.listener;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcsUtil.VcsUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author maple
 */
public class VcsCloneListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        VirtualFile virtualFile = ProjectUtil.guessProjectDir(project);

        String basePath = project.getBasePath();
        // 获取版本控制的根目录路径
        VirtualFile canonicalFile = project.getBaseDir().getCanonicalFile();

        // 监听项目是否是从版本控制的根目录克隆而来
        if (project.isInitialized() && project.isOpen() && VcsUtil.getVcsFor(project, canonicalFile) != null) {
            System.out.println("Hello World!");
        }
    }
}
