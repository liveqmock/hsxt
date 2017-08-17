set taget=192.168.229.120
cd ../..
call mvn install
echo pack over ftp to 192.168.229.120
call F:\Downloads\loadrunner-11\lrunner\MSI\bin\psftp -l fd -pw fd -b src\autoinstall\ftp.txt %taget%
echo down ftp install in 192.168.229.120
call F:\Downloads\loadrunner-11\lrunner\MSI\bin\plink -v -pw fd -m src\autoinstall\install.txt fd@%taget%
cd src/autoinstall
