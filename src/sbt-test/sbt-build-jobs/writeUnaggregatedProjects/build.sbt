val expectedContent =
  "it\n".stripMargin

lazy val root = (project in file("."))
  .settings(
    TaskKey[Unit]("check") := {
      val content = IO.read(new File(sys.env.getOrElse("UNAGGREGATED_PROJECTS_FILENAME", sys.error("UNAGGREGATED_PROJECTS_FILENAME env var not provided"))))
      if (content != expectedContent) sys.error(s"expected unaggregated projects:\n$expectedContent\nbut was:\n$content")
      ()
    }
  ).aggregate(sub)

lazy val sub =
  (project in file("sub"))

lazy val it =
  (project in file("it"))
