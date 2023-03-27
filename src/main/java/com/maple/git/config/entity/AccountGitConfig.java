package com.maple.git.config.entity;

import com.maple.git.config.project.space.AccountProjectSpaces;

import java.util.Objects;

/**
 * @author yangfeng
 * @date : 2023/3/26 21:12
 * desc:
 */

public class AccountGitConfig implements GitConfig {

    private final static String TITLE = AccountProjectSpaces.TARGET_TEXT;

    private String userName;

    private String userEmail;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toConfigText() {
        return TITLE + "\n\tname = " + userName + "\n\temail = " + userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountGitConfig that = (AccountGitConfig) o;
        return Objects.equals(userName, that.userName) && Objects.equals(userEmail, that.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userEmail);
    }
}
