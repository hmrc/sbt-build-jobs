lazy val root = (project in file("."))
  .settings(
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(sys.env.getOrElse("PROJECTS_FILENAME", sys.error("PROJECTS_FILENAME env var not provided"))))
      if (content != "root") sys.error("unexpected projects written: " + content)
      ()
    }
  )
