package co.edu.uniquindio.ingesis.services.implementation;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MqttService {

    private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    private MqttClient client;

    @PostConstruct
    public void init() {
        try {
            client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            client.connect(options);
            logger.info(" Conexión establecida con el broker MQTT");
        } catch (MqttException e) {
            logger.error(" Error al conectar con el broker MQTT: {}", e.getMessage(), e);
        }
    }

    public void publicar(String topic, String jsonPayload) {
        try {
            if (client != null && client.isConnected()) {
                MqttMessage message = new MqttMessage(jsonPayload.getBytes());
                message.setQos(1);
                client.publish(topic, message);
                logger.info(" Mensaje publicado en el tópico [{}]: {}", topic, jsonPayload);
            } else {
                logger.warn(" El cliente MQTT no está conectado.");
            }
        } catch (MqttException e) {
            logger.error(" Error publicando en el tópico {}: {}", topic, e.getMessage(), e);
        }
    }
}
