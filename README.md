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

## Running GlowDTF

**TODO**

<!--
Something like: Make sure you Java 11 or higher, download release, run jar, open browser
-->

## Using GlowDTF

**TODO**

<!--
Something like: Go to Fixture Types patch, upload GDTF, look at its details, patch a fixture and control it with sliders. Show ModeMaster behavior. Art-Net output is global broadcast. Maybe include some images?
-->

<!--
Screenshots???
-->

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