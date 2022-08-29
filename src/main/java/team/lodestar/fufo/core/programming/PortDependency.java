package team.lodestar.fufo.core.programming;

import java.util.UUID;

public class PortDependency {
    public UUID portId;
    public String portName;

    public PortDependency(UUID portId, String portName) {
        this.portId = portId;
        this.portName = portName;
    }
}
