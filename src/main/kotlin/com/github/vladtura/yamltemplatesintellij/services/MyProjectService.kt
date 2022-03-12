package com.github.vladtura.yamltemplatesintellij.services

import com.intellij.openapi.project.Project
import com.github.vladtura.yamltemplatesintellij.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
