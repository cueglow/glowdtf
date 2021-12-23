# GlowDTF

GlowDTF is a tech demo for controlling [GDTF](https://gdtf-share.com/) lighting
fixtures over Art-Net. 

It allows you to patch GDTF 1.1 (DIN SPEC 15800:2020-07) fixtures and control
their Channel Functions with a simple slider interface. The DMX values are
broadcast to the local network over Art-Net. 

The application consists of a browser frontend written in React/TypeScript and a
Kotlin/JVM server. The GDTF parser is machine-generated in Java from the
[official XML Schema](https://github.com/mvrdevelopment/spec/blob/main/gdtf.xsd)
with
[xjc](https://eclipse-ee4j.github.io/jaxb-ri/3.0.0/docs/ch03.html)/[JAXB](https://jakarta.ee/specifications/xml-binding/3.0/jakarta-xml-binding-spec-3.0.html).

Being a tech demo, GlowDTF should not be relied upon to work in production
situations. There are **no plans for continued development**. Since there are no
security measures, you should **only use GlowDTF in a secure local network**.
However, if you find any unexpected behavior or just want to tell us what you
think about our demo, feel free to [open an
issue](https://github.com/cueglow/glowdtf/issues/new) anyway. 

**TODO** Insert an Image

**TODO** Funnel towards new projects

## Running GlowDTF

**TODO**

<!--
Something like: Make sure you Java 11 or higher, download release, run jar, open browser
-->

## Using GlowDTF

**TODO**

<!--
Something like: Go to Fixture Types patch, upload GDTF, patch a fixture and control it with sliders. Show ModeMaster behavior. Art-Net output is global broadcast. Maybe include some images?
-->

## Notable Features

- Calculating the DMX Channel Layout of a GDTF
- Channel Functions including Mode Master Dependencies
- 

## Unimplemented Features

As GlowDTF focuses on rudimentary DMX control, there are many things in GDTF that are not supported:
- Channel Sets and Physical Values
- Wheels
- Color Management
- 3D Models
- Kinematic Geometry Chains
- Protocols other than DMX over Art-Net
- ...
