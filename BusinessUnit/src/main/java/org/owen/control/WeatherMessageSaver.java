package org.owen.control;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.*;

public class WeatherMessageSaver {

    private final String path;
    private WeatherDataBase weatherDataBase;

    public WeatherMessageSaver(String path) {
        this.path = path;
        this.weatherDataBase = new WeatherDataBase(path);
    }

    public void saveMessages() throws BusinessUnitExecutionException {
        try {
            processEventFiles(path, weatherDataBase);
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("prediction.Weather");
            MessageConsumer consumer = session.createConsumer(topic);

            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        try {
                            WeatherDataBase weatherDataBase = new WeatherDataBase(path);
                            weatherDataBase.save(textMessage.getText());
                        } catch (JMSException | BusinessUnitExecutionException e) {
                            System.out.println("Error Receiving Messages");
                        }
                    }
                }
            });

        } catch (JMSException e) {
            throw new BusinessUnitExecutionException("Connection Error");
        }
    }

    private static void processEventFiles(String path, WeatherDataBase weatherDataBase) throws BusinessUnitExecutionException {
        try {
            String directoryPath = path + "/datalake/eventstore/prediction.Weather/prediction-provider";
            Stream<Path> files = Files.list(Path.of(directoryPath)).filter(p -> p.toString().endsWith(".events"));

            files.forEach(filePath -> {
                try {
                    processFile(filePath, weatherDataBase);
                } catch (BusinessUnitExecutionException e) {
                    System.out.println("Error processing files");;
                }
            });
        } catch (IOException e) {
            throw new BusinessUnitExecutionException("Error listing files in directory");
        }
    }

    private static void processFile(Path filePath, WeatherDataBase weatherDataBase) throws BusinessUnitExecutionException {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                try {
                    weatherDataBase.save(line);
                } catch (BusinessUnitExecutionException e) {
                    System.out.println("Error while saving");;
                }
            });
        } catch (IOException e) {
            throw new BusinessUnitExecutionException("Error at accessing Path");
        }
    }
}


