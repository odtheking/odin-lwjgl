package me.odin.lwjgl.impl;

import me.odin.lwjgl.NanoVGGLUFramebufferWrapper;
import org.lwjgl.nanovg.NVGLUFramebuffer;

public class NanoVGGLUFramebufferWrapperImpl implements NanoVGGLUFramebufferWrapper {
	public final NVGLUFramebuffer framebuffer;

	public NanoVGGLUFramebufferWrapperImpl(NVGLUFramebuffer framebuffer) {
		this.framebuffer = framebuffer;
	}

	public int image() {
		return this.framebuffer.image();
	}
}
