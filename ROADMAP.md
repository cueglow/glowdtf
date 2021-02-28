# CueGlow Roadmap

This roadmap helps us keep track what we are trying to achieve and in 
what order. In the long term, this list will be migrated to Issues/Milestones. 

We will try to adhere to [Semantic Versioning](https://semver.org). 

## v0.1 

- [ ] Server that serves Browser-based client
    - [ ] Multiple clients at a time but no eventual synchrony in case of network issues 
(clients without network connection to server should disconnect)
    - [ ] Multiple Shows on one PC, New Show, Open Show, Take Show to different PC
    - [ ] Auto-Save
- [ ] Output DMX via Network
    - [ ] Show DMX Output in GUI
- [ ] Patch "Fixture Types"
- [ ] Represent Lighting Rig state and offer manipulation of state. Manipulations 
  are stored in the programmer for later use. 
- [ ] Select Fixtures, show and manipulate attributes
- [ ] 1 Cue List
    - [ ] Add cue after current Cue, even empty to later save into
    - [ ] Light scenes can be saved to cues 
    - [ ] Supports typical editing needs 
        - [ ] A change in a cue may need to affect all following cues (->  Merge) 
        - [ ] A change in a cue may need to only affect the current cue (-> Cue Only) 
        - [ ] A change in the current cue may need to affect cues before it (-> Update)  
    - [ ] Go, Back, Go To, manual Crossfader 
    - [ ] Fade Time between cues 
        - [ ] Toggle fade per attribute group per cue 
    - [ ] Delay Time before fade per cue 
    - [ ] Support go next toggle-able for each cue
    - [ ] Delete Cue 
    - [ ] Edit Cue 
    - [ ] Name Cues 
- [ ] Programmer Clear, Selection Clear 
- [ ] Undo/Redo 
- [ ] Hotkeys for everything you need often 
- [ ] Not crash, reliable 
- [ ] CONTRIBUTING.md (most important things only)
- [ ] Dev Docs (Javadocs, etc.) 
- [ ] Welcoming Dev Docs Home with Code structure intro 
- [ ] public repository

## v0.x

- [ ] 2D Layout Workflow
    - [ ] Select one or multiple fixtures in 2D Layout
        - [ ] Select with Box
    - [ ] Change attributes by dragging on symbols
    - [ ] Switch active attribute with shortcuts    
- [ ] Load Command 
- [ ] Website 
- [ ] Zulip, IRC, or something else 
- [ ] Forum 
- [ ] Offline/Online Help/Docs/Manual 

## v1.0 

- [ ] Skip Fadetime Forward/Back 
- [ ] Project "Reuse" 
- [ ] Project "FX" 
- [ ] Programmer partial save, Off, partial clear, .... 
- [ ] Selection Groups 
- [ ] Multiple 2D Layouts (?) 


## v1.x 

- [ ] OSC API 
- [ ] Hierarchy for Cues? 
    - [ ] Act, Scenes, etc. would be cool 
- [ ] PDF/TXT script annotations with firable cues? 
  (e.g. https://wiki-en.dmxcontrol-projects.org/index.php?title=Textbook_DMXC2)
- [ ] MIB? 
    - [ ] For single cues? 
    - [ ] For single fixtures? 
    - [ ] For single attributes? 
    - [ ] For whole cue list? 
- [ ] Fixture Type Changes and keeping cue values 

## v2 

- [ ] Park 
    - [ ] Park Command - Dump Programmer into Park 
    - [ ] Specific parts in Park or whole park can be cleared, similar to programmer 
    - [ ] Fade time similar to programmer 
    - [ ] Park always determines values, no matter what else happens 
    - [ ] Could also be "pin" and stay in the programmer as top level but remain 
            adjustable and is by default not saved 
- [ ] Blind mode? 
    - [ ] Including Fade 
    - [ ] Danger: User does something nothing happens and he wonders why 
    - [ ] Shouldn't it be a source/sink like the programmer? Then it would be stateless. 
            But how could one  combine it with actions on cue list/park then? 
- [ ] Highlight and Next/Prev 
- [ ] Programmer Changes Fade Time 
- [ ] Current Cue Only (No Tracking Side Effects) 
- [ ] Authentication 
- [ ] Spectrum-data based color management 
- [ ] How to edit unused presets in storage? 
- [ ] Do users want to use this software for real-time lighting (Concerts, etc.)? 
  What are their specific needs and problems with existing software? 
  What can we do about that? How can we grow into a flexible basis for all kinds 
  of lighting control? 
- [ ] Multiple Cue Lists?
- [ ] DIP Switch conversion in Patch 
- [ ] Scratchpad for Notes with ToDo-list (tickable) 
- [ ] Failover with tracking backup? (Net-Split Problems, requires merging of showfile)

## Moonshots (vX) 
- [ ] 3D Visualizer and Control 
    - [ ] https://github.com/BryanCrotaz/blender-artnet 
- [ ] Multiple People Editing at the same time with different selections, programmers, etc. 
- [ ] Plugins 
- [ ] Full spectrum-based color-management with CIE based color matching and spectrum 
        adjustment with same colors, spectral based fades, etc. 

