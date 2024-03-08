package org.apache.flink.streaming.connectors.rabbitmq.table;

import com.rabbitmq.client.AMQP;

import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.streaming.connectors.rabbitmq.RMQSinkPublishOptions;
import org.apache.flink.table.data.RowData;

import java.util.Collections;

public class DummyPublishOptions<RowData> implements RMQSinkPublishOptions<RowData> {
    private static final long serialVersionUID = 1L;

    private final boolean mandatory;
    private final boolean immediate;

    private final String routingKey;

    private final String exchange;

    private AMQP.BasicProperties props;

    public DummyPublishOptions(ReadableConfig readableConfig) {
        this.mandatory = readableConfig.get(RabbitMQOptions.MANDATORY);
        this.immediate = readableConfig.get(RabbitMQOptions.IMMEDIATE);
        this.routingKey = readableConfig.get(RabbitMQOptions.ROUTINGKEY);
        this.exchange = readableConfig.get(RabbitMQOptions.EXCHANGENAME);

        props =
                new RabbitProperties.Builder()
                        .headers(Collections.singletonMap("Flink", "DC"))
                        .expiration("10000")
                        .build();
    }

    @Override
    public String computeRoutingKey(RowData rowData) {
        return routingKey;
    }

    @Override
    public AMQP.BasicProperties computeProperties(RowData rowData) {
        return props;
    }

    @Override
    public String computeExchange(RowData rowData) {
        return exchange;
    }

    @Override
    public boolean computeMandatory(RowData rowData) {
        return mandatory;
    }

    @Override
    public boolean computeImmediate(RowData rowData) {
        return immediate;
    }
}
