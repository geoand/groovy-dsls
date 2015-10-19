package geoand.logging

import groovy.transform.ToString

/**
 * Created by gandrianakis on 19/10/2015.
 */
@ToString(includePackage = false, excludes = "appender")
class LoggerConfig {

    final String packageName

    AppenderConfig appender
    LogLevel logLevel

    LoggerConfig(String packageName) {
        this.packageName = packageName
    }

    LoggerConfig to(AppenderConfig appender) {
        this.appender = appender
        return this
    }

    LoggerConfig at(LogLevel logLevel) {
        this.logLevel = logLevel
        return this
    }
}
