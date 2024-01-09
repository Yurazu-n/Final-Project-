package org.owen.control;

import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.owen.model.Weather;

import javax.jms.*;

public class PredictionPublisher implements EventPublisher {
    @Override
    public void publishWeather(Weather weather) throws WeatherExecutionException {
        try {
            Gson gson = new Gson();
            String jsonPrediction = gson.toJson(weather);

            String url = ActiveMQConnection.DEFAULT_BROKER_URL;
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("prediction.Weather");
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();

            message.setText(jsonPrediction);
            producer.send(message);
            connection.close();

        } catch (JMSException e) {
            throw new WeatherExecutionException("Execution Error");
        }
    }
}
