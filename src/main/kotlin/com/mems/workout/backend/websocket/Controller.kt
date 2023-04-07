package com.mems.workout.backend.websocket

import com.fasterxml.jackson.databind.JsonNode
import com.mems.workout.backend.db.Data
import com.mems.workout.backend.db.InvalidIdException
import com.mems.workout.backend.db.UseCase
import com.mems.workout.backend.model.Message
import com.mems.workout.backend.mqtt.Subscriber
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*
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
    fun get(@RequestParam("id") id: Int): ResponseEntity<Map<String, Any>> {

        val data = useCase.get(id)
        return if (data != null) {
            println("[Success] got: \n$data")
            ResponseEntity.ok(
                mapOf(
                    "data" to data,
                    "error" to "",
                ))
        } else {
            println("[Error] getting $id failed")
            throw InvalidIdException("Invalid ID: $id")
        }
    }

    @ExceptionHandler(InvalidIdException::class)
    fun handleInvalidIdException(exception: InvalidIdException): ResponseEntity<Map<String, String>> {
        val error = exception.message ?: ""
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                mapOf(
                    "data" to "",
                    "error" to error,
                ))
    }

    @PostConstruct
    fun init() {
        createMqttSubscriber("host.docker.internal", "topic")
//        createMqttSubscriber("localhost", "topic")
    }
}


