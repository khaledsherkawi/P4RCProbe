/* 
 * Copyright (C) 2014 Christopher Metter
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.uniwuerzburg.info3.ofcprobe.vswitch.trafficgen.ofevent;

public enum EventType {
	// Resembles OFPacketIn Message
	PACKET_IN_EVENT,
	OFSWITCH_CONNECT_EVENT,
	OFSWITCH_CONCHECK_EVENT,
	OFSWITCH_QUEUESWITCH_EVENT,
	OFSWITCH_DISCONNECT_EVENT,
	GENERATION_END,
	ARP_EVENT,
	TCP_AFTER_ARP

}
