package team.lodestar.fufo.core.fluid;

import java.util.List;

/**
 * A PipeNode block entity class should implement this interface if it cares about which direction it's facing
 * or which way fluid is flowing through it. Pumps are the classic example of this.
 * @author ProfessorLucario
 *
 */
public interface SidedNode {

	public List<PipeNode> getConnectedNodes(FlowDir dir);
	
}