package org.apache.flink.streaming.connectors.rabbitmq.table;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.streaming.connectors.rabbitmq.RMQSink;
import org.apache.flink.streaming.connectors.rabbitmq.common.RMQConnectionConfig;
import org.apache.flink.table.catalog.ResolvedSchema;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.format.EncodingFormat;
import org.apache.flink.table.connector.sink.DynamicTableSink;
import org.apache.flink.table.connector.sink.SinkFunctionProvider;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.types.RowKind;

import java.util.Map;

import javax.annotation.Nullable;

public class RabbitMQDynamicTableSink implements DynamicTableSink {

    private final Integer sinkParallelism;

    private final Map<String, String> properties;

    private final ResolvedSchema resolvedSchema;

    private final ReadableConfig readableConfig;

    private final EncodingFormat<SerializationSchema<RowData>> encodingFormat;

    private final DataType physicalDataType;

    public RabbitMQDynamicTableSink(
            ReadableConfig readableConfig,
            Map<String, String> properties,
            ResolvedSchema resolvedSchema,
            @Nullable EncodingFormat<SerializationSchema<RowData>> encodingFormat,
            DataType physicalDataType) {
        this.properties = properties;
        this.sinkParallelism = readableConfig.get(RabbitMQOptions.SINK_PARALLELISM);
        this.resolvedSchema = resolvedSchema;
        this.readableConfig = readableConfig;
        this.encodingFormat = encodingFormat;
        this.physicalDataType = physicalDataType;
    }

    @Override
    public ChangelogMode getChangelogMode(ChangelogMode requestedMode) {
        return ChangelogMode.newBuilder()
                .addContainedKind(RowKind.INSERT)
                .addContainedKind(RowKind.UPDATE_AFTER)
                .build();
    }

    @Override
    public SinkRuntimeProvider getSinkRuntimeProvider(Context context) {
        RMQConnectionConfig connectionConfig =
                new RMQConnectionConfig.Builder()
                        .setPort(readableConfig.get(RabbitMQOptions.PORT))
                        .setPassword(readableConfig.get(RabbitMQOptions.PASSWORD))
                        .setHost(readableConfig.get(RabbitMQOptions.HOST))
                        .setUserName(readableConfig.get(RabbitMQOptions.USERNAME))
                        .setVirtualHost(readableConfig.get(RabbitMQOptions.VIRTUALHOST))
                        .build();

        final SerializationSchema<RowData> serializationSchema =
                encodingFormat.createRuntimeEncoder(context, physicalDataType);

        DummyPublishOptions rmqSinkPublishOptions = new DummyPublishOptions(readableConfig);
        RMQSink rabbitMQSinkFunction =
                new RMQSink(connectionConfig, serializationSchema, rmqSinkPublishOptions);
        return SinkFunctionProvider.of(rabbitMQSinkFunction, sinkParallelism);
    }

    @Override
    public DynamicTableSink copy() {
        return new RabbitMQDynamicTableSink(
                readableConfig, properties, resolvedSchema, encodingFormat, physicalDataType);
    }

    @Override
    public String asSummaryString() {
        return "RABBITMQ";
    }
}
