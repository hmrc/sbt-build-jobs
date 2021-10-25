lazy val myProject1 = (project in file("myProject1"))
  .settings(
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(sys.env.getOrElse("PROJECTS_FILENAME", sys.error("PROJECTS_FILENAME env var not provided"))))
      if (content != "myProject1\nmyProject2\nwriteprojects-multimodule") sys.error("unexpected projects written: " + content)
      ()
    }
  )

lazy val myProject2 = (project in file("myProject2"))
  .settings(
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(sys.env.getOrElse("PROJECTS_FILENAME", sys.error("PROJECTS_FILENAME env var not provided"))))
      if (content != "myProject1\nmyProject2\nwriteprojects-multimodule") sys.error("unexpected projects written: " + content)
      ()
    }
  )
