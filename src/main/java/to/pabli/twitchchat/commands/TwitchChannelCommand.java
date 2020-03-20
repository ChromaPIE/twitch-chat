package to.pabli.twitchchat.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.LiteralText;
import to.pabli.twitchchat.TwitchChatMod;
import to.pabli.twitchchat.config.ModConfig;
import to.pabli.twitchchat.config.ModMenuCompat;

public class TwitchChannelCommand {
  public static LiteralArgumentBuilder<CottonClientCommandSource> getArgumentBuilder() {
    return ArgumentBuilders.literal("channel")
        // The command to be executed if the command "twitch" is entered with the argument "channel"
        // It requires channel_name as an argument.
        // It will switch channels in the config to the channel name provided and
        // if the bot is connected to some channel, it will switch channels on the fly.
        .then(ArgumentBuilders.argument("channel_name", StringArgumentType.string())
            .executes(ctx -> {
              String channelName = StringArgumentType.getString(ctx, "channel_name");

              ModConfig.getConfig().setChannel(channelName);
              // Also switch channels if the bot has been initialized
              if (TwitchChatMod.bot != null) {
                ctx.getSource().sendFeedback(new LiteralText("Switching channels to '" + channelName + "'."));
                TwitchChatMod.bot.joinChannel(channelName);
              } else {
                ctx.getSource().sendFeedback(new LiteralText("The mod will connect to '" + channelName + "' as soon as you enable it."));
              }
              ModConfig.getConfig().save();
              return 1;
        }));
  }
}
