package com.mems.workout.backend.websocket

import com.mems.workout.backend.model.Message
import com.mems.workout.backend.mqtt.Subscriber
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Controller
class Controller {
    @Autowired
    lateinit var template: SimpMessagingTemplate

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    @MessageMapping("/send/message")
    @SendTo("/topic/message")
    fun handleMessage(message: Message): Message {
        println(message.getContent())
        return Message("server received: ${message.getContent()}")
    }

    fun sendMessage(number: Int) {
        val message = Message("example message $number")
        println(message.getContent())
        this.template.convertAndSend("/topic/message", message)
    }

    fun createMqttSubscriber(brokerHostName: String, subscribeTopic: String) {
        executor.submit {
            val subscriber = Subscriber(brokerHostName, subscribeTopic, template)
            subscriber.subscribe()
        }
    }

    @PostConstruct
    fun init() {
        var count = 0
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                count += 1
                sendMessage(count)
            }
        }
        timer.scheduleAtFixedRate(task, 0, 2000)

        createMqttSubscriber("localhost", "topic")
    }
}


