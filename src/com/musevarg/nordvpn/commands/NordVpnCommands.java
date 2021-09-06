package com.musevarg.nordvpn.commands;

/*
* This class is a singleton. It is used to run NordVPN commands in the background.
* We do not need to instantiate it more than once.
*/

import java.io.*;
import java.util.concurrent.TimeUnit;

public class NordVpnCommands {

    // Static variable reference of single_instance
    // of type NordVpnCommands
    private static NordVpnCommands single_instance = null;

    // Constructor of the singleton
    private NordVpnCommands() {}

    // Static method to create instance of Singleton class
    public static NordVpnCommands getInstance()
    {
        if (single_instance == null) {
            single_instance = new NordVpnCommands();
        }
        return single_instance;
    }

    public String connect(){
        String response;
        try{
            response = runCommand("nordvpn c");
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
            String lastLine = "";
            while((line = bufferedReader.readLine()) != null) {
                if (!isLoadingCharacter(line)){
                    System.out.println(line);
                    lastLine = line;
                }
            }
            return lastLine;
        }
    }

    // Remove the loading characters from the response
    // Loading characters are responses containing one of \|/- followed by a space
    // They have a string length of 2 (e.g. "\ " or "| ")
    private static Boolean isLoadingCharacter(String line){
        int loadingCharactersLength = 2;
        return line.length() <= loadingCharactersLength;
    }

    // Check if response contains "you are connected"
    private static Boolean isConnected(String response){
        return response.toLowerCase().contains("you are connected");
    }
}
