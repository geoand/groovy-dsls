package geoand.logging
/**
 * Created by gandrianakis on 16/10/2015.
 */
abstract class LogConfigScript extends Script{

    final Map<String, AppenderConfig> appenders = [:]
    final Map<String, LoggerConfig> loggers = [:]

    AppenderConfig appender(String name, @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=AppenderConfig) Closure config) {

        final def appender = new AppenderConfig(name)
        appenders[name] = appender

         def cl = config.rehydrate(appender, this, this)
         cl.resolveStrategy = Closure.DELEGATE_ONLY

        cl.call()

        return appender
    }

    LoggerConfig logger(String packageName) {
        def config = new LoggerConfig(packageName)
        loggers[packageName] = config
    }

    def propertyMissing(String name) {

        final AppenderConfig appender = appenders[name]

        if(!appender) {
            throw new MissingPropertyException(name, AppenderConfig)
        }

        return appender
    }
}
