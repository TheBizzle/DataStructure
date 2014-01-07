name := "DataStructure"

version := "0.7"

scalaVersion := "2.10.1"

artifactName := { (_, _, _) => "DataStructure.jar" }

scalaSource in Compile <<= baseDirectory(_ / "src" / "main")

scalaSource in Test <<= baseDirectory(_ / "src" / "test")

seq(bintrayResolverSettings: _*)

resolvers += bintray.Opts.resolver.repo("thebizzle", "Tester")

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
  "org.bizzle" %% "tester" % "1.0" % "test"
)

mainClass in Compile := None
