package org.opennms.features.amqp.internal;

import org.opennms.netmgt.alarmd.api.NorthboundAlarm;

public interface CamelAlarmForwarder {
    public void sendNow(NorthboundAlarm alarm);
}
