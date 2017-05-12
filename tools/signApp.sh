#!/bin/sh

cd ..

zipalign -v -p 4 app-release-unsigned.apk app-release-aligned.apk
apksigner sign --ks my-release-key.jks --out app-release.apk app-release-aligned.apk

