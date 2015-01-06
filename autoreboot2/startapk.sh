#!/system/bin/sh
echo execute reboot script now...
cd /system/bin 
echo $PWD
am start --user 0 com.into.stability/.Run
sleep(5)
reboot
