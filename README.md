# amq-camel-bridge

This Spring Boot application rovides an example of how to bridge messages between an AMQ/Artemis message broker and another broker that can handle AMQP messaging, which in this case is an IBM MQ queue manager.

This example assumes that an AMQ broker is running on your local host and that the IBM MQ queue manager is running on a separate host. If that is not the case, then just adjust the host names to reflect the name or IP address where the respective brokers are running.

The application provides a very simple web UI at http://localhost:8888. There are two links available. One sends a message to AMQ which will be forwarded to MQ, and the other sends a message to MQ which will be forwarded to AMQ.

The Camel bridge listens on the respective queues and forwards any incoming messages to the corresponding queue on the other broker.