package ust.demaf.demafshell.analysismanagerapi;

import java.util.ArrayList;
import java.util.List;


public class RegisteredPluginsResponse {
    
    private List<String> pluginNames = new ArrayList<>();


    public RegisteredPluginsResponse() {
    }

    public RegisteredPluginsResponse(List<String> pluginNames) {
        this.pluginNames = pluginNames;
    }

    public List<String> getPluginNames() {
        return this.pluginNames;
    }

    public void setPluginNames(List<String> pluginNames) {
        this.pluginNames = pluginNames;
    }

    @Override
    public String toString() {
        return "{" +
            " pluginNames='" + getPluginNames() + "'" +
            "}";
    }


}
