package com.mems.workout.backend.mqtt

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.system.exitProcess

class Subscriber(brokerHostName: String, subscribeTopic: String): MqttCallback {
    private val broker: String
    private val topic: String

    init {
        this.broker = "tcp://$brokerHostName:1883"
        this.topic = subscribeTopic
    }

    override fun connectionLost(cause: Throwable) {
        println("connection lost")
        exitProcess(1)
    }

    override fun messageArrived(topic: String, message: MqttMessage) {
        println("received: $message")
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

        client.subscribe(topic, qos)

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
