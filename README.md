## FungeeCommands

This is a BungeeCord implementation of the well-known ORE plugin, [FunCommands](https://github.com/OpenRedstoneEngineers/FunCommands-Legacy). This implementation focuses on proper OOP principles and a better application of separation of concerns.

Due to this being for BungeeCord, the proxy and the game servers have to have a FungeeCommands plugin installed. FungeeCommandsManager is the proxy plugin that handles the parsing and dispatch of all commands. FungeeCommandsExecutor is the bukkit-derived plugin that will actually execute these commands, if something other than text is being manipulated.

This implementation uses the [Bungee Messaging Channel](https://www.spigotmc.org/wiki/bukkit-bungee-plugin-messaging-channel/) to execute functions that modify players or other entities. The messaging channel is utilized through the use of PluginMessagingUtils. These utils will build a serialized representation of a task that can be sent over the messaging channel for the game servers to execute. 