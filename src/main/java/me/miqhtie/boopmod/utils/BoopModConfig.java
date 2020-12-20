package me.miqhtie.boopmod.utils;


import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import sun.security.krb5.Config;

public class BoopModConfig {
    private Configuration config;

    public BoopModConfig(Configuration config) {
        this.config = config;
    }

    public void setModState(boolean enabled){
        Property prop = config.get(Configuration.CATEGORY_GENERAL, "state", true);
        prop.set(enabled);
        config.save();
    }

    public void setBoopBackEnabled(boolean enabled){
        Property prop = config.get(Configuration.CATEGORY_GENERAL, "bbenabled", true);
        prop.set(enabled);
        config.save();
    }

    public void setBoopJoinEnabled(boolean enabled){
        Property prop = config.get(Configuration.CATEGORY_GENERAL, "bjenabled", true);
        prop.set(enabled);
        config.save();
    }

    public void setHideBoopEnabled(boolean enabled){
        Property prop = config.get(Configuration.CATEGORY_GENERAL, "hbenabled", false);
        prop.set(enabled);
        config.save();
    }

    public boolean getModState(){
        return config.get(Configuration.CATEGORY_GENERAL, "state", true).getBoolean();
    }

    public boolean getBoopBackEnabled(){
        return config.get(Configuration.CATEGORY_GENERAL, "bbenabled", true).getBoolean();
    }

    public boolean getBoopJoinEnabled(){
        return config.get(Configuration.CATEGORY_GENERAL, "bjenabled", true).getBoolean();
    }

    public boolean getHideBoopEnabled(){
        return config.get(Configuration.CATEGORY_GENERAL, "hbenabled", true).getBoolean();
    }
}