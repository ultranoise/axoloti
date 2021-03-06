set platformdir=%~sdp0

if not defined axoloti_release (
   set axoloti_release=%platformdir%..
)

if not defined axoloti_runtime (
   set axoloti_runtime=%platformdir%\..
)

if not defined axoloti_firmware (
   call :setfirmware "%axoloti_release%\firmware"
)
call :setrelease "%axoloti_release%"

if not defined axoloti_home (
   set axoloti_home=%platformdir%..
)

call :setgccpath "C:/Program Files (x86)/GNU Tools Arm Embedded/7 2018-q2-update/bin"

set PATH=%gcc_path%;%platformdir%bin;%windir%\system32
echo PATH=%PATH%

goto :eof

:setgccpath
set gcc_path=%~s1
goto :eof

:setfirmware
set axoloti_firmware=%~s1
goto :eof

:setrelease
set axoloti_release=%~s1
goto :eof
