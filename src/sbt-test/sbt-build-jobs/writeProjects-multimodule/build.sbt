val expectedContent =
  """|- module: myProject1
     |  folder: my-project-1
     |  aggregates: []
     |  root: false
     |- module: myProject2
     |  folder: my-project-2
     |  aggregates: []
     |  root: false
     |- module: root
     |  folder: _MODIFIED_ROOT_
     |  aggregates:
     |    - myProject1
     |    - myProject2
     |  root: true
     |""".stripMargin

lazy val root = (project in file(".")).aggregate(myProject1, myProject2)

lazy val myProject1 = (project in file("my-project-1"))

lazy val myProject2 = (project in file("my-project-2"))

TaskKey[Unit]("check") := {
  val content = IO.read(new File(
    sys.env.getOrElse("PROJECTS_FILENAME", sys.error("PROJECTS_FILENAME env var not provided"))))
  if (content != expectedContent) sys.error(s"expected projects:\n$expectedContent\nbut was:\n$content")
  ()
}
