package com.musevarg.nordvpn.vpn;

/*
* This class is a singleton. It is used to run NordVPN commands in the terminal.
* We do not need to instantiate it more than once.
*/

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class NordVPN {

    // Static variable reference of single_instance
    // of type NordVpnCommands
    private static NordVPN single_instance = null;

    // Constructor of the singleton
    private NordVPN() {}

    // Keep track of current connection status
    public boolean isConnected = false;

    // Store status in an object
    private VpnStatus status = new VpnStatus();

    // Keep track of all commands and responses in a log
    private static ArrayList<String> commandsLog = new ArrayList<>();

    // Static method to create instance of Singleton class
    public static NordVPN getInstance()
    {
        if (single_instance == null) {
            single_instance = new NordVPN();
        }
        return single_instance;
    }

    // Quick connect to the fastest server
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
        //System.out.println(response);
        return response;
    }

    // Connect to a specific server (country, city or group)
    public String connect(String server){
        String response;
        try{
            response = runCommand("nordvpn c " + server);
            isConnected = true;
        } catch (Exception e) {
            e.printStackTrace();
            response = "Something went wrong";
            isConnected = false;
        }
        //System.out.println(response);
        return response;
    }

    // Disconnect from NordVPN
    public String disconnect(){
        String response;
        try{
            response = runCommand("nordvpn d");
            isConnected = false;
        } catch (Exception e) {
            e.printStackTrace();
            response = "Something went wrong";
        }
        //System.out.println(response);
        response = response.toLowerCase().contains("you are not connected") ? "you are disconnected" : response;
        return response;
    }

    // Fetch current connection status
    public VpnStatus status(){
        String response;
        try{
            response = runCommand("nordvpn status");
        } catch (Exception e) {
            e.printStackTrace();
            response = "Something went wrong";
        }
        //System.out.println(response);
        status.parseNordResponse(response);
        return status;
    }

    // Fetch country list of available servers
    public String[] getCountries(){
        String response;
        try{
            response = runCommand("nordvpn countries");
        } catch (Exception e) {
            e.printStackTrace();
            response = "Something went wrong";
        }
        return response.split(", ");
    }

    // Fetch city list of available servers
    public String[] getCities(String country){
        String response;
        try{
            response = runCommand("nordvpn cities " + country);
        } catch (Exception e) {
            e.printStackTrace();
            response = "Something went wrong";
        }
        return response.split(", ");
    }

    // Fetch server groups
    public String[] getServerGroups(){
        String response;
        try{
            response = runCommand("nordvpn groups");
        } catch (Exception e) {
            e.printStackTrace();
            response = "Something went wrong";
        }
        return response.split(", ");
    }

    // Get a timestamp for the log
    private static String getTimestamp(){
        return "[" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "] ";
    }

    // Return log to the user
    public ArrayList<String> getLog(){
        return commandsLog;
    }

    // Runs a command in the background
    private static String runCommand(String command) throws Exception{
        // Let the user know which command is running
        System.out.println("Running '" + command + "'");
        commandsLog.add(getTimestamp() + "Running '" + command + "'");
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
                    //System.out.println(line);
                    allLines.append(line).append("\n");
                    if (!isStatusResponse(line))
                        commandsLog.add(getTimestamp() + line);
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

    private static Boolean isStatusResponse(String line){
        return line.contains(":");
    }
}
