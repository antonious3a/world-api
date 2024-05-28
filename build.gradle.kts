import org.springdoc.openapi.gradle.plugin.OpenApiGeneratorTask
import org.springframework.boot.buildpack.platform.build.PullPolicy
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun

val springdocOpenApiVersion = "2.5.0"
val keycloakVersion = "24.0.4"
extra["springCloudVersion"] = "2023.0.1"

group = "dev.antonio3a"
version = "0.0.1-SNAPSHOT"

plugins {
    java
    id("org.springframework.boot") version "3.2.6"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.sonarqube") version "5.0.0.4638"
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
    id("jacoco")
    id("idea")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
    implementation("org.springframework.cloud:spring-cloud-starter-vault-config")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.zipkin.reporter2:zipkin-reporter-brave")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocOpenApiVersion}")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

jacoco {
    toolVersion = "0.8.11"
}

sonar {
    properties {
        property("sonar.junit.reportPaths", "build/test-results/test/*.xml")
    }
}

tasks.sonar {
    dependsOn(tasks.test)
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName = "antonio3a/${project.name}:${project.version}"
    pullPolicy = PullPolicy.IF_NOT_PRESENT
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
    }
}
tasks.register<Test>("integrationTest") {
    description = "Runs integration tests"
    group = "verification"
    //testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    //classpath = sourceSets["integrationTest"].runtimeClasspath
    //shouldRunAfter("test")
}

/*sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
        runtimeClasspath += output + compileClasspath
    }
}*/

tasks.register<BootRun>("preIntegrationTest") {
    group = "verification"
    description = "Starts the Spring Boot application before integration tests"
    doFirst {
        springBoot.mainClass.set("dev.antonio3a.worldapi.WorldApiApplication")
    }
}

tasks.register<BootRun>("postIntegrationTest") {
    group = "verification"
    description = "Stops the Spring Boot application after integration tests"
    doLast {
        println("Spring Boot application stopped.")
    }
}

tasks.named<OpenApiGeneratorTask>("generateOpenApiDocs") {
    group = "documentation"
    description = "Generates OpenAPI documentation"
    dependsOn("integrationTest")
}

tasks.named("integrationTest") {
    dependsOn("preIntegrationTest")
    finalizedBy("postIntegrationTest", "generateOpenApiDocs")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}
