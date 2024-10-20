package me.odin.lwjgl.impl;

import me.odin.lwjgl.NanoSVGImageWrapper;
import org.lwjgl.nanovg.NSVGImage;

public class NanoSVGImageWrapperImpl implements NanoSVGImageWrapper {
	public final NSVGImage image;

	public NanoSVGImageWrapperImpl(NSVGImage image) {
		this.image = image;
	}

	@Override
	public float height() {
		return this.image.height();
	}

	@Override
	public float width() {
		return this.image.width();
	}
}
