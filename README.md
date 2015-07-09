## ws-stream

Small application streaming the contents of a file line by line on a websocket.

In [SBT](http://www.scala-sbt.org/) start with:

`run -Dhttp.port=PORT -Dfileservice.store=STORE_DIR -Dfileservice.frequency=MILLIS_FREQ`

where `STORE_DIR` is the local directory where the files available for stream are found and `MILLIS_FREQ` is the frequency at which the lines are streamed, in milliseconds.

Example usage:

`curl -i -N -H "Connection: Upgrade" -H "Upgrade: websocket" -H "Host: localhost" -H "Origin: localhost" http://localhost:PORT/file/FILE `

where <code>FILE</code> is a file found in the configured store directory. 

The application uses the Play! design pattern explained [here](https://www.playframework.com/documentation/2.4.x/ScalaWebSockets#Handling-WebSockets-with-actors).

### Deployment

Java 6 or later is required. 

To create the package in SBT use: `sbt dist`; a readymade zipfile is also available, `target/universal/ws-stream-1.0.zip`. Once unzipped you can launch the app in the background with:

`bin/ws-stream -Dhttp.port=PORT -Dfileservice.store=STORE_DIR -Dfileservice.frequency=MILLIS_FREQ &`

You should terminate the instance properly with a `SIGTERM` (eg `pkill -f ws-stream`); in case you don't do so, make sure the ports are freed and remove the `RUNNING_PID` pidfile. 