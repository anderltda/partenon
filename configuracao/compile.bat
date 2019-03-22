@echo off

GOTO INIT

:INIT

SET projectDir=%CD%

SET coreprojects=(bootstraptheme commons\core foundation\core commons\entity commons\service foundation\web commons\web seguranca\core seguranca\web cadastro\core cadastro\web monitoramento\core monitoramento\web project)		

CD %projectDir%

CD..

SET projectDir=%CD%

FOR %%n IN %coreprojects% DO (
	echo %projectDir%\%%n 
	CD %projectDir%\%%n 
	call mvn clean -U install eclipse:myeclipse
)

:END