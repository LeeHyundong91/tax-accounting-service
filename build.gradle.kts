import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.7.10"
    kotlin("jvm") version "1.7.21"
    kotlin("kapt") version "1.7.10"
    kotlin("plugin.spring") version "1.7.21"
    kotlin("plugin.jpa") version "1.7.21"
    kotlin("plugin.serialization") version "1.5.0"

}
noArg {
    annotation("javax.persistence.Entity")
}
//allOpen {
//    annotation("javax.persistence.Entity")
//    annotation("javax.persistence.MappedSuperclass")
//    annotation("javax.persistence.Embeddable")
//}

group = "net.dv"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://artifactory-oss.prod.netflix.net/artifactory/maven-oss-candidates") }
    maven { url = uri("https://repo.spring.io/milestone") }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

extra["springCloudVersion"] = "2022.0.0"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    /* Spring Cloud */
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

    /*Querydsl*/
    val queryDslVersion = dependencyManagement.importedProperties["querydsl.version"]
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")


    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")

    /*AWS*/
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
    implementation("com.amazonaws:aws-java-sdk-sts:1.12.368")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.386")


    /*XLS*/
    implementation("com.github.javaxcel:javaxcel-core:0.9.1")
    implementation("org.apache.poi:poi-ooxml:5.2.2")

    /*XLSX*/
    implementation("org.dhatim:fastexcel:0.15.2")
    implementation("org.dhatim:fastexcel-reader:0.15.2")

    /*JSON*/
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}