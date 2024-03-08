package org.apache.flink.streaming.connectors.rabbitmq.table;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.streaming.connectors.rabbitmq.RMQSource;
import org.apache.flink.streaming.connectors.rabbitmq.common.RMQConnectionConfig;
import org.apache.flink.table.catalog.ResolvedSchema;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.connector.source.ScanTableSource;
import org.apache.flink.table.connector.source.SourceFunctionProvider;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;

import java.util.Map;

import javax.annotation.Nullable;

public class RabbitMQDynamicTableSource implements ScanTableSource {

    private final Map<String, String> properties;

    private final ResolvedSchema resolvedSchema;

    private final ReadableConfig readableConfig;

    protected final DecodingFormat<DeserializationSchema<RowData>> decodingFormat;

    private final DataType physicalDataType;

    public RabbitMQDynamicTableSource(
            ReadableConfig readableConfig,
            Map<String, String> properties,
            ResolvedSchema resolvedSchema,
            @Nullable DecodingFormat<DeserializationSchema<RowData>> decodingFormat,
            DataType physicalDataType) {
        this.properties = properties;
        this.resolvedSchema = resolvedSchema;
        this.readableConfig = readableConfig;
        this.decodingFormat = decodingFormat;
        this.physicalDataType = physicalDataType;
    }

    @Override
    public ChangelogMode getChangelogMode() {
        return this.decodingFormat.getChangelogMode();
    }

    @Override
    public ScanRuntimeProvider getScanRuntimeProvider(ScanContext runtimeProviderContext) {
        RMQConnectionConfig connectionConfig =
                new RMQConnectionConfig.Builder()
                        .setPort(readableConfig.get(RabbitMQOptions.PORT))
                        .setPassword(readableConfig.get(RabbitMQOptions.PASSWORD))
                        .setHost(readableConfig.get(RabbitMQOptions.HOST))
                        .setUserName(readableConfig.get(RabbitMQOptions.USERNAME))
                        .setVirtualHost(readableConfig.get(RabbitMQOptions.VIRTUALHOST))
                        .build();
        final DeserializationSchema deserializationSchema =
                this.decodingFormat.createRuntimeDecoder(
                        runtimeProviderContext, this.physicalDataType);

        RMQSource rmqSource =
                new RMQSource(
                        connectionConfig,
                        readableConfig.get(RabbitMQOptions.QUEUENAME),
                        deserializationSchema);
        return SourceFunctionProvider.of(rmqSource, false);
    }

    @Override
    public DynamicTableSource copy() {
        return null;
    }

    @Override
    public String asSummaryString() {
        return "RABBITMQ";
    }
}
