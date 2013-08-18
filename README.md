DataStructure
=============

This project is a collection of my Scala-based implementations of handy data structures.  Most notably, it currently consists of a mostly-complete implementation of a `BiHashMap`, an experimental data structure that attempts to integrate the power of the standard Scala collections library with the idea of hashmap-based bijection.  It's of limited utility, and it's not without its faults, but implementing it proved to be a worthwhile experiment.

An example of where `BiHashMap` is currently being used can be found [here](https://github.com/TheBizzle/PathFindingCore/blob/master/src/main/org/bizzle/pathfinding/pathingmap/Terrain.scala).
