package nz.co.seclib.rabbitmq

import nz.co.seclib.rabbitmq.service.SMSService
import nz.co.seclib.rabbitmq.service.RabbitMQService
import nz.co.seclib.rabbitmq.vo.MsmVo
import android.app.PendingIntent
import android.content.Intent
import android.content.BroadcastReceiver
import android.app.Activity
import android.widget.Toast
import android.content.IntentFilter
import com.rabbitmq.client.ConnectionFactory
import nz.co.seclib.rabbitmq.R
import com.rabbitmq.client.DefaultConsumer
import kotlin.Throws
import com.rabbitmq.client.AMQP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun sendMessageToRMQ() {
    }
}