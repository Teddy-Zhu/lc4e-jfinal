package com.teddy.jfinal.tools;

import com.jfinal.kit.PathKit;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.config.Config;
import com.teddy.jfinal.plugin.PropPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.annotation.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class ClassSearcherTool {


    private static List<Class<? extends Annotation>> ignoreAnnoataion;

    static {
        ignoreAnnoataion = new ArrayList<>();
        ignoreAnnoataion.add(Documented.class);
        ignoreAnnoataion.add(Inherited.class);
        ignoreAnnoataion.add(Target.class);
        ignoreAnnoataion.add(Retention.class);
    }

    private static Map<Class<? extends Annotation>, Set<Class>> extraction(List<String> classFileList, Class<? extends Annotation>... clazzes) {
        Map<Class<? extends Annotation>, Set<Class>> classList = new HashMap<>();
        for (Class<? extends Annotation> clazz : clazzes) {
            classList.put(clazz, new HashSet<>());
        }
        classFileList.forEach(classFile -> {
            if (valiPkg(classFile)) {
                Class<?> classInFile = ReflectTool.on(classFile).get();
                for (Class<? extends Annotation> clazz : clazzes) {
                    if (classInFile.isAnnotationPresent(clazz)) {
                        classList.get(clazz).add(classInFile);
                    }
                }
            }
        });

        return classList;
    }

    private static Map<Class<? extends Annotation>, Set<Class>> putAll(List<String> classFileList) {
        Map<Class<? extends Annotation>, Set<Class>> classList = new HashMap<>();
        classFileList.forEach(classFile -> {
            if (valiPkg(classFile)) {
                Class<?> classInFile = ReflectTool.on(classFile).get();
                Annotation[] annotations = classInFile.getAnnotations();
                for (Annotation ans :
                        annotations) {
                    if (ignoreAnnoataion.contains(ans.annotationType())) {
                        continue;
                    }
                    putClass(classList, ans.annotationType(), classInFile);
                }
            }
        });
        return classList;
    }

    private static void putClass(Map<Class<? extends Annotation>, Set<Class>> classSetMap, Class<? extends Annotation> clazz, Class putedClass) {
        if (!classSetMap.containsKey(clazz)) {
            classSetMap.put(clazz, new HashSet<>());
        }
        classSetMap.get(clazz).add(putedClass);
    }

    @SuppressWarnings("unchecked")
    public static boolean valiPkg(String classFile) {
        List<String> pkgs = (List<String>) Config.getCustomConfig().getProp().getObject(Dict.SCAN_PACKAGE);
        for (String pkg : pkgs) {
            if (classFile.startsWith(pkg)) {
                return true;
            }
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    public static boolean isValiJar() {
        List<String> jars = (List<String>) Config.getCustomConfig().getProp().getObject(Dict.SCAN_JAR);
        return jars.size() > 0;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ClassSearcherTool of(Class<? extends Annotation>... target) {
        return new ClassSearcherTool(target);
    }

    /**
     * search
     *
     * @param baseDirName
     * @param targetFileName
     */
    private static List<String> findFiles(String baseDirName, String targetFileName) {

        List<String> classFiles = new ArrayList<String>();
        String tempName = null;
        File baseDir = new File(baseDirName);
        if (!baseDir.exists() || !baseDir.isDirectory()) {

        } else {
            String[] filelist = baseDir.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(baseDirName + File.separator + filelist[i]);
                if (readfile.isDirectory()) {
                    classFiles.addAll(findFiles(baseDirName + File.separator + filelist[i], targetFileName));
                } else {
                    tempName = readfile.getName();
                    if (ClassSearcherTool.wildcardMatch(targetFileName, tempName)) {
                        String classname;
                        String tem = readfile.getAbsoluteFile().toString().replaceAll("\\\\", "/");
                        classname = tem.substring(tem.indexOf("/classes") + "/classes".length() + 1,
                                tem.indexOf(".class"));
                        classFiles.add(classname.replaceAll("/", "."));
                    }
                }
            }
        }
        return classFiles;
    }

    private static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1), str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                strIndex++;
                if (strIndex > strLength) {
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return strIndex == strLength;
    }

    private String classpath = PathKit.getRootClassPath();

    private boolean includeAllJarsInLib = false;

    private List<String> includeJars = new ArrayList<String>();

    private String libDir = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + "lib";

    @SuppressWarnings("rawtypes")
    private Class<? extends Annotation>[] target;

    @SuppressWarnings("rawtypes")
    public ClassSearcherTool(Class<? extends Annotation>... target) {
        this.target = target;
    }

    public ClassSearcherTool injars(List<String> jars) {
        if (jars != null) {
            includeJars.addAll(jars);
        }
        return this;
    }

    public ClassSearcherTool inJars(String... jars) {
        if (jars != null) {
            for (String jar : jars) {
                includeJars.add(jar);
            }
        }
        return this;
    }

    public ClassSearcherTool classpath(String classpath) {
        this.classpath = classpath;
        return this;
    }

    @SuppressWarnings("unchecked")
    public Map<Class<? extends Annotation>, Set<Class>> search() {
        List<String> classFileList = findFiles(classpath, "*.class");
        classFileList.addAll(findjarFiles(libDir, includeJars));
        return extraction(classFileList, target);
    }

    public Map<Class<? extends Annotation>, Set<Class>> getAllAnnotation() {
        List<String> classFileList = findFiles(classpath, "*.class");
        classFileList.addAll(findjarFiles(libDir, includeJars));
        return putAll(classFileList);
    }

    /**
     * find jar in classes
     *
     * @param baseDirName
     * @param includeJars
     */
    private List<String> findjarFiles(String baseDirName, final List<String> includeJars) {
        List<String> classFiles = new ArrayList<String>();

        try {
            File baseDir = new File(baseDirName);
            if (!baseDir.exists() || !baseDir.isDirectory()) {

            } else {
                String[] filelist = baseDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return includeAllJarsInLib || includeJars.contains(name);
                    }
                });
                for (int i = 0; i < filelist.length; i++) {
                    JarFile localJarFile = new JarFile(new File(baseDirName + File.separator + filelist[i]));
                    Enumeration<JarEntry> entries = localJarFile.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();
                        String entryName = jarEntry.getName();
                        if (!jarEntry.isDirectory() && entryName.endsWith(".class")) {
                            String className = entryName.replaceAll("/", ".").substring(0, entryName.length() - 6);
                            classFiles.add(className);
                        }
                    }
                    localJarFile.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return classFiles;

    }

    public ClassSearcherTool includeAllJarsInLib(boolean includeAllJarsInLib) {
        this.includeAllJarsInLib = includeAllJarsInLib;
        return this;
    }

    public ClassSearcherTool libDir(String libDir) {
        this.libDir = libDir;
        return this;
    }

}
