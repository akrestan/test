name := "Maven POMs updater"
version := "0.0.1"
scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8")

scalaSource in Compile := baseDirectory.value / "src"

// overriding build defined repositories:
// http://stackoverflow.com/questions/16939280/override-sbt-default-resolvers-with-authenticated-repo#16940686
// http://www.scala-sbt.org/1.0/docs/Proxy-Repositories.html

// build-level dependency repositories:
// sbt commands:
// show resolvers 
// show compile:dependencyClasspath
resolvers ++= Seq(
	Resolver.mavenLocal,
	// all Maven repositories
	DefaultMavenRepository,
	Resolver.sonatypeRepo("public"),
	"Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases/",
	"Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
	Resolver.typesafeRepo("releases"),
	// this is an Ivy repository
	 Classpaths.typesafeReleases
)

// add scala-xml dependency when needed (for Scala 2.11 and newer) in a robust way
// this mechanism supports cross-version publishing
libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, add dependency on scala-xml module
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3",
        "org.scala-lang.modules" %% "scala-swing" % "1.0.1",
		"commons-cli" % "commons-cli" % "1.3.1"
		)
    case _ =>
      // or just libraryDependencies.value if you don't depend on scala-swing
      libraryDependencies.value :+ "org.scala-lang" % "scala-swing" % scalaVersion.value
  }
}