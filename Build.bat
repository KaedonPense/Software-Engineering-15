::@echo off
javac Main.java PlayerEntryScreen.java
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java Main
)
