plugins {
    `kotlin-dsl`
}
repositories{
    mavenCentral()
    google()
    gradlePluginPortal()
}
dependencies {
//    implementation("com.android.tools.build:gradle:7.0.2")
//    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    implementation(gradleApi())
    implementation(localGroovy())
}