package me.odin.lwjgl;

import java.nio.ByteBuffer;

public interface Lwjgl3Wrapper {
	// NANOVG

	long nvgCreate(int flags);

	void nvgBeginFrame(long ctx, float width, float height, float devicePixelRatio);

	void nvgTextAlign(long ctx, int align);

	void nvgEndFrame(long ctx);

	NanoVGGLUFramebufferWrapper nvgluCreateFramebuffer(long vg, int width, int height, int imageFlags);

	void nvgImagePattern(long ctx, float ox, float oy, float ex, float ey, float angle, int image, float alpha, NanoVGPaintWrapper result);

	void nvgBeginPath(long ctx);

	void nvgRect(long ctx, float x, float y, float w, float h);

	void nvgFillPaint(long ctx, NanoVGPaintWrapper paint);

	void nvgFill(long ctx);

	void nvgClosePath(long ctx);

	void nvgluBindFramebuffer(long ctx, NanoVGGLUFramebufferWrapper framebuffer);

	void nvgluDeleteFramebuffer(long ctx, NanoVGGLUFramebufferWrapper framebuffer);

	void nvgSave(long ctx);

	void nvgRestore(long ctx);

	void nvgScale(long ctx, float x, float y);

	void nvgTranslate(long ctx, float x, float y);

	void nvgRotate(long ctx, float angle);

	void nvgGlobalAlpha(long ctx, float alpha);

	void nvgScissor(long ctx, float x, float y, float w, float h);

	void nvgResetScissor(long ctx);

	void nvgMoveTo(long ctx, float x, float y);

	void nvgLineTo(long ctx, float x, float y);

	void nvgStrokeWidth(long ctx, float size);

	void nvgStroke(long ctx);

	void nvgRoundedRectVarying(long ctx, float x, float y, float w, float h, float radTopLeft, float radTopRight, float radBottomRight, float radBottomLeft);

	void nvgFillColor(long ctx, NanoVGColorWrapper color);

	void nvgPathWinding(long ctx, int dir);

	void nvgFontSize(long ctx, float size);

	void nvgFontFaceId(long ctx, int font);

	void nvgFontBlur(long ctx, float blur);

	float nvgText(long ctx, float x, float y, CharSequence string);

	float nvgTextBounds(long ctx, float x, float y, CharSequence string, float[] bounds);

	void nvgDeleteImage(long ctx, int image);

	int nvgCreateImageRGBA(long ctx, int w, int h, int imageFlags, ByteBuffer data);

	NanoVGColorWrapper nvgRGBA(byte r, byte g, byte b, byte a, NanoVGColorWrapper result);

	NanoVGPaintWrapper nvgLinearGradient(long ctx, float sx, float sy, float ex, float ey, NanoVGColorWrapper icol, NanoVGColorWrapper ocol, NanoVGPaintWrapper result);

	int nvgCreateFontMem(long ctx, CharSequence name, ByteBuffer data, int freeData);

	void nvgStrokeColor(long ctx, NanoVGColorWrapper color);

	void nvgBoxGradient(long ctx, float x, float y, float w, float h, float r, float f, NanoVGColorWrapper icol, NanoVGColorWrapper ocol, NanoVGPaintWrapper __result);

	void nvgRoundedRect(long ctx, float x, float y, float w, float h, float r);

    void nvgCircle(long ctx, float cx, float cy, float r);

    void nvgArcTo(long ctx, float x1, float y1, float x2, float y2, float radius);

    void nvgTextLineHeight(long ctx, float lineHeight);

    void nvgTextBox(long ctx, float x, float y, float breakRowWidth, CharSequence string);

    void nvgTextBoxBounds(long ctx, float x, float y, float breakRowWidth, CharSequence string, float[] bounds);

	NanoVGPaintWrapper createPaint();

	NanoVGColorWrapper createColor();

	int NVG_ANTIALIAS = 1;
	int NVG_STENCIL_STROKES = 2;
	int NVG_DEBUG = 4;
	int NVG_IMAGE_NODELETE = 65536;

	int NVG_ALIGN_LEFT = 1;
	int NVG_ALIGN_CENTER = 2;
	int NVG_ALIGN_RIGHT = 4;
	int NVG_ALIGN_TOP = 8;

	int NVG_ALIGN_MIDDLE = 16;
	int NVG_ALIGN_BOTTOM = 32;
	int NVG_ALIGN_BASELINE = 64;

	int
			NVG_SOLID = 0x1,
			NVG_HOLE = 0x2;

	// NANOSVG
	NanoSVGImageWrapper nsvgParse(CharSequence input, CharSequence units, float dpi);

	long nsvgCreateRasterizer();

	void nsvgRasterize(long r, NanoSVGImageWrapper image, float tx, float ty, float scale, ByteBuffer dst, int w, int h, int stride);

	void nsvgDeleteRasterizer(long rasterizer);

	void nsvgDelete(NanoSVGImageWrapper image);

	// STBI
	ByteBuffer stbi_load_from_memory(ByteBuffer buffer, int[] x, int[] y, int[] channels_in_file, int desired_channels);

	// MEMORY
	ByteBuffer memAlloc(int size);
}