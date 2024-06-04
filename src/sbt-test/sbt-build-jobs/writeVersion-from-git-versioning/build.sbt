lazy val root = (project in file("."))
  .settings(
    majorVersion := 0,
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(sys.env.getOrElse("VERSION_FILENAME", sys.error("VERSION_FILENAME env var not provided"))))
      if (content != version.value + "\n") sys.error("unexpected version written: " + content)
      ()
    }
  )
