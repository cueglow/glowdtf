## How to Build

### Linux/macOS

Make sure you have the following dependencies installed: 

- git
- [JDK 15 with Hotspot](https://adoptopenjdk.net/installation.html?variant=openjdk15&jvmVariant=hotspot)
- [Node.js 14 LTS](https://nodejs.org/) (recommended to install via [NodeSource](https://github.com/nodesource/distributions) or [nvm](https://github.com/nvm-sh/nvm))

First clone the CueGlow repository by running
```
git clone https://github.com/cueglow/cueglow.git
```
Go into the server folder with 
```
cd cueglow/cueglow-server
```
Build and run CueGlow with
```
./gradlew run
```
then, go to [localhost:7000](http://localhost:7000) in the browser to see the UI. 

If you run into any problems, feel free to [open an issue](https://github.com/cueglow/cueglow/issues/new). 


### Windows

Make sure you have the following dependencies installed: 

- [git](https://gitforwindows.org/)
- [JDK 15 with Hotspot](https://adoptopenjdk.net/installation.html?variant=openjdk15&jvmVariant=hotspot)
- [Node.js 14 LTS](https://nodejs.org/)

First clone the CueGlow repository by running
```
git clone https://github.com/cueglow/cueglow.git
```
Go into the server folder with 
```
cd cueglow\cueglow-server
```
Build and run CueGlow with
```
gradlew run
```
then, go to [localhost:7000](http://localhost:7000) in the browser to see the UI. 

If you run into any problems, feel free to [open an issue](https://github.com/cueglow/cueglow/issues/new). 

## Branching Guide

We try to adhere to OneFlow Branching as described in https://www.endoflineblog.com/oneflow-a-git-branching-model-and-workflow. To create a new feature, branch off from main with 

```
git checkout -b feature/my-feature main
```

and then once you want a review, open a pull request in GitHub. 
