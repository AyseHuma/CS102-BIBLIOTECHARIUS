@echo off
set LOCALHOST=%COMPUTERNAME%
if /i "%LOCALHOST%"=="labb20119" (taskkill /f /pid 7632)
if /i "%LOCALHOST%"=="labb20119" (taskkill /f /pid 788)
if /i "%LOCALHOST%"=="labb20119" (taskkill /f /pid 12640)
if /i "%LOCALHOST%"=="labb20119" (taskkill /f /pid 10384)

del /F cleanup-ansys-labb20119-10384.bat
