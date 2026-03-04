---
sidebar_position: 1
sidebar_label: Getting Started
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

# Getting Started

1. Add the dependencies to your module’s build.gradle.kts file

<Tabs>
  <TabItem value="dependencies" label="Dependencies" default>

```kotlin
dependencies {
    val multiplatformPrint = "1.1.0"

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
multiplatform-print = "1.1.0"

[libraries]
multiplatform-print-compose = { group = "dev.victorlpgazolli.multiplatform-print", name = "printer-compose", version.ref = "multiplatform-print" }
multiplatform-print-core = { group = "dev.victorlpgazolli.multiplatform-print", name = "printer-core", version.ref = "multiplatform-print" }
```
```kotlin
dependencies {
    implementation(libs.multiplatform.print.core)
    implementation(libs.multiplatform.print.compose)
}
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