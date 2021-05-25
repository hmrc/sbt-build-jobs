lazy val root = (project in file("."))
  .settings(
    version := "0.1.0",
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(sys.env.getOrElse("RELEASE_FILENAME", sys.error("RELEASE_FILENAME env var not provided"))))
      if (content != version.value) sys.error("unexpected version written: " + content)
      ()
    }
  )
