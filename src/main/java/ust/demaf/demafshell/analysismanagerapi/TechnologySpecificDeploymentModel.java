package ust.demaf.demafshell.analysismanagerapi;

import java.net.URL;
import java.util.List;

public class TechnologySpecificDeploymentModel {

    private String technology;
    private URL locationURL;
    private List<String> commands;


    public TechnologySpecificDeploymentModel() {
    }

    public TechnologySpecificDeploymentModel(String technology, URL locationURL, List<String> commands) {
        this.technology = technology;
        this.locationURL = locationURL;
        this.commands = commands;
    }

    public String getTechnology() {
        return this.technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public URL getLocationURL() {
        return this.locationURL;
    }

    public void setLocationURL(URL locationURL) {
        this.locationURL = locationURL;
    }

    public List<String> getCommands() {
        return this.commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "{" +
            " technology='" + getTechnology() + "'" +
            ", locationURL='" + getLocationURL() + "'" +
            ", commands='" + getCommands() + "'" +
            "}";
    }
    
}
