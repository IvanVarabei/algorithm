package by.org.singleton.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Transaction {
    static ExecutorService executor = Executors.newFixedThreadPool(3);

    public static void main(String[] klsdj) throws Exception {
        Class.forName("org.postgresql.Driver");
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            connections.add(DriverManager.getConnection("jdbc:postgresql://localhost:5432/test",
                    "postgres",
                    "postgres"));
        }
        Callable<?> callable1 = (() -> {
            Connection connection = connections.get(0);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            Statement statement = connection.createStatement();
            show(new StringBuilder("FIRST\n"),statement.executeQuery("select * from users"));
            Thread.sleep(500);

            statement.executeUpdate("update users set user_name = 'v2' where user_id = 1");
            Thread.sleep(1000);

            connection.commit();
            statement.close();
            System.out.println("1 end");
            return null;
        });
        Callable<?> callable2 = (() -> {
            try {
                Connection connection = connections.get(1);
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                Statement statement = connection.createStatement();
                show(new StringBuilder("SECOND\n"),statement.executeQuery("select * from users"));
                Thread.sleep(1000);

                System.out.println("before");
                //statement.executeUpdate("update users set user_name = 'v3' where user_id = 1");
                statement.executeUpdate("insert into users (user_name) values ('new')");
                System.out.println("after");

                connection.commit();
                statement.close();
                System.out.println("2 end");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        Callable<?> callable3 = (() -> {
            Connection connection = connections.get(2);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            Statement statement = connection.createStatement();
            Thread.sleep(500);



            connection.commit();
            statement.close();
            System.out.println("3 end");
            return null;
        });
        executor.submit(callable1);
        executor.submit(callable2);
        //executor.submit(callable3);
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    static void show(StringBuilder result, ResultSet resultSet) throws Exception {
        while (resultSet.next()) {
            Long userId = resultSet.getLong("user_id");
            String userName = resultSet.getString("user_name");
            result.append(userId).append("\t").append(userName).append("\n");
        }
        System.out.println(result);
    }
}