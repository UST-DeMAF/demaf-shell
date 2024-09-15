package ust.demaf.demafshell.commands;

import java.util.List;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ust.demaf.demafshell.analysismanagerapi.AnalysisManagerService;

@ShellComponent
@ShellCommandGroup("Plugin Commands")
public class ListPluginsCommand {

  @Autowired private AnalysisManagerService analysisManagerService;

  @Autowired private Terminal terminal;

  @ShellMethod("List all registered plugins.")
  public void plugins() {
    List<String> plugins = analysisManagerService.getRegisteredPlugins();

    if (plugins.isEmpty()) {
      terminal.writer().println("No plugins registered.");
    } else {
      terminal.writer().println("The following plugins are currently registered:");
      for (String pluginName : plugins) {
        terminal.writer().println(pluginName);
      }
    }

    terminal.flush();
  }
}
