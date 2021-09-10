# NordVPN Linux Client

Simple GUI to use NordVPN on Linux.

Currently, NordVPN only comes as a command line tool for linux. This project aims to add a UI on top of the shell commands for a smoothier user experience.

This project is currently under development. Keep checking this page, it will only get better !

![Screenshot from 2021-09-10 23-15-48](https://user-images.githubusercontent.com/49337864/132918571-caf6666c-1909-4488-9fdd-c563802413fc.png)


## Unit Tests

All unit tests should pass.

## Architecture

Built using Java 11.

This project doesn't use external libraries, only libraries that are part of the JDK 11 (Amazon Coretto).

## Requirements

In order to use this product, you must have installed the official [NordVPN app for Linux](https://nordvpn.com/download/linux/), and you must have logged in to your NordVPN account.

After that, you should be able to use this Java app instead of shell commands to connect and disconnect from NordVPN.
You need Java 11 or above.

## Run

Download the project and navigate to
```
your-path/NordVPN-Linux-Client/out/production/NordVPN-Client-Linux
```

and run
```
java -cp . com.musevarg.nordvpn.Main
```

OR

open the project with a Java IDE. This project was developed using IntelliJ IDEA.

## Coming soon

Better UI, country selection, extended status information, possibility to log in and out via ui, UI themes, etc.
