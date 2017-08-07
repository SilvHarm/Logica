# Logica
Le 3ème projet du parcours "Développeur d'Application - Java" de OpenClassroom


Compile
==========================

Pour compiler, vous pouvez soit utiliser un IDE soit utiliser l'un des scripts fourni pour Windows(.bat) ou Linux(.sh), après avoir effectué une manipulation pour le 1er.

Windows:
	Dans "compile.bat", ligne 11, vous devez modifier le chemin pour que celui ci corresponde au dossier bin de votre jdk.

Note: l'utilisation des scripts doit se faire dans un répertoire avec les droits de lecture/écriture/éxécution.


Si tout s'est bien passé, un fichier Logica.jar devrait avoir été généré.


Launch
==========================

Pour lancer le programme, voux pouvez soit l'éxécuter directement avec java, soit utiliser un .bat ou .sh afin de passer des arguments au programme.

ex:
java -jar "Logica.jar" -internconfig -cheat


Arguments
==========================

-cheat
	Le programme va afficher la solution du jeu en cours.
	
-internconfig
	Le programme va utiliser le config.properties interne.
	
