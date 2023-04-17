package com.mems.workout.backend.mqtt

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.mems.workout.backend.model.Data
import com.mems.workout.backend.model.Message
import com.mems.workout.backend.model.Value
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.system.exitProcess

class Subscriber(
    brokerHostName: String,
    subscribeTopics: Array<String>,
    private val template: SimpMessagingTemplate,
) : MqttCallback {
    private val broker: String
    private val topics: Array<String>

    init {
        this.broker = "tcp://$brokerHostName:1883"
        this.topics = subscribeTopics
    }

    override fun connectionLost(cause: Throwable) {
        println("connection lost")
        exitProcess(1)
    }

    override fun messageArrived(topic: String, message: MqttMessage) {
        println("[$topic] received: $message")
        template.convertAndSend("/topic/message", Message(message.toString()))

        try {
            val mapper = ObjectMapper()
            mapper.enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
            val data = mapper.readValue(message.toString(), Data::class.java)
            if (data != null) {
                println("data: $data")
                template.convertAndSend("/topic/time", data.getTime())
                template.convertAndSend("/topic/activity", data.getTime())
                template.convertAndSend("/topic/y_acc", data.getYAcceleration())
                template.convertAndSend("/topic/z_acc", data.getZAcceleration())
                template.convertAndSend("/topic/y_correct", data.getYCorrect())
                template.convertAndSend("/topic/z_correct", data.getZCorrect())
            }
        } catch (error: Exception) {
            println(error)
        }


        val value = message.toString().toDoubleOrNull()
        if (value != null) {
            template.convertAndSend("/topic/value", Value(value))
        }
    }

    override fun deliveryComplete(p0: IMqttDeliveryToken?) {
        TODO("Not yet implemented")
    }

    fun subscribe(qos: Int = 2, clientId: String = "Subscriber") {
        val client = MqttClient(broker, clientId, MemoryPersistence())
        client.setCallback(this)
        val connectOptions = MqttConnectOptions()
        connectOptions.isCleanSession = false

        println("Connecting to broker $broker")
        client.connect(connectOptions)

        for (topic in topics) {
            client.subscribe(topic, qos)
        }

        val bufferReader = BufferedReader(InputStreamReader(System.`in`))

        // Keep connection until receiving standard input
        try {
            bufferReader.readLine()
        } catch (e: IOException) {
            exitProcess(1)
        }

        client.disconnect()
        client.close()
        println("Disconnected")
    }
}

