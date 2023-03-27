package com.maple.git.config.notify;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonShortcuts;
import com.intellij.openapi.actionSystem.ShortcutSet;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;

/**
 * @author yangfeng
 */
public class SyncNotification extends LinkNotification {
    public SyncNotification(String title, String message, String groupId) {
        super(title, message, groupId);
    }


}
