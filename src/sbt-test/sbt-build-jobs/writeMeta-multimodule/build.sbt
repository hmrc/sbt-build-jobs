lazy val myProject1 = (project in file("my-project-1"))
  .settings(
    organization       := "uk.gov.hmrc",
    version            := "1.0.0",
    crossScalaVersions := Seq("2.12.15", "2.13.7"),
    publish / skip     := true,
    TaskKey[Unit]("check") := {
      val expectedContent =
        """|name              : "myProject1"
           |organization      : "uk.gov.hmrc"
           |version           : "1.0.0"
           |sbtVersion        : "1.4.9"
           |crossScalaVersions: ["2.12.15", "2.13.7"]
           |publish_skip      : true
           |""".stripMargin
      val content = IO.read(new File("my-project-1/target/meta.yaml"))
      if (content != expectedContent) sys.error(s"expected meta:\n$expectedContent\nbut was:\n$content")
      ()
    }
  )

lazy val myProject2 = (project in file("my-project-2"))
  .settings(
    organization       := "uk.gov.hmrc2",
    version            := "2.0.0",
    crossScalaVersions := Seq("2.12.13"),
    TaskKey[Unit]("check") := {
      val expectedContent =
        """|name              : myProject2
           |organization      : uk.gov.hmrc2
           |version           : 2.0.0
           |sbtVersion        : 1.4.9
           |crossScalaVersions: [2.12.13]
           |publish_skip      : false
           |""".stripMargin
      val content = IO.read(new File("my-project-2/target/meta.yaml"))
      if (content != expectedContent) sys.error(s"expected meta:\n$expectedContent\nbut was:\n$content")
      ()
    }
  )


TaskKey[Unit]("check") := {
  val expectedContent =
    """|name              : writemeta-multimodule
       |organization      : default
       |version           : 0.1.0-SNAPSHOT
       |sbtVersion        : 1.4.9
       |crossScalaVersions: [2.12.12]
       |publish_skip      : false
       |""".stripMargin
  val content = IO.read(new File("target/meta.yaml"))
  if (content != expectedContent) sys.error(s"expected meta:\n$expectedContent\nbut was:\n$content")
  ()
}
