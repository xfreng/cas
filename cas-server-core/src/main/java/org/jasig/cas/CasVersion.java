package org.jasig.cas;

import com.google.common.base.Throwables;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.VfsResource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Class that exposes the CAS version. Fetches the "Implementation-Version"
 * manifest attribute from the jar file.
 *
 * @author Dmitriy Kopylenko
 * @since 3.0.0
 */
public final class CasVersion {
    private static final Logger LOGGER = LoggerFactory.getLogger(CasEnvironmentContextListener.class);

    /**
     * Private constructor for CasVersion. You should not be able to instantiate
     * this class.
     */
    private CasVersion() {
        // this class is not instantiable
    }

    /**
     * @return Return the full CAS version string.
     * @see java.lang.Package#getImplementationVersion
     */
    public static String getVersion() {
        String implVersion = CasVersion.class.getPackage().getImplementationVersion();
        if (StringUtils.isEmpty(implVersion)) {
            Properties prop = new Properties();
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
            try {
                prop.load(classLoader.getResourceAsStream("version.properties"));
                implVersion = prop.getProperty("Implementation-Version");
            } catch (IOException e) {
                LOGGER.error("Load version.properties file error.", e);
            }
        }
        return implVersion;
    }

    /**
     * Gets last modified date/time for the module.
     *
     * @return the date/time
     */
    public static DateTime getDateTime() {
        try {
            final Class clazz = CasVersion.class;
            final URL resource = clazz.getResource(clazz.getSimpleName() + ".class");
            if ("file".equals(resource.getProtocol())) {
                return new DateTime(new File(resource.toURI()).lastModified());
            }

            if ("jar".equals(resource.getProtocol())) {
                final String path = resource.getPath();
                final File file = new File(path.substring(5, path.indexOf('!')));
                return new DateTime(file.lastModified());
            }

            if ("vfs".equals(resource.getProtocol())) {
                final File file = new VfsResource(resource.openConnection().getContent()).getFile();
                return new DateTime(file.lastModified());
            }

            LOGGER.warn("Unhandled url protocol: {} resource: {}", resource.getProtocol(), resource);
            return DateTime.now();
        } catch (final Exception e) {
            throw Throwables.propagate(e);
        }
    }
}