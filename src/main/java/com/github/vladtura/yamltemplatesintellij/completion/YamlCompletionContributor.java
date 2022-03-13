package com.github.vladtura.yamltemplatesintellij.completion;

import com.github.vladtura.yamltemplatesintellij.services.TemplateService;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class YamlCompletionContributor extends CompletionContributor {
    public YamlCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new CompletionProvider<>() {
            @Override
            protected void addCompletions(@NotNull final CompletionParameters parameters,
                                          @NotNull ProcessingContext context,
                                          @NotNull CompletionResultSet result) {
                TemplateService templateService = TemplateService.getInstance();
                Map<String, Object> templates = templateService.getTemplates();

                for (String templateName : templates.keySet()) {
                    LookupElement element = LookupElementBuilder.create(
                        templates.get(templateName)
                    ).withPresentableText(
                        templateName.toUpperCase().charAt(0) + templateName.substring(1)
                    ).withTypeText(
                        "YAML Template"
                    ).withCaseSensitivity(
                        false
                    ).withLookupString(
                        templateName
                    );
                    result.addElement(element);
                }
            }
        });
    }
}
