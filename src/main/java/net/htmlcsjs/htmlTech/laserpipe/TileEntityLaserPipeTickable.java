package net.htmlcsjs.htmlTech.laserpipe;

import net.minecraft.util.ITickable;

public class TileEntityLaserPipeTickable extends TileEntityLaserPipe implements ITickable {

    @Override
    public boolean supportsTicking() {
        return true;
    }

    @Override
    public void update() {
        getCoverableImplementation().update();
    }
}