package me.odin.lwjgl.impl;

import me.odin.lwjgl.*;
import org.lwjgl.nanovg.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

public class Lwjgl3WrapperImpl implements Lwjgl3Wrapper {
	private static final Logger LOGGER = Logger.getLogger(Lwjgl3WrapperImpl.class.getName());

	private NanoVGPaintWrapperImpl impl(NanoVGPaintWrapper paint) {
		return (NanoVGPaintWrapperImpl) paint;
	}

	private NanoVGColorWrapperImpl impl(NanoVGColorWrapper color) {
		return (NanoVGColorWrapperImpl) color;
	}

	private NanoVGGLUFramebufferWrapperImpl impl(NanoVGGLUFramebufferWrapper framebuffer) {
		return (NanoVGGLUFramebufferWrapperImpl) framebuffer;
	}

	private NanoSVGImageWrapperImpl impl(NanoSVGImageWrapper image) {
		return (NanoSVGImageWrapperImpl) image;
	}

	@Override
	public long nvgCreate(int flags) {
		return NanoVGGL2.nvgCreate(flags);
	}

	@Override
	public void nvgBeginFrame(long vg, float width, float height, float devicePixelRatio) {
		NanoVG.nvgBeginFrame(vg, width, height, devicePixelRatio);
	}

	@Override
	public void nvgTextAlign(long vg, int align) {
		NanoVG.nvgTextAlign(vg, align);
	}

	@Override
	public void nvgEndFrame(long vg) {
		NanoVG.nvgEndFrame(vg);
	}

	@Override
	public NanoVGGLUFramebufferWrapper nvgluCreateFramebuffer(long vg, int width, int height, int imageFlags) {
		return new NanoVGGLUFramebufferWrapperImpl(NanoVGGL2.nvgluCreateFramebuffer(vg, width, height, imageFlags));
	}

	@Override
	public void nvgImagePattern(long ctx, float ox, float oy, float ex, float ey, float angle, int image, float alpha, NanoVGPaintWrapper result) {
		NanoVG.nvgImagePattern(ctx, ox, oy, ex, ey, angle, image, alpha, impl(result).paint);
	}
 
	@Override
	public void nvgBeginPath(long ctx) {
		NanoVG.nvgBeginPath(ctx);
	}

	@Override
	public void nvgRect(long ctx, float x, float y, float w, float h) {
		NanoVG.nvgRect(ctx, x, y, w, h);
	}

	@Override
	public void nvgFillPaint(long ctx, NanoVGPaintWrapper paint) {
		NanoVG.nvgFillPaint(ctx, impl(paint).paint);
	}

	@Override
	public void nvgFill(long ctx) {
		NanoVG.nvgFill(ctx);
	}

	@Override
	public void nvgClosePath(long ctx) {
		NanoVG.nvgClosePath(ctx);
	}

	@Override
	public void nvgluBindFramebuffer(long ctx, NanoVGGLUFramebufferWrapper framebuffer) {
		NanoVGGL2.nvgluBindFramebuffer(ctx, impl(framebuffer).framebuffer);
	}

	@Override
	public void nvgluDeleteFramebuffer(long ctx, NanoVGGLUFramebufferWrapper framebuffer) {
		NanoVGGL2.nvgluDeleteFramebuffer(ctx, impl(framebuffer).framebuffer);
	}

	@Override
	public void nvgSave(long ctx) {
		NanoVG.nvgSave(ctx);
	}

	@Override
	public void nvgRestore(long ctx) {
		NanoVG.nvgRestore(ctx);
	}

	@Override
	public void nvgScale(long ctx, float x, float y) {
		NanoVG.nvgScale(ctx, x, y);
	}

	@Override
	public void nvgTranslate(long ctx, float x, float y) {
		NanoVG.nvgTranslate(ctx, x, y);
	}

	@Override
	public void nvgRotate(long ctx, float angle) {
		NanoVG.nvgRotate(ctx, angle);
	}

	@Override
	public void nvgGlobalAlpha(long ctx, float alpha) {
		NanoVG.nvgGlobalAlpha(ctx, alpha);
	}

	@Override
	public void nvgScissor(long ctx, float x, float y, float w, float h) {
		NanoVG.nvgScissor(ctx, x, y, w, h);
	}

	@Override
	public void nvgResetScissor(long ctx) {
		NanoVG.nvgResetScissor(ctx);
	}

	@Override
	public void nvgMoveTo(long ctx, float x, float y) {
		NanoVG.nvgMoveTo(ctx, x, y);
	}

	@Override
	public void nvgLineTo(long ctx, float x, float y) {
		NanoVG.nvgLineTo(ctx, x, y);
	}

	@Override
	public void nvgStrokeWidth(long ctx, float size) {
		NanoVG.nvgStrokeWidth(ctx, size);
	}

	@Override
	public void nvgStroke(long ctx) {
		NanoVG.nvgStroke(ctx);
	}

	@Override
	public void nvgRoundedRectVarying(long ctx, float x, float y, float w, float h, float radTopLeft, float radTopRight, float radBottomRight, float radBottomLeft) {
		NanoVG.nvgRoundedRectVarying(ctx, x, y, w, h, radTopLeft, radTopRight, radBottomRight, radBottomLeft);
	}

	@Override
	public void nvgFillColor(long ctx, NanoVGColorWrapper color) {
		NanoVG.nvgFillColor(ctx, impl(color).color);
	}

	@Override
	public void nvgPathWinding(long ctx, int dir) {
		NanoVG.nvgPathWinding(ctx, dir);
	}

