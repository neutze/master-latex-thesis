#!/bin/bash
#dex
diff -r /dex/original/ /dex/manipulated/ > dex.diff
#smali
diff -r /smali/original/ /smali/manipulated/ > smali.diff
#dad
diff -r /java/dad/original/ /java/dad/jadx/ > dad.diff
#jadx
diff -r /java/dad/original/ /java/dad/manipulated/ > jadx.diff
