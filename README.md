## Памятка по запуску

**build.common.gradle для проекта**
```gradle
android {
    defaultConfig {
        minSdkVersion 26
    }
}
```

**build.dependencies.gradle для проекта**
```gradle
repositories {
    maven {
        url = 'https://maven.brott.dev/'
    }
}
dependencies {
    implementation 'com.acmerobotics.dashboard:dashboard:0.5.0'
    implementation 'org.tensorflow:tensorflow-lite:2.15.0'
    implementation 'org.tensorflow:tensorflow-lite-support:0.4.4'
    implementation 'org.tensorflow:tensorflow-lite-gpu:2.15.0'
}
```

**build.gradle для FtcRobotController**
```gradle
android {
    defaultConfig {
        minSdkVersion 26
    }
}
```

**build.gradle для TeamCode**
```gradle
plugins {
    id 'org.jetbrains.kotlin.android' version '2.0.21'
}
android {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
```

**Ассеты**
Содержимое директории `moveToAssets` скопировать в `TeamCode/src/main/assets`, если директории нет - создать