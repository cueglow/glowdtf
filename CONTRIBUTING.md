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

For the rest of this file, all commands will be given for Linux/macOS. If you are on Windows, adapt accordingly: Run `gradlew` instead of `./gradlew`, replace forward with backward slashes, etc. 

## Repository Structure

The repository contains two main projects: The folder `cueglow-server` is a Gradle Kotlin project, the folder `cueglow-webui` is an npm project based on create-react-app with Typescript. When building the backend server with `./gradlew run`, Gradle calls `npm run build` to produce a production build of the website and bundles it with the generated jar file before starting the compiled jar. 

## Getting Started with Frontend Development

Starting from the root directory of the repository, build and run the backend server with
```
cd cueglow-server
./gradlew run
```

To benefit from faster frontend compile times, automatic reload when changing files and other niceties, start the frontend deveopment server with
```
cd cueglow-webui
npm start
```
The Web-UI from the development server should start in the browser at `localhost:3000`. The development server will serve http requests on port 3000 but forward all WebSocket connections to the backend server at `locahost:7000`. 

To learn how to setup your editor, refer to the docs of [create-react-app](https://create-react-app.dev/docs/setting-up-your-editor). 

### Frontend Testing 

We use Cypress for frontend testing. To test the site served by the frontend development server, use our custom npm script `cy`:
```
cd cueglow-webui
npm run cy
```
which should start the Cypress test runner. To learn how to use Cypress, please see the [Cypress Docs](https://docs.cypress.io/guides/overview/why-cypress.html). 

If you want to test the production site bundled with the backend server at `localhost:7000`, you can use the custom npm script `e2e` (for End-to-End testing):
```
cd cueglow-webui
npm run e2e
```

## Branching Guide

We try to adhere to OneFlow Branching as described in https://www.endoflineblog.com/oneflow-a-git-branching-model-and-workflow. To create a new feature, branch off from main with 

```
git checkout -b feature/my-feature main
```

and then once you want a review, open a pull request in GitHub. 
