val expectedContent =
  """|- module: myProject1
     |  folder: my-project-1
     |- module: myProject2
     |  folder: my-project-2
     |- module: writeprojects-multimodule
     |  folder: writeProjects-multimodule""".stripMargin

lazy val myProject1 = (project in file("my-project-1"))
  .settings(
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(sys.env.getOrElse("PROJECTS_FILENAME", sys.error("PROJECTS_FILENAME env var not provided"))))
      if (content != expectedContent) sys.error("unexpected projects written: " + content)
      ()
    }
  )

lazy val myProject2 = (project in file("my-project-2"))
  .settings(
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(sys.env.getOrElse("PROJECTS_FILENAME", sys.error("PROJECTS_FILENAME env var not provided"))))
      if (content != expectedContent) sys.error("unexpected projects written: " + content)
      ()
    }
  )
