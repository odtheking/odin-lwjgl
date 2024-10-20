package me.odin.lwjgl;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Lwjgl3Loader {
	private static final AtomicBoolean loaded = new AtomicBoolean(false);
	private static Lwjgl3Wrapper wrapper;
	private static final Logger LOGGER = Logger.getLogger(Lwjgl3Loader.class.getName());
	private static final Method addUrlMethod = getAddUrlMethod();

	private static final Object lock = new Object();

	private static final String ODIN_JAR = "/odin-lwjgl.jar";
	private static final String ONECONFIG_JAR = "/oneconfig-lwjgl.jar";

	private static URL getJarURL(String name) {
		try {
			File file = new File("odin" + name);
			if (file.exists()) {
				file.delete();
			}

			LOGGER.info("Writing " + file.getAbsolutePath());

			file.mkdirs();
			file.createNewFile();
			file.deleteOnExit();
			InputStream s = Lwjgl3Loader.class.getResourceAsStream(name);
			Files.copy(s, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

			return file.toURI().toURL();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Method getAddUrlMethod() {
		try {
			Class<URLClassLoader> clazz = URLClassLoader.class;
			Method method = clazz.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Map<String, byte[]> readZip(InputStream in) {
		Map<String, byte[]> map = new HashMap<>();

		try (ZipInputStream stream = new ZipInputStream(in)) {
			ZipEntry entry = stream.getNextEntry();

			while (entry != null) {
				if (entry.isDirectory()) {
					entry = stream.getNextEntry();
					continue;
				}
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				byte[] buf = new byte[2048];
				int len = stream.read(buf);
				while (len > 0) {
					outputStream.write(buf, 0, len);
					len = stream.read(buf);
				}
				map.put(entry.getName(), outputStream.toByteArray());
				entry = stream.getNextEntry();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return map;
	}

	/**
	 * Impl classes for oneconfig loading, order might matter
	 */
	private static final String[] CLASS_NAMES = {
			"org.lwjgl.system.odin.NanoSVGImageWrapperImpl",
			"org.lwjgl.system.odin.NanoVGColorWrapperImpl",
			"org.lwjgl.system.odin.NanoVGGLUFramebufferWrapperImpl",
			"org.lwjgl.system.odin.NanoVGPaintWrapperImpl",
			"org.lwjgl.system.odin.Lwjgl3WrapperImpl",
	};

	private static void loadOneconfigClasses(Object loader) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Field field = loader.getClass().getDeclaredField("classCache");
		field.setAccessible(true);
		Map<String, Class<?>> classCache = (Map<String, Class<?>>) field.get(loader);

		Method defineClassMethod = loader.getClass().getDeclaredMethod("defineClassBypass", String.class, byte[].class);
		defineClassMethod.setAccessible(true);

		Map<String, byte[]> fileMap = readZip(Lwjgl3Loader.class.getResourceAsStream(ONECONFIG_JAR));

		for (String className : CLASS_NAMES) {
			byte[] data = fileMap.get(className.replace(".", "/") + ".class");
			Class<?> c = (Class<?>) defineClassMethod.invoke(loader, className, data);
			classCache.put(className, c);
		}
	}

	private static boolean tryGetOneconfigWrapper() {
		try {
			Class<?> lwjglManagerClazz = Class.forName("cc.polyfrost.oneconfig.renderer.LwjglManager");
			Field instanceField = lwjglManagerClazz.getDeclaredField("INSTANCE");
			instanceField.setAccessible(true);
			URLClassLoader loader = (URLClassLoader) instanceField.get(null);

			loadOneconfigClasses(loader);

			Object instance = loader.loadClass("org.lwjgl.system.odin.Lwjgl3WrapperImpl").getConstructor().newInstance();
			wrapper = (Lwjgl3Wrapper) instance;
			return true;
		} catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | IllegalAccessException |
		         InstantiationException | InvocationTargetException e) {
			LOGGER.info("You can probably ignore the following error");
			e.printStackTrace();
			return false;
		}
	}

	private static boolean tryGetOdinWrapper() {
		try {
			URLClassLoader loader = (URLClassLoader) Lwjgl3Wrapper.class.getClassLoader();
			addUrlMethod.invoke(loader, getJarURL(ODIN_JAR));

			Object instance = loader.loadClass("me.odin.lwjgl.impl.Lwjgl3WrapperImpl").getConstructor()
					.newInstance();

			wrapper = (Lwjgl3Wrapper) instance;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Lwjgl3Wrapper load() {
		synchronized (lock) {
			if (loaded.get()) {
				return wrapper;
			}

			if (tryGetOneconfigWrapper()) {
				loaded.set(true);
				return wrapper;
			}

			if (tryGetOdinWrapper()) {
				loaded.set(true);
				return wrapper;
			}

			throw new RuntimeException("Failed to load lwjgl3");
		}
	}
}
