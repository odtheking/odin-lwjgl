package me.odin.lwjgl.impl;

import me.odin.lwjgl.NanoVGPaintWrapper;
import org.lwjgl.nanovg.NVGPaint;

public class NanoVGPaintWrapperImpl implements NanoVGPaintWrapper {
	public final NVGPaint paint;

	public NanoVGPaintWrapperImpl(NVGPaint paint) {
		this.paint = paint;
	}
}
