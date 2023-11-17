import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("org.springframework.boot") version "2.6.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    id("com.google.cloud.tools.jib") version "3.1.4"
}

application {
    mainClass.set("project.code.AppApplication")
}

val scalaBinaryVersion = "2.13"
val akkaVersion = "2.6.16"
val slickVersion = "3.3.3"
val akkaManagementVersion = "1.1.1"
val postgresDriverVersion = "42.2.24"

group = "project.code"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.typesafe.akka:akka-bom_${scalaBinaryVersion}:${akkaVersion}"))
    implementation("org.postgresql:postgresql:${postgresDriverVersion}")
    implementation("com.typesafe.akka:akka-cluster-sharding-typed_${scalaBinaryVersion}")
    implementation("com.typesafe.akka:akka-persistence-typed_${scalaBinaryVersion}")
    implementation("com.typesafe.akka:akka-serialization-jackson_${scalaBinaryVersion}")
    implementation("com.lightbend.akka:akka-persistence-jdbc_${scalaBinaryVersion}:5.0.4")
    implementation("com.typesafe.akka:akka-persistence-query_${scalaBinaryVersion}:${akkaVersion}")
    implementation("com.typesafe.slick:slick_${scalaBinaryVersion}:${slickVersion}")
    implementation("com.typesafe.slick:slick-hikaricp_${scalaBinaryVersion}:${slickVersion}")
    implementation("com.lightbend.akka.discovery:akka-discovery-kubernetes-api_${scalaBinaryVersion}:${akkaManagementVersion}")
    implementation("com.lightbend.akka.management:akka-management-cluster-bootstrap_${scalaBinaryVersion}:${akkaManagementVersion}")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
