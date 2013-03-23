package org.jbake.app;

import java.net.JarURLConnection;
import java.net.URLConnection;
import java.net.URL;

import java.util.Collections;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.io.IOUtils;

/**
 * Provides File related functions
 * 
 * @author Jonathan Bullock <jonbullock@gmail.com>
 *
 */
public class FileUtil {
	
	/**
	 * Filters files based on their file extension.
	 * 
	 * @return	Object for filtering files
	 */
	public static FileFilter getFileFilter() {
		return new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return !pathname.isFile()
						|| pathname.getPath().endsWith(".html")
						|| pathname.getPath().endsWith(".md");
			}
		};
	}
    
    public static void copyJarResourcesRecursively(URL originUrl, File destination) 
            throws IOException {
        URLConnection connection = originUrl.openConnection();
        if (connection instanceof JarURLConnection) {
            copyJarResourcesRecursively((JarURLConnection) connection, destination);
        } else {
            throw new IllegalArgumentException("Not a jar URL connection");
        }
    }

    private static void copyJarResourcesRecursively(JarURLConnection connection, File destination) 
           throws IOException {
        JarFile jarFile = connection.getJarFile();
        for (JarEntry entry: Collections.list(jarFile.entries())) {
            if (entry.getName().startsWith(connection.getEntryName())) {
                String fileName = StringUtils.removeStart(entry.getName(), connection.getEntryName());
                File destinationFile = new File(destination, fileName);
                if (entry.isDirectory()) {
                    FileUtil.ensureDirectoryExists(destinationFile);
                } else {
                    InputStream entryInputStream = null;
                    try {
                        entryInputStream = jarFile.getInputStream(entry);
                        IOUtils.copy(entryInputStream, new FileOutputStream(destinationFile));
                    } finally {
                        IOUtils.closeQuietly(entryInputStream);
                    }
                }
            }
        }
    }

    public static boolean ensureDirectoryExists(File f) {
        return f.exists() || f.mkdir();
    }
}
