# Treatment of GDTF in GlowDTF

[GDTF](https://gdtf-share.com/) or [DIN SPEC
15800:2020-07](https://www.beuth.de/de/technische-regel/din-spec-15800/324748671)
is a standard format to describe properties of lighting fixtures. GlowDTF uses
it to map fixture functionality to DMX values. It was founded by MA Lighting,
Vectorworks and Robe Lighting. It is still under active development. 

## Anatomy of a DmxMode

Each DmxMode in a GDTF has:
- exactly one main fixture. The corresponding Geometry is the one referenced in
  the DMXMode node. 
- zero or more subfixtures. These are created for each Geometry Reference. 

Each referenced Geometry in a DMXMode must be validated to be part of the main
geometry of the DMXMode. 

Each DmxMode contains DmxChannels and each of those contains LogicalChannels.
Each LogicalChannel contains at least one FullDmxRange. More are created if the
DmxStart values of multiple ChannelFunctions overlap. Each ChannelFunction
belongs to exactly one FullDmxRange. 

ChannelFunctions are shown to the user as the entry point for controlling
fixture attributes. 

GlowDTF adds one RawDmx ChannelFunction to each DMXChannel that is just a direct
control of the DMX values. It has no Mode Master and covers the whole DmxRange,
so it is always hot (see below). 

ChannelSets are value ranges that annotate certain values of a ChannelFunction
with a name and linearly changing Physical values. 

### Anatomy of ChannelFunctions

ChannelFunctions that contribute to the DMX output are said to be **hot**.
ChannelFunctions that do not contribute to the DMX output are said to be **frozen**.

The value of frozen ChannelFunctions is cached in the programmer in the case a
user freezes a ChannelFunction and then unfreezes it again, in which case it
should still have its old value. When the programmer is cleared or otherwise
emptied, frozen ChannelFunction values are reset to the default value of the
ChannelFunction. Frozen values are never saved to cues and cues do not respect
frozen values currently in the programmer, relying on default values instead to
create reproducible behavior. 

There are two mechanisms that cause a ChannelFunction to be frozen:
- Exclusion
- Mode Master

**Exclusion** occurs because a DmxChannel can only have one value at a time.
When this value is out of range of a ChannelFunction, that ChannelFunction is
said to be **excluded**.

**Mode Master** models dependencies between ChannelFunctions. If another
ChannelFunction is in a certain range, it can enable or disable other
ChannelFunctions. If the Mode Master of a ChannelFunction is in the specified
dependency range, the dependent ChannelFunction is said the be **enabled**. If
not, it is **disabled**. 

A ChannelFunction is frozen when it is excluded or disabled. A ChannelFunction
is hot when it is included and enabled. 

### State Transitions

A state transition occurs when the old, full state of all fixtures is updated
with new, sparse values. The new value of each DmxChannel, and therefore its
ChannelFunctions, is given by a simple hierarchy:

1. new values (must be respected, including defaults for frozen values)
2. previous hot values
3. previous frozen values

### Mode Master Structure

All ChannelFunctions in a DmxMode form an acyclic directed graph of Mode Master
dependencies. The nodes are ChannelFunctions. The edges go from the dependency
to the dependent ChannelFunction. The edges are weighted with the enabling
DmxRange of the dependency. These ranges must be clipped by GlowDTF to the
DmxRange of the dependency. 

A ChannelFunction is enabled when its dependency is enabled
and in the weight range. 

ModeMaster dependencies where the dependency is a DmxChannel instead of a
ChannelFunction are attached to the RawDmx Channel Function we add ourselves. 

Cycles in the ModeMaster Graph must result in an error for the GDTF file.
Unreachable ChannelFunctions, where all Mode Master dependency weights have a
length of 0 and there is at least one input, must result in a warning. 

Mode Master References within the same DMXChannel are not an issue. 

#### Mode Master and Abstract ChannelFunctions

Three cases can be differentiated:
- Abstract ChF depending on concrete ChF:  
  This is fine. Every instance of the abstract channel function depends on the
  concrete channel function
- Concrete ChF depending on abstract ChF:  
  Throw an error, as a channel function can only have one mode master
- Abstract ChF depending on abstract ChF:  
  This is only allowed if both ChFs reference the same top level geometry. Then
  there is a 1:1 relationship between the instances for each GeometryReference.
  Otherwise, throw an error. 

### Physical Values

Each ChannelFunction has an associated unit and each DMX value has exactly one
associated Physical value. 

### Rendering to DMX

The Graph of ChannelFunctions with their respective values and states can be
rendered to a DMX value for each DmxChannel. 