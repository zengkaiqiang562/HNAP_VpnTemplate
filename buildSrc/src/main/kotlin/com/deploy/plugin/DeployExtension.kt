package com.deploy.plugin

import org.gradle.api.Project
import java.util.*
import java.util.regex.Pattern

open class DeployExtension {

    val project: Project = DeployLoader.project

    val outputName = lazy { DeployLoader.map["outputName"].let { if (it is String) it else "" } }.value
    val versionName = lazy { DeployLoader.map["versionName"].let { if (it is String) it else "" } }.value
    val versionCode = lazy { DeployLoader.map["versionCode"].let { if (it is Int) it else -1 } }.value

    val zipwd = lazy { DeployLoader.map["zipwd"].let { if (it is String) it else "" } }.value

    val signPwd = lazy { DeployLoader.map["signPwd"].let { if (it is String) it else "" } }.value
    val signPath = lazy { DeployLoader.map["signPath"].let { if (it is String) it else "" } }.value // 保存在根目录下的 jks 文件路径

    val enableLog = lazy { DeployLoader.map["enableLog"].let { if (it is Boolean) it else false } }.value
    val limitVPN = lazy { DeployLoader.map["limitVPN"].let { if (it is Boolean) it else false } }.value
    val debug = lazy { DeployLoader.map["debug"].let { if (it is Boolean) it else false } }.value
    val uploadMappingFile = lazy { DeployLoader.map["uploadMappingFile"].let { if (it is Boolean) it else false } }.value

    val debugPkgName = lazy { DeployLoader.map["debugPkgName"].let { if (it is String) it else "" } }.value
    val releasePkgName = lazy { DeployLoader.map["releasePkgName"].let { if (it is String) it else "" } }.value
    val curPkgName = lazy { if (debug) debugPkgName else releasePkgName }.value


    val debugLibPrefix = lazy { DeployLoader.map["debugLibPrefix"].let { if (it is String) it else "" } }.value
    val releaseLibPrefix = lazy { DeployLoader.map["releaseLibPrefix"].let { if (it is String) it else "" } }.value
    // 注意：lib 模块的包名命名规则必须是 deploy.json 中顶端 libPrefix + 模块名（不包含开头的 lib ）
    val debugLibPkgName = lazy { DeployLoader.map["debugLibPrefix"].let { if (it is String) libName(it, project) else "" } }.value
    val releaseLibPkgName = lazy { DeployLoader.map["releaseLibPrefix"].let { if (it is String) libName(it, project) else "" } }.value
    val curLibPkgName = lazy { if (debug) debugLibPkgName else releaseLibPkgName }.value


    val debugMainDir = lazy { DeployLoader.map["debugMainDir"].let { if (it is String) it else "" } }.value
    val releaseMainDir = lazy { DeployLoader.map["releaseMainDir"].let { if (it is String) it else "" } }.value
    val curMainDir = lazy { if (debug) debugMainDir else releaseMainDir }.value

    val assetsPath = "${curMainDir}/assets"
    val javaPath = "${curMainDir}/java"
    val resPath = "${curMainDir}/res"
    val manifestPath = "${curMainDir}/AndroidManifest.xml"
    val cmakePath = "${curMainDir}/cpp/CMakeLists.txt"
    val proguardPath = "${curMainDir}/proguard-rules.pro"

    val facebookId = lazy { DeployLoader.map["facebookId"].let { if (it is String) it else "" } }.value
    val facebookToken = lazy { DeployLoader.map["facebookToken"].let { if (it is String) it else "" } }.value

    val adjustToken = lazy { DeployLoader.map["adjustToken"].let { if (it is String) it else "" } }.value

    val admobId = lazy { DeployLoader.map["admobId"].let { if (it is String) it else "" } }.value


    val urlPrivacy = lazy { DeployLoader.map["urlPrivacy"].let { if (it is String) it else "" } }.value
    val urlTerms = lazy { DeployLoader.map["urlTerms"].let { if (it is String) it else "" } }.value
    val emailFeedback = lazy { DeployLoader.map["emailFeedback"].let { if (it is String) it else "" } }.value


    val baseUrl = lazy { DeployLoader.map["baseUrl"].let { if (it is String) it else "" } }.value
    val pathConfig = lazy { DeployLoader.map["pathConfig"].let { if (it is String) it else "" } }.value
    val pathVpnNodeList = lazy { DeployLoader.map["pathVpnNodeList"].let { if (it is String) it else "" } }.value
    val pathVpnNodeInfo = lazy { DeployLoader.map["pathVpnNodeInfo"].let { if (it is String) it else "" } }.value

    val pathLocation1 = "http://ip-api.com/json"
    val pathLocation2 = "http://www.geoplugin.net/json.gp"
    val pathLocation3 = "https://ipapi.co/json"
    val pathLocation4 = "http://ipwho.is/"

    val ckey = lazy { DeployLoader.map["ckey"].let { if (it is String) it else "" } }.value
    val civ = lazy { DeployLoader.map["civ"].let { if (it is String) it else "" } }.value
    val skey = lazy { DeployLoader.map["civ"].let { if (it is String) it else "" } }.value
    val siv = lazy { DeployLoader.map["siv"].let { if (it is String) it else "" } }.value

    val sign = lazy { DeployLoader.map["sign"].let { if (it is String) it else "" } }.value // 根据 jks 文件生成的 SHA1 签名字符串
    val localConfig = lazy { DeployLoader.map["localConfig"].let { if (it is String) it else "" } }.value // 加密后的本地全局配置
    val vpnCert = lazy { DeployLoader.map["vpnCert"].let { if (it is String) it else "" } }.value // 加密后的本地全局配置

    companion object {
        fun libName(prefix: String, project: Project): String {
            val name = project.name
            return when {
                name == "app" -> ""
                name.startsWith("lib") -> "$prefix.${name.substring("lib".length).toLowerCase(Locale.ROOT)}"
                else -> "$prefix.${name.toLowerCase(Locale.ROOT)}"
            }
        }
    }
}