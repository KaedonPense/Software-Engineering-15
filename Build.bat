::@echo off
javac Main.java PlayerEntryScreen.java StartScreen.java udpBroadcast.java udpClient.java Database.java Timer.java PlayerActionScreen.java Log.java Music.java
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Program...
	java Main
)
