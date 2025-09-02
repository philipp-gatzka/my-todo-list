plugins {
  alias(libs.plugins.sonar)
}

sonar {
  properties {
    property("sonar.sourceEncoding","UTF-8")
    property("sonar.projectKey", "ch.gatzka:my-todo-list")
    property("sonar.organization", "philippgatzka")
  }
}

allprojects {
    group = "ch.gatzka"

    repositories {
        mavenCentral()
    }

}
