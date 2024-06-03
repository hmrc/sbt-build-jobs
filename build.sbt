lazy val project = Project("sbt-build-jobs", file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    majorVersion := 0,
    isPublicArtefact := true,
    scalaVersion := "2.12.18",
    addSbtPlugin("uk.gov.hmrc"  % "sbt-setting-keys" % "0.5.0" ),
    resolvers += Resolver.url("HMRC-open-artefacts-ivy2", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(Resolver.ivyStylePatterns),
    scriptedLaunchOpts := {
      val homeDir = sys.props.get("jenkins.home")
                             .orElse(sys.props.get("user.home"))
                             .getOrElse("")
      scriptedLaunchOpts.value ++
      Seq(
        "-Xmx1024M",
        "-Dplugin.version=" + version.value,
        s"-Dsbt.override.build.repos=${sys.props.getOrElse("sbt.override.build.repos", "false")}",
        // s"-Dsbt.global.base=$sbtHome/.sbt",
        // Global base is overwritten with <tmp scripted>/global and can not be reconfigured
        // We have to explicitly set all the params that rely on base
        s"-Dsbt.boot.directory=${file(homeDir)          / ".sbt" / "boot"}",
        s"-Dsbt.repository.config=${file(homeDir)       / ".sbt" / "repositories"}",
        s"-Dsbt.boot.properties=file:///${file(homeDir) / ".sbt" / "sbt.boot.properties"}",
      )
    },
    scriptedBufferLog := false
  )
