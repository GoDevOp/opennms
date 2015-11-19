package org.opennms.features.amqp.internal;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.bean.BeanInvocation;
import org.opennms.netmgt.alarmd.api.NorthboundAlarm;
import org.opennms.netmgt.xml.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Maps;

import java.util.Map;

public class AMQPTranslator {
    private static final Logger LOG = LoggerFactory.getLogger(AMQPTranslator.class);

    public static final String ELEMENT_ID = "ELEMENT_ID";
    public static final String MESSAGE_CODE = "MESSAGE_CODE";
    public static final String MESSAGE_TYPE = "MESSAGE_TYPE";
    public static final String MESSAGE_SUBTYPE = "MESSAGE_SUBTYPE";
    public static final String MESSAGE_TITLE = "MESSAGE_TITLE";
    public static final String MESSAGE_DESCRIPTION = "MESSAGE_DESCRIPTION";
    public static final String MESSAGE_SEQUENCENUMBER = "MESSAGE_SEQUENCENUMBER";
    public static final String MESSAGE_TIMESTAMP = "MESSAGE_TIMESTAMP";
    public static final String CUSTOM_MESSAGE = "CUSTOM_MESSAGE";

    public static final int OPENNMS_ELEMENT_ID = 6000;
    public static final String MESSAGE_TYPE_INFO = "INFO";

    public void process(Exchange exchange) {
        final Map<String, String> msg = Maps.newHashMap();
        
        final Map<String, Object> envelope = Maps.newHashMap();
        envelope.put(ELEMENT_ID, OPENNMS_ELEMENT_ID);
        envelope.put(MESSAGE_TYPE, MESSAGE_TYPE_INFO);
        envelope.put(CUSTOM_MESSAGE, msg);

        Message in = exchange.getIn();
        Object incoming = in.getBody();

        if(incoming instanceof BeanInvocation) {
            Object argument=((BeanInvocation)incoming).getArgs()[0];
            if(argument instanceof Event) {
                Event event = (Event) argument;
                LOG.info("Processing event: {}", event);
                msg.put("uei", event.getUei());
            } else if(argument instanceof NorthboundAlarm) {
                NorthboundAlarm alarm = (NorthboundAlarm) argument;
                LOG.info("Processing alarm: {}", alarm);
                msg.put("key", alarm.getAlarmKey());
            }
        }

        exchange.getOut().setBody(envelope);
    }
 
    public void log(Exchange exchange) {
        LOG.info("Got: {}", exchange);
    }
}
