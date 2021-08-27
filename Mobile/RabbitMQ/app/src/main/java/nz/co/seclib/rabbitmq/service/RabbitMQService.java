package nz.co.seclib.rabbitmq.service;

import android.content.Context;
import android.util.Log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

import nz.co.seclib.rabbitmq.R;

public class RabbitMQService {
    private Context context;
    SMSService smsService;

    public RabbitMQService(Context context){
        this.context = context;
        smsService = new SMSService(context);
    }


    ConnectionFactory factory = new ConnectionFactory();
    public Thread subscribeThread;
    public Thread publishThread;
    String exchangeName;
    String routingKey;
    String queueName;


    public void setupConnectionFactory() {
        factory.setUsername(context.getString(R.string.user_name)) ;//"guest");
        factory.setPassword(context.getString(R.string.password)) ;//"guest");
        factory.setVirtualHost(context.getString(R.string.virtual_host)) ;//"/");
        factory.setHost(context.getString(R.string.host)) ;//"192.168.1.10");
        factory.setPort(Integer.parseInt( context.getString(R.string.port))) ;//5672);

        exchangeName = context.getString(R.string.exchange_name);// "exchange.direct.msm";
        routingKey = context.getString(R.string.routing_key);//"msm.item";
        queueName = context.getString(R.string.queue_name) ;//"queue.msm.item";
    }

    public void publishToAMQP()
    {
        //sleep and then try again
        publishThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = factory.newConnection();
                    Channel ch = connection.createChannel();
                    ch.exchangeDeclare(exchangeName, "direct", true);
                    ch.queueDeclare(queueName, true, false, false, null);
                    ch.queueBind(queueName, exchangeName, routingKey);

                    byte[] messageBodyBytes = "Hello, world!".getBytes();
                    ch.basicPublish(exchangeName, routingKey, null, messageBodyBytes);
                } catch (Exception e) {
                    Log.d("", "Connection broken: " + e.getClass().getName());
                }
            }
        });
        publishThread.start();
    }

    public void subscribe()
    {
        //sleep and then try again
        subscribeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = factory.newConnection();
                    Channel ch = connection.createChannel();
                    ch.exchangeDeclare(exchangeName, "direct", true);
                    ch.queueDeclare(queueName, true, false, false, null);
                    ch.queueBind(queueName, exchangeName, routingKey);

                    Consumer consumer = new DefaultConsumer(ch);
                    boolean autoAck = false;
                    ch.basicConsume(queueName, autoAck, "HtbpConsumerTag",
                            new DefaultConsumer(ch) {
                                @Override
                                public void handleDelivery(String consumerTag,
                                                           Envelope envelope,
                                                           AMQP.BasicProperties properties,
                                                           byte[] body)
                                        throws IOException
                                {
                                    String routingKey = envelope.getRoutingKey();
                                    String contentType = properties.getContentType();
                                    long deliveryTag = envelope.getDeliveryTag();

                                    String message = new String(body);
                                    smsService.SendMessage(message);

                                    Log.d("", "[r] " + message);
                                    System.out.println(message);
                                    ch.basicAck(deliveryTag, false);
                                }
                            });
                    while (true){
                        Thread.sleep(1000);
                    }
                } catch (Exception e1) {
                    Log.d("", "Connection broken: " + e1.getClass().getName());
                }
            }
        });
        subscribeThread.start();
    }

    public void finish(){
        if(publishThread != null)
            publishThread.interrupt();
        if(subscribeThread != null)
            subscribeThread.interrupt();
    }
}
