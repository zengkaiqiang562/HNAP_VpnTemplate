plugins {
    id("com.android.library")

    id("com.deploy.plugin") // Custom Gradle Plugin in buildSrc
}

android {
    println("curLibPkgName=${deployExt.curLibPkgName}")
    namespace = deployExt.curLibPkgName
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        buildConfigField("boolean", "VPN_DEBUG", "${deployExt.debug}")
        buildConfigField("boolean", "ENABLE_LOG", "${deployExt.enableLog}")
        buildConfigField("boolean", "LIMIT_VPN", "${deployExt.limitVPN}")

        buildConfigField("String", "PROTOCOL_PRIVACY", "\"${deployExt.urlPrivacy}\"")
        buildConfigField("String", "PROTOCOL_SERVICE", "\"${deployExt.urlTerms}\"")
        buildConfigField("String", "FEEDBACK_EMAIL", "\"${deployExt.emailFeedback}\"")

        buildConfigField("String", "BASE_URL", "\"${deployExt.baseUrl}\"")
        buildConfigField("String", "PATH_CONFIG", "\"${deployExt.pathConfig}\"")
        buildConfigField("String", "PATH_VPN_NODE_LIST", "\"${deployExt.pathVpnNodeList}\"")
        buildConfigField("String", "PATH_VPN_NODE_INFO", "\"${deployExt.pathVpnNodeInfo}\"")

        buildConfigField("String", "PATH_LOCATION_1", "\"${deployExt.pathLocation1}\"")
        buildConfigField("String", "PATH_LOCATION_2", "\"${deployExt.pathLocation2}\"")
        buildConfigField("String", "PATH_LOCATION_3", "\"${deployExt.pathLocation3}\"")
        buildConfigField("String", "PATH_LOCATION_4", "\"${deployExt.pathLocation4}\"")

        resValue("string", "facebook_id", deployExt.facebookId)
        resValue("string", "facebook_token", deployExt.facebookToken)
        resValue("string", "adjust_token", deployExt.adjustToken)
        resValue("string", "admob_id", deployExt.admobId)

        buildConfigField("String", "FACEBOOK_ID", "\"${deployExt.facebookId}\"")
        buildConfigField("String", "FACEBOOK_TOKEN", "\"${deployExt.facebookToken}\"")
        buildConfigField("String", "ADJUST_TOKEN", "\"${deployExt.adjustToken}\"")
    }

    buildTypes {
        release {
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), deployExt.proguardPath)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main") {
            assets.srcDirs(deployExt.assetsPath)
            java.srcDirs(deployExt.javaPath)
            res.srcDirs(deployExt.resPath)
            manifest.srcFile(deployExt.manifestPath)
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
}