	@Override
	public void nvgFontSize(long ctx, float size) {
		NanoVG.nvgFontSize(ctx, size);
	}

	@Override
	public void nvgFontFaceId(long ctx, int font) {
		NanoVG.nvgFontFaceId(ctx, font);
	}

	@Override
	public void nvgFontBlur(long ctx, float blur) {
		NanoVG.nvgFontBlur(ctx, blur);
	}

	@Override
	public float nvgText(long ctx, float x, float y, CharSequence string) {
		return NanoVG.nvgText(ctx, x, y, string);
	}

	@Override
	public float nvgTextBounds(long ctx, float x, float y, CharSequence string, float[] bounds) {
		return NanoVG.nvgTextBounds(ctx, x, y, string, bounds);
	}

	@Override
	public void nvgDeleteImage(long ctx, int image) {
		NanoVG.nvgDeleteImage(ctx, image);
	}

	@Override
	public int nvgCreateImageRGBA(long ctx, int w, int h, int imageFlags, ByteBuffer data) {
		return NanoVG.nvgCreateImageRGBA(ctx, w, h, imageFlags, data);
	}

	@Override
	public NanoVGColorWrapper nvgRGBA(byte r, byte g, byte b, byte a, NanoVGColorWrapper result) {
		NanoVG.nvgRGBA(r, g, b, a, impl(result).color);
		return result;
	}

	@Override
	public NanoVGPaintWrapper nvgLinearGradient(long ctx, float sx, float sy, float ex, float ey, NanoVGColorWrapper icol, NanoVGColorWrapper ocol, NanoVGPaintWrapper result) {
		NanoVG.nvgLinearGradient(ctx, sx, sy, ex, ey, impl(icol).color, impl(ocol).color, impl(result).paint);
		return result;
	}

	@Override
	public int nvgCreateFontMem(long ctx, CharSequence name, ByteBuffer data, int freeData) {
		return NanoVG.nvgCreateFontMem(ctx, name, data, freeData);
	}

	@Override
	public void nvgStrokeColor(long ctx, NanoVGColorWrapper color) {
		NanoVG.nvgStrokeColor(ctx, impl(color).color);
	}

	@Override
	public void nvgBoxGradient(long ctx, float x, float y, float w, float h, float r, float f, NanoVGColorWrapper icol, NanoVGColorWrapper ocol, NanoVGPaintWrapper __result) {
		NanoVG.nvgBoxGradient(ctx, x, y, w, h, r, f, impl(icol).color, impl(ocol).color, impl(__result).paint);
	}

	@Override
	public void nvgRoundedRect(long ctx, float x, float y, float w, float h, float r) {
		NanoVG.nvgRoundedRect(ctx, x, y, w, h, r);
	}

    @Override
    public void nvgCircle(long ctx, float x, float y, float r) {
        NanoVG.nvgCircle(ctx, x, y, r);
    }

    @Override
    public void nvgArcTo(long ctx, float x1, float y1, float x2, float y2, float radius) {
        NanoVG.nvgArcTo(ctx, x1, y1, x2, y2, radius);
    }

    @Override
    public void nvgTextLineHeight(long ctx, float lineHeight) {
        NanoVG.nvgTextLineHeight(ctx, lineHeight);
    }

    @Override
    public void nvgTextBox(long ctx, float x, float y, float breakRowWidth, CharSequence string) {
        NanoVG.nvgTextBox(ctx, x, y, breakRowWidth, string);
    }

    @Override
    public void nvgTextBoxBounds(long ctx, float x, float y, float breakRowWidth, CharSequence string, float[] bounds) {
        NanoVG.nvgTextBoxBounds(ctx, x, y, breakRowWidth, string, bounds);
    }

	@Override
	public NanoVGPaintWrapper createPaint() {
		return new NanoVGPaintWrapperImpl(NVGPaint.malloc());
	}

	@Override
	public NanoVGColorWrapper createColor() {
		return new NanoVGColorWrapperImpl(NVGColor.malloc());
	}

	@Override
	public NanoSVGImageWrapper nsvgParse(CharSequence input, CharSequence units, float dpi) {
		return new NanoSVGImageWrapperImpl(NanoSVG.nsvgParse(input, units, dpi));
	}

	@Override
	public long nsvgCreateRasterizer() {
		return NanoSVG.nsvgCreateRasterizer();
	}

	@Override
	public void nsvgRasterize(long r, NanoSVGImageWrapper image, float tx, float ty, float scale, ByteBuffer dst, int w, int h, int stride) {
		NanoSVG.nsvgRasterize(r, impl(image).image, tx, ty, scale, dst, w, h, stride);
	}

	@Override
	public void nsvgDeleteRasterizer(long rasterizer) {
		NanoSVG.nsvgDeleteRasterizer(rasterizer);
	}

	@Override
	public void nsvgDelete(NanoSVGImageWrapper image) {
		NanoSVG.nsvgDelete(impl(image).image);
	}

	@Override
	public ByteBuffer stbi_load_from_memory(@NativeType("stbi_uc const *") ByteBuffer buffer, @NativeType("int *") int[] x, @NativeType("int *") int[] y, @NativeType("int *") int[] channels_in_file, int desired_channels) {
		return STBImage.stbi_load_from_memory(buffer, x, y, channels_in_file, desired_channels);
	}

	@Override
	public ByteBuffer memAlloc(int size) {
		return MemoryUtil.memAlloc(size);
	}
}
