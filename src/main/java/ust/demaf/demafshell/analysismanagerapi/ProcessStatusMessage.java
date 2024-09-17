package ust.demaf.demafshell.analysismanagerapi;

import java.util.UUID;

public class ProcessStatusMessage {

  private UUID transformationProcessId;

  private Boolean isFinished;

  private Result result = null;

  public ProcessStatusMessage() {}

  public ProcessStatusMessage(
      UUID transformationProcessId, Boolean isFinished, String message, Result result) {
    this.transformationProcessId = transformationProcessId;
    this.isFinished = isFinished;
    this.result = result;
  }

  public UUID getTransformationProcessId() {
    return this.transformationProcessId;
  }

  public void setTransformationProcessId(UUID transformationProcessId) {
    this.transformationProcessId = transformationProcessId;
  }

  public Boolean getIsFinished() {
    return this.isFinished;
  }

  public void setIsFinished(Boolean isFinished) {
    this.isFinished = isFinished;
  }

  public Result getResult() {
    return this.result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  @Override
  public String toString() {
    return "{"
        + " transformationProcessId='"
        + getTransformationProcessId()
        + "'"
        + ", isFinished='"
        + getIsFinished()
        + "'"
        + ", result='"
        + getResult()
        + "'"
        + "}";
  }
}
