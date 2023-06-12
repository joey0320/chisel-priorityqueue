Chisel Priority Queue
=======================

### Background
The microarchitecture is based off of this paper. [Efficent-Priority-Queue](https://ieeexplore.ieee.org/document/4380693)

### Dependencies

#### JDK 8 or newer

We recommend LTS releases Java 8 and Java 11. You can install the JDK as recommended by your operating system, or use the prebuilt binaries from [AdoptOpenJDK](https://adoptopenjdk.net/).

#### SBT or mill

SBT is the most common built tool in the Scala community. You can download it [here](https://www.scala-sbt.org/download.html).  
mill is another Scala/Java build tool without obscure DSL like SBT. You can download it [here](https://github.com/com-lihaoyi/mill/releases)


### Getting started

#### First lets clone the repo

```sh
git clone git@github.com:ucb-bar/chisel-priorityqueue.git
cd chisel-priorityqueue
```

#### We can generate verilog by
```sh
sbt run
```

#### We can run unit tests by
```sh
sbt test
```

You should see a whole bunch of output that ends with something like the following lines
```
[info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 5 s, completed Dec 16, 2020 12:18:44 PM
```
If you see the above then...

#### It worked!

### Other resources

- [Chisel Bootcamp](https://github.com/freechipsproject/chisel-bootcamp)
- [Chisel3](https://www.chisel-lang.org/)

