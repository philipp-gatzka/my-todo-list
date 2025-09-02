rootProject.name = "my-todo-list"

include(":my-todo-list-data")
include(":my-todo-list-rest")

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
