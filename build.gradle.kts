import org.springframework.boot.buildpack.platform.build.PullPolicy
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

val springdocOpenApiVersion = "2.5.0"
extra["springCloudVersion"] = "2023.0.2"

group = "dev.antonio3a"
version = "0.0.1-SNAPSHOT"

plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.sonarqube") version "5.0.0.4638"
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
    id("jacoco")
    id("idea")
}

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
    implementation("org.springframework.cloud:spring-cloud-starter-vault-config")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocOpenApiVersion}")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework:spring-webflux")
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
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
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

tasks.named("build") {
    finalizedBy("generateOpenApiDocs")
}

tasks {
    forkedSpringBootRun {
        doNotTrackState("See https://github.com/springdoc/springdoc-openapi-gradle-plugin/issues/102")
    }
}

repositories {
    mavenCentral()
}

openApi {
    apiDocsUrl.set("http://localhost:9088/v3/api-docs")
    outputDir.set(file("${layout.buildDirectory.get()}/docs"))
    outputFileName.set("openapi.json")
    waitTimeInSeconds.set(30)
    /*groupedApiMappings.set(
        mapOf(
            "https://localhost:8080/v3/api-docs/groupA" to "swagger-groupA.json",
            "https://localhost:8080/v3/api-docs/groupB" to "swagger-groupB.json"
        )
    )*/
    customBootRun {
        args.add("--spring.profiles.active=3A")
    }
}
