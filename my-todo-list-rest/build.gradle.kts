plugins {
  id("java")
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.lombok)
}

dependencies {
  implementation(project(":my-todo-list-data"))
  implementation(platform(libs.spring.boot.dependencies))
  implementation(libs.spring.boot.web)
  implementation(libs.spring.boot.security)

  implementation(libs.jwt.api)
  implementation(libs.jwt.impl)
  implementation(libs.jwt.jackson)

  runtimeOnly("org.postgresql:postgresql")

  testRuntimeOnly("com.h2database:h2")

  implementation(libs.spring.boot.devtools)
}
