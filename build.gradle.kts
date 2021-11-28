import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.5.31"
	val springBootVersion = "2.5.6"
	val depManVer = "1.0.11.RELEASE"

	id("org.springframework.boot") version springBootVersion apply true
	id("io.spring.dependency-management") version depManVer apply true
	kotlin("jvm") version kotlinVersion apply true
	kotlin("plugin.spring") version kotlinVersion apply true
	kotlin("plugin.jpa") version kotlinVersion apply true
	kotlin("kapt") version kotlinVersion apply true
}

group = "com.caveman"
version = "1.0.0"
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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.6")
	implementation("org.springframework.boot:spring-boot-starter-data-rest:2.5.6")
	implementation("org.springframework.boot:spring-boot-starter-security:2.5.6")
	implementation("org.springframework.boot:spring-boot-starter-web:2.5.6")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
	implementation("org.flywaydb:flyway-core:8.0.2")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
	implementation("org.junit.jupiter:junit-jupiter:5.7.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools:2.5.6")
	runtimeOnly("org.postgresql:postgresql:42.3.1")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.5.6")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.6")
	testImplementation("org.springframework.security:spring-security-test:5.5.1")
//	--------------------------------- ********** STARTER DEPs. END *********--------------------------------------------
	// Swagger
	implementation("io.springfox:springfox-boot-starter:3.0.0")

	// JWT
	implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")

	// Kapt annotation processor
	kapt("org.springframework.boot:spring-boot-configuration-processor")

	// MocKK/SpringMocKK
	testImplementation("com.ninja-squad:springmockk:3.0.1")

	// For Argon 2
	implementation("org.bouncycastle:bcpkix-jdk15on:1.69")

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
