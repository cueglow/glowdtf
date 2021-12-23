# GlowDTF Developer Guide

This document will help you to build GlowDTF and develop the code. 

## How to Build

Make sure you have the following dependencies installed: 

- [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- JDK 11 (Hotspot) or higher, e.g. from [Azul](https://www.azul.com/downloads)
- [Node.js 14 LTS](https://nodejs.org/) (we recommend a version manager like [nvm](https://github.com/nvm-sh/nvm) for Linux/macOS or [nvm-windows](https://github.com/coreybutler/nvm-windows) for Windows)

First clone the GlowDTF repository by running
```sh
git clone https://github.com/cueglow/glowdtf.git
```

On **Windows**, build and run the server with

```cmd
cd glowdtf\server
gradlew run
```

For the rest of this file, all commands will be given for Linux/macOS. If you
are on Windows, adapt accordingly: Run `gradlew` instead of `./gradlew`, replace
`/` with `\`, etc. 

On **Linux/macOS**, build and run the server with

```sh
cd glowdtf/server
./gradlew run
```

Then, open [localhost:7000](http://localhost:7000) in the browser to see the
UI. If you run into any problems, have a look at our [known issues](#known-issues). 


## Repository Structure

The repository contains two main projects: The folder `server` is a
Gradle Kotlin project, the folder `webui` is an npm project based on
create-react-app with TypeScript. When building the backend server with
`./gradlew run`, Gradle calls `npm run build` to produce a production build of
the website and bundles it with the generated jar file before starting the
compiled jar. 

## Getting Started with Frontend Development

First, run the backend server without building the frontend by
executing
```sh
cd server
./gradlew runNoNpm
```

Then start the frontend development server with
```sh
cd webui
npm start
```

The Web-UI from the development server should start in the browser at
`localhost:3000`. The development server will serve http requests on port 3000
but forward relevant connections to the backend server at `locahost:7000`. This
is configured in [setupProxy.js](webui/src/setupProxy.js).

To learn how to setup your editor, refer to the docs of
[create-react-app](https://create-react-app.dev/docs/setting-up-your-editor) and
[styled-components](https://styled-components.com/docs/tooling#syntax-highlighting).


### Automated Frontend Testing 

We use Cypress for frontend testing. To test the site served by the frontend
development server, use our custom npm script `cy`:
```sh
cd webui
npm run cy
```
which should start the Cypress test runner. To learn how to use Cypress, please
see the [Cypress
Docs](https://docs.cypress.io/guides/overview/why-cypress.html). 

If you want to test the production site bundled with the backend server at
`localhost:7000`, you can use the custom npm script `e2e` (for End-to-End
testing):
```sh
cd webui
npm run e2e
```

For non-GUI unit tests, [Jest](https://jestjs.io/) and [Testing Library](https://testing-library.com/docs/) are available and can be run with
```sh
npm test
```
But currently there are no tests written for Jest, we only use Cypress. 

### Frontend Code Structure

The GlowDTF WebUI is a create-react-app based npm project utilizing TypeScript.
It builds on components from the [Blueprint.js](https://blueprintjs.com/docs/)
library.

Other notable tools are:
- Client-Side Routing: `react-router` (v6)
- Utilities: `lodash`
- CSS-in-JS: `styled-components`
    - `styled` interface: `import styled from 'styled-components/macro'`
    - `css` prop interface: put an empty import into the file for it to work: 
      `import { } from 'styled-components/macro';`
    - Where possible, use SCSS Variables from Blueprint.js which are exported to
      JavaScript in [BlueprintVariables](webui/src/BlueprintVariables/BlueprintVariables.ts)
- Tables: 
    - `tabulator-tables` for tables. We built a React
      [wrapper](webui/src/Components/GlowTabulator.tsx) for it. 
    - Since `tabulator-tables` does not support cell level editing or selection,
      future components should use `react-table` with `react-table-plugins` and
      a custom frontend based on the Blueprint.js Table (which shouldn't be used
      due to [Firefox Scrolling
      Bug](https://github.com/palantir/blueprint/issues/1712))
- Form Validation: `react-hook-form` with `zod`

### Frontend Connection with Server

The WebSocket connection between frontend and server is managed outside of React
in the
[ConnectionProvider](webui/src/ConnectionProvider/ConnectionProvider.tsx)
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

## Backend Testing

Server or API features should be tested server-side in JUnit 5. The client is
then tested against the server during end-to-end testing in Cypress. 

Backend Tests can be run with

```sh
cd server
./gradlew test
```

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

If you have any remaining issues, please [open an issue](https://github.com/glowdtf/glowdtf/issues/new).