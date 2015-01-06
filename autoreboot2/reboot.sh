#!/system/bin/sh
echo execute reboot script now...
cd /system/bin 
echo $PWD
am boardcast android.intent.action.ACTION_SHUTDOWN
sleep(5)
reboot
