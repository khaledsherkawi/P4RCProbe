/* 
 * Copyright 2016 christopher.metter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.uniwuerzburg.info3.p4rcprobe.vswitch.trafficgen.ofevent;

import de.uniwuerzburg.info3.p4rcprobe.vswitch.connection.Connection;

/**
 * Interface for Events
 *
 * @author Christopher Metter(christopher.metter@informatik.uni-wuerzburg.de)
 */
public interface IOFEvent {

    /**
     * Returns the EventType
     *
     * @return EventType
     */
    public EventType getType();

    /**
     * Returns the Connection of this Event
     *
     * @return ofSwitch
     */
    public Connection getCon();

}