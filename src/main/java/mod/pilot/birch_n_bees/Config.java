package mod.pilot.birch_n_bees;

import com.google.common.collect.Lists;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class Config
{
    public static final Server SERVER;
    public static final ModConfigSpec SERVER_SPEC;
    private static final Logger log = LoggerFactory.getLogger(Config.class);

    public static class Server{
        public final ModConfigSpec.ConfigValue<Double> BirchChance;

        public final ModConfigSpec.ConfigValue<Integer> BrickCookTime;

        public Server(ModConfigSpec.Builder builder){
            builder.push("Ballad Config");

            builder.push("World generation settings");
            BirchChance = builder.defineInRange("Chance for a biome to be replaced with a birch biome: ", 0.95, 0, 1);
            builder.pop();

            builder.push("Misc");
            BrickCookTime = builder.defineInRange("How long it takes for clay bricks to cook", 1000, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.pop();
        }
    }

    static {
        Pair<Server, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER = commonSpecPair.getLeft();
        SERVER_SPEC = commonSpecPair.getRight();
    }
}
