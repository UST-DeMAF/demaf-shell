package ust.demaf.demafshell.analysismanagerapi;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Service
public class AnalysisManagerService {

    @Autowired
    private Terminal terminal;

    @Autowired
    private WebClient analysisManagerApiClient;

    @Value("${analysis-manager.url}")
    private String analysisManagerURL;

    /**
     * Calls the endpoint of the analysis manager service to retrieve the registered plugins.
     * 
     * @return a list of registered plugins.
     */
    public List<String> getRegisteredPlugins() {
        return analysisManagerApiClient.get()
            .uri("/plugins")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(RegisteredPluginsResponse.class)
            .block()
            .getPluginNames();
    }

    /**
     * Starts the transformation process and then continously polls for the result.
     * At success, the result is presented in the terminal.
     * If an error occures, an error message is printed to the terminal.
     * 
     * @param technology
     * @param location
     * @param commands
     * @return a message to display to the user.
     */
    public String runTransformationProcess(String technology, URL location, List<String> commands) {
        TechnologySpecificDeploymentModel tsdm = new TechnologySpecificDeploymentModel(technology, location, commands);        
        try {
            UUID transformationProcessId = startTransformationProcess(tsdm);
            terminal.writer().write("Transforming deployment model...");
            return pollTransformationProcessStatusForResult(transformationProcessId, 0l);
        } catch (WebClientResponseException e) {
            return e.getResponseBodyAsString();
        }   
    }     

    /**
     * Start the transformation process by invoking the corresponding endpoint of the analysis manager service.
     * 
     * @param tsdm
     * @return the transformation process id of the started process.
     */
    public UUID startTransformationProcess( TechnologySpecificDeploymentModel tsdm) {
        return analysisManagerApiClient.post()
        .uri("/transform")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(tsdm))
        .exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.ACCEPTED)) {
                return response.bodyToMono(UUID.class);
            }
            else {
                return response.createException().flatMap(Mono::error);
            }
        })
        .block();
    }

    /**
     * Polls the status endpoint of the analysis manager for the result of the transformation process.
     * It polls the endpoint in an interval given by delayInSeconds until the returned message indicates a finished process.
     * 
     * @param transformationProcessId
     * @param delayInSeconds the time between polling the endpoint.
     * @return a message of the transformation process status.
     */
    public String pollTransformationProcessStatusForResult(UUID transformationProcessId, long delayInSeconds) {
        try {
            ProcessStatusMessage message = analysisManagerApiClient.get()
            .uri("/status/"+transformationProcessId)
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(response -> {
                if (response.statusCode().equals(HttpStatus.OK)) {
                    return response.bodyToMono(ProcessStatusMessage.class);
                }
                else {
                    return response.createException().flatMap(Mono::error);
                }
            })
            .delaySubscription(Duration.ofSeconds(delayInSeconds))
            .block();

            if(message.getIsFinished()) {
                printTransformationResult(message.getResult());
                return "Transformation completed!";
            } else {
                printPollingIndicator();
                return pollTransformationProcessStatusForResult(transformationProcessId, 3l);
            }
        } catch (WebClientResponseException e) {
            return e.getResponseBodyAsString();
        } 
    }

    /**
     * Print the transformation result to the terminal.
     * 
     * @param result
     */
    private void printTransformationResult(Result result) {
        terminal.writer().println("\n");
        terminal.writer().println("Transformation process finished!");
        terminal.writer().println("Find the technology-agnostic deployment model under the following path:");
        terminal.writer().println(result.getPath());
        terminal.writer().println("Analysis Progress: "+result.getAnalysisProgress()*100+"%");
        terminal.writer().println("Comprehensibility: "+result.getComprehensibility()*100+"%");
        terminal.writer().println("Confidence: "+result.getConfidence()*100+"%");
        terminal.writer().println("Type Completeness Val1: "+result.getTypeCompletenessVal1()*100+"%");
        terminal.writer().println("Type Completeness Val2: "+result.getTypeCompletenessVal2()*100+"%");

        terminal.flush();
    }

     /**
     * Prints a dot as an indicator for the running transformation process to the terminal.
     * 
     * @param result
     */
    private void printPollingIndicator() {        
        terminal.writer().print(".");
        terminal.flush();
    }

}
