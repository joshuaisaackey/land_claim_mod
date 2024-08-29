package com.example.isaac_land_claim_mod.utils;

public class JarUtils {
    public static String getJarPath(Class c) {
        try {
        // Obtain the class URL
        String className = c.getName().replace('.', '/') + ".class";
        String classFile = c.getResource("/" + className).toString();

        // Check if the class is loaded from a JAR
        if (classFile.startsWith("jar:")) {
            int endIndex = classFile.indexOf('!');
            if (endIndex > 0) {
                return classFile.substring(4, endIndex);
            }
        } else {
            // If running from the filesystem (e.g., in an IDE)
            return classFile.substring(0, classFile.indexOf(className));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;

    }

}
