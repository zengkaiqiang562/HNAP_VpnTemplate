import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")  // Google Services plugin
    id("com.google.firebase.crashlytics") // Apply the Crashlytics Gradle plugin

    id("com.deploy.plugin") // Custom Gradle Plugin in buildSrc
}

android {
    namespace = deployExt.curPkgName
    compileSdk = 32

    defaultConfig {
        applicationId = deployExt.curPkgName
        minSdk = 21
        targetSdk = 32
        versionCode = deployExt.versionCode
        versionName = deployExt.versionName

        externalNativeBuild {
            ndk {
                abiFilters += listOf(/*"x86", "x86_64",*/ "armeabi-v7a", "arm64-v8a")
            }
        }
    }

    signingConfigs {
        register("release") {
            enableV1Signing = true
            enableV2Signing = true
            keyAlias = deployExt.signPwd
            keyPassword = deployExt.signPwd
            storePassword = deployExt.signPwd
            storeFile = rootProject.file(deployExt.signPath)

            println("storeFile=${storeFile?.absolutePath}")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), deployExt.proguardPath)

//            // 设置是否要自动上传（默认为true，要自动上传），测试环境为 false，正式环境为 true
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = deployExt.uploadMappingFile
            }

            // 放开注释，aab 包体积会减小。
            // 因为会把 aab 包中 BUNDLE_MEATADATA 目录下的 debugsymbols 文件夹去掉，即不添加调试符号
            // 不添加调试符号虽然可以减小 aab 的体积，但 native crash 时无法跟踪到问题代码
            ndk {
                debugSymbolLevel = "none"
            }

            signingConfig = signingConfigs.getByName("release")
        }
    }

    sourceSets {
        getByName("main") {
            assets.srcDirs(deployExt.assetsPath)
            java.srcDirs(deployExt.javaPath)
            res.srcDirs(deployExt.resPath)
            manifest.srcFile(deployExt.manifestPath)
        }
    }

    externalNativeBuild {
        cmake {
            path(deployExt.cmakePath)
            version = "3.18.1"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    bundle {
        abi { enableSplit = false }
    }

    packagingOptions {
        jniLibs {
            useLegacyPackaging = true
        }
    }

    dataBinding {
        enable = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")

    implementation(files("libs/commons-codec-1.11.jar"))

    implementation(project(":libconfig"))

    /* Firebase */
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:30.5.0"))
    // Declare the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")

    /* Facebook */
    implementation("com.facebook.android:facebook-core:12.1.0")
    implementation("com.facebook.android:facebook-applinks:12.1.0")

    /* Admob */
    implementation("com.google.android.gms:play-services-ads:20.6.0")

    implementation("com.adjust.sdk:adjust-android:4.33.0")
    implementation("com.android.installreferrer:installreferrer:2.2")
    // Add the following if you are using the Adjust SDK inside web views on your app
    implementation("com.adjust.sdk:adjust-android-webbridge:4.33.0")
    implementation("com.google.android.gms:play-services-ads-identifier:17.0.1")
}
