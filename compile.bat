cd Logica

::prepare the output folder for the compilation if it does not exist
if not exist bin MD bin

::copy the content of the resources folder that javac will not copy
ROBOCOPY src\main\resources bin /S


::set the path of the jdk to use javac
set path="C:\Program Files\Java\jdk1.8.0_141\bin"

::compile the sources files and place them into the bin folder
javac -d bin -sourcepath src\main\java src\main\java\fr\silvharm\logica\Main.java


cd bin

::create Logica.jar who will contain all the files in the bin directory and have Main has entry point of the jar
jar cfve Logica.jar fr.silvharm.logica.Main *


MOVE Logica.jar .\..\..