appender "main", {
    path = "log.out"
    ttl = 10 * 24 * 60 * 60
}

appender "other", {
    path = "other-log.out"
}

logger 'geoand.logging' to main at DEBUG
