package com.trackingsystem.driverapp.kafka;

import com.trackingsystem.driverapp.config.ApplicationProperties;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    ApplicationProperties applicationProperties;
    private static final Serde<String> STRING_SERDE = Serdes.String();


    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream(applicationProperties.getStreamInput(), Consumed.with(STRING_SERDE, STRING_SERDE));
        messageStream.to(applicationProperties.getStreamOutput());
    }


}
