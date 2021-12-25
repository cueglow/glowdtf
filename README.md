# GlowDTF

GlowDTF is a tech demo for controlling [GDTF](https://gdtf-share.com/) lighting
fixtures over Art-Net. 

GDTF stands for *General Device Type Fixture*. It is an open file format to
describe the functionality of lighting fixtures in the entertainment industry,
like LED bars, scanners or moving heads. You can find more details in the [GDTF
Resources](#gdtf-resources) below. 

GlowDTF allows you to patch GDTF 1.1 (DIN SPEC 15800:2020-07) fixtures and control
their Channel Functions with a simple slider interface. The DMX values are
broadcast to the local network over Art-Net. 

The application consists of a browser frontend written in React/TypeScript and a
Kotlin/JVM server. The GDTF parser is machine-generated in Java from the
[official XML Schema](https://github.com/mvrdevelopment/spec/blob/main/gdtf.xsd)
with
[xjc](https://eclipse-ee4j.github.io/jaxb-ri/3.0.0/docs/ch03.html)/[JAXB](https://jakarta.ee/specifications/xml-binding/3.0/jakarta-xml-binding-spec-3.0.html).

Being a tech demo, GlowDTF should not be relied upon to work in production
situations. Since there are no security measures, you should **only use GlowDTF
in a secure local network**. There are **no plans for continued development**.
However, if you find any unexpected behavior or just want to tell us what you
think about our demo, feel free to [open an
issue](https://github.com/cueglow/glowdtf/issues/new) anyway. 

## How To Run

- Make sure you have Java 11 or higher installed ([How
  To](https://www.baeldung.com/java-check-is-installed))
  - If not, you can install it for example from
    [Azul](https://www.azul.com/downloads/?package=jre)
- Download the jar file from our [Releases](https://github.com/cueglow/glowdtf/releases)
- Run the jar by double-clicking on it, or if your prefer the command line:

```sh
java -jar glowdtf-0.0.1-dev-all.jar
```

- You should see the Javalin server start up in the terminal. Open the browser
  and navigate to <http://localhost:7000>. You should see the empty GlowDTF main
  screen. 

## Things to Try Out

**Add a GDTF**
- Open the patch by clicking on the button in the upper right
- Navigate to the "Fixture Types" tab and click "Add GDTF"
- Select a GDTF file and confirm
- The GDTF file will show up in the table. Click on it to see Details on the right. 
- Click "Show ModeMaster Dependencies" below the DMX channel list to show the
  dependency graph (only visible if there are ModeMaster dependencies). You can
  drag the nodes around and zoom with the mouse wheel. 

**Patch a Fixture**
- Switch to the "Fixtures" tab in the patch and click "Add New Fixtures". Make
  your settings and confirm with "Add Fixtures".  Your fixtures should show up
  in the Fixture table. 
- To go back to the main screen, click the Exit button in the upper left
  
**Control the Fixture**
- On the main screen, select a fixture in the table on the left. Its
  channel functions show up as sliders on the right. Drag them to change
  the DMX output.
- The DMX values are output via Art-Net to the address `255.255.255.255` (local
  network broadcast address), so should reach all devices in your local network.
  The Art-Net universes are numbered from 0 to 32767 and can be set in the
  Fixture Patch. 
- The channel function sliders react to out-of-range and ModeMaster values. If
  their name is written in gray but the slider is enabled, the current value of
  the channel is outside the range of the channel function. If the slider is
  completely disabled, its ModeMaster needs to be set appropriately. 

**Quitting GlowDTF**
- Hit `Ctrl + C` in the GlowDTF terminal or just close the terminal. All
added Fixture Types and Fixtures will be lost.

## Unimplemented Features

As GlowDTF focuses on rudimentary DMX control, there are many things in GDTF
that are not supported:
- Channel Sets and Physical Values
- Wheels
- Color Management
- 3D Models
- Kinematic Geometry Chains
- Protocols other than DMX over Art-Net
- ...

## GDTF Resources

- [GDTF Share](https://gdtf-share.com) - Offical Website for GDTF. Database
  contains many GDTF files for download after registration and the Builder allows
  you to create your own GDTF files. 
- [GDTF Forum](https://gdtf-share.com/forum/)  - Official Forum. Best place to
  ask questions regarding GDTF. 
- [DIN SPEC
  15800:2020-07](https://www.beuth.de/de/technische-regel/din-spec-15800/324748671) - Standard document, free download with registration. 
- [mvrdevelopment/spec](https://github.com/mvrdevelopment/spec) - Repository for
  GDTF Development. If you find an issue with the standard document (or the
  Builder?), open an issue here. You can also find the XML Schema here. 
- [GDTF Wiki](https://gdtf-share.com/wiki/Main_Page) - This Wiki on the official
  GDTF site has an up-to-date main page. Older pages from
  GDTF 1.0 days can still be found with the search. They sometimes contain
  background info that you won't find anywhere else.  
  Note that the library for parsing GDTF and MVR mentioned here is currently
  available [only upon
  request](https://gdtf-share.com/forum/index.php?/topic/339-mvr-portable-library/)
  and I don't know about its licensing. 

## Developer Documentation

Information on how to build and develop can be found in the
[CONTRIBUTING.md](CONTRIBUTING.md). Additional in-depth notes on the design are
in the folder `/dev-docs`. 