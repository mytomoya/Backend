# Backend


## Install MQTT

```bash
sudo apt install mosquitto
sudo apt install mosquitto-clients
```


## MQTT Broker

Before the server starts, run the following command to launch the MQTT broker:

```bash
mosquitto -c mosquitto.conf
```

### Example

To publish messages from the command line, run the following command when the server is running:

```bash
mosquitto_pub -h localhost -t "topic" -m "1234"
```

In the above example, `topic` is the topic.

You can check the message is successfully received in the server log, e.g.,

```bash
received: hello, this is an example message.
```


## Run the App

Follow [Install Docker Engine](https://docs.docker.com/engine/install/) to install `Docker Desktop`.

To run the app, run:

```bash
docker-compose up
```

If you get the `network mems-network declared as external, but could not be found` error, run:

```bash
docker network create mems-network --subnet=192.168.1.0/24 --gateway=192.168.1.1
```


## WebSocket

- WebSocket endpoint: `http://localhost:8080/endpoint`
  - `http://localhost:3000` (e.g., React client) can connect to it.
- For debug purposes, the server sends a message to the topic `/topic/message` every 2 seconds.
- The server listens at `/app/send/message`.
  - If a message is received, the server responds is sent to the topic `/topic/message`.
