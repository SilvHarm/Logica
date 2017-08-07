#prepare the output folder for the compilation
rm -R bin

mkdir bin


#copy the content of the resources folder that javac will not copy
cp -r src/main/resources/* bin


#compile the sources files and place them into the bin folder
javac -d bin -sourcepath src/main/java src/main/java/fr/silvharm/logica/Main.java


cd bin

#create Logica.jar who will contain all the files in the bin directory and have Main has entry point of the jar
jar cfvm Logica.jar ../manifest.txt *

mv Logica.jar ./..


cd ..

rm -R bin