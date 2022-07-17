package com.sammy.fufo.helpers;

/**
 * This interface indicates that it is a valid target for the Debug Tool.
 * It should be removed prior to public release.
 * @author davidpowell
 * @see com.sammy.fufo.common.item.DebugTool
 */
public interface Debuggable {
	public String getDebugMessage(boolean sneak);
}
