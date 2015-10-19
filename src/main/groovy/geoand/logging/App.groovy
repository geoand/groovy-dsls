package geoand.logging

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.CompilationCustomizer
import org.codehaus.groovy.control.customizers.ImportCustomizer

/**
 * Created by gandrianakis on 16/10/2015.
 */
class App {

    public static void main(String[] args) {
        final def binding = new Binding()

        final def config = new CompilerConfiguration()
        config.scriptBaseClass = LogConfigScript.name

        def importCustomizer = new ImportCustomizer()
        importCustomizer.addStaticStars("geoand.logging.LogLevel")

        config.addCompilationCustomizers(importCustomizer)

        final def shell = new GroovyShell(binding, config)
        final def script = shell.parse(new File(App.getResource("/log-config.groovy").toURI()).text) as LogConfigScript

        try {
            script.run()
            println "APPENDERS >> ${script.appenders}"
            println "LOGGERS >> ${script.loggers}"
        } catch (MissingPropertyException e) {
            println "ERROR: no appender defined with the name ${e.property}"
        }

    }
}
