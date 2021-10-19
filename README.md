# LMT_Decompiled

Unstable decompiled port of the amazing LMT app by @noname81 [on XDA](https://forum.xda-developers.com/t/app-root-lmt-launcher-v2-9.1330150/page-545. 

Decompiled using jadx - https://github.com/skylot/jadx

## Known Issues / Bugs / Limitations

- Gestures/ISAS is totally dependant on libTouchServiceNative.so which I cannot decompile.
- Pie Status info does not render at the correct location
- Bottom Pie does not render properly (Only 1/2 of the Pie Items are rendered)
- Currently compiles with no major hiccups on Android Studio 3.5.9 with target SDK 28

## TODOs
- Ensuring all stock features working as expected
- Updating to newer dependecies and getting rid of depricated libraries
- Comments in the code to understand the logic
