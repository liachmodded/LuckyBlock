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


import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.util.Tristate;

/**
 * Created by liach on 1/28/2016.
 *
 * @author liach
 */
public interface LuckyBlockHandler {

  default Tristate isLuckyBlock(BlockState block) {
    return Tristate.UNDEFINED;
  }

  default Tristate canApplyDrop(ChangeBlockEvent event) {
    return Tristate.UNDEFINED;
  }

}
