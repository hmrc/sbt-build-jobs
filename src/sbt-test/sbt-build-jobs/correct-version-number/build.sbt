lazy val root = (project in file("."))
  .enablePlugins(SbtBuildJobsPlugin, SbtGitVersioning)
  .settings(
    majorVersion := 0,
    releaseFileName := "version.txt",
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(releaseFileName.value))
      if (content != "0.1.0-SNAPSHOT") sys.error("unexpected version written: " + content)
      ()
    }
  )
