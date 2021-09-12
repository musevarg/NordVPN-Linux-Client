package com.musevarg.nordvpn.vpn;

public class VpnStatus {

    private String status;
    private String server;
    private String country;
    private String city;
    private String ip;
    private String technology;
    private String protocol;
    private String uptime;
    private String received;
    private String sent;

    public VpnStatus() {}

    public void parseNordResponse(String response){
        String[] lines = response.split("\n");
        for(String s : lines){
            String[] values = s.split(":");
            if (s.contains("Status")){
                this.status = values[1].trim();
            } else if (s.contains("Current server")){
                this.server = values[1].trim();
            } else if (s.contains("Country")){
                this.country = values[1].trim();
            } else if (s.contains("City")){
                this.city = values[1].trim();
            } else if (s.contains("Server IP")){
                this.ip = values[1].trim();
            } else if (s.contains("Current technology")){
                this.technology = values[1].trim();
            } else if (s.contains("Current protocol")){
                this.protocol = values[1].trim();
            } else if (s.contains("Uptime")){
                this.uptime = values[1].trim();
            } else if (s.contains("Transfer")){
                String[] transfers = values[1].split(",");
                this.received = transfers[0].replace("received", "").trim();
                this.sent = transfers[1].replace("sent", "").trim();
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public String getServer() {
        return server;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getIp() {
        return ip;
    }

    public String getTechnology() {
        return technology;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUptime() {
        return uptime;
    }

    public String getReceived() {
        return received;
    }

    public String getSent() {
        return sent;
    }

    @Override
    public String toString() {
        return "VpnStatus{" +
                "status='" + status + '\'' +
                ", server='" + server + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", ip='" + ip + '\'' +
                ", technology='" + technology + '\'' +
                ", protocol='" + protocol + '\'' +
                ", uptime='" + uptime + '\'' +
                ", received='" + received + '\'' +
                ", sent='" + sent + '\'' +
                '}';
    }
}
