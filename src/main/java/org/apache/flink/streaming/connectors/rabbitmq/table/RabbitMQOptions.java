package org.apache.flink.streaming.connectors.rabbitmq.table;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;

public class RabbitMQOptions {
    private RabbitMQOptions() {}

    public static final ConfigOption<String> PASSWORD =
            ConfigOptions.key("password")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Optional password for connect to rabbitmq");

    public static final ConfigOption<String> USERNAME =
            ConfigOptions.key("username")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Optional username for connect to rabbitmq");

    public static final ConfigOption<Integer> PORT =
            ConfigOptions.key("port")
                    .intType()
                    .defaultValue(5672)
                    .withDescription("Optional port for connect to rabbitmq");

    public static final ConfigOption<String> HOST =
            ConfigOptions.key("host")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Optional host for connect to rabbitmq");

    public static final ConfigOption<String> ROUTINGKEY =
            ConfigOptions.key("routing-key")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Optional routing-key for connect to rabbitmq");

    public static final ConfigOption<String> EXCHANGENAME =
            ConfigOptions.key("exchange-name")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Optional exchange-name for connect to rabbitmq");

    public static final ConfigOption<String> VIRTUALHOST =
            ConfigOptions.key("virtual-host")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Optional virtual-host for connect to rabbitmq");

    public static final ConfigOption<Integer> SINK_PARALLELISM =
            ConfigOptions.key("sink.parallelism")
                    .intType()
                    .defaultValue(1)
                    .withDescription("Optional parrallelism for sink");

    public static final ConfigOption<Boolean> IMMEDIATE =
            ConfigOptions.key("immediate")
                    .booleanType()
                    .defaultValue(false)
                    .withDescription("Optional immediate for sink");

    public static final ConfigOption<Boolean> MANDATORY =
            ConfigOptions.key("mandatory")
                    .booleanType()
                    .defaultValue(false)
                    .withDescription("Optional mandatory for sink");

    public static final ConfigOption<String> QUEUENAME =
            ConfigOptions.key("queue-name")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Optional queuename for source");
}
