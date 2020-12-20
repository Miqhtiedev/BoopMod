package me.miqhtie.boopmod;

import me.miqhtie.boopmod.commands.BoopModCommand;
import me.miqhtie.boopmod.commands.BoopWhitelistCommand;
import me.miqhtie.boopmod.events.ClientRecieveChatEvent;
import me.miqhtie.boopmod.events.ConnectToServerEvent;
import me.miqhtie.boopmod.events.DisconnectedFromServerEvent;
import me.miqhtie.boopmod.multithreading.Multithreading;
import me.miqhtie.boopmod.utils.BoopModConfig;
import me.miqhtie.boopmod.utils.WhitelistConfig;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Mod(
    modid = BoopMod.MODID,
    name = BoopMod.NAME,
    version = BoopMod.VERSION,
    clientSideOnly = true
)

public class BoopMod {
    public static final String MODID = "boopmod";
    public static final String NAME = "BoopMod";
    public static final String VERSION = "1.0";

    public static BoopMod instance;

    public WhitelistConfig whitelistConfig;
    public BoopModConfig boopModConfig;

    public Multithreading multithreading;

    public boolean isHypixel = false;

    private Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException, ClassNotFoundException {
        // Initialize Whitelist Configuration
        File file = new File(event.getModConfigurationDirectory().getPath() + "/boopwhitelistconfig.txt");
        whitelistConfig = new WhitelistConfig(event.getModConfigurationDirectory().getPath() + "/boopwhitelistconfig.txt");
        if(!file.exists()){
            file.createNewFile();
            whitelistConfig.save(new ArrayList<String>());
        }

        // Initialize Regular Config
        config = new Configuration(event.getSuggestedConfigurationFile());
        boopModConfig = new BoopModConfig(config);

        // Initialize Multithreading
        multithreading = new Multithreading();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        instance = this;

        // Register Events
        EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;
        EVENT_BUS.register(new ConnectToServerEvent());
        EVENT_BUS.register(new DisconnectedFromServerEvent());
        EVENT_BUS.register(new ClientRecieveChatEvent());

        // Register Commands
        ClientCommandHandler.instance.registerCommand(new BoopModCommand());
        ClientCommandHandler.instance.registerCommand(new BoopWhitelistCommand());
    }


}