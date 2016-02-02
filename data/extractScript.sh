#!/bin/bash

#remove old files
rm -rf ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/java/
rm -rf ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/smali/
rm -rf ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/jasmin/
#!/bin/bash
#androguard
python androdd.py -i lvltest-version.apk -o /androguard/version/

#apktool
mkdir ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/smali/
mkdir ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/smali/apktool/

java -jar ~/Google\ Drive/master_thesis/sourcefiles/_tools/apktool.jar d  ~/Google\ Drive/master_thesis/sourcefiles/lvl/auto/apk-lvltest-version/lvltest-version.apk -o ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/tmp/
mv -f ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/tmp/smali/* ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/smali/apktool/
rm -rf ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/tmp/

#!/bin/bash
#baksmali
java -jar baksmali.jar -x lvltest-version.apk -o /baksmali/version/

#dex2jar
bash ~/Google\ Drive/master_thesis/sourcefiles/_tools/_dex2jar-2.0/d2j-dex2jar.sh -f -o ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/jar/lvltest-version.jar ~/Google\ Drive/master_thesis/sourcefiles/lvl/auto/apk-lvltest-version/lvltest-version.apk

#dex2jasmin
bash ~/Google\ Drive/master_thesis/sourcefiles/_tools/_dex2jar-2.0/d2j-jar2jasmin.sh -f -o ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/jasmin ~/workspace/master-code-applications/code-lvltest-version-lvl-auto/jar/lvltest-version.jar
#!/bin/bash
#jadx
jadx -d /jadx/version/ --deobf --show-bad-code lvltest-version.apk

#!/bin/bash
#hexdump dex
unzip lvltest-version.apk -d /tmp/
hexdump -C /tmp/classes.dex >> /dex/version/classes.txt
