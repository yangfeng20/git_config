package com.maple.git.config.project.space;

import com.maple.git.config.entity.GitConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

        List<String> readAllLineList = Collections.emptyList();
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
}
