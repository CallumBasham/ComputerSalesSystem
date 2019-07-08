@ECHO OFF

REM This may not work if you are running an old version of Java, modify the System Environment Variables to more recent JDK & JRE to resolve!

TITLE 3CS Computer Sales System Standalone Launcher

java --module-path "C:\Users\Callum.B\Documents\Java Development\IntelliJ\ComputerSalesSystem\Libraries\JavaFX12\lib" --add-modules javafx.controls,javafx.fxml -jar "C:\Users\Callum.B\Documents\Java Development\IntelliJ\ComputerSalesSystem\out\artifacts\ComputerSalesSystem_jar\ComputerSalesSystem.jar"

ECHO Application has been closed!

TIMEOUT 5
CLS
EXIT