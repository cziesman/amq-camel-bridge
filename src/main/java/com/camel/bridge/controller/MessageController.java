package com.camel.bridge.controller;

import com.camel.bridge.client.AmqProducer;
import com.camel.bridge.client.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {

    @Autowired
    private AmqProducer amqProducer;

    @Autowired
    private MqProducer mqProducer;

    @GetMapping({
            "/", ""
    })
    public String index() {

        return "redirect:/home";
    }

    @GetMapping("/send-amq")
    public String sendJms() {

        amqProducer.sendMessage("{'source':'amq', 'dest':'mq'}");

        return "home";
    }

    @GetMapping("/send-mq")
    public String sendAmqp() {

        mqProducer.sendMessage("{'source':'mq', 'dest':'amq'}");

        return "home";
    }

    @GetMapping("/home")
    public String home() {

        return "home";
    }
}
