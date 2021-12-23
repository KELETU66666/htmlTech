package net.htmlcsjs.htmlTech.laserpipe.net;

import net.htmlcsjs.htmlTech.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.laserpipe.TileEntityLaserPipe;
import net.minecraft.util.EnumFacing;

public class PipeLaserContainer implements ILaserContainer {

    private final TileEntityLaserPipe pipe;

    private long diodeVoltage;
    private long diodeAmperage;

    public PipeLaserContainer(TileEntityLaserPipe pipe) {
        this.pipe = pipe;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing enumFacing, long voltage, long amperage) {
        if (enumFacing == null || voltage <= 0 || amperage <= 0) return 0;
        ILaserContainer laserContainer = pipe.findLaserContainer(enumFacing);
        if (laserContainer != null)
            return laserContainer.acceptEnergyFromNetwork(enumFacing, voltage, amperage);
        return 0;
    }

    @Override
    public boolean inputsEnergy(EnumFacing enumFacing) {
        return false;
    }

    @Override
    public long changeEnergy(long l) {
        throw new UnsupportedOperationException("Don't use changeEnergy on laser pipes");
    }

    @Override
    public long getEnergyStored() {
        return 0;
    }

    @Override
    public long getEnergyCapacity() {
        return 0;
    }

    @Override
    public long getInputAmperage() {
        return 0;
    }

    @Override
    public long getInputVoltage() {
        return 0;
    }

    @Override
    public long getDiodeAmperage() {
        return diodeAmperage;
    }

    @Override
    public long getDiodeVoltage() {
        return diodeVoltage;
    }

    @Override
    public void setDiodeAmperage(long amperage) {
        diodeAmperage = amperage;
    }

    @Override
    public void setDiodeVoltage(long voltage) {
        diodeVoltage = voltage;
    }
}