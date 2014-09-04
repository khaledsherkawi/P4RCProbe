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
package de.uniwuerzburg.info3.ofcprobe.vswitch.main.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openflow.util.HexString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uniwuerzburg.info3.ofcprobe.util.Util;
import de.uniwuerzburg.info3.ofcprobe.vswitch.connection.IOFConnection;
import de.uniwuerzburg.info3.ofcprobe.vswitch.trafficgen.arping.Device;
import de.uniwuerzburg.info3.ofcprobe.vswitch.trafficgen.ipgen.IIpGenerator;
import de.uniwuerzburg.info3.ofcprobe.vswitch.trafficgen.ipgen.SerialIPGenerator;
import de.uniwuerzburg.info3.ofcprobe.vswitch.trafficgen.macgen.IMacGen;
import de.uniwuerzburg.info3.ofcprobe.vswitch.trafficgen.macgen.SerialMacGen;

import com.google.common.collect.HashBiMap;

/**
 * This Class tries to Map every free SwitchPort to a Device
 *
 * @author Christopher Metter(christopher.metter@informatik.uni-wuerzburg.de)
 *
 */
public class HostMapper {

    /**
     * Debugger
     */
    private static final Logger logger = LoggerFactory.getLogger(HostMapper.class);

    /**
     * Connected IPGenerator
     */
    private IIpGenerator ipGen;
    /**
     * Connected MACGenerator
     */
    private IMacGen macGen;
    /**
     * MAP Device->IP
     */
    private Map<Device, String> deviceToIP;
    /**
     * MAP IP->Device
     */
    private Map<String, Device> ipToDevice;
    /**
     * MAP Device->MAC
     */
    private Map<Device, String> deviceToMac;
    /**
     * MAP MAC->Device
     */
    private Map<String, Device> macToDevice;
    /**
     * MAP MAC<->IP
     */
    private HashBiMap<String, String> macIPmap;
    /**
     * Config
     */
    private Config config;

    /**
     * Constructor
     *
     * @param config Overallconfiguration
     * @param ofSwitches
     */
    public HostMapper(Config config, List<IOFConnection> ofSwitches) {
        this.config = config;
        this.deviceToIP = new HashMap<Device, String>();
        this.ipToDevice = new HashMap<String, Device>();

        this.deviceToMac = new HashMap<Device, String>();
        this.macToDevice = new HashMap<String, Device>();

        this.macIPmap = HashBiMap.create();

        this.macGen = new SerialMacGen();
        this.ipGen = new SerialIPGenerator();

        logger.debug("Hostmapper initialized, setting up Stuff...");
        setupStuff(ofSwitches);
        logger.debug("Arping set up!");
    }

    /**
     * Fills the MAPs with Items, so every ofSwitch with freePorts gets an
     * IP/MAC for a faked "HOST"
     *
     * @param ofSwitches the ofSwitches
     */
    public void setupStuff(List<IOFConnection> ofSwitches) {
        List<Device> devices = new ArrayList<Device>();
        for (IOFConnection ofSwitch : ofSwitches) {
            List<Short> freePorts = this.config.getTopology().getFreePorts(ofSwitch.getDpid());
            logger.info("Switch " + String.valueOf(ofSwitch.getDpid()) + "   free Ports: " + String.valueOf(freePorts.size()));
            for (short port : freePorts) {
                Device portSwitch = new Device(ofSwitch, port);
                byte[] macByte = this.macGen.getMac();
                String mac = HexString.toHexString(macByte);
                byte[] ipByte = this.ipGen.getIp();
                String ip = Util.fromIPv4Address(Util.toIPv4Address((ipByte)));
                devices.add(portSwitch);
                this.deviceToMac.put(portSwitch, mac);
                this.macToDevice.put(mac, portSwitch);

                this.deviceToIP.put(portSwitch, ip);
                this.ipToDevice.put(ip, portSwitch);

                this.macIPmap.put(mac, ip);
            }
        }

        logger.info("#Devices: " + devices.size());
        logger.trace("DeviceToIPMap: {}", deviceToIP.toString());
        logger.trace("IPtoDeviceMap: {}", ipToDevice.toString());
        logger.trace("DevicetoMacMap: {}", deviceToMac.toString());
        logger.trace("MacToDeviceMap: {}", macToDevice.toString());
        logger.trace("Mac<->IP BiMap: {} ", macIPmap.toString());
    }

    /**
     * Gets IP hanging on the Device
     *
     * @param device the Device
     * @return the IP
     */
    public String getIpToDevice(Device device) {
        return this.deviceToIP.get(device);
    }

    /**
     * Gets the Device on which IP is hanging on
     *
     * @param ip the IP
     * @return the Device
     */
    public Device getDeviceToIp(String ip) {
        return this.ipToDevice.get(ip);
    }

    /**
     * Gets the MAC hanging on the Device
     *
     * @param device the Device
     * @return the MAC
     */
    public String getMacToDevice(Device device) {
        return this.deviceToMac.get(device);
    }

    /**
     * Gets the Device on which MAC is hanging on
     *
     * @param mac the MAC
     * @return the Device
     */
    public Device getDeviceToMac(String mac) {
        return this.macToDevice.get(mac);
    }

    /**
     * Gets the Mac to a IP
     *
     * @param ip the IP
     * @return the Mac
     */
    public String getMacToIp(String ip) {
        return this.macIPmap.inverse().get(ip);
    }

    /**
     * Gets the IP to a MAC
     *
     * @param mac the MAC
     * @return the IP
     */
    public String getIpToMac(String mac) {
        return this.macIPmap.get(mac);
    }

    /**
     * Gets possible ARP Targets for a IP
     *
     * @param srcIP the IP
     * @return the Targets
     */
    public List<String> getTargetsForIP(String srcIP) {
        Set<String> targetIPs = new HashSet<>(this.macIPmap.inverse().keySet());
        targetIPs.remove(srcIP);

        return new ArrayList<>(targetIPs);
    }

}
