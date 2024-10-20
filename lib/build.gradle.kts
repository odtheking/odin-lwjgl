import rip.cosmos.utils.storage.exportStorageToZip
import rip.cosmos.utils.storage.importStorageFromZip

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(8))
	}
}

buildscript {
	repositories {
		maven("https://jitpack.io/")
	}

	dependencies {
		classpath("com.github.cosmosrip:utils:3025784a05")
	}
}

plugins {
	java
	`java-library`
	`maven-publish`
	id("com.gradleup.shadow") version "8.3.3"
}

group = "me.odin"
version = "0.41"

repositories {
	mavenCentral()
}

val odinJar by configurations.creating
val oneconfigJar by configurations.creating

dependencies {
	api(projects.wrapper)

	odinJar(projects.implementation) {
		targetConfiguration = "odin"
	}

	oneconfigJar(projects.implementation) {
		targetConfiguration = "oneconfig"
	}
}

java.withSourcesJar()
java.withJavadocJar()

abstract class BundleTask : DefaultTask() {
	@get:InputFile
	abstract val baseJar: RegularFileProperty

	@get:OutputFile
	abstract val outJar: RegularFileProperty

	@TaskAction
	fun execute() {
		val storage = importStorageFromZip(baseJar.get().asFile.readBytes())

		storage.putFile(
			"odin-lwjgl.jar",
			project.configurations.getByName("odinJar").incoming.artifacts.artifactFiles.first().readBytes()
		)
		storage.putFile(
			"oneconfig-lwjgl.jar",
			project.configurations.getByName("oneconfigJar").incoming.artifacts.artifactFiles.first().readBytes()
		)

		for (file in storage) {
			if (!file.endsWith(".class") && !file.endsWith(".jar")) {
				logger.lifecycle(file)
				storage.removeFile(file)
			}

			if (
				(!file.startsWith("me/odin") && !file.startsWith("org/lwjgl/system/odin")) && file.endsWith(".class")
			) {
				logger.lifecycle(file)
				storage.removeFile(file)
			}
		}

		outJar.asFile.get().writeBytes(exportStorageToZip(storage))
	}
}

val bundleTask = tasks.create<BundleTask>("bundle") {
	baseJar.set(tasks.shadowJar.flatMap { it.archiveFile })
	outJar.set(layout.buildDirectory.file("bundle.jar"))

	dependsOn(odinJar)
	dependsOn(oneconfigJar)
	dependsOn(tasks.shadowJar)
}

tasks {
	build {
		dependsOn(bundleTask)
	}
}

val bundled by configurations.creating

val bundledArtifact = artifacts.add("bundled", bundleTask.outJar) {
	type = "bundled"
	builtBy(bundleTask)
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			artifact(bundledArtifact)
		}
	}
}