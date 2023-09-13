lazy val project = Project("sbt-build-jobs", file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    majorVersion := 0,
    isPublicArtefact := true,
    scalaVersion := "2.12.18",
    addSbtPlugin("uk.gov.hmrc"  % "sbt-setting-keys" % "0.3.0" ),
    resolvers += Resolver.url("HMRC-open-artefacts-ivy2", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(Resolver.ivyStylePatterns),
    scriptedLaunchOpts := { scriptedLaunchOpts.value ++
      Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false
  )
