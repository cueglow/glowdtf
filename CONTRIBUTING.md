# CueGlow Contribution Guide

You can help to improve CueGlow in many different ways: 

- Reporting Bugs
- Suggesting New Features
- Triaging Issues
- Programming
- UX and Design Work
- Telling Other People about CueGlow

If you are not sure how you can contribute, don't hesitate to [open an
issue](https://github.com/cueglow/cueglow/issues/new). We'd love to hear from
you!

The following sections will help you to build CueGlow and to contribute code. 

## How to Build

Make sure you have the following dependencies installed: 

- [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- [JDK 16 with Hotspot](https://adoptium.net/installation.html?variant=openjdk16&jvmVariant=hotspot)
- [Node.js 14 LTS](https://nodejs.org/) (we recommend a version manager like [nvm](https://github.com/nvm-sh/nvm) for Linux/macOS or [nvm-windows](https://github.com/coreybutler/nvm-windows) for Windows)

First clone the CueGlow repository by running
```
git clone https://github.com/cueglow/cueglow.git
```

On **Windows**, build and run the server with

```
cd cueglow\cueglow-server
gradlew run
```

For the rest of this file, all commands will be given for Linux/macOS. If you
are on Windows, adapt accordingly: Run `gradlew` instead of `./gradlew`, replace
forward with backward slashes, etc. 

On **Linux/macOS**, build and run the server with

```
cd cueglow/cueglow-server
./gradlew run
```

Then, open [localhost:7000](http://localhost:7000) in the browser to see the
UI. If you run into any problems, have a look at our [known issues](#known-issues). 


## Repository Structure

The repository contains two main projects: The folder `cueglow-server` is a
Gradle Kotlin project, the folder `cueglow-webui` is an npm project based on
create-react-app with Typescript. When building the backend server with
`./gradlew run`, Gradle calls `npm run build` to produce a production build of
the website and bundles it with the generated jar file before starting the
compiled jar. 

## Getting Started with Frontend Development

Starting from the root directory of the repository, build and run the backend
server without building the frontend by executing
```
cd cueglow-server
./gradlew runNoNpm
```

Then start the frontend development server with
```
cd cueglow-webui
npm start
```

The Web-UI from the development server should start in the browser at
`localhost:3000`. The development server will serve http requests on port 3000
but forward relevant connections to the backend server at `locahost:7000`. This
is configured in [setupProxy.js](cueglow-webui/src/setupProxy.js).

To learn how to setup your editor, refer to the docs of
[create-react-app](https://create-react-app.dev/docs/setting-up-your-editor) and
[styled-components](https://styled-components.com/docs/tooling#syntax-highlighting).


### Automated Frontend Testing 

We use Cypress for frontend testing. To test the site served by the frontend
development server, use our custom npm script `cy`:
```
cd cueglow-webui
npm run cy
```
which should start the Cypress test runner. To learn how to use Cypress, please
see the [Cypress
Docs](https://docs.cypress.io/guides/overview/why-cypress.html). 

If you want to test the production site bundled with the backend server at
`localhost:7000`, you can use the custom npm script `e2e` (for End-to-End
testing):
```
cd cueglow-webui
npm run e2e
```

For simple, non-GUI unit tests we use Jest which you can run with
```
npm test
```
[Testing Library](https://testing-library.com/docs/) is installed, but we
currently don't use Jest for UI tests and use Cypress instead. 

### Frontend Code Structure

The Cueglow WebUI is a create-react-app based npm project utilizing TypeScript.
It builds on components from the [Blueprint.js](https://blueprintjs.com/docs/)
library.

Other notable tools are:
- Client-Side Routing: `react-router` v6
- Utilities: `lodash`
- CSS-in-JS: `styled-components`
    - `styled` interface: `import styled from 'styled-components/macro'`
    - `css` prop interface: put an empty import into the file for it to work: 
      `import { } from 'styled-components/macro';`
    - Where possible, use SCSS Variables from Blueprint.js which are exported to
      JavaScript in [BlueprintVariables](cueglow-webui/src/BlueprintVariables/BlueprintVariables.ts)
- Tables: 
    - `tabulator-tables`/`react-tabulator` is currently used for the Patch, but
      has typing issues (I had to [re-wrap
      it](cueglow-webui/src/Components/GlowTabulator.tsx)) and does not support
      cell-level selection
    - Future components should use `react-table` with `react-table-plugins` and
      a custom frontend based on the Blueprint.js Table (which can't be used due
      to [Firefox Scrolling Bug](https://github.com/palantir/blueprint/issues/1712))
- Form Validation: `react-hook-form` with `zod`

### Frontend Connection with Server

The WebSocket connection between frontend and server is managed outside of React
in the
[ConnectionProvider](cueglow-webui/src/ConnectionProvider/ConnectionProvider.tsx)
and associated modules. The current architecture works like this:  
The singleton
`ConnectionProvider` ensures that a connection is established. Each connection
has an associated `SubscriptionProvider` and `MessageHandler`. The job of the
`SubscriptionProvider` is to subscribe/unsubscribe from the right topics. The
`MessageHandler` handles and dispatches incoming messages, e.g. to the
`patchDataHandler`. The `patchDataHandler` is a global singleton that tries to
keep a consistent representation of the data in the Patch topic at all times.
This state is fed to the React application via the Context API in the
`PatchDataProvider` component. It registers an update handler in the
`onPatchChange` field of the `patchDataHandler`. The `PatchDataProvider`
therefore must only be instantiated once. 


## Branching Guide

We try to adhere to OneFlow Branching as described in
https://www.endoflineblog.com/oneflow-a-git-branching-model-and-workflow. To
create a new feature, branch off from main with 

```
git checkout -b feature/my-feature main
```

Once you want a review of your code or want to discuss some open questions, open
a Pull Request on GitHub. 

## Code Style

All code should be well tested to ensure our goal of reliability. 

Server or API features should be tested server-side in JUnit 5. The client is
then tested against the server during end-to-end testing in Cypress. 

## Known Issues

### npmInstall failed

If you run `./gradlew run` and get
```
> Task :npmInstall FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':npmInstall'.
> A problem occurred starting process 'command 'npm''
```
you can try running `./gradlew --stop` to stop the Gradle daemon and then re-run
`./gradlew run`. For me, this occurs if the first start of the Gradle daemon
occurs in IntelliJ, so try stopping and then running something in the terminal
before trying it in IntelliJ again. 

### Still didn't work?

If you have any remaining issues, please [open an issue](https://github.com/cueglow/cueglow/issues/new).