package org.apache.flink.streaming.connectors.rabbitmq.table;

import com.rabbitmq.client.AMQP;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class RabbitProperties extends AMQP.BasicProperties implements Serializable {
    private static final long serialVersionUID = 1L;

    public RabbitProperties(
            String contentType,
            String contentEncoding,
            Map<String, Object> headers,
            Integer deliveryMode,
            Integer priority,
            String correlationId,
            String replyTo,
            String expiration,
            String messageId,
            Date timestamp,
            String type,
            String userId,
            String appId,
            String clusterId) {
        super(
                contentType,
                contentEncoding,
                headers,
                deliveryMode,
                priority,
                correlationId,
                replyTo,
                expiration,
                messageId,
                timestamp,
                type,
                userId,
                appId,
                clusterId);
    }

    public static final class Builder {
        private String contentType;
        private String contentEncoding;
        private Map<String, Object> headers;
        private Integer deliveryMode;
        private Integer priority;
        private String correlationId;
        private String replyTo;
        private String expiration;
        private String messageId;
        private Date timestamp;
        private String type;
        private String userId;
        private String appId;
        private String clusterId;

        public Builder() {}
        ;

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder contentEncoding(String contentEncoding) {
            this.contentEncoding = contentEncoding;
            return this;
        }

        public Builder headers(Map<String, Object> headers) {
            this.headers = headers;
            return this;
        }

        public Builder deliveryMode(Integer deliveryMode) {
            this.deliveryMode = deliveryMode;
            return this;
        }

        public Builder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public Builder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public Builder replyTo(String replyTo) {
            this.replyTo = replyTo;
            return this;
        }

        public Builder expiration(String expiration) {
            this.expiration = expiration;
            return this;
        }

        public Builder messageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder clusterId(String clusterId) {
            this.clusterId = clusterId;
            return this;
        }

        public RabbitProperties build() {
            return new RabbitProperties(
                    contentType,
                    contentEncoding,
                    headers,
                    deliveryMode,
                    priority,
                    correlationId,
                    replyTo,
                    expiration,
                    messageId,
                    timestamp,
                    type,
                    userId,
                    appId,
                    clusterId);
        }
    }
}
