package com.musevarg.nordvpn.vpn;

/*
* This class is a singleton. It is used to run NordVPN commands in the background.
* We do not need to instantiate it more than once.
*/

import java.io.*;
import java.util.concurrent.TimeUnit;

public class NordVPN {

    // Static variable reference of single_instance
    // of type NordVpnCommands
    private static NordVPN single_instance = null;

    // Constructor of the singleton
    private NordVPN() {}

    // Keep track of current connection status
    public boolean isConnected = false;

    // Static method to create instance of Singleton class
    public static NordVPN getInstance()
    {
        if (single_instance == null) {
            single_instance = new NordVPN();
        }
        return single_instance;
    }

    public String connect(){
        String response;
        try{
            response = runCommand("nordvpn c");
            isConnected = true;
        } catch (Exception e) {
            e.printStackTrace();
            response = "Something went wrong";
            isConnected = false;
        }
        return response;
    }

    public String disconnect(){
        String response;
        try{
            response = runCommand("nordvpn d");
            isConnected = false;
        } catch (Exception e) {
            e.printStackTrace();
            response = "Something went wrong";
        }
        return response;
    }

    public String status(){
        String response;
        try{
            response = runCommand("nordvpn status");
        } catch (Exception e) {
            e.printStackTrace();
            response = "Something went wrong";
        }
        return response;
    }

    private static String runCommand(String command) throws Exception{
        // Let the user know which command is running
        System.out.println("Running '" + command + "'");
        // Create a process builder to run command
        ProcessBuilder builder = new ProcessBuilder();
        // Set the command to run in a Linux shell
        builder.command("sh", "-c", command);
        // Run command
        Process process = builder.start();

        // Create the streams that will read responses from the shell
        OutputStream outputStream = process.getOutputStream();
        InputStream inputStream = process.getInputStream();
        InputStream errorStream = process.getErrorStream();

        String response;

        // Print responses to the user
        response = printStream(inputStream);
        printStream(errorStream);

        boolean isFinished = process.waitFor(30, TimeUnit.SECONDS);
        outputStream.flush();
        outputStream.close();

        if(!isFinished) {
            process.destroyForcibly();
        }
        return response;
    }

    // Method to print response from the shell
    private static String printStream(InputStream inputStream) throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            StringBuilder allLines = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                if (!isLoadingCharacter(line)){
                    System.out.println(line);
                    allLines.append(line).append("\n");
                }
            }
            return allLines.toString();
        }
    }

    // Remove the loading characters from the response
    // Loading characters are responses containing one of \|/- followed by a space
    // They have a string length of 2 (e.g. "\ " or "| ")
    private static Boolean isLoadingCharacter(String line){
        int loadingCharactersLength = 2;
        return line.length() <= loadingCharactersLength;
    }
}
