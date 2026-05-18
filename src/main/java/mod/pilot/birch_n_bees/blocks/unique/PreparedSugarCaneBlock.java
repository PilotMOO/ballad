package mod.pilot.birch_n_bees.blocks.unique;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class PreparedSugarCaneBlock extends DirectionalBlock {
    public static final int MAX_PROGRESS = 20;
    public static final IntegerProperty PROGRESS = IntegerProperty.create("progress", 0, MAX_PROGRESS);
    public PreparedSugarCaneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(PROGRESS, 0));
    }

    public static final MapCodec<PreparedSugarCaneBlock> CODEC = simpleCodec(PreparedSugarCaneBlock::new);
    @Override
    protected @NotNull MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(PROGRESS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(PROGRESS) < MAX_PROGRESS;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isBrightOutside() && level.canSeeSky(pos)){
            int progress = state.getValue(PROGRESS);
            if (progress <= MAX_PROGRESS) {

            }
        }
    }
}
