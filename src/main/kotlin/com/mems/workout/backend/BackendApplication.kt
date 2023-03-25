package com.mems.workout.backend

import com.mems.workout.backend.mqtt.Subscriber
import org.eclipse.paho.client.mqttv3.MqttException
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)

	val subscriber = Subscriber(
		"localhost",
		"mqttTopic",
	)
	try {
		subscriber.subscribe()
	} catch (me: MqttException) {
		println("reason: ${me.reasonCode}")
		println("message: ${me.message}")
		println("localize: ${me.localizedMessage}")
		println("cause: ${me.cause}")
		println("exception: $me")

		exitProcess(1)
	}
}
