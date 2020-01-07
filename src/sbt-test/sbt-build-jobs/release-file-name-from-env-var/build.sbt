lazy val root = (project in file("."))
  .enablePlugins(SbtBuildJobsPlugin)
  .settings(
    version := "0.1.0",
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(releaseFileName.value))
      if (content != "0.1.0") sys.error("unexpected version written: " + content)
      ()
    }
  )
