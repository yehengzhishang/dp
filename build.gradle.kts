plugins {
    java
    kotlin("jvm") version "1.3.72"
    kotlin("kapt") version "1.3.72"
}

group = "org.yu.zz"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.dagger","dagger","2.28.3")
    kapt("com.google.dagger","dagger-compiler","2.28.3")
    implementation("com.mpatric","mp3agic","0.9.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
