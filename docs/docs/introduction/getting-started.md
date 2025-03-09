---
sidebar_position: 1
sidebar_label: Getting Started
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Getting Started

1. Add github gradle registry to your repositories ([github oficial documentation](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry))

```kotlin
repositories {
    // ...other repositories...
    maven("https://maven.pkg.github.com/victorlpgazolli/multiplatform-print") {
        username = System.getenv("GITHUB_USERNAME")
        password = System.getenv("GITHUB_PAT_TOKEN")
    }
}
```

2. Add the dependencies to your module’s build.gradle file

<Tabs>
  <TabItem value="dependencies" label="Dependencies" default>

```kotlin
dependencies {
    val multiplatformPrint = "0.0.1"

    // Printer core utils
    implementation("dev.victorlpgazolli.multiplatform-print:printer-core:$multiplatformPrint")

    // Compose integration
    implementation("dev.victorlpgazolli.multiplatform-print:printer-compose:$multiplatformPrint")
}
```

  </TabItem>
  <TabItem value="version-catalog" label="Version Catalog">

```kotlin
[versions]
multiplatformPrint = "0.0.1"

[libraries]
multiplatform-print-core = { group = "dev.victorlpgazolli.multiplatform-print", name = "printer-core", version.ref = "multiplatformPrint" }
multiplatform-print-compose = { group = "dev.victorlpgazolli.multiplatform-print", name = "printer-compose", version.ref = "multiplatformPrint" }
```


  </TabItem>
</Tabs>




:::info

Version from this doc can be outdated. [Check the latest version on github](https://github.com/victorlpgazolli/multiplatform-print/releases)

:::





# Platforms Compatibility

|  | Android | iOS | Desktop | Wasm/JS |
| --- | --- | --- | --- | --- |
| printer-core | :white_check_mark: | :white_check_mark: | :white_check_mark: | :x: |
| printer-compose | :white_check_mark: | :white_check_mark: | :white_check_mark: | :x: |