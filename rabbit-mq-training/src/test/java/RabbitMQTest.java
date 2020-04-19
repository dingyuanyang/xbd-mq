import com.xiaobaiding.note.rabbitmq.training.helloRabbitMQ.RabbitMQConsumer;
import com.xiaobaiding.note.rabbitmq.training.helloRabbitMQ.RabbitMQProduct;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class RabbitMQTest {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQConsumer consumer = new RabbitMQConsumer();
        RabbitMQProduct product = new RabbitMQProduct();
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while (!"exit".equals(line)) {
            product.sendMsg(line);
            line = scanner.nextLine();
        }
        product.close();
    }
}
