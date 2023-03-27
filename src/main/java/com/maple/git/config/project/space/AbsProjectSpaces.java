package com.maple.git.config.project.space;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.maple.git.config.entity.GitConfig;
import com.maple.git.config.notify.Notifier;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author yangfeng
 * @date : 2023/3/26 11:13
 * desc:
 */

public abstract class AbsProjectSpaces {

    private static final String LINE_BREAK = "\n";
    private static final String PROJECT_SPACE_GIT_CONFIG_PATH = ".gitConfig";
    private static final String PROJECT_GIT_CONFIG_PATH = ".git" + File.separator + "config";


    /**
     * 是否为项目空间，上级目录有.gitConfig表示为项目空间
     *
     * @param path 路径
     * @return boolean
     */
    public static boolean hasProjectSpace(String path) {
        String projectSpaceConfigPath = path + File.separator + PROJECT_SPACE_GIT_CONFIG_PATH;
        File projectSpaceConfigFile = new File(projectSpaceConfigPath);

        return projectSpaceConfigFile.isDirectory() || projectSpaceConfigFile.isFile();
    }

    /**
     * 不是项目空间
     *
     * @param path 路径
     * @return boolean
     */
    public static boolean notProjectSpace(String path) {
        return !hasProjectSpace(path);
    }

    /**
     * 获取项目空间git配置路径
     *
     * @param projectSpace 项目空间
     * @return {@link String}
     */
    public static String getProjectSpaceConfigPath(ProjectSpace projectSpace) {
        return projectSpace.getProjectSpacePath() + File.separator + PROJECT_SPACE_GIT_CONFIG_PATH;
    }

    /**
     * 获取项目git配置路径
     *
     * @param gitProject git项目
     * @return {@link String}
     */
    public static String getProjectConfigPath(GitProject gitProject) {
        return gitProject.getProjectPath() + File.separator + PROJECT_GIT_CONFIG_PATH;
    }

    public boolean equalsProjectSpaceConfig(ProjectSpace space, GitProject project) {
        GitConfig spaceConfig = readGitConfig(getProjectSpaceConfigPath(space));
        GitConfig projectConfig = readGitConfig(getProjectConfigPath(project));
        return Objects.equals(spaceConfig, projectConfig);
    }

    public GitConfig readGitConfig(String gitConfigPath) {

        List<String> readAllLineList;
        try {
            readAllLineList = Files.readAllLines(Paths.get(gitConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("无法获取git配置文件");
        }


        return handlerAllLineData(readAllLineList);
    }

    private String readConfigText(String gitConfigPath) {
        try {
            return String.join("\n", Files.readAllLines(Paths.get(gitConfigPath)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("无法获取git配置文件");
        }
    }

    /**
     * 处理所有行数据
     *
     * @param readAllLineList 阅读所有线列表
     * @return {@link GitConfig}
     */
    protected abstract GitConfig handlerAllLineData(List<String> readAllLineList);

    /**
     * 同步项目空间配置
     *
     * @param space   空间
     * @param project 项目
     */
    public void syncProjectSpaceConfig(ProjectSpace space, GitProject project) {
        String oldProjectConfig = readConfigText(getProjectConfigPath(project));
        GitConfig gitConfig = readGitConfig(getProjectSpaceConfigPath(space));

        if (!oldProjectConfig.endsWith(LINE_BREAK)) {
            oldProjectConfig += LINE_BREAK;
        }
        String newProjectConfig = oldProjectConfig + gitConfig.toConfigText();

        try {
            Files.write(Paths.get(getProjectConfigPath(project)), newProjectConfig.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void syncProjectSpaceAllProjectConfig(@NotNull ProjectSpace space, Project ideaProject) {
        String projectSpacePath = space.getProjectSpacePath();
        File[] fileArr = new File(projectSpacePath).listFiles();
        List<Runnable> taskList = new ArrayList<>();
        for (File projectFile : fileArr) {
            // 项目文件夹中有.git文件夹并且其中有config文件
            boolean isProject = Optional.of(projectFile)
                    .filter(File::exists)
                    .filter(File::isDirectory)
                    .flatMap(
                            dir -> Optional.of(new File(dir, ".git"))
                                    .filter(File::exists)
                                    .filter(File::isDirectory)
                                    .flatMap(
                                            gitDir -> Optional.of(new File(gitDir, "config"))
                                                    .filter(File::exists)
                                                    .filter(f -> !f.isDirectory())
                                    )
                    )
                    .isPresent();
            // 不是git项目，没有配置文件，无需同步
            if (!isProject) {
                continue;
            }
            GitProject gitProject = new DefaultGitProject(projectFile.getAbsolutePath());
            // 是否和项目空间配置相同
            if (this.equalsProjectSpaceConfig(space, gitProject)) {
                continue;
            }

            // 同步当前项目
            taskList.add(() -> syncProjectSpaceConfig(space, gitProject));

        }

        Task.Backgroundable taskIndicator = new Task.Backgroundable(ideaProject, "SyncSpaceConfig") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                for (int i = 0; i < taskList.size();) {
                    taskList.get(i).run();
                    indicator.setFraction((double) ++i / taskList.size());
                }
            }

            @Override
            public void onFinished() {
                Notifier.notifyInfo("git config sync finish", ideaProject);
            }
        };
        ProgressManager.getInstance().run(taskIndicator);

    }
}
