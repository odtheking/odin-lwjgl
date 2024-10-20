plugins {
	java
}

group = "me.odin"

repositories {
	mavenCentral()
}

dependencies {
	compileOnly("org.lwjgl:lwjgl:3.3.1")
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(8))
	}
}