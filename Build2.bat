echo off
CLS

setlocal enabledelayedexpansion

Set list=Main.java PlayerActionScreen.java PlayerEntryScreen.java StartScreen.java udpBroadcast.java udpClient.java Database.java Timer.java Log.java Music.java
Set missing=
Set error=0

for %%f in (%list%) do (
    IF not EXIST %%f (
        set "missing=!missing! %%f"
        set "error=1"
    )
)
IF !error!==1 (
    echo [1;31mERROR FILES NOT FOUND[0m; build may not work as the following files can't be found [0m
    for %%m in  (%missing%) do (
        echo %%m
    )
    echo [1;32mAttempting to compile java program [0m
    
) else (
    echo All files found
    echo [1;32mCompiling java program [0m
)

javac Main.java PlayerActionScreen.java PlayerEntryScreen.java StartScreen.java udpBroadcast.java udpClient.java Database.java Timer.java Log.java Music.java

if %errorlevel% neq 0 (
	echo [1;31mERROR COMPILEING[0m; exiting now
) else (
    echo [1;32mCompiled Correctly;[0m running program
	java Main
)
exit /b 0
