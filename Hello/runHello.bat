rem start the Main remote client

set JAVA=%JAVA_HOME%\bin\java

set LIB_DIR=..\..\jars
set CLASSPATH=classes;%LIB_DIR%\mysql-connector-java-5.1.21-bin.jar;%LIB_DIR%\javax.persistence-2.0.0.jar;%LIB_DIR%\eclipselink-2.0.0.jar 


%JAVA% -cp %CLASSPATH% domain.Main
