package me.miqhtie.boopmod.utils;


import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BoopModConfig {
    private final Configuration config;

    public BoopModConfig(Configuration config) {
        this.config = config;
    }

    public boolean getModState() {
        return config.get(Configuration.CATEGORY_GENERAL, "state", true).getBoolean();
    }

    public void setModState(boolean enabled) {
        Property prop = config.get(Configuration.CATEGORY_GENERAL, "state", true);
        prop.set(enabled);
        config.save();
    }

    public boolean getBoopBackEnabled() {
        return config.get(Configuration.CATEGORY_GENERAL, "bbenabled", true).getBoolean();
    }

    public void setBoopBackEnabled(boolean enabled) {
        Property prop = config.get(Configuration.CATEGORY_GENERAL, "bbenabled", true);
        prop.set(enabled);
        config.save();
    }

    public boolean getBoopJoinEnabled() {
        return config.get(Configuration.CATEGORY_GENERAL, "bjenabled", true).getBoolean();
    }

    public void setBoopJoinEnabled(boolean enabled) {
        Property prop = config.get(Configuration.CATEGORY_GENERAL, "bjenabled", true);
        prop.set(enabled);
        config.save();
    }

    public boolean getHideBoopEnabled() {
        return config.get(Configuration.CATEGORY_GENERAL, "hbenabled", true).getBoolean();
    }

    public void setHideBoopEnabled(boolean enabled) {
        Property prop = config.get(Configuration.CATEGORY_GENERAL, "hbenabled", false);
        prop.set(enabled);
        config.save();
    }
}