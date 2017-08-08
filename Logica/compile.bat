::prepare the output folder for the compilation
RMDIR /S /Q bin

MD bin


::copy the content of the resources folder that javac will not copy
ROBOCOPY src\main\resources bin /S


::set the path of the jdk to use javac
set path="C:\Program Files\Java\jdk1.8.0_141\bin"

::compile the sources files and place them into the bin folder
javac -d bin -encoding UTF-8  -cp lib\log4j2\log4j-1.2-api-2.8.2.jar;log4j-core-2.8.2.jar;log4j-api-2.8.2.jar -sourcepath src\main\java src\main\java\fr\silvharm\logica\Main.java


cd bin

::moving the config for log4j2 to the right place
MOVE config\log4j2\log4j2.xml .

RMDIR /S /Q config\log4j2


::create Logica.jar who will contain all the files in the bin directory and have Main has entry point of the jar
jar cfvm Logica.jar ../manifest.txt *

MOVE Logica.jar ./..


cd ..

RMDIR /S /Q bin