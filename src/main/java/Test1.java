import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Test1 {

    public static void main(String[] args) throws IOException {
        List<String> numbers = Files.readAllLines(Path.of("D:/stus.txt"));
        StringBuilder builder = new StringBuilder();
        numbers.forEach(n -> {
            builder.append("create user if not exists '");
            builder.append(n);
            builder.append("'@'%' identified by '");
            builder.append(n);
            builder.append("';");
            builder.append("\n");
            builder.append("CREATE DATABASE IF NOT EXISTS `");
            builder.append(n);
            builder.append("`;");
            builder.append("\n");
            builder.append("grant all privileges on `");
            builder.append(n);
            builder.append("`.* to '");
            builder.append(n);
            builder.append("'@'%' with grant option;");
            builder.append("\n");
        });
        builder.append("flush privileges;");
        Files.writeString(Path.of("D:/result.sql"), builder.toString());
    }
}
