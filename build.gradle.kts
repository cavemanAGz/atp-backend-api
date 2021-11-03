import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
	kotlin("plugin.jpa") version "1.5.31"
}

group = "com.caveman"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
//	--------------------------------- ********** STARTER DEPs. START *********-----------------------------------------
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.5")
	implementation("org.springframework.boot:spring-boot-starter-data-rest:2.5.5")
	implementation("org.springframework.boot:spring-boot-starter-security:2.5.5")
	implementation("org.springframework.boot:spring-boot-starter-web:2.5.5")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
	implementation("org.flywaydb:flyway-core:8.0.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools:2.5.5")
	runtimeOnly("org.postgresql:postgresql:42.2.24.jre7")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.5.5")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.5")
	testImplementation("org.springframework.security:spring-security-test:5.5.1")
//	--------------------------------- ********** STARTER DEPs. END *********--------------------------------------------
	// Swagger
	implementation("io.springfox:springfox-boot-starter:3.0.0")

	// Argon 2 JVM
	// https://mvnrepository.com/artifact/de.mkammerer/argon2-jvm
	implementation("de.mkammerer:argon2-jvm:2.11")

	// JWT
	implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")

	// MocKK/SpringMocKK
	testImplementation("com.ninja-squad:springmockk:3.0.1")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
