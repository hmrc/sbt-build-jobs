resolvers += Resolver.url("HMRC-open-artefacts-ivy2", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(Resolver.ivyStylePatterns)

// Required to be able to set the env variable to test
addSbtPlugin("au.com.onegeek" %% "sbt-dotenv" % "2.1.146")

sys.props.get("plugin.version") match {
  case Some(x) => addSbtPlugin("uk.gov.hmrc" % "sbt-build-jobs" % x)
  case _       => sys.error(
                    """|The system property 'plugin.version' is not defined.
                       |Specify this property using the scriptedLaunchOpts -D.""".stripMargin
                  )
}
