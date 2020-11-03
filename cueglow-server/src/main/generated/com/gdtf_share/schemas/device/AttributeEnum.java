//
// Diese Datei wurde mit der Eclipse Implementation of JAXB, v2.3.3 generiert 
// Siehe https://eclipse-ee4j.github.io/jaxb-ri 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2020.11.03 um 10:54:32 AM CET 
//


package com.gdtf_share.schemas.device;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java-Klasse für AttributeEnum.
 * 
 * &lt;p&gt;Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="AttributeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1Audio"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1Macro"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1Mode"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1Pos"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1PosShake"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1Random"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1SelectEffects"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1SelectShake"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel1SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2Audio"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2Macro"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2Mode"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2Pos"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2PosShake"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2Random"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2SelectEffects"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2SelectShake"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel2SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3Audio"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3Macro"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3Mode"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3Pos"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3PosShake"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3Random"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3SelectEffects"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3SelectShake"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel3SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4Audio"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4Macro"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4Mode"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4Pos"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4PosShake"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4Random"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4SelectEffects"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4SelectShake"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel4SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5Audio"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5Macro"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5Mode"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5Pos"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5PosShake"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5Random"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5SelectEffects"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5SelectShake"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheel5SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="AnimationWheelShortcutMode"/&amp;gt;
 *     &amp;lt;enumeration value="BeamEffectIndexRotateMode"/&amp;gt;
 *     &amp;lt;enumeration value="BeamReset"/&amp;gt;
 *     &amp;lt;enumeration value="BeamShaper"/&amp;gt;
 *     &amp;lt;enumeration value="BeamShaperMacro"/&amp;gt;
 *     &amp;lt;enumeration value="BeamShaperPos"/&amp;gt;
 *     &amp;lt;enumeration value="BeamShaperPosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="BlackoutMode"/&amp;gt;
 *     &amp;lt;enumeration value="Blade1A"/&amp;gt;
 *     &amp;lt;enumeration value="Blade1B"/&amp;gt;
 *     &amp;lt;enumeration value="Blade1Rot"/&amp;gt;
 *     &amp;lt;enumeration value="Blade2A"/&amp;gt;
 *     &amp;lt;enumeration value="Blade2B"/&amp;gt;
 *     &amp;lt;enumeration value="Blade2Rot"/&amp;gt;
 *     &amp;lt;enumeration value="Blade3A"/&amp;gt;
 *     &amp;lt;enumeration value="Blade3B"/&amp;gt;
 *     &amp;lt;enumeration value="Blade3Rot"/&amp;gt;
 *     &amp;lt;enumeration value="Blade4A"/&amp;gt;
 *     &amp;lt;enumeration value="Blade4B"/&amp;gt;
 *     &amp;lt;enumeration value="Blade4Rot"/&amp;gt;
 *     &amp;lt;enumeration value="Blade5A"/&amp;gt;
 *     &amp;lt;enumeration value="Blade5B"/&amp;gt;
 *     &amp;lt;enumeration value="Blade5Rot"/&amp;gt;
 *     &amp;lt;enumeration value="Blower1"/&amp;gt;
 *     &amp;lt;enumeration value="Blower2"/&amp;gt;
 *     &amp;lt;enumeration value="Blower3"/&amp;gt;
 *     &amp;lt;enumeration value="Blower4"/&amp;gt;
 *     &amp;lt;enumeration value="Blower5"/&amp;gt;
 *     &amp;lt;enumeration value="CIE_Brightness"/&amp;gt;
 *     &amp;lt;enumeration value="CIE_X"/&amp;gt;
 *     &amp;lt;enumeration value="CIE_Y"/&amp;gt;
 *     &amp;lt;enumeration value="CRIMode"/&amp;gt;
 *     &amp;lt;enumeration value="CTB"/&amp;gt;
 *     &amp;lt;enumeration value="CTBReset"/&amp;gt;
 *     &amp;lt;enumeration value="CTC"/&amp;gt;
 *     &amp;lt;enumeration value="CTCReset"/&amp;gt;
 *     &amp;lt;enumeration value="CTO"/&amp;gt;
 *     &amp;lt;enumeration value="CTOReset"/&amp;gt;
 *     &amp;lt;enumeration value="ChromaticMode"/&amp;gt;
 *     &amp;lt;enumeration value="Color1"/&amp;gt;
 *     &amp;lt;enumeration value="Color1Mode"/&amp;gt;
 *     &amp;lt;enumeration value="Color1WheelAudio"/&amp;gt;
 *     &amp;lt;enumeration value="Color1WheelIndex"/&amp;gt;
 *     &amp;lt;enumeration value="Color1WheelRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Color1WheelSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Color2"/&amp;gt;
 *     &amp;lt;enumeration value="Color2Mode"/&amp;gt;
 *     &amp;lt;enumeration value="Color2WheelAudio"/&amp;gt;
 *     &amp;lt;enumeration value="Color2WheelIndex"/&amp;gt;
 *     &amp;lt;enumeration value="Color2WheelRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Color2WheelSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Color3"/&amp;gt;
 *     &amp;lt;enumeration value="Color3Mode"/&amp;gt;
 *     &amp;lt;enumeration value="Color3WheelAudio"/&amp;gt;
 *     &amp;lt;enumeration value="Color3WheelIndex"/&amp;gt;
 *     &amp;lt;enumeration value="Color3WheelRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Color3WheelSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Color4"/&amp;gt;
 *     &amp;lt;enumeration value="Color4Mode"/&amp;gt;
 *     &amp;lt;enumeration value="Color4WheelAudio"/&amp;gt;
 *     &amp;lt;enumeration value="Color4WheelIndex"/&amp;gt;
 *     &amp;lt;enumeration value="Color4WheelRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Color4WheelSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Color5"/&amp;gt;
 *     &amp;lt;enumeration value="Color5Mode"/&amp;gt;
 *     &amp;lt;enumeration value="Color5WheelAudio"/&amp;gt;
 *     &amp;lt;enumeration value="Color5WheelIndex"/&amp;gt;
 *     &amp;lt;enumeration value="Color5WheelRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Color5WheelSpin"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_B"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_BC"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_BM"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_C"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_CW"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_G"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_GC"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_GY"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_M"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_R"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_RM"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_RY"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_UV"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_W"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_WW"/&amp;gt;
 *     &amp;lt;enumeration value="ColorAdd_Y"/&amp;gt;
 *     &amp;lt;enumeration value="ColorCalibrationMode"/&amp;gt;
 *     &amp;lt;enumeration value="ColorConsistency"/&amp;gt;
 *     &amp;lt;enumeration value="ColorControl"/&amp;gt;
 *     &amp;lt;enumeration value="ColorEffects1"/&amp;gt;
 *     &amp;lt;enumeration value="ColorEffects2"/&amp;gt;
 *     &amp;lt;enumeration value="ColorEffects3"/&amp;gt;
 *     &amp;lt;enumeration value="ColorEffects4"/&amp;gt;
 *     &amp;lt;enumeration value="ColorEffects5"/&amp;gt;
 *     &amp;lt;enumeration value="ColorMacro1"/&amp;gt;
 *     &amp;lt;enumeration value="ColorMacro2"/&amp;gt;
 *     &amp;lt;enumeration value="ColorMacro3"/&amp;gt;
 *     &amp;lt;enumeration value="ColorMacro4"/&amp;gt;
 *     &amp;lt;enumeration value="ColorMacro5"/&amp;gt;
 *     &amp;lt;enumeration value="ColorMixMSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="ColorMixMode"/&amp;gt;
 *     &amp;lt;enumeration value="ColorMixReset"/&amp;gt;
 *     &amp;lt;enumeration value="ColorModelMode"/&amp;gt;
 *     &amp;lt;enumeration value="ColorRGB_Blue"/&amp;gt;
 *     &amp;lt;enumeration value="ColorRGB_Cyan"/&amp;gt;
 *     &amp;lt;enumeration value="ColorRGB_Green"/&amp;gt;
 *     &amp;lt;enumeration value="ColorRGB_Magenta"/&amp;gt;
 *     &amp;lt;enumeration value="ColorRGB_Quality"/&amp;gt;
 *     &amp;lt;enumeration value="ColorRGB_Red"/&amp;gt;
 *     &amp;lt;enumeration value="ColorRGB_Yellow"/&amp;gt;
 *     &amp;lt;enumeration value="ColorSettingsReset"/&amp;gt;
 *     &amp;lt;enumeration value="ColorSub_B"/&amp;gt;
 *     &amp;lt;enumeration value="ColorSub_C"/&amp;gt;
 *     &amp;lt;enumeration value="ColorSub_G"/&amp;gt;
 *     &amp;lt;enumeration value="ColorSub_M"/&amp;gt;
 *     &amp;lt;enumeration value="ColorSub_R"/&amp;gt;
 *     &amp;lt;enumeration value="ColorSub_Y"/&amp;gt;
 *     &amp;lt;enumeration value="ColorUniformity"/&amp;gt;
 *     &amp;lt;enumeration value="ColorWheelReset"/&amp;gt;
 *     &amp;lt;enumeration value="ColorWheelSelectMSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="ColorWheelShortcutMode"/&amp;gt;
 *     &amp;lt;enumeration value="Control1"/&amp;gt;
 *     &amp;lt;enumeration value="Control2"/&amp;gt;
 *     &amp;lt;enumeration value="Control3"/&amp;gt;
 *     &amp;lt;enumeration value="Control4"/&amp;gt;
 *     &amp;lt;enumeration value="Control5"/&amp;gt;
 *     &amp;lt;enumeration value="CustomColor"/&amp;gt;
 *     &amp;lt;enumeration value="CyanMode"/&amp;gt;
 *     &amp;lt;enumeration value="DMXInput"/&amp;gt;
 *     &amp;lt;enumeration value="Dimmer"/&amp;gt;
 *     &amp;lt;enumeration value="DimmerCurve"/&amp;gt;
 *     &amp;lt;enumeration value="DimmerMode"/&amp;gt;
 *     &amp;lt;enumeration value="DisplayIntensity"/&amp;gt;
 *     &amp;lt;enumeration value="Dummy"/&amp;gt;
 *     &amp;lt;enumeration value="Effects1"/&amp;gt;
 *     &amp;lt;enumeration value="Effects1Adjust1"/&amp;gt;
 *     &amp;lt;enumeration value="Effects1Adjust2"/&amp;gt;
 *     &amp;lt;enumeration value="Effects1Adjust3"/&amp;gt;
 *     &amp;lt;enumeration value="Effects1Adjust4"/&amp;gt;
 *     &amp;lt;enumeration value="Effects1Adjust5"/&amp;gt;
 *     &amp;lt;enumeration value="Effects1Fade"/&amp;gt;
 *     &amp;lt;enumeration value="Effects1Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Effects1PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Effects1Rate"/&amp;gt;
 *     &amp;lt;enumeration value="Effects2"/&amp;gt;
 *     &amp;lt;enumeration value="Effects2Adjust1"/&amp;gt;
 *     &amp;lt;enumeration value="Effects2Adjust2"/&amp;gt;
 *     &amp;lt;enumeration value="Effects2Adjust3"/&amp;gt;
 *     &amp;lt;enumeration value="Effects2Adjust4"/&amp;gt;
 *     &amp;lt;enumeration value="Effects2Adjust5"/&amp;gt;
 *     &amp;lt;enumeration value="Effects2Fade"/&amp;gt;
 *     &amp;lt;enumeration value="Effects2Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Effects2PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Effects2Rate"/&amp;gt;
 *     &amp;lt;enumeration value="Effects3"/&amp;gt;
 *     &amp;lt;enumeration value="Effects3Adjust1"/&amp;gt;
 *     &amp;lt;enumeration value="Effects3Adjust2"/&amp;gt;
 *     &amp;lt;enumeration value="Effects3Adjust3"/&amp;gt;
 *     &amp;lt;enumeration value="Effects3Adjust4"/&amp;gt;
 *     &amp;lt;enumeration value="Effects3Adjust5"/&amp;gt;
 *     &amp;lt;enumeration value="Effects3Fade"/&amp;gt;
 *     &amp;lt;enumeration value="Effects3Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Effects3PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Effects3Rate"/&amp;gt;
 *     &amp;lt;enumeration value="Effects4"/&amp;gt;
 *     &amp;lt;enumeration value="Effects4Adjust1"/&amp;gt;
 *     &amp;lt;enumeration value="Effects4Adjust2"/&amp;gt;
 *     &amp;lt;enumeration value="Effects4Adjust3"/&amp;gt;
 *     &amp;lt;enumeration value="Effects4Adjust4"/&amp;gt;
 *     &amp;lt;enumeration value="Effects4Adjust5"/&amp;gt;
 *     &amp;lt;enumeration value="Effects4Fade"/&amp;gt;
 *     &amp;lt;enumeration value="Effects4Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Effects4PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Effects4Rate"/&amp;gt;
 *     &amp;lt;enumeration value="Effects5"/&amp;gt;
 *     &amp;lt;enumeration value="Effects5Adjust1"/&amp;gt;
 *     &amp;lt;enumeration value="Effects5Adjust2"/&amp;gt;
 *     &amp;lt;enumeration value="Effects5Adjust3"/&amp;gt;
 *     &amp;lt;enumeration value="Effects5Adjust4"/&amp;gt;
 *     &amp;lt;enumeration value="Effects5Adjust5"/&amp;gt;
 *     &amp;lt;enumeration value="Effects5Fade"/&amp;gt;
 *     &amp;lt;enumeration value="Effects5Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Effects5PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Effects5Rate"/&amp;gt;
 *     &amp;lt;enumeration value="EffectsSync"/&amp;gt;
 *     &amp;lt;enumeration value="Fan1"/&amp;gt;
 *     &amp;lt;enumeration value="Fan2"/&amp;gt;
 *     &amp;lt;enumeration value="Fan3"/&amp;gt;
 *     &amp;lt;enumeration value="Fan4"/&amp;gt;
 *     &amp;lt;enumeration value="Fan5"/&amp;gt;
 *     &amp;lt;enumeration value="FanMode"/&amp;gt;
 *     &amp;lt;enumeration value="Fans"/&amp;gt;
 *     &amp;lt;enumeration value="FixtureCalibrationReset"/&amp;gt;
 *     &amp;lt;enumeration value="FixtureGlobalReset"/&amp;gt;
 *     &amp;lt;enumeration value="Focus1"/&amp;gt;
 *     &amp;lt;enumeration value="Focus1Adjust"/&amp;gt;
 *     &amp;lt;enumeration value="Focus1Distance"/&amp;gt;
 *     &amp;lt;enumeration value="Focus2"/&amp;gt;
 *     &amp;lt;enumeration value="Focus2Adjust"/&amp;gt;
 *     &amp;lt;enumeration value="Focus2Distance"/&amp;gt;
 *     &amp;lt;enumeration value="Focus3"/&amp;gt;
 *     &amp;lt;enumeration value="Focus3Adjust"/&amp;gt;
 *     &amp;lt;enumeration value="Focus3Distance"/&amp;gt;
 *     &amp;lt;enumeration value="Focus4"/&amp;gt;
 *     &amp;lt;enumeration value="Focus4Adjust"/&amp;gt;
 *     &amp;lt;enumeration value="Focus4Distance"/&amp;gt;
 *     &amp;lt;enumeration value="Focus5"/&amp;gt;
 *     &amp;lt;enumeration value="Focus5Adjust"/&amp;gt;
 *     &amp;lt;enumeration value="Focus5Distance"/&amp;gt;
 *     &amp;lt;enumeration value="FocusMSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="FocusMode"/&amp;gt;
 *     &amp;lt;enumeration value="FocusReset"/&amp;gt;
 *     &amp;lt;enumeration value="Fog1"/&amp;gt;
 *     &amp;lt;enumeration value="Fog2"/&amp;gt;
 *     &amp;lt;enumeration value="Fog3"/&amp;gt;
 *     &amp;lt;enumeration value="Fog4"/&amp;gt;
 *     &amp;lt;enumeration value="Fog5"/&amp;gt;
 *     &amp;lt;enumeration value="FollowSpotMode"/&amp;gt;
 *     &amp;lt;enumeration value="FrameMSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="FrameReset"/&amp;gt;
 *     &amp;lt;enumeration value="Frost1"/&amp;gt;
 *     &amp;lt;enumeration value="Frost1MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Frost2"/&amp;gt;
 *     &amp;lt;enumeration value="Frost2MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Frost3"/&amp;gt;
 *     &amp;lt;enumeration value="Frost3MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Frost4"/&amp;gt;
 *     &amp;lt;enumeration value="Frost4MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Frost5"/&amp;gt;
 *     &amp;lt;enumeration value="Frost5MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Function"/&amp;gt;
 *     &amp;lt;enumeration value="GlobalMSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1PosShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1SelectEffects"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1SelectShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1WheelAudio"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1WheelIndex"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1WheelMode"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1WheelRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1WheelShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo1WheelSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2PosShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2SelectEffects"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2SelectShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2WheelAudio"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2WheelIndex"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2WheelMode"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2WheelRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2WheelShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo2WheelSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3PosShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3SelectEffects"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3SelectShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3WheelAudio"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3WheelIndex"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3WheelMode"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3WheelRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3WheelShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo3WheelSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4PosShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4SelectEffects"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4SelectShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4WheelAudio"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4WheelIndex"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4WheelMode"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4WheelRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4WheelShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo4WheelSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5PosShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5SelectEffects"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5SelectShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5WheelAudio"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5WheelIndex"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5WheelMode"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5WheelRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5WheelShake"/&amp;gt;
 *     &amp;lt;enumeration value="Gobo5WheelSpin"/&amp;gt;
 *     &amp;lt;enumeration value="GoboWheel1MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="GoboWheel2MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="GoboWheel3MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="GoboWheel4MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="GoboWheel5MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="GoboWheelReset"/&amp;gt;
 *     &amp;lt;enumeration value="HSB_Brightness"/&amp;gt;
 *     &amp;lt;enumeration value="HSB_Hue"/&amp;gt;
 *     &amp;lt;enumeration value="HSB_Quality"/&amp;gt;
 *     &amp;lt;enumeration value="HSB_Saturation"/&amp;gt;
 *     &amp;lt;enumeration value="Haze1"/&amp;gt;
 *     &amp;lt;enumeration value="Haze2"/&amp;gt;
 *     &amp;lt;enumeration value="Haze3"/&amp;gt;
 *     &amp;lt;enumeration value="Haze4"/&amp;gt;
 *     &amp;lt;enumeration value="Haze5"/&amp;gt;
 *     &amp;lt;enumeration value="IntensityMSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="IntensityReset"/&amp;gt;
 *     &amp;lt;enumeration value="Iris"/&amp;gt;
 *     &amp;lt;enumeration value="IrisMSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="IrisMode"/&amp;gt;
 *     &amp;lt;enumeration value="IrisPulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="IrisPulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="IrisReset"/&amp;gt;
 *     &amp;lt;enumeration value="IrisStrobe"/&amp;gt;
 *     &amp;lt;enumeration value="IrisStrobeRandom"/&amp;gt;
 *     &amp;lt;enumeration value="LEDFrequency"/&amp;gt;
 *     &amp;lt;enumeration value="LEDZoneMode"/&amp;gt;
 *     &amp;lt;enumeration value="LampControl"/&amp;gt;
 *     &amp;lt;enumeration value="LampPowerMode"/&amp;gt;
 *     &amp;lt;enumeration value="MagentaMode"/&amp;gt;
 *     &amp;lt;enumeration value="MediaContent"/&amp;gt;
 *     &amp;lt;enumeration value="MediaFolder"/&amp;gt;
 *     &amp;lt;enumeration value="NoFeature"/&amp;gt;
 *     &amp;lt;enumeration value="NoFeature1"/&amp;gt;
 *     &amp;lt;enumeration value="NoFeature2"/&amp;gt;
 *     &amp;lt;enumeration value="NoFeature3"/&amp;gt;
 *     &amp;lt;enumeration value="Pan"/&amp;gt;
 *     &amp;lt;enumeration value="PanMode"/&amp;gt;
 *     &amp;lt;enumeration value="PanReset"/&amp;gt;
 *     &amp;lt;enumeration value="PanTiltMode"/&amp;gt;
 *     &amp;lt;enumeration value="PixelMode"/&amp;gt;
 *     &amp;lt;enumeration value="Playmode"/&amp;gt;
 *     &amp;lt;enumeration value="PositionEffect"/&amp;gt;
 *     &amp;lt;enumeration value="PositionEffectFade"/&amp;gt;
 *     &amp;lt;enumeration value="PositionEffectRate"/&amp;gt;
 *     &amp;lt;enumeration value="PositionMSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="PositionModes"/&amp;gt;
 *     &amp;lt;enumeration value="PositionReset"/&amp;gt;
 *     &amp;lt;enumeration value="Prism1"/&amp;gt;
 *     &amp;lt;enumeration value="Prism1MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Prism1Macro"/&amp;gt;
 *     &amp;lt;enumeration value="Prism1Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Prism1PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Prism1SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Prism2"/&amp;gt;
 *     &amp;lt;enumeration value="Prism2MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Prism2Macro"/&amp;gt;
 *     &amp;lt;enumeration value="Prism2Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Prism2PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Prism2SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Prism3"/&amp;gt;
 *     &amp;lt;enumeration value="Prism3MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Prism3Macro"/&amp;gt;
 *     &amp;lt;enumeration value="Prism3Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Prism3PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Prism3SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Prism4"/&amp;gt;
 *     &amp;lt;enumeration value="Prism4MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Prism4Macro"/&amp;gt;
 *     &amp;lt;enumeration value="Prism4Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Prism4PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Prism4SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="Prism5"/&amp;gt;
 *     &amp;lt;enumeration value="Prism5MSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="Prism5Macro"/&amp;gt;
 *     &amp;lt;enumeration value="Prism5Pos"/&amp;gt;
 *     &amp;lt;enumeration value="Prism5PosRotate"/&amp;gt;
 *     &amp;lt;enumeration value="Prism5SelectSpin"/&amp;gt;
 *     &amp;lt;enumeration value="ReflectorAdjust"/&amp;gt;
 *     &amp;lt;enumeration value="ShaperMacros"/&amp;gt;
 *     &amp;lt;enumeration value="ShaperMacrosSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="ShaperRot"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter1"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter1Strobe"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter1StrobeEffect"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter1StrobePulse"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter1StrobePulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter1StrobePulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter1StrobeRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter1StrobeRandomPulse"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter1StrobeRandomPulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter1StrobeRandomPulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter2"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter2Strobe"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter2StrobeEffect"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter2StrobePulse"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter2StrobePulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter2StrobePulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter2StrobeRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter2StrobeRandomPulse"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter2StrobeRandomPulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter2StrobeRandomPulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter3"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter3Strobe"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter3StrobeEffect"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter3StrobePulse"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter3StrobePulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter3StrobePulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter3StrobeRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter3StrobeRandomPulse"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter3StrobeRandomPulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter3StrobeRandomPulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter4"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter4Strobe"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter4StrobeEffect"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter4StrobePulse"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter4StrobePulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter4StrobePulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter4StrobeRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter4StrobeRandomPulse"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter4StrobeRandomPulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter4StrobeRandomPulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter5"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter5Strobe"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter5StrobeEffect"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter5StrobePulse"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter5StrobePulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter5StrobePulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter5StrobeRandom"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter5StrobeRandomPulse"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter5StrobeRandomPulseClose"/&amp;gt;
 *     &amp;lt;enumeration value="Shutter5StrobeRandomPulseOpen"/&amp;gt;
 *     &amp;lt;enumeration value="ShutterReset"/&amp;gt;
 *     &amp;lt;enumeration value="StrobeDuration"/&amp;gt;
 *     &amp;lt;enumeration value="StrobeMode"/&amp;gt;
 *     &amp;lt;enumeration value="StrobeRate"/&amp;gt;
 *     &amp;lt;enumeration value="Tilt"/&amp;gt;
 *     &amp;lt;enumeration value="TiltMode"/&amp;gt;
 *     &amp;lt;enumeration value="TiltReset"/&amp;gt;
 *     &amp;lt;enumeration value="UVStability"/&amp;gt;
 *     &amp;lt;enumeration value="Video"/&amp;gt;
 *     &amp;lt;enumeration value="VideoCamera1"/&amp;gt;
 *     &amp;lt;enumeration value="VideoCamera2"/&amp;gt;
 *     &amp;lt;enumeration value="VideoCamera3"/&amp;gt;
 *     &amp;lt;enumeration value="VideoCamera4"/&amp;gt;
 *     &amp;lt;enumeration value="VideoCamera5"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect1Parameter1"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect1Parameter2"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect1Parameter3"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect1Parameter4"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect1Parameter5"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect1Type"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect2Parameter1"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect2Parameter2"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect2Parameter3"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect2Parameter4"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect2Parameter5"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect2Type"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect3Parameter1"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect3Parameter2"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect3Parameter3"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect3Parameter4"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect3Parameter5"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect3Type"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect4Parameter1"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect4Parameter2"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect4Parameter3"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect4Parameter4"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect4Parameter5"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect4Type"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect5Parameter1"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect5Parameter2"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect5Parameter3"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect5Parameter4"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect5Parameter5"/&amp;gt;
 *     &amp;lt;enumeration value="VideoEffect5Type"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale1_All"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale1_X"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale1_Y"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale1_Z"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale2_All"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale2_X"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale2_Y"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale2_Z"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale3_All"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale3_X"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale3_Y"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale3_Z"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale4_All"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale4_X"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale4_Y"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale4_Z"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale5_All"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale5_X"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale5_Y"/&amp;gt;
 *     &amp;lt;enumeration value="VideoScale5_Z"/&amp;gt;
 *     &amp;lt;enumeration value="WavelengthCorrection"/&amp;gt;
 *     &amp;lt;enumeration value="WhiteCount"/&amp;gt;
 *     &amp;lt;enumeration value="XYZ_X"/&amp;gt;
 *     &amp;lt;enumeration value="XYZ_Y"/&amp;gt;
 *     &amp;lt;enumeration value="XYZ_Z"/&amp;gt;
 *     &amp;lt;enumeration value="YellowMode"/&amp;gt;
 *     &amp;lt;enumeration value="Zoom"/&amp;gt;
 *     &amp;lt;enumeration value="ZoomMSpeed"/&amp;gt;
 *     &amp;lt;enumeration value="ZoomMode"/&amp;gt;
 *     &amp;lt;enumeration value="ZoomModeBeam"/&amp;gt;
 *     &amp;lt;enumeration value="ZoomModeSpot"/&amp;gt;
 *     &amp;lt;enumeration value="ZoomReset"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "AttributeEnum")
