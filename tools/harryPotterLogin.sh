#!/bin/sh

adb shell "input text harry@nottingham.ac.uk"
adb shell input keyevent TAB
adb shell "input text harry"
adb shell input keyevent TAB ENTER

