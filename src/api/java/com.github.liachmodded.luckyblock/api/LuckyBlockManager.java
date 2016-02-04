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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.util.Tristate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liach on 1/28/2016.
 *
 * @author liach
 */
public abstract class LuckyBlockManager {

  protected List<LuckyBlockHandler> checkers;
  protected ReentrantLock checkerLock;

  protected LuckyBlockManager(Object plugin) {
    assert Sponge.getPluginManager().fromInstance(plugin).isPresent()
        : "invalid plugin instance";
    checkers = Sponge.getServiceManager().isRegistered(LuckyBlockManager.class)
        ? Lists.newArrayList(Sponge.getServiceManager().provide(LuckyBlockManager.class)
            .get().checkers)
        : new ArrayList<>();
    checkerLock = new ReentrantLock();
    Sponge.getServiceManager().setProvider(plugin, LuckyBlockManager.class, this);
  }

  public static LuckyBlockManager getInstance() {
    assert Sponge.getServiceManager().isRegistered(LuckyBlockManager.class)
        : "No lucky block manager available";
    return Sponge.getServiceManager().provide(LuckyBlockManager.class).get();
  }

  public void addHandler(LuckyBlockHandler newHandler) {
    checkerLock.lock();
    try {
      for (int i = 0, n = checkers.size(); i < n; i++) {
        if (checkers.get(i) == newHandler) {
          checkers.remove(i);
          break;
        }
      }
      checkers.add(newHandler);
    } finally {
      checkerLock.unlock();
    }
  }

  public void removeHandler(LuckyBlockHandler oldHandler) {
    checkerLock.lock();
    try {
      for (int i = 0, n = checkers.size(); i < n; i++) {
        if (checkers.get(i) == oldHandler) {
          checkers.remove(i);
          break;
        }
      }
    } finally {
      checkerLock.unlock();
    }
  }

  public boolean checkLuckyBlock(BlockState state) {
    checkerLock.lock();
    boolean result = false;
    try {
      for (int i = 0, n = checkers.size(); i < n; i++) {
        Tristate tristate = checkers.get(i).isLuckyBlock(state);
        if (tristate == Tristate.FALSE) {
          result = false;
        }
        if (tristate == Tristate.TRUE) {
          result = true;
        }
      }
    } finally {
      checkerLock.unlock();
    }
    return result;
  }

  public boolean checkDrop(ChangeBlockEvent event) {
    checkerLock.lock();
    boolean result = false;
    try {
      for (int i = 0, n = checkers.size(); i < n; i++) {
        Tristate tristate = checkers.get(i).canApplyDrop(event);
        if (tristate == Tristate.FALSE) {
          result = false;
        }
        if (tristate == Tristate.TRUE) {
          result = true;
        }
      }
    } finally {
      checkerLock.unlock();
    }
    return result;
  }

  public List<LuckyBlockHandler> getHandlers() {
    return ImmutableList.copyOf(checkers);
  }
}
