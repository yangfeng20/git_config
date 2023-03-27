package com.maple.git.config.notify;

import com.intellij.notification.Notification;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

/**
 * @author yangfeng
 * @date : 2023/3/27 9:59
 * desc:
 */

public class LinkNotificationAction {

    /**
     * actionLinkName
     */
    private String name;

    private BiConsumer<@NotNull AnActionEvent, @NotNull Notification> actionEventHandler;

    public LinkNotificationAction() {
    }

    public LinkNotificationAction(String name, BiConsumer<@NotNull AnActionEvent, @NotNull Notification> actionEventHandler) {
        this.name = name;
        this.actionEventHandler = actionEventHandler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BiConsumer<AnActionEvent, Notification> getActionEventHandler() {
        return actionEventHandler;
    }

    public void setActionEventHandler(BiConsumer<AnActionEvent, Notification> actionEventHandler) {
        this.actionEventHandler = actionEventHandler;
    }
}
