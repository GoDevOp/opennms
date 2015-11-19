package org.opennms.features.amqp;

import org.opennms.netmgt.events.api.EventForwarder;
import org.opennms.netmgt.events.api.EventIpcManager;
import org.opennms.netmgt.events.api.EventListener;
import org.opennms.netmgt.xml.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class ForwardingEventListener implements EventListener {

    private static final Logger LOG = LoggerFactory.getLogger(ForwardingEventListener.class);

    private volatile EventForwarder eventForwarder;
    private volatile EventIpcManager eventIpcManager;

    public EventForwarder getEventForwarder() {
        return eventForwarder;
    }

    public void setEventForwarder(EventForwarder eventForwarder) {
        this.eventForwarder = eventForwarder;
    }

    public EventIpcManager getEventIpcManager() {
        return eventIpcManager;
    }

    public void setEventIpcManager(EventIpcManager eventIpcManager) {
        this.eventIpcManager = eventIpcManager;
    }

    public void init() {
        Preconditions.checkNotNull(eventIpcManager, "eventIpcManager must not be null");
        Preconditions.checkNotNull(eventForwarder, "eventForwarder must not be null");

        installMessageSelectors();

        LOG.info("AMQP Event Forwarder initialized");
    }

    private void installMessageSelectors() {
        getEventIpcManager().addEventListener(this);
    }

    @Override
    public void onEvent(final Event event) {
        // Forward all events
        eventForwarder.sendNow(event);
    }

    @Override
    public String getName() {
        return "AMQPEventForwarder";
    }
}
