# Backend

### WebSocket

- WebSocket endpoint: `http://localhost:8080/endpoint`
  - `http://localhost:3000` (e.g., React client) can connect to it.
- For debug purposes, the server sends a message to the topic `/topic/message` every 2 seconds.
- The server listens at `/app/send/message`.
  - If a message is received, the server responds is sent to the topic `/topic/message`.

### MQTT

Before the server starts, run the following command to launch the MQTT broker:

```bash
mosquitto
```

To publish messages from the command line, run the following command when the server is running:

```bash
mosquitto_pub -h localhost -t "mqttTopic" -m "hello, this is an example message"
```

In the above example, `mqttTopic` is the topic.

You can check the message is successfully received in the server log, e.g.,

```bash
received: hello, this is an example message.
```
