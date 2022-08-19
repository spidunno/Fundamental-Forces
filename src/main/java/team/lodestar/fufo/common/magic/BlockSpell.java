package team.lodestar.fufo.common.magic;

//public class BlockSpell extends SpellType {
//    public final Supplier<Block> blockSupplier;
//
//    public BlockSpell(String id, Supplier<Block> blockSupplier) {
//        super(id);
//        System.out.println(blockSupplier.toString());
//        this.blockSupplier = blockSupplier;
//    }
//
//    @Override
//    public void castBlock(SpellInstance instance, ServerPlayer player, BlockPos pos, BlockHitResult hitVec) {
//        instance.cooldown = new SpellCooldown(100);
////        PlayerDataCapability.getCapability(player).ifPresent(c -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new  ResetRightClickDelayPacket()));
//        player.level.setBlockAndUpdate(pos.relative(hitVec.getDirection()), blockSupplier.get().defaultBlockState());
//        player.swing(InteractionHand.MAIN_HAND, true);
//    }
//}