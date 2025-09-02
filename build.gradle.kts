plugins {
  alias(libs.plugins.sonar)
}

sonar {
  properties {
    property("sonar.sourceEncoding","UTF-8")
    property("sonar.projectKey", "ch.gatzka:my-todo-list")
    property("sonar.organization", "philippgatzka")
    property("sonar.coverage.jacoco.xmlReportPaths", "my-todo-list-data/build/reports/jacoco/test/jacocoTestReport.xml,my-todo-list-rest/build/reports/jacoco/test/jacocoTestReport.xml")
  }
}

allprojects {
    group = "ch.gatzka"

    repositories {
        mavenCentral()
    }

}
