# Harmony

A Java wrapper around the Discord API.

## Warning

This API/Library is unfinished as it is now. It is "mostly" stable but everything is subject to change and to not work nor is everything included.

## Getting Started

### Creating a Harmony Instance
The Harmony Instance is used to communicate with the Discord servers. There are two main ways to do this along with a third unrecomended way, the first is to provide Harmony with your email and password for Discord.

```Java
Harmony harmony = new Harmony("email_here", "password_here");
```

---
The second is to tell Harmony to use a file that contains the email and password in a JSON format.
```Java
Harmony harmony = new Harmony("file_path");
```
**or**
```Java
Harmony harmony = new Harmony(new File("file_path"));
```
with the file being a file in the format
```JSON
{
	"email": "email_here",
    "password": "password_here"
}
```
whitespace does not matter in JSON files.

*NOTE*: If you specify a file to use and update the email or password affter a connection has been made Harmony will update the file.

---
The third way will only connect if there is a data file containing a valid token when the `connect()` method is called on the Harmony instance.
```Java
Harmony harmony = new Harmony();
```

---
### Data File
Harmony requiers the use of a data file to store certan data between sessions as Discord has a rate limit on authentications. It is not recomended to have multiple bots using the same data file, or login, as Discord also has a rate limit on the amount of messages that can be sent to its server of 5 messages or edits per 5 seconds.

To use a file other than the default use
```Java
harmony.setDataFile("file_path");
```
**or**
```Java
harmony.setDataFile(new File("file_path"));
```
This **must** be set before harmony connects otherwise it will fail silently.

---
### Connecting
Actually telling Harmony to start its connection is easy, all  thats needed is to call
```Java
harmony.connect();
```
and Harmony will begin its connection cycle to Discord. It may take a few seconds for the connection and processing to finish. When it does Harmony will throw the `HarmonyReadyEvent`. Harmony itself will take care of keeping the connection alive and processing messages from Discord. Harmony will also not take control of the main thread.

---
### Disconnecting
For disconnecting properly from Discord simply call
```Java
harmony.disconnect();
```
This will make sure all needed data is properly stored and will close and stop all open connections and threads used internally by Harmony. A Data File and a Login File may be the same file if desiered.
## Event System

The Event System is the main way that Harmony and the application interact. Harmony recieves event information from the Discord servers and translates it into different events that it then throws out to the application.

---
### Events
#### Regular Events
Harmony has a lot of different events that can be thrown These are:
* HarmonyReadyEvent - Thrown after Harmony has finished all of its setup and fully processed the Ready event from the Discord Servers.
* NYF

---
#### Advanced Events
These Events are only thrown when Harmony is started with the stand_back_im_a_professional Option enabaled. They are the only events thrown or, in other words, **NONE** of the regular events other than the HarmonyReadyEvent will be throw.
* HarmonyReadyEvent - Same as the Regular HarmonyReadyEvent except that it is throw as soon as Harmony has opened up connections to the Discord Servers.
* MessageReceivedEvent - Thrown whenever Harmony gets a message from the Discord Servers from the Web Socket. This will always be the decoded/decompressed form of the message.

## Advanced Configuration

NYI - 
* stand_back_im_a_professional - With this enabled Harmony will only act as the connector between Discord and the Application. All messages that Harmony recives will be passed on to the client using the MessageReceivedEvent after being decoded if needed. Harmony itself will not make any helper objects, such as user or message, all that will be left up to the Application.

## Dependencies
Harmony uses several other libraries to help it do what it does.
- [SLF4J](http://www.slf4j.org/) - Logging framework
- [JSON](http://www.json.org/java/) - JSON Format parser
- ~~[Java-Websocket](http://java-websocket.org/) - WebSocket connection~~ Removed in favor of nv-websocket-client
- [httpclient](https://hc.apache.org/) - HTTP/REST connection (I would prefer a smaller one)
- [Caffeine](https://github.com/ben-manes/caffeine) - Limited high speed Caches
- [nv-websocket-client](https://github.com/TakahikoKawasaki/nv-websocket-client) - WebSocket connection