@XmlEnum
public enum AttributeEnum {

    @XmlEnumValue("AnimationWheel1")
    ANIMATION_WHEEL_1("AnimationWheel1"),
    @XmlEnumValue("AnimationWheel1Audio")
    ANIMATION_WHEEL_1_AUDIO("AnimationWheel1Audio"),
    @XmlEnumValue("AnimationWheel1Macro")
    ANIMATION_WHEEL_1_MACRO("AnimationWheel1Macro"),
    @XmlEnumValue("AnimationWheel1Mode")
    ANIMATION_WHEEL_1_MODE("AnimationWheel1Mode"),
    @XmlEnumValue("AnimationWheel1Pos")
    ANIMATION_WHEEL_1_POS("AnimationWheel1Pos"),
    @XmlEnumValue("AnimationWheel1PosRotate")
    ANIMATION_WHEEL_1_POS_ROTATE("AnimationWheel1PosRotate"),
    @XmlEnumValue("AnimationWheel1PosShake")
    ANIMATION_WHEEL_1_POS_SHAKE("AnimationWheel1PosShake"),
    @XmlEnumValue("AnimationWheel1Random")
    ANIMATION_WHEEL_1_RANDOM("AnimationWheel1Random"),
    @XmlEnumValue("AnimationWheel1SelectEffects")
    ANIMATION_WHEEL_1_SELECT_EFFECTS("AnimationWheel1SelectEffects"),
    @XmlEnumValue("AnimationWheel1SelectShake")
    ANIMATION_WHEEL_1_SELECT_SHAKE("AnimationWheel1SelectShake"),
    @XmlEnumValue("AnimationWheel1SelectSpin")
    ANIMATION_WHEEL_1_SELECT_SPIN("AnimationWheel1SelectSpin"),
    @XmlEnumValue("AnimationWheel2")
    ANIMATION_WHEEL_2("AnimationWheel2"),
    @XmlEnumValue("AnimationWheel2Audio")
    ANIMATION_WHEEL_2_AUDIO("AnimationWheel2Audio"),
    @XmlEnumValue("AnimationWheel2Macro")
    ANIMATION_WHEEL_2_MACRO("AnimationWheel2Macro"),
    @XmlEnumValue("AnimationWheel2Mode")
    ANIMATION_WHEEL_2_MODE("AnimationWheel2Mode"),
    @XmlEnumValue("AnimationWheel2Pos")
    ANIMATION_WHEEL_2_POS("AnimationWheel2Pos"),
    @XmlEnumValue("AnimationWheel2PosRotate")
    ANIMATION_WHEEL_2_POS_ROTATE("AnimationWheel2PosRotate"),
    @XmlEnumValue("AnimationWheel2PosShake")
    ANIMATION_WHEEL_2_POS_SHAKE("AnimationWheel2PosShake"),
    @XmlEnumValue("AnimationWheel2Random")
    ANIMATION_WHEEL_2_RANDOM("AnimationWheel2Random"),
    @XmlEnumValue("AnimationWheel2SelectEffects")
    ANIMATION_WHEEL_2_SELECT_EFFECTS("AnimationWheel2SelectEffects"),
    @XmlEnumValue("AnimationWheel2SelectShake")
    ANIMATION_WHEEL_2_SELECT_SHAKE("AnimationWheel2SelectShake"),
    @XmlEnumValue("AnimationWheel2SelectSpin")
    ANIMATION_WHEEL_2_SELECT_SPIN("AnimationWheel2SelectSpin"),
    @XmlEnumValue("AnimationWheel3")
    ANIMATION_WHEEL_3("AnimationWheel3"),
    @XmlEnumValue("AnimationWheel3Audio")
    ANIMATION_WHEEL_3_AUDIO("AnimationWheel3Audio"),
    @XmlEnumValue("AnimationWheel3Macro")
    ANIMATION_WHEEL_3_MACRO("AnimationWheel3Macro"),
    @XmlEnumValue("AnimationWheel3Mode")
    ANIMATION_WHEEL_3_MODE("AnimationWheel3Mode"),
    @XmlEnumValue("AnimationWheel3Pos")
    ANIMATION_WHEEL_3_POS("AnimationWheel3Pos"),
    @XmlEnumValue("AnimationWheel3PosRotate")
    ANIMATION_WHEEL_3_POS_ROTATE("AnimationWheel3PosRotate"),
    @XmlEnumValue("AnimationWheel3PosShake")
    ANIMATION_WHEEL_3_POS_SHAKE("AnimationWheel3PosShake"),
    @XmlEnumValue("AnimationWheel3Random")
    ANIMATION_WHEEL_3_RANDOM("AnimationWheel3Random"),
    @XmlEnumValue("AnimationWheel3SelectEffects")
    ANIMATION_WHEEL_3_SELECT_EFFECTS("AnimationWheel3SelectEffects"),
    @XmlEnumValue("AnimationWheel3SelectShake")
    ANIMATION_WHEEL_3_SELECT_SHAKE("AnimationWheel3SelectShake"),
    @XmlEnumValue("AnimationWheel3SelectSpin")
    ANIMATION_WHEEL_3_SELECT_SPIN("AnimationWheel3SelectSpin"),
    @XmlEnumValue("AnimationWheel4")
    ANIMATION_WHEEL_4("AnimationWheel4"),
    @XmlEnumValue("AnimationWheel4Audio")
    ANIMATION_WHEEL_4_AUDIO("AnimationWheel4Audio"),
    @XmlEnumValue("AnimationWheel4Macro")
    ANIMATION_WHEEL_4_MACRO("AnimationWheel4Macro"),
    @XmlEnumValue("AnimationWheel4Mode")
    ANIMATION_WHEEL_4_MODE("AnimationWheel4Mode"),
    @XmlEnumValue("AnimationWheel4Pos")
    ANIMATION_WHEEL_4_POS("AnimationWheel4Pos"),
    @XmlEnumValue("AnimationWheel4PosRotate")
    ANIMATION_WHEEL_4_POS_ROTATE("AnimationWheel4PosRotate"),
    @XmlEnumValue("AnimationWheel4PosShake")
    ANIMATION_WHEEL_4_POS_SHAKE("AnimationWheel4PosShake"),
    @XmlEnumValue("AnimationWheel4Random")
    ANIMATION_WHEEL_4_RANDOM("AnimationWheel4Random"),
    @XmlEnumValue("AnimationWheel4SelectEffects")
    ANIMATION_WHEEL_4_SELECT_EFFECTS("AnimationWheel4SelectEffects"),
    @XmlEnumValue("AnimationWheel4SelectShake")
    ANIMATION_WHEEL_4_SELECT_SHAKE("AnimationWheel4SelectShake"),
    @XmlEnumValue("AnimationWheel4SelectSpin")
    ANIMATION_WHEEL_4_SELECT_SPIN("AnimationWheel4SelectSpin"),
    @XmlEnumValue("AnimationWheel5")
    ANIMATION_WHEEL_5("AnimationWheel5"),
    @XmlEnumValue("AnimationWheel5Audio")
    ANIMATION_WHEEL_5_AUDIO("AnimationWheel5Audio"),
    @XmlEnumValue("AnimationWheel5Macro")
    ANIMATION_WHEEL_5_MACRO("AnimationWheel5Macro"),
    @XmlEnumValue("AnimationWheel5Mode")
    ANIMATION_WHEEL_5_MODE("AnimationWheel5Mode"),
    @XmlEnumValue("AnimationWheel5Pos")
    ANIMATION_WHEEL_5_POS("AnimationWheel5Pos"),
    @XmlEnumValue("AnimationWheel5PosRotate")
    ANIMATION_WHEEL_5_POS_ROTATE("AnimationWheel5PosRotate"),
    @XmlEnumValue("AnimationWheel5PosShake")
    ANIMATION_WHEEL_5_POS_SHAKE("AnimationWheel5PosShake"),
    @XmlEnumValue("AnimationWheel5Random")
    ANIMATION_WHEEL_5_RANDOM("AnimationWheel5Random"),
    @XmlEnumValue("AnimationWheel5SelectEffects")
    ANIMATION_WHEEL_5_SELECT_EFFECTS("AnimationWheel5SelectEffects"),
    @XmlEnumValue("AnimationWheel5SelectShake")
    ANIMATION_WHEEL_5_SELECT_SHAKE("AnimationWheel5SelectShake"),
    @XmlEnumValue("AnimationWheel5SelectSpin")
    ANIMATION_WHEEL_5_SELECT_SPIN("AnimationWheel5SelectSpin"),
    @XmlEnumValue("AnimationWheelShortcutMode")
    ANIMATION_WHEEL_SHORTCUT_MODE("AnimationWheelShortcutMode"),
    @XmlEnumValue("BeamEffectIndexRotateMode")
    BEAM_EFFECT_INDEX_ROTATE_MODE("BeamEffectIndexRotateMode"),
    @XmlEnumValue("BeamReset")
    BEAM_RESET("BeamReset"),
    @XmlEnumValue("BeamShaper")
    BEAM_SHAPER("BeamShaper"),
    @XmlEnumValue("BeamShaperMacro")
    BEAM_SHAPER_MACRO("BeamShaperMacro"),
    @XmlEnumValue("BeamShaperPos")
    BEAM_SHAPER_POS("BeamShaperPos"),
    @XmlEnumValue("BeamShaperPosRotate")
    BEAM_SHAPER_POS_ROTATE("BeamShaperPosRotate"),
    @XmlEnumValue("BlackoutMode")
    BLACKOUT_MODE("BlackoutMode"),
    @XmlEnumValue("Blade1A")
    BLADE_1_A("Blade1A"),
    @XmlEnumValue("Blade1B")
    BLADE_1_B("Blade1B"),
    @XmlEnumValue("Blade1Rot")
    BLADE_1_ROT("Blade1Rot"),
    @XmlEnumValue("Blade2A")
    BLADE_2_A("Blade2A"),
    @XmlEnumValue("Blade2B")
    BLADE_2_B("Blade2B"),
    @XmlEnumValue("Blade2Rot")
    BLADE_2_ROT("Blade2Rot"),
    @XmlEnumValue("Blade3A")
    BLADE_3_A("Blade3A"),
    @XmlEnumValue("Blade3B")
    BLADE_3_B("Blade3B"),
    @XmlEnumValue("Blade3Rot")
    BLADE_3_ROT("Blade3Rot"),
    @XmlEnumValue("Blade4A")
    BLADE_4_A("Blade4A"),
    @XmlEnumValue("Blade4B")
    BLADE_4_B("Blade4B"),
    @XmlEnumValue("Blade4Rot")
    BLADE_4_ROT("Blade4Rot"),
    @XmlEnumValue("Blade5A")
    BLADE_5_A("Blade5A"),
    @XmlEnumValue("Blade5B")
    BLADE_5_B("Blade5B"),
    @XmlEnumValue("Blade5Rot")
    BLADE_5_ROT("Blade5Rot"),
    @XmlEnumValue("Blower1")
    BLOWER_1("Blower1"),
    @XmlEnumValue("Blower2")
    BLOWER_2("Blower2"),
    @XmlEnumValue("Blower3")
    BLOWER_3("Blower3"),
    @XmlEnumValue("Blower4")
    BLOWER_4("Blower4"),
    @XmlEnumValue("Blower5")
    BLOWER_5("Blower5"),
    @XmlEnumValue("CIE_Brightness")
    CIE_BRIGHTNESS("CIE_Brightness"),
    CIE_X("CIE_X"),
    CIE_Y("CIE_Y"),
    @XmlEnumValue("CRIMode")
    CRI_MODE("CRIMode"),
    CTB("CTB"),
    @XmlEnumValue("CTBReset")
    CTB_RESET("CTBReset"),
    CTC("CTC"),
    @XmlEnumValue("CTCReset")
    CTC_RESET("CTCReset"),
    CTO("CTO"),
    @XmlEnumValue("CTOReset")
    CTO_RESET("CTOReset"),
    @XmlEnumValue("ChromaticMode")
    CHROMATIC_MODE("ChromaticMode"),
    @XmlEnumValue("Color1")
    COLOR_1("Color1"),
    @XmlEnumValue("Color1Mode")
    COLOR_1_MODE("Color1Mode"),
    @XmlEnumValue("Color1WheelAudio")
    COLOR_1_WHEEL_AUDIO("Color1WheelAudio"),
    @XmlEnumValue("Color1WheelIndex")
    COLOR_1_WHEEL_INDEX("Color1WheelIndex"),
    @XmlEnumValue("Color1WheelRandom")
    COLOR_1_WHEEL_RANDOM("Color1WheelRandom"),
    @XmlEnumValue("Color1WheelSpin")
    COLOR_1_WHEEL_SPIN("Color1WheelSpin"),
    @XmlEnumValue("Color2")
    COLOR_2("Color2"),
    @XmlEnumValue("Color2Mode")
    COLOR_2_MODE("Color2Mode"),
    @XmlEnumValue("Color2WheelAudio")
    COLOR_2_WHEEL_AUDIO("Color2WheelAudio"),
    @XmlEnumValue("Color2WheelIndex")
    COLOR_2_WHEEL_INDEX("Color2WheelIndex"),
    @XmlEnumValue("Color2WheelRandom")
    COLOR_2_WHEEL_RANDOM("Color2WheelRandom"),
    @XmlEnumValue("Color2WheelSpin")
    COLOR_2_WHEEL_SPIN("Color2WheelSpin"),
    @XmlEnumValue("Color3")
    COLOR_3("Color3"),
    @XmlEnumValue("Color3Mode")
    COLOR_3_MODE("Color3Mode"),
    @XmlEnumValue("Color3WheelAudio")
    COLOR_3_WHEEL_AUDIO("Color3WheelAudio"),
    @XmlEnumValue("Color3WheelIndex")
    COLOR_3_WHEEL_INDEX("Color3WheelIndex"),
    @XmlEnumValue("Color3WheelRandom")
    COLOR_3_WHEEL_RANDOM("Color3WheelRandom"),
    @XmlEnumValue("Color3WheelSpin")
    COLOR_3_WHEEL_SPIN("Color3WheelSpin"),
    @XmlEnumValue("Color4")
    COLOR_4("Color4"),
    @XmlEnumValue("Color4Mode")
    COLOR_4_MODE("Color4Mode"),
    @XmlEnumValue("Color4WheelAudio")
    COLOR_4_WHEEL_AUDIO("Color4WheelAudio"),
    @XmlEnumValue("Color4WheelIndex")
    COLOR_4_WHEEL_INDEX("Color4WheelIndex"),
    @XmlEnumValue("Color4WheelRandom")
    COLOR_4_WHEEL_RANDOM("Color4WheelRandom"),
    @XmlEnumValue("Color4WheelSpin")
    COLOR_4_WHEEL_SPIN("Color4WheelSpin"),
    @XmlEnumValue("Color5")
    COLOR_5("Color5"),
    @XmlEnumValue("Color5Mode")
    COLOR_5_MODE("Color5Mode"),
    @XmlEnumValue("Color5WheelAudio")
    COLOR_5_WHEEL_AUDIO("Color5WheelAudio"),
    @XmlEnumValue("Color5WheelIndex")
    COLOR_5_WHEEL_INDEX("Color5WheelIndex"),
    @XmlEnumValue("Color5WheelRandom")
    COLOR_5_WHEEL_RANDOM("Color5WheelRandom"),
    @XmlEnumValue("Color5WheelSpin")
    COLOR_5_WHEEL_SPIN("Color5WheelSpin"),
    @XmlEnumValue("ColorAdd_B")
    COLOR_ADD_B("ColorAdd_B"),
    @XmlEnumValue("ColorAdd_BC")
    COLOR_ADD_BC("ColorAdd_BC"),
    @XmlEnumValue("ColorAdd_BM")
    COLOR_ADD_BM("ColorAdd_BM"),
    @XmlEnumValue("ColorAdd_C")
    COLOR_ADD_C("ColorAdd_C"),
    @XmlEnumValue("ColorAdd_CW")
    COLOR_ADD_CW("ColorAdd_CW"),
    @XmlEnumValue("ColorAdd_G")
    COLOR_ADD_G("ColorAdd_G"),
    @XmlEnumValue("ColorAdd_GC")
    COLOR_ADD_GC("ColorAdd_GC"),
    @XmlEnumValue("ColorAdd_GY")
    COLOR_ADD_GY("ColorAdd_GY"),
    @XmlEnumValue("ColorAdd_M")
    COLOR_ADD_M("ColorAdd_M"),
    @XmlEnumValue("ColorAdd_R")
    COLOR_ADD_R("ColorAdd_R"),
    @XmlEnumValue("ColorAdd_RM")
    COLOR_ADD_RM("ColorAdd_RM"),
    @XmlEnumValue("ColorAdd_RY")
    COLOR_ADD_RY("ColorAdd_RY"),
    @XmlEnumValue("ColorAdd_UV")
    COLOR_ADD_UV("ColorAdd_UV"),
    @XmlEnumValue("ColorAdd_W")
    COLOR_ADD_W("ColorAdd_W"),
    @XmlEnumValue("ColorAdd_WW")
    COLOR_ADD_WW("ColorAdd_WW"),
    @XmlEnumValue("ColorAdd_Y")
    COLOR_ADD_Y("ColorAdd_Y"),
    @XmlEnumValue("ColorCalibrationMode")
    COLOR_CALIBRATION_MODE("ColorCalibrationMode"),
    @XmlEnumValue("ColorConsistency")
    COLOR_CONSISTENCY("ColorConsistency"),
    @XmlEnumValue("ColorControl")
    COLOR_CONTROL("ColorControl"),
    @XmlEnumValue("ColorEffects1")
    COLOR_EFFECTS_1("ColorEffects1"),
    @XmlEnumValue("ColorEffects2")
    COLOR_EFFECTS_2("ColorEffects2"),
    @XmlEnumValue("ColorEffects3")
    COLOR_EFFECTS_3("ColorEffects3"),
    @XmlEnumValue("ColorEffects4")
    COLOR_EFFECTS_4("ColorEffects4"),
    @XmlEnumValue("ColorEffects5")
    COLOR_EFFECTS_5("ColorEffects5"),
    @XmlEnumValue("ColorMacro1")
    COLOR_MACRO_1("ColorMacro1"),
    @XmlEnumValue("ColorMacro2")
    COLOR_MACRO_2("ColorMacro2"),
    @XmlEnumValue("ColorMacro3")
    COLOR_MACRO_3("ColorMacro3"),
    @XmlEnumValue("ColorMacro4")
    COLOR_MACRO_4("ColorMacro4"),
    @XmlEnumValue("ColorMacro5")
    COLOR_MACRO_5("ColorMacro5"),
    @XmlEnumValue("ColorMixMSpeed")
    COLOR_MIX_M_SPEED("ColorMixMSpeed"),
    @XmlEnumValue("ColorMixMode")
    COLOR_MIX_MODE("ColorMixMode"),
    @XmlEnumValue("ColorMixReset")
    COLOR_MIX_RESET("ColorMixReset"),
    @XmlEnumValue("ColorModelMode")
    COLOR_MODEL_MODE("ColorModelMode"),
    @XmlEnumValue("ColorRGB_Blue")
    COLOR_RGB_BLUE("ColorRGB_Blue"),
    @XmlEnumValue("ColorRGB_Cyan")
    COLOR_RGB_CYAN("ColorRGB_Cyan"),
    @XmlEnumValue("ColorRGB_Green")
    COLOR_RGB_GREEN("ColorRGB_Green"),
    @XmlEnumValue("ColorRGB_Magenta")
    COLOR_RGB_MAGENTA("ColorRGB_Magenta"),
    @XmlEnumValue("ColorRGB_Quality")
    COLOR_RGB_QUALITY("ColorRGB_Quality"),
    @XmlEnumValue("ColorRGB_Red")
    COLOR_RGB_RED("ColorRGB_Red"),
    @XmlEnumValue("ColorRGB_Yellow")
    COLOR_RGB_YELLOW("ColorRGB_Yellow"),
    @XmlEnumValue("ColorSettingsReset")
    COLOR_SETTINGS_RESET("ColorSettingsReset"),
    @XmlEnumValue("ColorSub_B")
    COLOR_SUB_B("ColorSub_B"),
    @XmlEnumValue("ColorSub_C")
    COLOR_SUB_C("ColorSub_C"),
    @XmlEnumValue("ColorSub_G")
    COLOR_SUB_G("ColorSub_G"),
    @XmlEnumValue("ColorSub_M")
    COLOR_SUB_M("ColorSub_M"),
    @XmlEnumValue("ColorSub_R")
    COLOR_SUB_R("ColorSub_R"),
    @XmlEnumValue("ColorSub_Y")
    COLOR_SUB_Y("ColorSub_Y"),
    @XmlEnumValue("ColorUniformity")
    COLOR_UNIFORMITY("ColorUniformity"),
    @XmlEnumValue("ColorWheelReset")
    COLOR_WHEEL_RESET("ColorWheelReset"),
    @XmlEnumValue("ColorWheelSelectMSpeed")
    COLOR_WHEEL_SELECT_M_SPEED("ColorWheelSelectMSpeed"),
    @XmlEnumValue("ColorWheelShortcutMode")
    COLOR_WHEEL_SHORTCUT_MODE("ColorWheelShortcutMode"),
    @XmlEnumValue("Control1")
    CONTROL_1("Control1"),
    @XmlEnumValue("Control2")
    CONTROL_2("Control2"),
    @XmlEnumValue("Control3")
    CONTROL_3("Control3"),
    @XmlEnumValue("Control4")
    CONTROL_4("Control4"),
    @XmlEnumValue("Control5")
    CONTROL_5("Control5"),
    @XmlEnumValue("CustomColor")
    CUSTOM_COLOR("CustomColor"),
    @XmlEnumValue("CyanMode")
    CYAN_MODE("CyanMode"),
    @XmlEnumValue("DMXInput")
    DMX_INPUT("DMXInput"),
    @XmlEnumValue("Dimmer")
    DIMMER("Dimmer"),
    @XmlEnumValue("DimmerCurve")
    DIMMER_CURVE("DimmerCurve"),
    @XmlEnumValue("DimmerMode")
    DIMMER_MODE("DimmerMode"),
    @XmlEnumValue("DisplayIntensity")
    DISPLAY_INTENSITY("DisplayIntensity"),
    @XmlEnumValue("Dummy")
    DUMMY("Dummy"),
    @XmlEnumValue("Effects1")
    EFFECTS_1("Effects1"),
    @XmlEnumValue("Effects1Adjust1")
    EFFECTS_1_ADJUST_1("Effects1Adjust1"),
    @XmlEnumValue("Effects1Adjust2")
    EFFECTS_1_ADJUST_2("Effects1Adjust2"),
    @XmlEnumValue("Effects1Adjust3")
    EFFECTS_1_ADJUST_3("Effects1Adjust3"),
    @XmlEnumValue("Effects1Adjust4")
    EFFECTS_1_ADJUST_4("Effects1Adjust4"),
    @XmlEnumValue("Effects1Adjust5")
    EFFECTS_1_ADJUST_5("Effects1Adjust5"),
    @XmlEnumValue("Effects1Fade")
    EFFECTS_1_FADE("Effects1Fade"),
    @XmlEnumValue("Effects1Pos")
    EFFECTS_1_POS("Effects1Pos"),
    @XmlEnumValue("Effects1PosRotate")
    EFFECTS_1_POS_ROTATE("Effects1PosRotate"),
    @XmlEnumValue("Effects1Rate")
    EFFECTS_1_RATE("Effects1Rate"),
    @XmlEnumValue("Effects2")
    EFFECTS_2("Effects2"),
    @XmlEnumValue("Effects2Adjust1")
    EFFECTS_2_ADJUST_1("Effects2Adjust1"),
    @XmlEnumValue("Effects2Adjust2")
    EFFECTS_2_ADJUST_2("Effects2Adjust2"),
    @XmlEnumValue("Effects2Adjust3")
    EFFECTS_2_ADJUST_3("Effects2Adjust3"),
    @XmlEnumValue("Effects2Adjust4")
    EFFECTS_2_ADJUST_4("Effects2Adjust4"),
    @XmlEnumValue("Effects2Adjust5")
    EFFECTS_2_ADJUST_5("Effects2Adjust5"),
    @XmlEnumValue("Effects2Fade")
    EFFECTS_2_FADE("Effects2Fade"),
    @XmlEnumValue("Effects2Pos")
    EFFECTS_2_POS("Effects2Pos"),
    @XmlEnumValue("Effects2PosRotate")
    EFFECTS_2_POS_ROTATE("Effects2PosRotate"),
    @XmlEnumValue("Effects2Rate")
    EFFECTS_2_RATE("Effects2Rate"),
    @XmlEnumValue("Effects3")
    EFFECTS_3("Effects3"),
    @XmlEnumValue("Effects3Adjust1")
    EFFECTS_3_ADJUST_1("Effects3Adjust1"),
    @XmlEnumValue("Effects3Adjust2")
    EFFECTS_3_ADJUST_2("Effects3Adjust2"),
    @XmlEnumValue("Effects3Adjust3")
    EFFECTS_3_ADJUST_3("Effects3Adjust3"),
    @XmlEnumValue("Effects3Adjust4")
    EFFECTS_3_ADJUST_4("Effects3Adjust4"),
    @XmlEnumValue("Effects3Adjust5")
    EFFECTS_3_ADJUST_5("Effects3Adjust5"),
    @XmlEnumValue("Effects3Fade")
    EFFECTS_3_FADE("Effects3Fade"),
    @XmlEnumValue("Effects3Pos")
    EFFECTS_3_POS("Effects3Pos"),
    @XmlEnumValue("Effects3PosRotate")
    EFFECTS_3_POS_ROTATE("Effects3PosRotate"),
    @XmlEnumValue("Effects3Rate")
    EFFECTS_3_RATE("Effects3Rate"),
    @XmlEnumValue("Effects4")
    EFFECTS_4("Effects4"),
    @XmlEnumValue("Effects4Adjust1")
    EFFECTS_4_ADJUST_1("Effects4Adjust1"),
    @XmlEnumValue("Effects4Adjust2")
    EFFECTS_4_ADJUST_2("Effects4Adjust2"),
    @XmlEnumValue("Effects4Adjust3")
    EFFECTS_4_ADJUST_3("Effects4Adjust3"),
    @XmlEnumValue("Effects4Adjust4")
    EFFECTS_4_ADJUST_4("Effects4Adjust4"),
    @XmlEnumValue("Effects4Adjust5")
    EFFECTS_4_ADJUST_5("Effects4Adjust5"),
    @XmlEnumValue("Effects4Fade")
    EFFECTS_4_FADE("Effects4Fade"),
    @XmlEnumValue("Effects4Pos")
    EFFECTS_4_POS("Effects4Pos"),
    @XmlEnumValue("Effects4PosRotate")
    EFFECTS_4_POS_ROTATE("Effects4PosRotate"),
    @XmlEnumValue("Effects4Rate")
    EFFECTS_4_RATE("Effects4Rate"),
    @XmlEnumValue("Effects5")
    EFFECTS_5("Effects5"),
    @XmlEnumValue("Effects5Adjust1")
    EFFECTS_5_ADJUST_1("Effects5Adjust1"),
    @XmlEnumValue("Effects5Adjust2")
    EFFECTS_5_ADJUST_2("Effects5Adjust2"),
    @XmlEnumValue("Effects5Adjust3")
    EFFECTS_5_ADJUST_3("Effects5Adjust3"),
    @XmlEnumValue("Effects5Adjust4")
    EFFECTS_5_ADJUST_4("Effects5Adjust4"),
    @XmlEnumValue("Effects5Adjust5")
    EFFECTS_5_ADJUST_5("Effects5Adjust5"),
    @XmlEnumValue("Effects5Fade")
    EFFECTS_5_FADE("Effects5Fade"),
    @XmlEnumValue("Effects5Pos")
    EFFECTS_5_POS("Effects5Pos"),
    @XmlEnumValue("Effects5PosRotate")
    EFFECTS_5_POS_ROTATE("Effects5PosRotate"),
    @XmlEnumValue("Effects5Rate")
    EFFECTS_5_RATE("Effects5Rate"),
    @XmlEnumValue("EffectsSync")
    EFFECTS_SYNC("EffectsSync"),
    @XmlEnumValue("Fan1")
    FAN_1("Fan1"),
    @XmlEnumValue("Fan2")
    FAN_2("Fan2"),
    @XmlEnumValue("Fan3")
    FAN_3("Fan3"),
    @XmlEnumValue("Fan4")
    FAN_4("Fan4"),
    @XmlEnumValue("Fan5")
    FAN_5("Fan5"),
    @XmlEnumValue("FanMode")
    FAN_MODE("FanMode"),
    @XmlEnumValue("Fans")
    FANS("Fans"),
    @XmlEnumValue("FixtureCalibrationReset")
    FIXTURE_CALIBRATION_RESET("FixtureCalibrationReset"),
    @XmlEnumValue("FixtureGlobalReset")
    FIXTURE_GLOBAL_RESET("FixtureGlobalReset"),
    @XmlEnumValue("Focus1")
    FOCUS_1("Focus1"),
    @XmlEnumValue("Focus1Adjust")
    FOCUS_1_ADJUST("Focus1Adjust"),
    @XmlEnumValue("Focus1Distance")
    FOCUS_1_DISTANCE("Focus1Distance"),
    @XmlEnumValue("Focus2")
    FOCUS_2("Focus2"),
    @XmlEnumValue("Focus2Adjust")
    FOCUS_2_ADJUST("Focus2Adjust"),
    @XmlEnumValue("Focus2Distance")
    FOCUS_2_DISTANCE("Focus2Distance"),
    @XmlEnumValue("Focus3")
    FOCUS_3("Focus3"),
    @XmlEnumValue("Focus3Adjust")
    FOCUS_3_ADJUST("Focus3Adjust"),
    @XmlEnumValue("Focus3Distance")
    FOCUS_3_DISTANCE("Focus3Distance"),
    @XmlEnumValue("Focus4")
    FOCUS_4("Focus4"),
    @XmlEnumValue("Focus4Adjust")
    FOCUS_4_ADJUST("Focus4Adjust"),
    @XmlEnumValue("Focus4Distance")
    FOCUS_4_DISTANCE("Focus4Distance"),
    @XmlEnumValue("Focus5")
    FOCUS_5("Focus5"),
    @XmlEnumValue("Focus5Adjust")
    FOCUS_5_ADJUST("Focus5Adjust"),
    @XmlEnumValue("Focus5Distance")
    FOCUS_5_DISTANCE("Focus5Distance"),
    @XmlEnumValue("FocusMSpeed")
    FOCUS_M_SPEED("FocusMSpeed"),
    @XmlEnumValue("FocusMode")
    FOCUS_MODE("FocusMode"),
    @XmlEnumValue("FocusReset")
    FOCUS_RESET("FocusReset"),
    @XmlEnumValue("Fog1")
    FOG_1("Fog1"),
    @XmlEnumValue("Fog2")
    FOG_2("Fog2"),
    @XmlEnumValue("Fog3")
    FOG_3("Fog3"),
    @XmlEnumValue("Fog4")
    FOG_4("Fog4"),
    @XmlEnumValue("Fog5")
    FOG_5("Fog5"),
    @XmlEnumValue("FollowSpotMode")
    FOLLOW_SPOT_MODE("FollowSpotMode"),
    @XmlEnumValue("FrameMSpeed")
    FRAME_M_SPEED("FrameMSpeed"),
    @XmlEnumValue("FrameReset")
    FRAME_RESET("FrameReset"),
    @XmlEnumValue("Frost1")
    FROST_1("Frost1"),
    @XmlEnumValue("Frost1MSpeed")
    FROST_1_M_SPEED("Frost1MSpeed"),
    @XmlEnumValue("Frost2")
    FROST_2("Frost2"),
    @XmlEnumValue("Frost2MSpeed")
    FROST_2_M_SPEED("Frost2MSpeed"),
    @XmlEnumValue("Frost3")
    FROST_3("Frost3"),
    @XmlEnumValue("Frost3MSpeed")
    FROST_3_M_SPEED("Frost3MSpeed"),
    @XmlEnumValue("Frost4")
    FROST_4("Frost4"),
    @XmlEnumValue("Frost4MSpeed")
    FROST_4_M_SPEED("Frost4MSpeed"),
    @XmlEnumValue("Frost5")
    FROST_5("Frost5"),
    @XmlEnumValue("Frost5MSpeed")
    FROST_5_M_SPEED("Frost5MSpeed"),
    @XmlEnumValue("Function")
    FUNCTION("Function"),
    @XmlEnumValue("GlobalMSpeed")
    GLOBAL_M_SPEED("GlobalMSpeed"),
    @XmlEnumValue("Gobo1")
    GOBO_1("Gobo1"),
    @XmlEnumValue("Gobo1Pos")
    GOBO_1_POS("Gobo1Pos"),
    @XmlEnumValue("Gobo1PosRotate")
    GOBO_1_POS_ROTATE("Gobo1PosRotate"),
    @XmlEnumValue("Gobo1PosShake")
    GOBO_1_POS_SHAKE("Gobo1PosShake"),
    @XmlEnumValue("Gobo1SelectEffects")
    GOBO_1_SELECT_EFFECTS("Gobo1SelectEffects"),
    @XmlEnumValue("Gobo1SelectShake")
    GOBO_1_SELECT_SHAKE("Gobo1SelectShake"),
    @XmlEnumValue("Gobo1SelectSpin")
    GOBO_1_SELECT_SPIN("Gobo1SelectSpin"),
    @XmlEnumValue("Gobo1WheelAudio")
    GOBO_1_WHEEL_AUDIO("Gobo1WheelAudio"),
    @XmlEnumValue("Gobo1WheelIndex")
    GOBO_1_WHEEL_INDEX("Gobo1WheelIndex"),
    @XmlEnumValue("Gobo1WheelMode")
    GOBO_1_WHEEL_MODE("Gobo1WheelMode"),
    @XmlEnumValue("Gobo1WheelRandom")
    GOBO_1_WHEEL_RANDOM("Gobo1WheelRandom"),
    @XmlEnumValue("Gobo1WheelShake")
    GOBO_1_WHEEL_SHAKE("Gobo1WheelShake"),
    @XmlEnumValue("Gobo1WheelSpin")
    GOBO_1_WHEEL_SPIN("Gobo1WheelSpin"),
    @XmlEnumValue("Gobo2")
    GOBO_2("Gobo2"),
    @XmlEnumValue("Gobo2Pos")
    GOBO_2_POS("Gobo2Pos"),
    @XmlEnumValue("Gobo2PosRotate")
    GOBO_2_POS_ROTATE("Gobo2PosRotate"),
    @XmlEnumValue("Gobo2PosShake")
    GOBO_2_POS_SHAKE("Gobo2PosShake"),
    @XmlEnumValue("Gobo2SelectEffects")
    GOBO_2_SELECT_EFFECTS("Gobo2SelectEffects"),
    @XmlEnumValue("Gobo2SelectShake")
    GOBO_2_SELECT_SHAKE("Gobo2SelectShake"),
    @XmlEnumValue("Gobo2SelectSpin")
    GOBO_2_SELECT_SPIN("Gobo2SelectSpin"),
    @XmlEnumValue("Gobo2WheelAudio")
    GOBO_2_WHEEL_AUDIO("Gobo2WheelAudio"),
    @XmlEnumValue("Gobo2WheelIndex")
    GOBO_2_WHEEL_INDEX("Gobo2WheelIndex"),
    @XmlEnumValue("Gobo2WheelMode")
    GOBO_2_WHEEL_MODE("Gobo2WheelMode"),
    @XmlEnumValue("Gobo2WheelRandom")
    GOBO_2_WHEEL_RANDOM("Gobo2WheelRandom"),
    @XmlEnumValue("Gobo2WheelShake")
    GOBO_2_WHEEL_SHAKE("Gobo2WheelShake"),
    @XmlEnumValue("Gobo2WheelSpin")
    GOBO_2_WHEEL_SPIN("Gobo2WheelSpin"),
    @XmlEnumValue("Gobo3")
    GOBO_3("Gobo3"),
    @XmlEnumValue("Gobo3Pos")
    GOBO_3_POS("Gobo3Pos"),
    @XmlEnumValue("Gobo3PosRotate")
    GOBO_3_POS_ROTATE("Gobo3PosRotate"),
    @XmlEnumValue("Gobo3PosShake")
    GOBO_3_POS_SHAKE("Gobo3PosShake"),
    @XmlEnumValue("Gobo3SelectEffects")
    GOBO_3_SELECT_EFFECTS("Gobo3SelectEffects"),
    @XmlEnumValue("Gobo3SelectShake")
    GOBO_3_SELECT_SHAKE("Gobo3SelectShake"),
    @XmlEnumValue("Gobo3SelectSpin")
    GOBO_3_SELECT_SPIN("Gobo3SelectSpin"),
    @XmlEnumValue("Gobo3WheelAudio")
    GOBO_3_WHEEL_AUDIO("Gobo3WheelAudio"),
    @XmlEnumValue("Gobo3WheelIndex")
    GOBO_3_WHEEL_INDEX("Gobo3WheelIndex"),
    @XmlEnumValue("Gobo3WheelMode")
    GOBO_3_WHEEL_MODE("Gobo3WheelMode"),
    @XmlEnumValue("Gobo3WheelRandom")
    GOBO_3_WHEEL_RANDOM("Gobo3WheelRandom"),
    @XmlEnumValue("Gobo3WheelShake")
    GOBO_3_WHEEL_SHAKE("Gobo3WheelShake"),
    @XmlEnumValue("Gobo3WheelSpin")
    GOBO_3_WHEEL_SPIN("Gobo3WheelSpin"),
    @XmlEnumValue("Gobo4")
    GOBO_4("Gobo4"),
    @XmlEnumValue("Gobo4Pos")
    GOBO_4_POS("Gobo4Pos"),
    @XmlEnumValue("Gobo4PosRotate")
    GOBO_4_POS_ROTATE("Gobo4PosRotate"),
    @XmlEnumValue("Gobo4PosShake")
    GOBO_4_POS_SHAKE("Gobo4PosShake"),
    @XmlEnumValue("Gobo4SelectEffects")
    GOBO_4_SELECT_EFFECTS("Gobo4SelectEffects"),
    @XmlEnumValue("Gobo4SelectShake")
    GOBO_4_SELECT_SHAKE("Gobo4SelectShake"),
    @XmlEnumValue("Gobo4SelectSpin")
    GOBO_4_SELECT_SPIN("Gobo4SelectSpin"),
    @XmlEnumValue("Gobo4WheelAudio")
    GOBO_4_WHEEL_AUDIO("Gobo4WheelAudio"),
    @XmlEnumValue("Gobo4WheelIndex")
    GOBO_4_WHEEL_INDEX("Gobo4WheelIndex"),
    @XmlEnumValue("Gobo4WheelMode")
    GOBO_4_WHEEL_MODE("Gobo4WheelMode"),
    @XmlEnumValue("Gobo4WheelRandom")
    GOBO_4_WHEEL_RANDOM("Gobo4WheelRandom"),
    @XmlEnumValue("Gobo4WheelShake")
    GOBO_4_WHEEL_SHAKE("Gobo4WheelShake"),
    @XmlEnumValue("Gobo4WheelSpin")
    GOBO_4_WHEEL_SPIN("Gobo4WheelSpin"),
    @XmlEnumValue("Gobo5")
    GOBO_5("Gobo5"),
    @XmlEnumValue("Gobo5Pos")
    GOBO_5_POS("Gobo5Pos"),
    @XmlEnumValue("Gobo5PosRotate")
    GOBO_5_POS_ROTATE("Gobo5PosRotate"),
    @XmlEnumValue("Gobo5PosShake")
    GOBO_5_POS_SHAKE("Gobo5PosShake"),
    @XmlEnumValue("Gobo5SelectEffects")
    GOBO_5_SELECT_EFFECTS("Gobo5SelectEffects"),
    @XmlEnumValue("Gobo5SelectShake")
    GOBO_5_SELECT_SHAKE("Gobo5SelectShake"),
    @XmlEnumValue("Gobo5SelectSpin")
    GOBO_5_SELECT_SPIN("Gobo5SelectSpin"),
    @XmlEnumValue("Gobo5WheelAudio")
    GOBO_5_WHEEL_AUDIO("Gobo5WheelAudio"),
    @XmlEnumValue("Gobo5WheelIndex")
    GOBO_5_WHEEL_INDEX("Gobo5WheelIndex"),
    @XmlEnumValue("Gobo5WheelMode")
    GOBO_5_WHEEL_MODE("Gobo5WheelMode"),
    @XmlEnumValue("Gobo5WheelRandom")
    GOBO_5_WHEEL_RANDOM("Gobo5WheelRandom"),
    @XmlEnumValue("Gobo5WheelShake")
    GOBO_5_WHEEL_SHAKE("Gobo5WheelShake"),
    @XmlEnumValue("Gobo5WheelSpin")
    GOBO_5_WHEEL_SPIN("Gobo5WheelSpin"),
    @XmlEnumValue("GoboWheel1MSpeed")
    GOBO_WHEEL_1_M_SPEED("GoboWheel1MSpeed"),
    @XmlEnumValue("GoboWheel2MSpeed")
    GOBO_WHEEL_2_M_SPEED("GoboWheel2MSpeed"),
    @XmlEnumValue("GoboWheel3MSpeed")
    GOBO_WHEEL_3_M_SPEED("GoboWheel3MSpeed"),
    @XmlEnumValue("GoboWheel4MSpeed")
    GOBO_WHEEL_4_M_SPEED("GoboWheel4MSpeed"),
    @XmlEnumValue("GoboWheel5MSpeed")
    GOBO_WHEEL_5_M_SPEED("GoboWheel5MSpeed"),
    @XmlEnumValue("GoboWheelReset")
    GOBO_WHEEL_RESET("GoboWheelReset"),
    @XmlEnumValue("HSB_Brightness")
    HSB_BRIGHTNESS("HSB_Brightness"),
    @XmlEnumValue("HSB_Hue")
    HSB_HUE("HSB_Hue"),
    @XmlEnumValue("HSB_Quality")
    HSB_QUALITY("HSB_Quality"),
    @XmlEnumValue("HSB_Saturation")
    HSB_SATURATION("HSB_Saturation"),
    @XmlEnumValue("Haze1")
    HAZE_1("Haze1"),
    @XmlEnumValue("Haze2")
    HAZE_2("Haze2"),
    @XmlEnumValue("Haze3")
    HAZE_3("Haze3"),
    @XmlEnumValue("Haze4")
    HAZE_4("Haze4"),
    @XmlEnumValue("Haze5")
    HAZE_5("Haze5"),
    @XmlEnumValue("IntensityMSpeed")
    INTENSITY_M_SPEED("IntensityMSpeed"),
    @XmlEnumValue("IntensityReset")
    INTENSITY_RESET("IntensityReset"),
    @XmlEnumValue("Iris")
    IRIS("Iris"),
    @XmlEnumValue("IrisMSpeed")
    IRIS_M_SPEED("IrisMSpeed"),
    @XmlEnumValue("IrisMode")
    IRIS_MODE("IrisMode"),
    @XmlEnumValue("IrisPulseClose")
    IRIS_PULSE_CLOSE("IrisPulseClose"),
    @XmlEnumValue("IrisPulseOpen")
    IRIS_PULSE_OPEN("IrisPulseOpen"),
    @XmlEnumValue("IrisReset")
    IRIS_RESET("IrisReset"),
    @XmlEnumValue("IrisStrobe")
    IRIS_STROBE("IrisStrobe"),
    @XmlEnumValue("IrisStrobeRandom")
    IRIS_STROBE_RANDOM("IrisStrobeRandom"),
    @XmlEnumValue("LEDFrequency")
    LED_FREQUENCY("LEDFrequency"),
    @XmlEnumValue("LEDZoneMode")
    LED_ZONE_MODE("LEDZoneMode"),
    @XmlEnumValue("LampControl")
    LAMP_CONTROL("LampControl"),
    @XmlEnumValue("LampPowerMode")
    LAMP_POWER_MODE("LampPowerMode"),
    @XmlEnumValue("MagentaMode")
    MAGENTA_MODE("MagentaMode"),
    @XmlEnumValue("MediaContent")
    MEDIA_CONTENT("MediaContent"),
    @XmlEnumValue("MediaFolder")
    MEDIA_FOLDER("MediaFolder"),
    @XmlEnumValue("NoFeature")
    NO_FEATURE("NoFeature"),
    @XmlEnumValue("NoFeature1")
    NO_FEATURE_1("NoFeature1"),
    @XmlEnumValue("NoFeature2")
    NO_FEATURE_2("NoFeature2"),
    @XmlEnumValue("NoFeature3")
    NO_FEATURE_3("NoFeature3"),
    @XmlEnumValue("Pan")
    PAN("Pan"),
    @XmlEnumValue("PanMode")
    PAN_MODE("PanMode"),
    @XmlEnumValue("PanReset")
    PAN_RESET("PanReset"),
    @XmlEnumValue("PanTiltMode")
    PAN_TILT_MODE("PanTiltMode"),
    @XmlEnumValue("PixelMode")
    PIXEL_MODE("PixelMode"),
    @XmlEnumValue("Playmode")
    PLAYMODE("Playmode"),
    @XmlEnumValue("PositionEffect")
    POSITION_EFFECT("PositionEffect"),
    @XmlEnumValue("PositionEffectFade")
    POSITION_EFFECT_FADE("PositionEffectFade"),
    @XmlEnumValue("PositionEffectRate")
    POSITION_EFFECT_RATE("PositionEffectRate"),
    @XmlEnumValue("PositionMSpeed")
    POSITION_M_SPEED("PositionMSpeed"),
    @XmlEnumValue("PositionModes")
    POSITION_MODES("PositionModes"),
    @XmlEnumValue("PositionReset")
    POSITION_RESET("PositionReset"),
    @XmlEnumValue("Prism1")
    PRISM_1("Prism1"),
    @XmlEnumValue("Prism1MSpeed")
    PRISM_1_M_SPEED("Prism1MSpeed"),
    @XmlEnumValue("Prism1Macro")
    PRISM_1_MACRO("Prism1Macro"),
    @XmlEnumValue("Prism1Pos")
    PRISM_1_POS("Prism1Pos"),
    @XmlEnumValue("Prism1PosRotate")
    PRISM_1_POS_ROTATE("Prism1PosRotate"),
    @XmlEnumValue("Prism1SelectSpin")
    PRISM_1_SELECT_SPIN("Prism1SelectSpin"),
    @XmlEnumValue("Prism2")
    PRISM_2("Prism2"),
    @XmlEnumValue("Prism2MSpeed")
    PRISM_2_M_SPEED("Prism2MSpeed"),
    @XmlEnumValue("Prism2Macro")
    PRISM_2_MACRO("Prism2Macro"),
    @XmlEnumValue("Prism2Pos")
    PRISM_2_POS("Prism2Pos"),
    @XmlEnumValue("Prism2PosRotate")
    PRISM_2_POS_ROTATE("Prism2PosRotate"),
    @XmlEnumValue("Prism2SelectSpin")
    PRISM_2_SELECT_SPIN("Prism2SelectSpin"),
    @XmlEnumValue("Prism3")
    PRISM_3("Prism3"),
    @XmlEnumValue("Prism3MSpeed")
    PRISM_3_M_SPEED("Prism3MSpeed"),
    @XmlEnumValue("Prism3Macro")
    PRISM_3_MACRO("Prism3Macro"),
    @XmlEnumValue("Prism3Pos")
    PRISM_3_POS("Prism3Pos"),
    @XmlEnumValue("Prism3PosRotate")
    PRISM_3_POS_ROTATE("Prism3PosRotate"),
    @XmlEnumValue("Prism3SelectSpin")
    PRISM_3_SELECT_SPIN("Prism3SelectSpin"),
    @XmlEnumValue("Prism4")
    PRISM_4("Prism4"),
    @XmlEnumValue("Prism4MSpeed")
    PRISM_4_M_SPEED("Prism4MSpeed"),
    @XmlEnumValue("Prism4Macro")
    PRISM_4_MACRO("Prism4Macro"),
    @XmlEnumValue("Prism4Pos")
    PRISM_4_POS("Prism4Pos"),
    @XmlEnumValue("Prism4PosRotate")
    PRISM_4_POS_ROTATE("Prism4PosRotate"),
    @XmlEnumValue("Prism4SelectSpin")
    PRISM_4_SELECT_SPIN("Prism4SelectSpin"),
    @XmlEnumValue("Prism5")
    PRISM_5("Prism5"),
    @XmlEnumValue("Prism5MSpeed")
    PRISM_5_M_SPEED("Prism5MSpeed"),
    @XmlEnumValue("Prism5Macro")
    PRISM_5_MACRO("Prism5Macro"),
    @XmlEnumValue("Prism5Pos")
    PRISM_5_POS("Prism5Pos"),
    @XmlEnumValue("Prism5PosRotate")
    PRISM_5_POS_ROTATE("Prism5PosRotate"),
    @XmlEnumValue("Prism5SelectSpin")
    PRISM_5_SELECT_SPIN("Prism5SelectSpin"),
    @XmlEnumValue("ReflectorAdjust")
    REFLECTOR_ADJUST("ReflectorAdjust"),
    @XmlEnumValue("ShaperMacros")
    SHAPER_MACROS("ShaperMacros"),
    @XmlEnumValue("ShaperMacrosSpeed")
    SHAPER_MACROS_SPEED("ShaperMacrosSpeed"),
    @XmlEnumValue("ShaperRot")
    SHAPER_ROT("ShaperRot"),
    @XmlEnumValue("Shutter1")
    SHUTTER_1("Shutter1"),
    @XmlEnumValue("Shutter1Strobe")
    SHUTTER_1_STROBE("Shutter1Strobe"),
    @XmlEnumValue("Shutter1StrobeEffect")
    SHUTTER_1_STROBE_EFFECT("Shutter1StrobeEffect"),
    @XmlEnumValue("Shutter1StrobePulse")
    SHUTTER_1_STROBE_PULSE("Shutter1StrobePulse"),
    @XmlEnumValue("Shutter1StrobePulseClose")
    SHUTTER_1_STROBE_PULSE_CLOSE("Shutter1StrobePulseClose"),
    @XmlEnumValue("Shutter1StrobePulseOpen")
    SHUTTER_1_STROBE_PULSE_OPEN("Shutter1StrobePulseOpen"),
    @XmlEnumValue("Shutter1StrobeRandom")
    SHUTTER_1_STROBE_RANDOM("Shutter1StrobeRandom"),
    @XmlEnumValue("Shutter1StrobeRandomPulse")
    SHUTTER_1_STROBE_RANDOM_PULSE("Shutter1StrobeRandomPulse"),
    @XmlEnumValue("Shutter1StrobeRandomPulseClose")
    SHUTTER_1_STROBE_RANDOM_PULSE_CLOSE("Shutter1StrobeRandomPulseClose"),
    @XmlEnumValue("Shutter1StrobeRandomPulseOpen")
    SHUTTER_1_STROBE_RANDOM_PULSE_OPEN("Shutter1StrobeRandomPulseOpen"),
    @XmlEnumValue("Shutter2")
    SHUTTER_2("Shutter2"),
    @XmlEnumValue("Shutter2Strobe")
    SHUTTER_2_STROBE("Shutter2Strobe"),
    @XmlEnumValue("Shutter2StrobeEffect")
    SHUTTER_2_STROBE_EFFECT("Shutter2StrobeEffect"),
    @XmlEnumValue("Shutter2StrobePulse")
    SHUTTER_2_STROBE_PULSE("Shutter2StrobePulse"),
    @XmlEnumValue("Shutter2StrobePulseClose")
    SHUTTER_2_STROBE_PULSE_CLOSE("Shutter2StrobePulseClose"),
    @XmlEnumValue("Shutter2StrobePulseOpen")
    SHUTTER_2_STROBE_PULSE_OPEN("Shutter2StrobePulseOpen"),
    @XmlEnumValue("Shutter2StrobeRandom")
    SHUTTER_2_STROBE_RANDOM("Shutter2StrobeRandom"),
    @XmlEnumValue("Shutter2StrobeRandomPulse")
    SHUTTER_2_STROBE_RANDOM_PULSE("Shutter2StrobeRandomPulse"),
    @XmlEnumValue("Shutter2StrobeRandomPulseClose")
    SHUTTER_2_STROBE_RANDOM_PULSE_CLOSE("Shutter2StrobeRandomPulseClose"),
    @XmlEnumValue("Shutter2StrobeRandomPulseOpen")
    SHUTTER_2_STROBE_RANDOM_PULSE_OPEN("Shutter2StrobeRandomPulseOpen"),
    @XmlEnumValue("Shutter3")
    SHUTTER_3("Shutter3"),
    @XmlEnumValue("Shutter3Strobe")
    SHUTTER_3_STROBE("Shutter3Strobe"),
    @XmlEnumValue("Shutter3StrobeEffect")
    SHUTTER_3_STROBE_EFFECT("Shutter3StrobeEffect"),
    @XmlEnumValue("Shutter3StrobePulse")
    SHUTTER_3_STROBE_PULSE("Shutter3StrobePulse"),
    @XmlEnumValue("Shutter3StrobePulseClose")
    SHUTTER_3_STROBE_PULSE_CLOSE("Shutter3StrobePulseClose"),
    @XmlEnumValue("Shutter3StrobePulseOpen")
    SHUTTER_3_STROBE_PULSE_OPEN("Shutter3StrobePulseOpen"),
    @XmlEnumValue("Shutter3StrobeRandom")
    SHUTTER_3_STROBE_RANDOM("Shutter3StrobeRandom"),
    @XmlEnumValue("Shutter3StrobeRandomPulse")
    SHUTTER_3_STROBE_RANDOM_PULSE("Shutter3StrobeRandomPulse"),
    @XmlEnumValue("Shutter3StrobeRandomPulseClose")
    SHUTTER_3_STROBE_RANDOM_PULSE_CLOSE("Shutter3StrobeRandomPulseClose"),
    @XmlEnumValue("Shutter3StrobeRandomPulseOpen")
    SHUTTER_3_STROBE_RANDOM_PULSE_OPEN("Shutter3StrobeRandomPulseOpen"),
    @XmlEnumValue("Shutter4")
    SHUTTER_4("Shutter4"),
    @XmlEnumValue("Shutter4Strobe")
    SHUTTER_4_STROBE("Shutter4Strobe"),
    @XmlEnumValue("Shutter4StrobeEffect")
    SHUTTER_4_STROBE_EFFECT("Shutter4StrobeEffect"),
    @XmlEnumValue("Shutter4StrobePulse")
    SHUTTER_4_STROBE_PULSE("Shutter4StrobePulse"),
    @XmlEnumValue("Shutter4StrobePulseClose")
    SHUTTER_4_STROBE_PULSE_CLOSE("Shutter4StrobePulseClose"),
    @XmlEnumValue("Shutter4StrobePulseOpen")
    SHUTTER_4_STROBE_PULSE_OPEN("Shutter4StrobePulseOpen"),
    @XmlEnumValue("Shutter4StrobeRandom")
    SHUTTER_4_STROBE_RANDOM("Shutter4StrobeRandom"),
    @XmlEnumValue("Shutter4StrobeRandomPulse")
    SHUTTER_4_STROBE_RANDOM_PULSE("Shutter4StrobeRandomPulse"),
    @XmlEnumValue("Shutter4StrobeRandomPulseClose")
    SHUTTER_4_STROBE_RANDOM_PULSE_CLOSE("Shutter4StrobeRandomPulseClose"),
    @XmlEnumValue("Shutter4StrobeRandomPulseOpen")
    SHUTTER_4_STROBE_RANDOM_PULSE_OPEN("Shutter4StrobeRandomPulseOpen"),
    @XmlEnumValue("Shutter5")
    SHUTTER_5("Shutter5"),
    @XmlEnumValue("Shutter5Strobe")
    SHUTTER_5_STROBE("Shutter5Strobe"),
    @XmlEnumValue("Shutter5StrobeEffect")
    SHUTTER_5_STROBE_EFFECT("Shutter5StrobeEffect"),
    @XmlEnumValue("Shutter5StrobePulse")
    SHUTTER_5_STROBE_PULSE("Shutter5StrobePulse"),
    @XmlEnumValue("Shutter5StrobePulseClose")
    SHUTTER_5_STROBE_PULSE_CLOSE("Shutter5StrobePulseClose"),
    @XmlEnumValue("Shutter5StrobePulseOpen")
    SHUTTER_5_STROBE_PULSE_OPEN("Shutter5StrobePulseOpen"),
    @XmlEnumValue("Shutter5StrobeRandom")
    SHUTTER_5_STROBE_RANDOM("Shutter5StrobeRandom"),
    @XmlEnumValue("Shutter5StrobeRandomPulse")
    SHUTTER_5_STROBE_RANDOM_PULSE("Shutter5StrobeRandomPulse"),
    @XmlEnumValue("Shutter5StrobeRandomPulseClose")
    SHUTTER_5_STROBE_RANDOM_PULSE_CLOSE("Shutter5StrobeRandomPulseClose"),
    @XmlEnumValue("Shutter5StrobeRandomPulseOpen")
    SHUTTER_5_STROBE_RANDOM_PULSE_OPEN("Shutter5StrobeRandomPulseOpen"),
    @XmlEnumValue("ShutterReset")
    SHUTTER_RESET("ShutterReset"),
    @XmlEnumValue("StrobeDuration")
    STROBE_DURATION("StrobeDuration"),
    @XmlEnumValue("StrobeMode")
    STROBE_MODE("StrobeMode"),
    @XmlEnumValue("StrobeRate")
    STROBE_RATE("StrobeRate"),
    @XmlEnumValue("Tilt")
    TILT("Tilt"),
    @XmlEnumValue("TiltMode")
    TILT_MODE("TiltMode"),
    @XmlEnumValue("TiltReset")
    TILT_RESET("TiltReset"),
    @XmlEnumValue("UVStability")
    UV_STABILITY("UVStability"),
    @XmlEnumValue("Video")
    VIDEO("Video"),
    @XmlEnumValue("VideoCamera1")
    VIDEO_CAMERA_1("VideoCamera1"),
    @XmlEnumValue("VideoCamera2")
    VIDEO_CAMERA_2("VideoCamera2"),
    @XmlEnumValue("VideoCamera3")
    VIDEO_CAMERA_3("VideoCamera3"),
    @XmlEnumValue("VideoCamera4")
    VIDEO_CAMERA_4("VideoCamera4"),
    @XmlEnumValue("VideoCamera5")
    VIDEO_CAMERA_5("VideoCamera5"),
    @XmlEnumValue("VideoEffect1Parameter1")
    VIDEO_EFFECT_1_PARAMETER_1("VideoEffect1Parameter1"),
    @XmlEnumValue("VideoEffect1Parameter2")
    VIDEO_EFFECT_1_PARAMETER_2("VideoEffect1Parameter2"),
    @XmlEnumValue("VideoEffect1Parameter3")
    VIDEO_EFFECT_1_PARAMETER_3("VideoEffect1Parameter3"),
    @XmlEnumValue("VideoEffect1Parameter4")
    VIDEO_EFFECT_1_PARAMETER_4("VideoEffect1Parameter4"),
    @XmlEnumValue("VideoEffect1Parameter5")
    VIDEO_EFFECT_1_PARAMETER_5("VideoEffect1Parameter5"),
    @XmlEnumValue("VideoEffect1Type")
    VIDEO_EFFECT_1_TYPE("VideoEffect1Type"),
    @XmlEnumValue("VideoEffect2Parameter1")
    VIDEO_EFFECT_2_PARAMETER_1("VideoEffect2Parameter1"),
    @XmlEnumValue("VideoEffect2Parameter2")
    VIDEO_EFFECT_2_PARAMETER_2("VideoEffect2Parameter2"),
    @XmlEnumValue("VideoEffect2Parameter3")
    VIDEO_EFFECT_2_PARAMETER_3("VideoEffect2Parameter3"),
    @XmlEnumValue("VideoEffect2Parameter4")
    VIDEO_EFFECT_2_PARAMETER_4("VideoEffect2Parameter4"),
    @XmlEnumValue("VideoEffect2Parameter5")
    VIDEO_EFFECT_2_PARAMETER_5("VideoEffect2Parameter5"),
    @XmlEnumValue("VideoEffect2Type")
    VIDEO_EFFECT_2_TYPE("VideoEffect2Type"),
    @XmlEnumValue("VideoEffect3Parameter1")
    VIDEO_EFFECT_3_PARAMETER_1("VideoEffect3Parameter1"),
    @XmlEnumValue("VideoEffect3Parameter2")
    VIDEO_EFFECT_3_PARAMETER_2("VideoEffect3Parameter2"),
    @XmlEnumValue("VideoEffect3Parameter3")
    VIDEO_EFFECT_3_PARAMETER_3("VideoEffect3Parameter3"),
    @XmlEnumValue("VideoEffect3Parameter4")
    VIDEO_EFFECT_3_PARAMETER_4("VideoEffect3Parameter4"),
    @XmlEnumValue("VideoEffect3Parameter5")
    VIDEO_EFFECT_3_PARAMETER_5("VideoEffect3Parameter5"),
    @XmlEnumValue("VideoEffect3Type")
    VIDEO_EFFECT_3_TYPE("VideoEffect3Type"),
    @XmlEnumValue("VideoEffect4Parameter1")
    VIDEO_EFFECT_4_PARAMETER_1("VideoEffect4Parameter1"),
    @XmlEnumValue("VideoEffect4Parameter2")
    VIDEO_EFFECT_4_PARAMETER_2("VideoEffect4Parameter2"),
    @XmlEnumValue("VideoEffect4Parameter3")
    VIDEO_EFFECT_4_PARAMETER_3("VideoEffect4Parameter3"),
    @XmlEnumValue("VideoEffect4Parameter4")
    VIDEO_EFFECT_4_PARAMETER_4("VideoEffect4Parameter4"),
    @XmlEnumValue("VideoEffect4Parameter5")
    VIDEO_EFFECT_4_PARAMETER_5("VideoEffect4Parameter5"),
    @XmlEnumValue("VideoEffect4Type")
    VIDEO_EFFECT_4_TYPE("VideoEffect4Type"),
    @XmlEnumValue("VideoEffect5Parameter1")
    VIDEO_EFFECT_5_PARAMETER_1("VideoEffect5Parameter1"),
    @XmlEnumValue("VideoEffect5Parameter2")
    VIDEO_EFFECT_5_PARAMETER_2("VideoEffect5Parameter2"),
    @XmlEnumValue("VideoEffect5Parameter3")
    VIDEO_EFFECT_5_PARAMETER_3("VideoEffect5Parameter3"),
    @XmlEnumValue("VideoEffect5Parameter4")
    VIDEO_EFFECT_5_PARAMETER_4("VideoEffect5Parameter4"),
    @XmlEnumValue("VideoEffect5Parameter5")
    VIDEO_EFFECT_5_PARAMETER_5("VideoEffect5Parameter5"),
    @XmlEnumValue("VideoEffect5Type")
    VIDEO_EFFECT_5_TYPE("VideoEffect5Type"),
    @XmlEnumValue("VideoScale1_All")
    VIDEO_SCALE_1_ALL("VideoScale1_All"),
    @XmlEnumValue("VideoScale1_X")
    VIDEO_SCALE_1_X("VideoScale1_X"),
    @XmlEnumValue("VideoScale1_Y")
    VIDEO_SCALE_1_Y("VideoScale1_Y"),
    @XmlEnumValue("VideoScale1_Z")
    VIDEO_SCALE_1_Z("VideoScale1_Z"),
    @XmlEnumValue("VideoScale2_All")
    VIDEO_SCALE_2_ALL("VideoScale2_All"),
    @XmlEnumValue("VideoScale2_X")
    VIDEO_SCALE_2_X("VideoScale2_X"),
    @XmlEnumValue("VideoScale2_Y")
    VIDEO_SCALE_2_Y("VideoScale2_Y"),
    @XmlEnumValue("VideoScale2_Z")
    VIDEO_SCALE_2_Z("VideoScale2_Z"),
    @XmlEnumValue("VideoScale3_All")
    VIDEO_SCALE_3_ALL("VideoScale3_All"),
    @XmlEnumValue("VideoScale3_X")
    VIDEO_SCALE_3_X("VideoScale3_X"),
    @XmlEnumValue("VideoScale3_Y")
    VIDEO_SCALE_3_Y("VideoScale3_Y"),
    @XmlEnumValue("VideoScale3_Z")
    VIDEO_SCALE_3_Z("VideoScale3_Z"),
    @XmlEnumValue("VideoScale4_All")
    VIDEO_SCALE_4_ALL("VideoScale4_All"),
    @XmlEnumValue("VideoScale4_X")
    VIDEO_SCALE_4_X("VideoScale4_X"),
    @XmlEnumValue("VideoScale4_Y")
    VIDEO_SCALE_4_Y("VideoScale4_Y"),
    @XmlEnumValue("VideoScale4_Z")
    VIDEO_SCALE_4_Z("VideoScale4_Z"),
    @XmlEnumValue("VideoScale5_All")
    VIDEO_SCALE_5_ALL("VideoScale5_All"),
    @XmlEnumValue("VideoScale5_X")
    VIDEO_SCALE_5_X("VideoScale5_X"),
    @XmlEnumValue("VideoScale5_Y")
    VIDEO_SCALE_5_Y("VideoScale5_Y"),
    @XmlEnumValue("VideoScale5_Z")
    VIDEO_SCALE_5_Z("VideoScale5_Z"),
    @XmlEnumValue("WavelengthCorrection")
    WAVELENGTH_CORRECTION("WavelengthCorrection"),
    @XmlEnumValue("WhiteCount")
    WHITE_COUNT("WhiteCount"),
    XYZ_X("XYZ_X"),
    XYZ_Y("XYZ_Y"),
    XYZ_Z("XYZ_Z"),
    @XmlEnumValue("YellowMode")
    YELLOW_MODE("YellowMode"),
    @XmlEnumValue("Zoom")
    ZOOM("Zoom"),
    @XmlEnumValue("ZoomMSpeed")
    ZOOM_M_SPEED("ZoomMSpeed"),
    @XmlEnumValue("ZoomMode")
    ZOOM_MODE("ZoomMode"),
    @XmlEnumValue("ZoomModeBeam")
    ZOOM_MODE_BEAM("ZoomModeBeam"),
    @XmlEnumValue("ZoomModeSpot")
    ZOOM_MODE_SPOT("ZoomModeSpot"),
    @XmlEnumValue("ZoomReset")
    ZOOM_RESET("ZoomReset");
    private final String value;

    AttributeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AttributeEnum fromValue(String v) {
        for (AttributeEnum c: AttributeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
