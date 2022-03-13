package com.github.vladtura.yamltemplatesintellij.services;

import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vladtura.yamltemplatesintellij.notifications.NotificationManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 Service that loads and holds templates
 {@link #loadTemplates()} needs to be called to load YAMLs into memory
 and {@link #getTemplates()} to get them.
 **/
@Service
public final class TemplateService {
    private Map<String, Object> TEMPLATES = new HashMap<>();
    private NotificationManager MANAGER;

    private TemplateService() {
        loadTemplates();
    }

    public static TemplateService getInstance() {
        return ApplicationManager.getApplication().getService(
            TemplateService.class
        );
    }

    public void loadTemplates() {
        List<VirtualFile> files = getTemplateFiles();
        MANAGER.notifyInfo("Loaded YAML temples", "");
        TEMPLATES = convertFilesToMap(files);
    }

    public Map<String, Object> getTemplates() {
        return TEMPLATES;
    }

    private DumperOptions getDumperOptions() {
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return options;
    }

    private List<VirtualFile> getTemplateFiles() {
        final String templateDir = ".yaml-templates";

        // Search all open projects for templates
        final Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        List<VirtualFile> templates = new ArrayList<>();

        for (Project project : openProjects) {
            templates.addAll(getDirectoryChildren(project, templateDir));
            MANAGER = new NotificationManager(project);
        }

        return templates;
    }

    private List<VirtualFile> getDirectoryChildren(Project project, String dirPath) {
        LocalFileSystem localFileSystem = LocalFileSystem.getInstance();
        String directory = project.getBasePath();
        VirtualFile virtualDir = localFileSystem.findFileByPath(directory + "/" + dirPath);

        if (virtualDir == null) {
            return new ArrayList<>();
        }

        return Arrays.asList(virtualDir.getChildren());
    }

    private Map<String, Object> convertFilesToMap(List<VirtualFile> files) {
        Map<String, Object> loadedTemplates = new HashMap<>();
        files.forEach(file -> {
            Map<String, Object> yaml = loadYamlFromFile(file);
            loadedTemplates.putAll(yaml);
        });
        return loadedTemplates;
    }

    private Map<String, Object> loadYamlFromFile(VirtualFile file) {
        Map<String, Object> templateMap = new HashMap<>();

        try {
            Yaml template = new Yaml(getDumperOptions());
            Iterable<Object> subTemplates = template.loadAll(file.getInputStream());

            // A file cn contain multiple templates, delimited by ---
            for (Object subTemplate : subTemplates) {
                // Convert to an object
                Map<String, Object> map = new ObjectMapper().convertValue(
                        subTemplate,
                        new TypeReference<>() {}
                );
                if (map == null) {
                    continue;
                }

                String prefix = (String) map.get("prefix");
                Object data = map.get("data");

                if (prefix == null || prefix.equals("") || data == null) {
                    continue;
                }

                templateMap.put(prefix, template.dump(data));
            }
        } catch (Exception e) {
            MANAGER.notifyError("Cannot load template", file.getPath() + "\n" + e);
        }
        return templateMap;
    }
}
