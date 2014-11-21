--------------------------------------------------------------------
-- This projected is licensed under the terms of the MIT license. --
--------------------------------------------------------------------


To use the software:
--------------------
Download the distribution zip file. 
Unzip the zip file.
To run Yeti, ensure you have at least Java 7 installed.
Type into the command prompt (in the folder where the zip file was unpacked):

java -jar Yeti-<version>.jar

You will be presented with a screen where the configuration of Yeti is done.



To build the source:
-------------------

1. mvn clean install
2. mvn assembly:single

1. Cleans previous build files, then builds the executable jar file from the source code (Yeti-<version>.jar. The source jar is then packaged with the libraries it uses into another jar file (Yeti-<version>-inc.jar. 
2. The previously created jar file is packaged with the provided resources and configuration files into the distribution zip file (Yeti-<version>-distribution.zip).

