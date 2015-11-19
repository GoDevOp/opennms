package org.opennms.features.amqp;

import org.opennms.features.amqp.internal.DefaultAlarmForwarder;
import org.opennms.netmgt.alarmd.api.NorthboundAlarm;
import org.opennms.netmgt.alarmd.api.NorthbounderException;
import org.opennms.netmgt.alarmd.api.support.AbstractNorthbounder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AMQPNorthbounder extends AbstractNorthbounder {

    private volatile DefaultAlarmForwarder alarmForwarder;

    private static final Logger LOG = LoggerFactory.getLogger(AMQPNorthbounder.class);

    public AMQPNorthbounder() {
        super("AMQPNorthbounder");
        LOG.debug("AMQPNorthbounder created");
    }

    @Override
    protected boolean accepts(NorthboundAlarm alarm) {
        return true; // Accept ANY alarm
    }

    @Override
    public void forwardAlarms(List<NorthboundAlarm> alarms) throws NorthbounderException {
        for(NorthboundAlarm alarm: alarms) {
            LOG.trace("AMQPNorthbounder Forwarding alarm: {}", alarm);
            alarmForwarder.sendNow(alarm);
        }
    }

    public DefaultAlarmForwarder getAlarmForwarder() {
        return alarmForwarder;
    }

    public void setAlarmForwarder(DefaultAlarmForwarder alarmForwarder) {
        this.alarmForwarder = alarmForwarder;
    }
}
