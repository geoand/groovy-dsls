package geoand.logging

import groovy.transform.ToString

/**
 * Created by gandrianakis on 16/10/2015.
 */
@ToString(includePackage = false)
class AppenderConfig {

    final String name

    String path
    long ttl = -1

    AppenderConfig(String name) {
        this.name = name
    }
}
