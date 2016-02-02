#!/bin/bash
#dex
diff -Naur /dex/original/ /dex/manipulated/ > dex.diff
#smali
diff -Naur /baksmali/original/ /baksmali/manipulated/ > baksmali.diff
#jadx
diff -Naur /jadx/original/ /androguard/jadx/ > jadx.diff
#androguard
diff -Naur /androguard/original/ /androguard/manipulated/ > andro.diff
