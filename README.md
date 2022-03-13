# yaml-templates-intellij

![Build](https://github.com/vlad-tura/yaml-templates-intellij/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/com.github.vladtura.yamltemplatesintellij.svg)](https://plugins.jetbrains.com/plugin/com.github.vladtura.yamltemplatesintellij)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/com.github.vladtura.yamltemplatesintellij.svg)](https://plugins.jetbrains.com/plugin/com.github.vladtura.yamltemplatesintellij)

<!-- Plugin description -->
Adds support for YAML completion based on user-defined templates.

#### How it works
- Create a new .yaml-templates folder in the root of your project.
- Start definining templates. 
- Each template should have a `prefix:` key, defining the autocomplete lookup prefix,
and a `data:` key, containing the data that will be inserted

Example:
```
prefix: template
data:
  my:
    data: here
```

Check the [documentation](https://github.com/JetBrains/intellij-platform-plugin-template)
to see examples of templates.

<!-- Plugin description end -->


## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "yaml-templates-intellij"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/vlad-tura/yaml-templates-intellij/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
