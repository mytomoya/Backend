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
received: 1234
```

You can also send random data continuously to simulate the real use case by running the command:

```bash
i=1; while true; do mosquitto_pub -h localhost -t "topic" -m "$RANDOM"; i=$((i+1)); sleep 1; done
```


### MQTT Topics

- `topic`: data captured by the sensor is sent with this topic.
  ```json
  {
    "time": "float",
    "activity": "bool",
    "y_acc": "float",
    "z_acc": "flaot",
    "y_correct": "bool",
    "z_correct": "bool"
  }
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


### WebSocket Topics

- `/topic/time`
  - type: `float`
- `/topic/activity`
  - type: `bool`
- `/topic/y_acc`
  - type: `float`
- `/topic/z_acc`
  - type: `float`
- `/topic/y_correct`
  - type: `bool`
- `/topic/z_correct`
  - type: `bool`
