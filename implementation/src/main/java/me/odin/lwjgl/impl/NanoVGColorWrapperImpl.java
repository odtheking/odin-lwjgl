package me.odin.lwjgl.impl;

import me.odin.lwjgl.NanoVGColorWrapper;
import org.lwjgl.nanovg.NVGColor;

public class NanoVGColorWrapperImpl implements NanoVGColorWrapper {
	public final NVGColor color;

	public NanoVGColorWrapperImpl(NVGColor color) {
		this.color = color;
	}
}
