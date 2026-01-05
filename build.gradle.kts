plugins {
    java
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.sqlcanvas"
version = "0.0.1-SNAPSHOT"
description = "lesson_Spring_Boot3"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Web (Tomcat + MVC)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // DB & JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")

    // Validation (重複を削除しました)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Actuator (3回書いてあったので1つにしました)
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Documentation (Swagger/OpenAPI)
    // ★重要: 2.2.0 を消して、3.0.0 だけにしました
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.0")

    // Tools
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}