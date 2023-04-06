package com.mems.workout.backend.websocket

import com.fasterxml.jackson.databind.JsonNode
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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
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

    @PostMapping("/add")
    fun add(@RequestBody dataJson: JsonNode) {

        val id = 0 // this value is not important
        val datetime = Date()
        val data = Data(id, datetime, dataJson)

        val result = useCase.add(data)
        if (result) {
            println("[Success] added $dataJson")
        } else {
            println("[Error] adding $dataJson failed")
        }
    }

    @GetMapping("/get")
    fun get(@RequestParam("id") id: Int) {

        val data = useCase.get(id)
        if (data != null) {
            println("[Success] got: \n$data")
        } else {
            println("[Error] getting $id failed")
        }
    }

    @PostConstruct
    fun init() {
//        createMqttSubscriber("host.docker.internal", "topic")
        createMqttSubscriber("localhost", "topic")
    }
}


