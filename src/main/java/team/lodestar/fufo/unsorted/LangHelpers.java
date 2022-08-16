package team.lodestar.fufo.unsorted;

import static team.lodestar.fufo.FufoMod.FUFO;

public class LangHelpers {

    public static String getOption(String option) {
        return "options." + FUFO + "." + option;
    }

    public static String getKey(String key) {
        return "key." + FUFO + "." + key;
    }

    public static String getOptionTooltip(String option) {
        return "options." + FUFO + "." + option + ".tooltip";
    }

    public static String getCommand(String command) {
        return "command." + FUFO + "." + command;
    }

    public static String getCommandOutput(String output) {
        return "command." + FUFO + "." + output;
    }
}
