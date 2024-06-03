lazy val root = (project in file("."))
  .settings(
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(sys.env.getOrElse("IS_PUBLIC_ARTEFACT_FILENAME", sys.error("IS_PUBLIC_ARTEFACT_FILENAME env var not provided"))))
      if (content != "false\n") sys.error("unexpected ispublic written: " + content)
      ()
    }
  )
