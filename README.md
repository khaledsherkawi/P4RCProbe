#P4RCProbe
![P4RCProbe]

We present the platform-independent and flexible SDN Controller Analysis Tool P4RCProbe, an extension of OFCProce, on this website. It features 
a scalable and modular architecture that allows a granular and deep analysis of the controllers behaviors and 
characteristics. OpenFlow and P4Runtime controllers can be pushed to their limit and the bottlenecks can be investigated.
The tool allows the emulation of virtual switches that each provide sophisticated statistics about the controller 
behavior.

## Table of Contents
- [Tutorial](#tut)
  - [Requirements](#req)
  - [Preperations](#prep)
  - [Simulation Sequence](#simseq)
  - [Topology Testing](#topotest)
  - [Random IAT Values after Distribution](#radomdistri)
  - [Individual Settings for Switches](#indipcap)
- [Statistics Content](#statcontent)


## <a name="building"></a>Building OFCProbe

- Download and install Maven
- Make sure that your Maven setup allows new repositories
- Execute `mvn package` to download dependencies and compile OFCProbe.
- The result can be found in `target/p4rcprobe-*.one-jar.jar`.
- Command: `java -jar target/p4rcprobe-*.one-jar.jar demo.ini`

## <a name="tut"></a>Tutorial

### <a name="req"></a>Requirements:

- Java 7
- OpenFlow or P4Runtime (Only ONOS) Controller
- Preferably a Linux System (Shell Scripts + Screen Usage in Scripts provided)
- Passwordless SSH-Connection between OF Controller Host and P4RCProbe Host for User openflow
- Optional: SNMP Server on Controller Host (for CPU and RAM Utilization of OF Controller Host)


### <a name="prep"></a>Preperations:

see [PREPERATIONS](https://github.com/lsinfo3/ofcprobe/blob/master/PREPERATIONS.md)

### <a name="simseq"></a>Simulation Sequence

see [BEST_EFFORT](https://github.com/lsinfo3/ofcprobe/blob/master/BEST_EFFORT.md)


### <a name="topotest"></a>Topology Testing

see [TOPOLOGY_EMU](https://github.com/lsinfo3/ofcprobe/blob/master/TOPOLOGY_EMU.md)

#### <a name="randomdistri"></a>Random IAT Values after Distribution

Example for NormalDistribution, mean = 10, stdev=5:

* trafficGenConfig.iatType = 1
* trafficGenConfig.iatDistribution = Normal
* trafficGenConfig.iatDistributionParamter1 = 10
* trafficGenConfig.iatDistributionParamter2 = 5

#### <a name="indipcap"></a>Individual Settings for Switches and PCAP File Playback

* Set config.checkForIndividualSwitchSettings = true in config.ini on OFCProbeHost
* Check ofSwitch.ini and change it
* Allows to change IAT, fillThreshold, start- and stopDelay, PCAP file and Distribution Settings
* If pcapFile Options is set, this specific ofSwitch will only send OF_PACKET_INs with Payloads sequentially taken from that PCAP File (File will be looped)


### <a name="statcontent"></a>Statistics Content

see [STATISTICS_CONTENT](https://github.com/lsinfo3/ofcprobe/blob/master/STATISTICS_CONTENT.md)
