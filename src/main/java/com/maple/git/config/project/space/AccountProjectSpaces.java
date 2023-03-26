package com.maple.git.config.project.space;

import com.maple.git.config.entity.AccountGitConfig;
import com.maple.git.config.entity.GitConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangfeng
 * @date : 2023/3/26 21:44
 * desc:
 */

public class AccountProjectSpaces extends AbsProjectSpaces {

    public static final String TARGET_TEXT = "[user]";
    private static final String END = "\\[.+\\]";

    public AccountProjectSpaces() {
        super();
    }

    @Override
    protected GitConfig handlerAllLineData(List<String> readAllLineList) {
        Map<String, String> userData = new HashMap<>(16);
        boolean isInUserSection = false;
        for (String line : readAllLineList) {
            line = line.trim();

            // 进入 [user] 段落
            if (TARGET_TEXT.equals(line)) {
                isInUserSection = true;
                // 离开 [user] 段落
            } else if (isInUserSection && line.matches(END)) {
                break;
                // 处理 [user] 段落中的每一行数据
            } else if (isInUserSection && !line.isEmpty()) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    userData.put(parts[0].trim(), parts[1].trim());
                }
            }
        }

        return buildField(userData);
    }

    private AccountGitConfig buildField(Map<String, String> userData) {
        AccountGitConfig accountGitConfig = new AccountGitConfig();
        accountGitConfig.setUserName(userData.get("user"));
        accountGitConfig.setUserEmail(userData.get("email"));
        return accountGitConfig;
    }
}
