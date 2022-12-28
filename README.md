# SkyLib
[![Repo](https://github.com/Skyslycer/SkyLib/actions/workflows/push.yml/badge.svg)](https://repo.skyslycer.de/#/releases/de/skyslycer/skylib)
[![Docs](https://github.com/Skyslycer/SkyLib/actions/workflows/docs.yml/badge.svg)](https://skyslycer.github.io/SkyLib/)

[![Version](https://img.shields.io/maven-metadata/v?label=SkyRepo&metadataUrl=https%3A%2F%2Frepo.skyslycer.de%2Freleases%2Fde%2Fskyslycer%2Fskylib%2Fmaven-metadata.xml)](https://repo.skyslycer.de/#/releases/de/skyslycer/skylib)
[![License](https://img.shields.io/github/license/Skyslycer/SkyLib)](https://opensource.org/licenses/AGPL-3.0)

SkyLib is a useful utility library I use to create many plugins containing many boilerplate classes
like plugin update checkers, actions, messages and utilities.

## Usage
Replace `VERSION` by the version above.
### Gradle Groovy
```groovy
repositories {
    maven {
        url "https://repo.skyslycer.de/releases"
    }
}

dependencies {
    implementation "de.skyslycer:skylib:VERSION"
}
```

### Gradle Kotlin
```kotlin
repositories {
    implementation("https://repo.skyslycer.de/releases")
}

dependencies {
    implementation("de.skyslycer:skylib:VERSION")
}
```