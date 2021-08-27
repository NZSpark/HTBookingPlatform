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
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("nz.co.seclib.rabbitmq", appContext.packageName)
    }

    @Test
    fun sendSMS() {
        val smsService = SMSService(InstrumentationRegistry.getInstrumentation().targetContext)
        smsService.mySendMessage("", "")
    }

    @Test
    fun sendMessageToRMQ() {
        val rabbitMQService = RabbitMQService(InstrumentationRegistry.getInstrumentation().targetContext)
        rabbitMQService.setupConnectionFactory()
        //rabbitMQService.publishToAMQP();
        rabbitMQService.subscribe()
        println("Comsumer started!")
        try {
            Thread.sleep(400000)
        } catch (e: Exception) {
        }
        rabbitMQService.finish()
    }
}