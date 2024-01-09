package org.owen.control;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherStoreBuilder {

    private final String path;

    public WeatherStoreBuilder(String path) {
        this.path = path;
    }

    //TODO encapsular excepcion
    public void weatherStoreBuild() throws MyEventException {
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        MessageConsumer consumer = null;
        try {
            consumer = getMessageConsumer(url);
        } catch (MyEventException e) {
            System.out.println("Connection Error");
        }
        String eventDirectory = path + "/datalake/eventstore/prediction.Weather";

        try {
            consumer.setMessageListener(message -> {
                try {
                    String jsonEvent = ((TextMessage) message).getText();
                    writeEvent(jsonEvent, eventDirectory);
                } catch (JMSException | MyEventException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (JMSException e) {
            throw new MyEventException("Error setting listener");
        }
    }

    private static MessageConsumer getMessageConsumer(String url) throws MyEventException {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("weatherStoreBuilder");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic destination = session.createTopic("prediction.Weather");

            return session.createDurableSubscriber(destination, "weatherStoreBuilder");
        } catch (JMSException e) {
            throw new MyEventException("Error");
        }
    }

    private static void writeEvent(String jsonEvent, String eventDirectory) throws MyEventException {
        JsonObject jsonObjectEvent = JsonParser.parseString(jsonEvent).getAsJsonObject();

        String ss = jsonObjectEvent.get("ss").getAsString();
        Instant instant = Instant.parse(jsonObjectEvent.get("ts").getAsString());

        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));
        String eventDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(zonedDateTime);

        String eventFilePath = eventDirectory + "/" + ss + "/" + eventDate + ".events";

        new File(eventDirectory + "/" + ss).mkdirs();

        try (FileWriter writer = new FileWriter(eventFilePath, true)){
            writer.write(jsonEvent + System.lineSeparator());
        } catch (IOException e) {
            throw new MyEventException("Error");
        }
    }
}
