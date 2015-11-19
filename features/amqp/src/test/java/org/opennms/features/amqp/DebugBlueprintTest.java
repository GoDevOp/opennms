package org.opennms.features.amqp;

import java.util.Map;
import java.util.Dictionary;

import org.apache.camel.util.KeyValueHolder;
import org.apache.camel.Exchange;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.opennms.netmgt.dao.mock.MockEventIpcManager;
import org.opennms.netmgt.xml.event.Event;

@Ignore
public class DebugBlueprintTest extends CamelBlueprintTestSupport {
 
    private boolean debugBeforeMethodCalled;
    private boolean debugAfterMethodCalled;
 
    // override this method, and return the location of our Blueprint XML file to be used for testing
    @Override
    protected String getBlueprintDescriptor() {
        return "OSGI-INF/blueprint/blueprint-amqp.xml";
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
        MockEventIpcManager mockEventIpcManager = new MockEventIpcManager();
        services.put(org.opennms.netmgt.events.api.EventIpcManager.class.getName(), asService(mockEventIpcManager, null));
    }

    // here we have regular JUnit @Test method
    @Test
    public void testRoute() throws Exception {
 
        // set mock expectations
        //getMockEndpoint("mock:a").expectedMessageCount(1);
 
        Event event = new Event();
        template.sendBody("direct:AMQP_PRE", event);
 
        // assert mocks
        //assertMockEndpointsSatisfied();
 
        // assert on the debugBefore/debugAfter methods below being called as we've enabled the debugger
        //assertTrue(debugBeforeMethodCalled);
        //assertTrue(debugAfterMethodCalled);
    }
 
    @Override
    public boolean isUseDebugger() {
        // must enable debugger
        return true;
    }
 
    @Override
    protected void debugBefore(Exchange exchange, org.apache.camel.Processor processor, ProcessorDefinition<?> definition, String id, String label) {
        log.info("Before " + definition + " with body " + exchange.getIn().getBody());
        debugBeforeMethodCalled = true;
    }
 
    @Override
    protected void debugAfter(Exchange exchange, org.apache.camel.Processor processor, ProcessorDefinition<?> definition, String id, String label, long timeTaken) {
        log.info("After " + definition + " with body " + exchange.getIn().getBody());
        debugAfterMethodCalled = true;
    }
}