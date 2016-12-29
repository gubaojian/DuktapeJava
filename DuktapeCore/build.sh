## gradle buildJar
cd DuktapeJava/src/main/jni/
sh build.sh
echo "Create So Path DuktapeEngine/src/main/libs/";
cd ../../../
gradle build
echo "Create Jar Path DuktapeEngine/build/libs/";
