# CueGlow

CueGlow aims to be a lighting control software for low-budget theater, opera or 
musical. 

It focuses on PC or Tablet control, Art-Net output and single cue 
list operation. This small feature set should be reliable, fast and easy to use. 

CueGlow is currently in initial development and not yet usable. But if you like 
our vision, you can help make it real by [contributing](#contributing). 

## Installation

Installation instructions will be added once CI builds are ready.  

## Documentation

Documentation will be added in the future. 

## Support

If you have any question, idea or suggestion regarding CueGlow, please [open 
an issue](https://github.com/cueglow/cueglow/issues/new). 

## Non-Goals

We currently don't plan to implement any of the following features: 

- "Live" Lighting where one assembles the lighting show in real-time ("busking")
- Media Playback and Synchronization
- Projection Mapping
- DMX-Outputs other than Art-Net (such as USB interfaces or other network standards)
- Hardware Input Devices (MIDI, Launchpad, etc.)
- Integrated Visualizer

If you need some of these features, check out some other free software, such as 
[QLC+](https://www.qlcplus.org/), 
[DMXControl](https://dmxcontrol.de/en/), 
[Elation ONYX](https://support.obsidiancontrol.com/Content/Home.htm), 
[Chamsys MagicQ](https://chamsyslighting.com/pages/magicq-downloads), 
[Freestyler](http://www.freestylerdmx.be/) or 
[PCDimmer](https://www.pcdimmer.de/). 

If you don't have an Art-Net node, you can bridge to a large range of other 
protocols with e.g. 
[OLA](https://github.com/OpenLightingProject/ola) (mostly Linux/macOS),
[QLC+](https://www.qlcplus.org/) or
[MIDIMonster](https://midimonster.net/). 

## Contributing

Contributing to CueGlow can be as simple as using the software and reporting any
bugs you find. To learn about the different ways of contributing to CueGlow, see
our [CONTRIBUTING.md](CONTRIBUTING.md). 

## Roadmap

Please see our [ROADMAP.md](ROADMAP.md). 

## Why does CueGlow exist?

We ran the lights for a semi-professional opera project. The long rehearsal phase and 
low budget did not allow us to rent or buy a professional solution (GrandMA, ETC, ...).  

The professional, free offers (Chamsys, ONYX, MA dot2 onPC) were all not designed 
for use on a PC and could never provide the kind of swift usability we wished for when trying 
to program lights during the rehearsals. 

The software that is built for PC (QLC+, DMXControl, Freestyler, ...) often is
not built for theater but more for DJ or band style applications. For example,
usually there is no tracking cue list or advanced multi-cue editing. If there
is, the usability is bad enough to render the software useless in the heat of
the moment. 

Sometimes, I tried a new software and was able to crash it in minutes. Such
software cannot be relied upon to drive a show. 

We though we should be able to do better. We set out to make a software that would be
- Open Source 
- Built for commodity PCs 
- Built for theater, musical and opera specifically
- Tracking, Single Cue List with "Go", Patching & Fixture Definitions, Effect Generator, ... 
- Offer all important features, but no more
- Reliable 
- Well documented 
- Good at explaining and visualizing its functional model 
- Transparent in why a specific value is output 
- Flexibly allow the reuse of parts you have previously built, even if you
  didn't think at the time of creation you want to reuse them. These reused
  parts should be linkable.  
- Non-technical and elicit emotional playing with lights 

We are not there yet, but we started the journey. 