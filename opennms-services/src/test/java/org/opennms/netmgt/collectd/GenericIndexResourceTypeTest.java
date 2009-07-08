/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * Copyright (C) 2007-2008 The OpenNMS Group, Inc.  All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc.:
 *
 *      51 Franklin Street
 *      5th Floor
 *      Boston, MA 02110-1301
 *      USA
 *
 * For more information contact:
 *
 *      OpenNMS Licensing <license@opennms.org>
 *      http://www.opennms.org/
 *      http://www.opennms.com/
 *
 *******************************************************************************/

package org.opennms.netmgt.collectd;

import org.opennms.netmgt.config.datacollection.PersistenceSelectorStrategy;
import org.opennms.netmgt.config.datacollection.StorageStrategy;
import org.opennms.test.ThrowableAnticipator;

import junit.framework.TestCase;

/**
 * Tests for GenericIndexResourceType.
 *
 * @author <a href="dj@opennms.org">DJ Gregor</a>
 * @see GenericIndexResourceType
 */
public class GenericIndexResourceTypeTest extends TestCase {
    public void testNullResourceType() {
        ThrowableAnticipator ta = new ThrowableAnticipator();
        ta.anticipate(new IllegalArgumentException("resourceType argument must not be null"));
        try {
            new GenericIndexResourceType(null, null, null);
        } catch (Throwable t) {
            ta.throwableReceived(t);
        }
        ta.verifyAnticipated();
    }

    public void testInstantiate() {
        instantiate();
    }
    
    public void testGetStorageStrategy() {
        GenericIndexResourceType g = instantiate();
        assertNotNull("storageStrategy should not be null", g.getStorageStrategy());
    }

    private GenericIndexResourceType instantiate() {
        org.opennms.netmgt.config.datacollection.ResourceType rt = new org.opennms.netmgt.config.datacollection.ResourceType();
        
        PersistenceSelectorStrategy ps = new PersistenceSelectorStrategy();
        ps.setClazz("org.opennms.netmgt.collectd.PersistAllSelectorStrategy");
        rt.setPersistenceSelectorStrategy(ps);
        
        StorageStrategy ss = new StorageStrategy();
        ss.setClazz("org.opennms.netmgt.dao.support.IndexStorageStrategy");
        rt.setStorageStrategy(ss);
        
        return new GenericIndexResourceType(null, null, rt);
    }
}
