![alt text][logo]
[logo]: http://reedlatam.com/sadmoweb/img/modulos/Listasweb/expo-tecnologia/2016/lista-expositores//logo_iw_soluciones_honestas.png "Interware de México"
-----
# [Arp Appender](http://www.interware.com.mx)   

Arp appender is a log4j 1.X appender to filter and send formated log messages to arp platform.

## Features
* Log4j appender
* Reconnection on error and disconnection

## Language
 * Pure java

## Requirements
 * Log4j 1.X
 * Ant 2.X (for compiling)

## Installation
1. Make sure you already set up log4j 1.X in your project
2. Add ArpAppender jar to your project's classpath
3. Add the ArpAppender configuration to your log4j configuration

## Properties:
```
log4j.rootLogger = DEBUG, FILE, arquimedes

#Arquimedes
log4j.appender.arquimedes=mx.com.interware.arp.appender.ArquimidesAppender
log4j.appender.arquimedes.regexp=.*(EJECUTANDO) +([a-zA-Z0-9]+).*time .>>.*|.*(FINALIZANDO) +([a-zA-Z0-9]+).*time .>> +([0-9]+).*
log4j.appender.arquimedes.ednFormat=:thread "%thread%", :timestamp %timestamp%, :level "%level%", :start "%s", :tx "%s", :end "%s", :tx "%s", :delta "%s"
log4j.appender.arquimedes.host=127.0.0.1
log4j.appender.arquimedes.port=55555
log4j.appender.arquimedes.reconnectionTime=10000
log4j.appender.arquimedes.maxQueue=5
log4j.appender.arquimedes.sendDelta=1000
```

## Features
For ednFormat key at properties file, there are some keywords:  
**%thread%** - Thread name  
**%timestamp%** - Timestamp of logging  
**%level%** - Logging level  
**%mensaje%** - Logging Message  
**%s** - Strings associated to regexp groups  

## Build
run in terminal:
```
ant -f build.xml
```

## Javadoc
https://interwaremx.github.io/ArpAppender/javadoc/

## Using Log4J 2.x?
https://github.com/interwaremx/ArpAppender-for-Log4j2
