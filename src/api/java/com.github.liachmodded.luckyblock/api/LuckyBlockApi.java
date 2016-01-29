/*
 * This file is part of LuckyBlock, licensed under MIT License (MIT).
 *
 * Copyright (c) 2015 liachmodded <http://github.com/liachmodded>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.liachmodded.luckyblock.api;

import com.google.inject.Inject;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

/**
 * Created by liach on 1/28/2016.
 *
 * @author liach
 */
@Plugin(id = LuckyBlockApi.ID, name = LuckyBlockApi.NAME, version = LuckyBlockApi.VERSION)
public class LuckyBlockApi {

  public static final String ID = "luckyblock";
  public static final String NAME = "LuckyBlock";
  public static final String VERSION = "0.1-SNAPSHOT";

  private static LuckyBlockApi instance;

  @Inject
  private Game game;

  public static LuckyBlockApi getInstance() {
    if (instance == null) {
      throw new RuntimeException("Lucky Block API has not initialized.");
    }
    return LuckyBlockApi.instance;
  }

  @Listener
  public void onConstruction(GameConstructionEvent event) {
    instance = this;
  }

  @Listener
  public void init(GameInitializationEvent event) {
  }

  public LuckyBlockApi() {
    instance = this;
  }

}
