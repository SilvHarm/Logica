#prepare the output folder for the compilation
rm -R bin

mkdir bin


#copy the content of the resources folder that javac will not copy
cp -r src/main/resources/* bin


#compile the sources files and place them into the bin folder
javac -d bin -cp lib/log4j2/log4j-1.2-api-2.8.2.jar:log4j-core-2.8.2.jar:log4j-api-2.8.2.jar -sourcepath src/main/java src/main/java/fr/silvharm/logica/Main.java


cd bin

#create Logica.jar who will contain all the files in the bin directory and have Main has entry point of the jar
jar cfvm Logica.jar ../manifest.txt *

mv Logica.jar ./..


cd ..

rm -R bin