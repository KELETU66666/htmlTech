package net.htmlcsjs.htmlTech.common.laserpipe.net;

import gregtech.api.pipenet.PipeNetWalker;
import gregtech.api.pipenet.tile.IPipeTile;
import net.htmlcsjs.htmlTech.api.capability.HtmlTechCapabilities;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;
import net.htmlcsjs.htmlTech.common.laserpipe.tile.TileEntityLaserPipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LaserNetWalker extends PipeNetWalker {
    private final List<LaserRoutePath> routes;
    private Set<TileEntityLaserPipe> pipes = new HashSet<>();

    protected LaserNetWalker(World world, BlockPos sourcePipe, int walkedBlocks, List<LaserRoutePath> routes) {
        super(world, sourcePipe, walkedBlocks);
        this.routes = routes;
    }

    public static List<LaserRoutePath> createNetData(World world, BlockPos sourcePipe) {
        LaserNetWalker walker = new LaserNetWalker(world, sourcePipe, 1, new ArrayList<>());
        walker.traversePipeNet();
        return walker.routes;
    }

    @Override
    protected PipeNetWalker createSubWalker(World world, BlockPos blockPos, int walkedBlocks) {
        LaserNetWalker walker = new LaserNetWalker(world, blockPos, walkedBlocks, routes);
        walker.pipes = new HashSet<>(pipes);
        return walker;
    }

    @Override
    protected void checkPipe(IPipeTile<?, ?> pipeTile, BlockPos blockPos) {
        pipes.add((TileEntityLaserPipe) pipeTile);
    }

    @Override
    protected void checkNeighbour(IPipeTile<?, ?> pipeTile, BlockPos blockPos, EnumFacing faceToNeighbour, @Nullable TileEntity neighbourTile) {
        if (neighbourTile != null) {
            ILaserContainer container = neighbourTile.getCapability(HtmlTechCapabilities.LASER_CONTAINER, faceToNeighbour.getOpposite());
            if (container != null) {
                routes.add(new LaserRoutePath(new BlockPos(blockPos), faceToNeighbour, new HashSet<>(pipes), getWalkedBlocks()));
            }
        }
    }

    @Override
    protected boolean isValidPipe(IPipeTile<?, ?> currentPipe, IPipeTile<?, ?> neighbourPipe, BlockPos blockPos, EnumFacing enumFacing) {
        return neighbourPipe instanceof TileEntityLaserPipe;
    }
}
