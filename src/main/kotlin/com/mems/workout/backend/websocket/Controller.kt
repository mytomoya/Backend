package com.mems.workout.backend.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.mems.workout.backend.db.Data
import com.mems.workout.backend.db.UseCase
import com.mems.workout.backend.model.Message
import com.mems.workout.backend.mqtt.Subscriber
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@RestController
class Controller {
    @Autowired
    lateinit var template: SimpMessagingTemplate
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    @Autowired
    private lateinit var useCase: UseCase

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

    @GetMapping("/add")
    fun add() {
        val mapper = ObjectMapper()

        val id = 0
        val datetime = Date()
        val dataJson = mapper.readTree("{\"time\":[0,1,2],\"value\":[3,4,5]}")
        val data = Data(0, datetime, dataJson)

        val result = useCase.add(data)
        if (result) {
            println("[Success] registered $dataJson")
        } else {
            println("[Error] registering $dataJson failed")
        }
    }

    @PostConstruct
    fun init() {
//        createMqttSubscriber("host.docker.internal", "topic")
        createMqttSubscriber("localhost", "topic")
    }
}


