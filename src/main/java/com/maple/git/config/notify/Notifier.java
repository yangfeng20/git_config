package com.maple.git.config.notify;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.notification.NotificationGroup;
import org.jetbrains.annotations.NotNull;

public class Notifier {


    public static void notifyError(String content, Project project) {
        notify(content, NotificationType.ERROR, project);
    }

    public static void notifyWarn(String content, Project project) {
        notify(content, NotificationType.WARNING, project);
    }

    public static void notifyInfo(String content, Project project) {
        notify(content, NotificationType.INFORMATION, project);
    }

    public static void notify(String content, NotificationType type, Project project) {
        NotificationGroup notificationGroup = getNotificationGroup("ba");
        notificationGroup.createNotification(content, type)
                .notify(project);
    }

    public static NotificationGroup getNotificationGroup(@NotNull String groupId){
        NotificationGroupManager notificationGroupManager = NotificationGroupManager.getInstance();
        return notificationGroupManager.getNotificationGroup(groupId);
    }
}
