@echo off
rem Variables necesarias
SET compiler="C:\Program Files\Java\jre7\bin\
SET JAVA=java.exe"
SET pathboss=%CD%
SET JAR=SERVERWS.jar
SET  DIRBIN=bin

rem 
rem cd %pathboss%
rem cd %DIRBIN%
::echo "Ahora estoy en:"
echo %CD%

rem Colocar ruta donde se encuentra el compilador de java
::echo "***************************************************"
::echo "*         Impresion de Variables                  *"
::echo "***************************************************"
::echo %compiler%
::echo %JAVA%
::echo %pathboss%
::echo %JAR%
::echo %DIRBIN%
::echo "***************************************************"

rem Iniciar el cliente
echo %compiler%%JAVA% -jar %JAR%  Revision del comando
%compiler%%JAVA% -jar %JAR%
pause on	