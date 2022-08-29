package team.lodestar.fufo.unsorted.util;

import team.lodestar.fufo.common.item.DebugTool;

/**
 * This interface indicates that it is a valid target for the Debug Tool.
 * It should be removed prior to public release.
 * @author davidpowell
 * @see DebugTool
 */
public interface Debuggable {
	public String getDebugMessage(boolean sneak);
}
