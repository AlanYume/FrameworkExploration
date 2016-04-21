package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 */
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(final String className, final boolean isInitialized) {
        final Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (final ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 加载类（默认将初始化类）
     */
    public static Class<?> loadClass(final String className) {
        return loadClass(className, true);
    }

    /**
     * 获取指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(final String packageName) {
        final Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            final Enumeration<URL> urls = getClassLoader().getResources(
                    packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                final URL url = urls.nextElement();
                if (url != null) {
                    final String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        final String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classSet, packagePath, packageName);
                    } else if (protocol.equals("jar")) {
                        final JarURLConnection jarURLConnection =
                                (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            final JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                final Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    final JarEntry jarEntry = jarEntries.nextElement();
                                    final String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        final String className = jarEntryName.substring(0,
                                                jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (final Exception e) {
            LOGGER.error("get class set failure", e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void addClass(final Set<Class<?>> classSet, final String packagePath,
            final String packageName) {
        final File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(final File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for (final File file : files) {
            final String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(final Set<Class<?>> classSet, final String className) {
        final Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
}
