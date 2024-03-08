package org.apache.flink.streaming.connectors.rabbitmq.table;

import static org.apache.flink.table.factories.FactoryUtil.FORMAT;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.connector.format.EncodingFormat;
import org.apache.flink.table.connector.sink.DynamicTableSink;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.factories.*;
import org.apache.flink.table.types.DataType;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RabbitMQDynamicTableFactory
        implements DynamicTableSinkFactory, DynamicTableSourceFactory {

    public static final String IDENTIFIER = "rabbitmq";

    @Override
    public DynamicTableSink createDynamicTableSink(Context context) {
        FactoryUtil.TableFactoryHelper helper = FactoryUtil.createTableFactoryHelper(this, context);

        final Optional<EncodingFormat<SerializationSchema<RowData>>> encodingFormat =
                helper.discoverOptionalEncodingFormat(SerializationFormatFactory.class, FORMAT);
        final DataType physicalDataType = context.getPhysicalRowDataType();
        ReadableConfig config = helper.getOptions();
        helper.validate();
        return new RabbitMQDynamicTableSink(
                config,
                context.getCatalogTable().getOptions(),
                context.getCatalogTable().getResolvedSchema(),
                encodingFormat.orElse(null),
                physicalDataType);
    }

    @Override
    public DynamicTableSource createDynamicTableSource(Context context) {
        FactoryUtil.TableFactoryHelper helper = FactoryUtil.createTableFactoryHelper(this, context);

        final Optional<DecodingFormat<DeserializationSchema<RowData>>> decodingFormat =
                helper.discoverOptionalDecodingFormat(DeserializationFormatFactory.class, FORMAT);
        final DataType physicalDataType = context.getPhysicalRowDataType();
        ReadableConfig config = helper.getOptions();
        helper.validate();
        return new RabbitMQDynamicTableSource(
                config,
                context.getCatalogTable().getOptions(),
                context.getCatalogTable().getResolvedSchema(),
                decodingFormat.orElse(null),
                physicalDataType);
    }

    @Override
    public String factoryIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(RabbitMQOptions.PASSWORD);
        options.add(RabbitMQOptions.HOST);
        options.add(RabbitMQOptions.USERNAME);
        options.add(RabbitMQOptions.PORT);
        options.add(RabbitMQOptions.VIRTUALHOST);
        options.add(FORMAT);
        return options;
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(RabbitMQOptions.ROUTINGKEY);
        options.add(RabbitMQOptions.EXCHANGENAME);
        options.add(RabbitMQOptions.SINK_PARALLELISM);
        options.add(RabbitMQOptions.QUEUENAME);
        return options;
    }
}
