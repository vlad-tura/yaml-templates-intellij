package com.github.vladtura.yamltemplatesintellij.notifications;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;


public class NotificationManager {
    private final Project project;

    public NotificationManager(Project proj) {
        project = proj;
    }

    public void notifyError(String title, String content) {
        sendNotification(title, content, "YAML Templates Errors", NotificationType.ERROR);
    }

    public void notifyInfo(String title, String content) {
        sendNotification(title, content, "YAML Templates Info", NotificationType.INFORMATION);
    }

    public void sendNotification(
            String title,
            String content,
            String notificationGroup,
            NotificationType notificationType
    ) {
        NotificationGroupManager.getInstance().getNotificationGroup(
                notificationGroup
        ).createNotification(
                title,
                content,
                notificationType,
                null
        ).notify(
                project
        );
    }
}
