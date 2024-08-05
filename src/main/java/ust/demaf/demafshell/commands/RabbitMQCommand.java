package ust.demaf.demafshell.commands;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ust.demaf.demafshell.service.RabbitMQService;

import java.util.List;

@ShellComponent
@ShellCommandGroup("Purge Commands")
public class RabbitMQCommand {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @ShellMethod("Purge the message queue.")
    public String purgeQueue(
            @ShellOption(value = {"-q", "--queue"},
                help = "The name of the queue to purge.")
                String queueName) {
        rabbitAdmin.purgeQueue(queueName, false);
        return "Queue purged.";
    }

    @Autowired
    private RabbitMQService rabbitMQService;

    @ShellMethod("List all available RabbitMQ queues.")
    public List<String> listQueues() {
        return rabbitMQService.getQueueNames();
    }
}
