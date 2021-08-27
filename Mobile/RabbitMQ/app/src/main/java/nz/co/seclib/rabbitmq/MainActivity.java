package nz.co.seclib.rabbitmq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeoutException;

import nz.co.seclib.rabbitmq.service.RabbitMQService;


public class MainActivity extends AppCompatActivity {

    private RabbitMQService rabbitMQService ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rabbitMQService = new RabbitMQService(this);
        rabbitMQService.setupConnectionFactory();
        //rabbitMQService.publishToAMQP();
        rabbitMQService.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(rabbitMQService != null) {
            rabbitMQService.finish();
        }
    }
}