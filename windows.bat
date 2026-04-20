@echo off
REM Define paths (Windows uses semicolons ; for classpaths)
set OUT_DIR=out\production\Student-Grading-System
set LIB_DIR=lib\*
set DATA_DIR=data\*

REM Run the program
java -cp "%OUT_DIR%;%LIB_DIR%;%DATA_DIR%" Main

pause
