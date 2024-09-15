package ust.demaf.demafshell.commands;

import java.net.URL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ust.demaf.demafshell.analysismanagerapi.AnalysisManagerService;

@ShellComponent
@ShellCommandGroup("Transformation Commands")
public class TransformCommand {

  @Autowired private AnalysisManagerService analysisManagerService;

  @ShellMethod(
      "Transform technology-specific deployment model to technology-agnostic deployment model.")
  public String transform(
      @ShellOption(
              value = {"-t", "--technology"},
              help = "The deployment technology the deployment model was created with.")
          String technology,
      @ShellOption(
              value = {"-l", "--location"},
              help =
                  "The location of the deployment model as a valid URL following the syntax: "
                      + "scheme \":\" [\"//\" [userinfo \"@\"] host [\":\" port]] path [\"?\" query] [\"#\" fragment].")
          URL location,
      @ShellOption(
              value = {"-c", "--commands"},
              help =
                  "The commands for executing the deployment model. Use a comma-separated list for specifiying multiple commands.")
          List<String> commands,
      @ShellOption(
              value = {"-o", "--options"},
              defaultValue = "",
              help =
                  "Options that affect the transformation of the deployment model, e.g. its visualization. Use a comma-separated list to specify multiple options.")
          List<String> options) {
    return analysisManagerService.runTransformationProcess(technology, location, commands, options);
  }
}
