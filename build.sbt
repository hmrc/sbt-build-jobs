import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning

val pluginName = "sbt-build-jobs"

lazy val project = Project(pluginName, file("."))
  .enablePlugins(SbtPlugin, SbtAutoBuildPlugin, SbtGitVersioning)
  .settings(
    majorVersion := 0,
    makePublicallyAvailableOnBintray := false,
    crossSbtVersions := Vector("0.13.18", "1.3.4"),
    scalaVersion := "2.12.10",
    libraryDependencies ++= Seq(
    )
  )
