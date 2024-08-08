package ust.demaf.demafshell.commands;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ust.demaf.demafshell.service.RabbitMQService;

import java.util.List;

@ShellComponent
@ShellCommandGroup("Purge Commands")
public class RabbitMQCommand {


    @Autowired
    private RabbitAdmin rabbitAdmin;

    /**
     * Purges the specified RabbitMQ queue.
     * The queue can be identified either by its name or by its position in the list of queues.
     *
     * @param queueIdentifier the name or position of the queue to purge.
     * @return a message indicating the result of the purge operation.
     */

    @ShellMethod("Purge the message queue.")
    public String purge(String queueIdentifier) {
        List<String> queueNames = rabbitMQService.getQueueNames();
        String queueName;

        try {
            int position = Integer.parseInt(queueIdentifier);
            if (position < 1 || position > queueNames.size()) {
                return "Invalid queue position.";
            }
            queueName = queueNames.get(position - 1);
        } catch (NumberFormatException e) {
            queueName = queueIdentifier;
            if (!queueNames.contains(queueName)) {
                return "Queue name not found.";
            }
        }
        rabbitAdmin.purgeQueue(queueName, false);
        return "Queue " + queueName + " purged.";
    }

    /**
     * Lists all available RabbitMQ queues.
     *
     * @return a list of queue names.
     */

    @Autowired
    private RabbitMQService rabbitMQService;

    @ShellMethod("List all available RabbitMQ queues.")
    public List<String> listq() {
        return rabbitMQService.getQueueNames();
    }
}
