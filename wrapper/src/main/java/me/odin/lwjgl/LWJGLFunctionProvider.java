package me.odin.lwjgl;

import org.lwjgl.system.FunctionProvider;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

public class LWJGLFunctionProvider implements FunctionProvider {
	private final Logger LOGGER = Logger.getLogger(LWJGLFunctionProvider.class.getName());
	private final Method getFunctionAddress;

	public LWJGLFunctionProvider() {
		try {
			Class<?> glContextClazz = Class.forName("org.lwjgl.opengl.GLContext");
			Method method = glContextClazz.getDeclaredMethod("getFunctionAddress", String.class);
			method.setAccessible(true);
			getFunctionAddress = method;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getFunctionAddress(CharSequence functionName) {
		try {
//            LOGGER.info(functionName.toString());

			long addr = (long) getFunctionAddress.invoke(null, functionName.toString());
//            LOGGER.info("fn " + functionName + " " + addr);

			return addr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getFunctionAddress(ByteBuffer functionName) {
		try {
//            LOGGER.info(functionName.toString());

			long addr = (long) getFunctionAddress.invoke(null, functionName.toString());
//            LOGGER.info("fn " + functionName + " " + addr);
			return addr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
