package org.owen.control;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class HotelMessageSaver {

    private final String path;
    private HotelDataBase hotelDataBase;

    public HotelMessageSaver(String path) {
        this.path = path;
        this.hotelDataBase = new HotelDataBase(path);
    }

    public void saveMessages() throws BusinessUnitExecutionException {
        try {
            processEventFiles(path, hotelDataBase);
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("location.Hotels");
            MessageConsumer consumer = session.createConsumer(topic);

            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        try {
                            hotelDataBase.save(textMessage.getText());
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
    public static void processEventFiles(String path, HotelDataBase hotelDataBase) throws BusinessUnitExecutionException {
        try {
            String directoryPath = path + "/datalake/eventstore/location.Hotels/hotel-provider";
            Stream<Path> files = Files.list(Path.of(directoryPath)).filter(p -> p.toString().endsWith(".events"));

            files.forEach(filePath -> {
                try {
                    processFile(filePath, hotelDataBase);
                } catch (BusinessUnitExecutionException e) {
                    System.out.println("Error processing files");;
                }
            });
        } catch (IOException e) {
            throw new BusinessUnitExecutionException("Error listing files in directory");
        }
    }

    private static void processFile(Path filePath, HotelDataBase hotelDataBase) throws BusinessUnitExecutionException {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                try {
                    hotelDataBase.save(line);
                } catch (BusinessUnitExecutionException e) {
                    System.out.println("Error processing");;
                }
            });
        } catch (IOException e) {
            throw new BusinessUnitExecutionException("Error at Path");
        }
    }
}
