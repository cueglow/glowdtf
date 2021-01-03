## How to Build

### Linux/macOS

Dependencies: 

- git
- [JDK 15 with Hotspot](https://adoptopenjdk.net/?variant=openjdk15&jvmVariant=hotspot)

First clone the CueGlow repository by running

```
git clone https://github.com/cueglow/cueglow.git
```

which will clone the repo into a new folder named `cueglow`. 

Run

```
./gradlew
```




## Branching Guide

We try to adhere to OneFlow Branching as described in https://www.endoflineblog.com/oneflow-a-git-branching-model-and-workflow. To create a new feature, branch off from main with 

```
git checkout -b feature/my-feature main
```

and then once you want a review, open a pull request in GitHub. 
