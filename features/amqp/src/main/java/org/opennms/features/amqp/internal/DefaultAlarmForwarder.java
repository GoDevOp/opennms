package org.opennms.features.amqp.internal;

import org.apache.camel.InOnly;
import org.apache.camel.Produce;
import org.opennms.core.camel.DefaultDispatcher;
import org.opennms.netmgt.alarmd.api.NorthboundAlarm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@InOnly
public class DefaultAlarmForwarder extends DefaultDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAlarmForwarder.class);

    @Produce(property="endpointUri")
    CamelAlarmForwarder m_proxy;

    public DefaultAlarmForwarder(final String endpointUri) {
        super(endpointUri);
    }

    public void sendNow(NorthboundAlarm alarm) {
        LOG.debug("Forwarding alarm {}", alarm);
        m_proxy.sendNow(alarm);
    }
}
