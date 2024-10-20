import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	java
	id("com.gradleup.shadow") version "8.3.3"
}

group = "me.odin"

repositories {
	mavenCentral()
}

val lwjgl by configurations.creating

configurations {
	compileOnly {
		extendsFrom(lwjgl)
	}
}

val lwjglNative by configurations.creating

dependencies {
	compileOnly(projects.wrapper)

	lwjgl("org.lwjgl:lwjgl:3.3.1")
	lwjgl("org.lwjgl:lwjgl-stb:3.3.1")
	lwjgl("org.lwjgl:lwjgl-nanovg:3.3.1")

	lwjglNative("org.lwjgl:lwjgl:3.3.1:natives-windows")
	lwjglNative("org.lwjgl:lwjgl-stb:3.3.1:natives-windows")
	lwjglNative("org.lwjgl:lwjgl-nanovg:3.3.1:natives-windows")
	lwjglNative("org.lwjgl:lwjgl:3.3.1:natives-linux")
	lwjglNative("org.lwjgl:lwjgl-stb:3.3.1:natives-linux")
	lwjglNative("org.lwjgl:lwjgl-nanovg:3.3.1:natives-linux")
	lwjglNative("org.lwjgl:lwjgl:3.3.1:natives-macos")
	lwjglNative("org.lwjgl:lwjgl-stb:3.3.1:natives-macos")
	lwjglNative("org.lwjgl:lwjgl-nanovg:3.3.1:natives-macos")
}

val odinJar = tasks.create<ShadowJar>("odinJar") {
	group = "odin"
	archiveClassifier.set("odin")
	configurations = listOf(lwjgl, lwjglNative)

	from(
		sourceSets.main.map { it.runtimeClasspath }
	)

	exclude("META-INF/versions/**")
	exclude("**/module-info.class")
	exclude("**/package-info.class")
	exclude("kotlin/**")

	relocate("org.lwjgl", "org.lwjgl3") {
		include("org.lwjgl.PointerBuffer")
		include("org.lwjgl.BufferUtils")
	}
}

val oneconfigJar = tasks.create<ShadowJar>("oneconfigJar") {
	group = "odin"
	archiveClassifier.set("oneconfig")
	from(
		sourceSets.main.map { it.runtimeClasspath }
	)

	relocate("me.odin.lwjgl.impl", "org.lwjgl.system.odin")
}

val odin by configurations.creating
val oneconfig by configurations.creating

artifacts {
	add("odin", odinJar.archiveFile) {
		type = "odin"
		builtBy(odinJar)
	}

	add("oneconfig", oneconfigJar.archiveFile) {
		type = "oneconfig"
		builtBy(oneconfigJar)
	}
}

tasks {
	build {
		dependsOn(odinJar)
		dependsOn(oneconfigJar)
	}
}

java {
	java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}