name := "DataStructure"

organization := "org.bizzle"

version := "0.7"

scalaVersion := "2.10.1"

licenses += ("BSD", url("http://choosealicense.com/licenses/bsd-3-clause/"))

scalaSource in Compile <<= baseDirectory(_ / "src" / "main")

scalaSource in Test <<= baseDirectory(_ / "src" / "test")

seq(bintraySettings: _*)

bintray.Keys.repository in bintray.Keys.bintray := "DataStructure"

bintray.Keys.bintrayOrganization in bintray.Keys.bintray := Some("thebizzle")

resolvers += bintray.Opts.resolver.repo("thebizzle", "Tester")

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
  "org.bizzle" %% "tester" % "1.0" % "test"
)

mainClass in Compile := None
