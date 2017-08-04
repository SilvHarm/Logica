cd Logica

#prepare the output folder for the compilation if it does not exist
if [ ! -d bin ]
then
	mkdir bin
fi

#copy the content of the resources folder that javac will not copy
cp -r src/main/resources/* bin


#compile the sources files and place them into the bin folder
javac -d bin -sourcepath src/main/java src/main/java/fr/silvharm/logica/Main.java


cd bin

#create Logica.jar who will contain all the files in the bin directory and have Main has entry point of the jar
jar cfve Logica.jar fr.silvharm.logica.Main *


mv Logica.jar ./../..