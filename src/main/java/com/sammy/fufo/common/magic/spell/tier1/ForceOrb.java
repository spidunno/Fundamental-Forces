package com.sammy.fufo.common.magic.spell.tier1;

import com.sammy.fufo.common.magic.BlockSpell;
import com.sammy.fufo.core.registratation.BlockRegistrate;
//TODO this code currently does not work with registrate. why is it referencing blocks though, anyways?
public class ForceOrb extends BlockSpell {
    public ForceOrb() {
        super("force_orb", BlockRegistrate.FORCE_ORB::get);
    }
};
