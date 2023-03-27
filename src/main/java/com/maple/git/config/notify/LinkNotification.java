package com.maple.git.config.notify;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangfeng
 * @date : 2023/3/27 1:50
 * desc:
 */

public class LinkNotification {

    protected String title;

    protected String message;

    /**
     * 挂载到的groupId
     */
    protected String groupId;

    protected Notification notification;

    protected List<LinkNotificationAction> linkNotificationActionList = new ArrayList<>();

    public LinkNotification(String title, String message, String groupId) {
        this.title = title;
        this.message = message;
        this.groupId = groupId;
        NotificationType type = NotificationType.INFORMATION;
        this.notification = new Notification(groupId, title, message, type);
    }

    public void addAction(@NotNull LinkNotificationAction linkNotificationAction) {
        linkNotificationActionList.add(linkNotificationAction);
    }

    public LinkNotification end() {
        if (CollectionUtils.isEmpty(linkNotificationActionList)) {
            throw new RuntimeException("通知内无link超链接");
        }
        init();
        return this;
    }


    protected void init() {
        notification.setListener(new NotificationListener.Adapter() {
            @Override
            protected void hyperlinkActivated(@NotNull Notification notification, @NotNull HyperlinkEvent e) {
                System.out.println();
            }
        });

        for (LinkNotificationAction action : linkNotificationActionList) {
            NotificationAction notificationAction = new NotificationAction(action.getName()) {
                @Override
                public void actionPerformed(@NotNull AnActionEvent event, @NotNull Notification notification) {
                    action.getActionEventHandler().accept(event, notification);
                    notification.expire();
                }
            };
            notification.addAction(notificationAction);
        }
    }


    public void showNotification(Project project) {
        Notifications.Bus.notify(notification, project);
    }
}
