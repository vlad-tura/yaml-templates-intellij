package com.github.vladtura.yamltemplatesintellij.listener;

import com.github.vladtura.yamltemplatesintellij.services.TemplateService;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class VfsListener implements BulkFileListener {
    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        boolean shouldReload = false;
        for (VFileEvent event : events) {
            if (event.getPath().contains(".yaml-templates")) {
                shouldReload = true;
            }
        }
        if (shouldReload) {
            TemplateService templateService = TemplateService.getInstance();
            templateService.loadTemplates();
        }
    }
}
