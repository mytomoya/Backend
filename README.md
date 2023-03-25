# Backend

- WebSocket endpoint: `http://localhost:8080/endpoint`
  - `http://localhost:3000` (e.g., React client) can connect to it.
- For debug purposes, the server sends a message to the topic `/topic/message` every 2 seconds.
- The server listens at `/app/send/message`.
  - If a message is received, the server responds is sent to the topic `/topic/message`.
