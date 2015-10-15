package geoand.users

import geoand.users.integration.UsersBaseScript
import geoand.users.model.User
import groovy.util.logging.Slf4j
import org.codehaus.groovy.control.CompilerConfiguration

/**
 * Created by gandrianakis on 15/10/2015.
 */
@Slf4j
abstract class UsersLoader {

    static Set<User> loadUsersFromAbsolutePath(String absolutePath) {
        loadUsers(absolutePathResourceContents(absolutePath))
    }

    private static String absolutePathResourceContents(String absolutePath) {
        try {
            new File(absolutePath).text
        } catch (Exception e) {
            log.debug("An error while trying to read the file from absolute path $absolutePath", e)
            throw new RuntimeException("Unable to load users from DSL content", e)
        }
    }

    static Set<User> loadUsersFromClasspath(String path) {
        loadUsers(classPathResourceContents(path))
    }

    private static String classPathResourceContents(String path) {
        try {
            new File(UsersLoader.getResource(path).toURI()).text
        } catch (Exception e) {
            log.debug("An error while trying to read the classpath resource $path", e)
            throw new RuntimeException("Unable to load users from DSL content", e)
        }
    }

    static Set<User> loadUsers(String dslStr) {
        try {
            final def binding = new Binding()
            final def config = new CompilerConfiguration()
            config.scriptBaseClass = UsersBaseScript.name

            def shell = new GroovyShell(this.class.classLoader, binding, config)

            shell.evaluate dslStr
        } catch (Exception | Error e) {
            log.debug("An error occurred while loading users", e)
            throw new RuntimeException("Unable to load users from DSL content", e)
        }
    }
}
