package team.lodestar.fufo.core.fluid;

public interface PressureSource extends PipeNode {
	public PipeNode getConnection(FlowDir dir);
	
	public int getForce(FlowDir dir);
}
