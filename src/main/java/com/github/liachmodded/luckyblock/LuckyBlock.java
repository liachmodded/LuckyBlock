/**
 * This file is part of LuckyBlock, licensed under the MIT License (MIT).
 * <p>
 * Copyright (c) liachmodded <http://github.com/liachmodded>
 * Copyright (c) contributors
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.liachmodded.luckyblock;

import com.google.inject.Inject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.nio.file.Path;

@Plugin(
    id = LuckyBlock.ID,
    name = LuckyBlock.NAME,
    version = LuckyBlock.VERSION,
    dependencies = "required-after:luckyblockapi"
)
public final class LuckyBlock {

  public static final String ID = "luckyblock";
  public static final String NAME = "LuckyBlock";
  public static final String VERSION = "0.1-SNAPSHOT";

  public static final String CONFIG_FILE = LuckyBlock.NAME + ".json";

  @Inject private Game game;

  @Inject private GameRegistry registry;

  @Inject @DefaultConfig(sharedRoot = false) private File configDir;
  private File configFile;

  private static LuckyBlock instance;

  public LuckyBlock() {
    assert LuckyBlock.instance == null : "LuckyBlock has already initialized";
    LuckyBlock.instance = this;
  }

  public LuckyBlock getInstance() {
    assert LuckyBlock.instance == null : "LuckyBlock is not yet initialized";
    return LuckyBlock.instance;
  }

  @Listener public void onPreInit(GamePreInitializationEvent event) {
    this.configFile = new File(configDir, LuckyBlock.CONFIG_FILE);
  }

  @Listener public void onInit(GameInitializationEvent event) {
    this.game.getServiceManager();

    this.loadConfig();
  }

  private void loadConfig() {
    GsonConfigurationLoader configLoader =
        GsonConfigurationLoader.builder().setFile(configFile).build();
    ConfigurationNode configNode = configLoader.createEmptyNode();
    configNode.getOptions().setHeader(
        "LuckyBlock Configuration"
    );
  }
}
