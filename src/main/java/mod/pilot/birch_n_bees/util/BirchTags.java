package mod.pilot.birch_n_bees.util;

import mod.pilot.birch_n_bees.ABalladofBirchandBees;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BirchTags {
    public static class Blocks{
        private static TagKey<Block> tag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(ABalladofBirchandBees.MOD_ID, name));
        }
    }

    public static class Items{
        private static TagKey<Item> tag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(ABalladofBirchandBees.MOD_ID, name));
        }

        public static final TagKey<Item> HONEYCOMB_CRAFTING = tag("honey_crafting");
        public static final TagKey<Item> ROCK_CHIPPING = tag("rock_chipping");
        public static final TagKey<Item> ROCK_TIER_1 = tag("rock_tier_1");
        public static final TagKey<Item> ROCK_TIER_2 = tag("rock_tier_2");
        public static final TagKey<Item> ROCK_TIER_3 = tag("rock_tier_3");

        public static final TagKey<Item> COBBLE_TIER_1 = tag("cobble_tier_1");
        public static final TagKey<Item> COBBLE_TIER_2 = tag("cobble_tier_2");
        public static final TagKey<Item> COBBLE_TIER_3 = tag("cobble_tier_3");
    }
}
