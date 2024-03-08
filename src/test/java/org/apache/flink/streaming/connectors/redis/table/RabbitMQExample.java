package org.apache.flink.streaming.connectors.redis.table;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.junit.jupiter.api.Test;

public class RabbitMQExample {

    @Test
    public void testRabbitProducer() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.executeSql(
                "create table rabbit (id int) with ('connector'= 'rabbitmq', 'exchange-name'='test',"
                        + "'host'='127.0.0.1', 'port'='5672', 'username'='user', 'password'='123', "
                        + "'format'='json', 'routing-key'='test', 'virtual-host'='/test/') ");

        TableResult tableResult = tEnv.executeSql("insert into rabbit select * from (values(1))");
        tableResult.getJobClient().get().getJobExecutionResult().get();
    }

    @Test
    public void testRabbitConsumer() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.executeSql(
                "create table rabbit (id int) with ('connector'= 'rabbitmq', 'queue-name'='test',"
                        + "'host'='127.0.0.1', 'port'='5672', 'username'='user', 'password'='123', "
                        + "'format'='json', 'virtual-host'='/test/') ");

        tEnv.executeSql("create table sink_table (id int) with ('connector'='print')");
        TableResult tableResult = tEnv.executeSql("insert into sink_table select * from rabbit");
        tableResult.getJobClient().get().getJobExecutionResult().get();
    }
}
