0.4.0
-----

### Additions
* Previously, all object systems were updated each tick. That, however, resulted in problems with both accurate physics simulation as well as finer-graned time resolutions. The new system informs everybody about the passed time &Delta;t and even allows for discrete time steps smaller than the length of a tick.
* Overall simplified the generics used by the object system API.
* Added traits to object system objects. Objects are now specialized by adding traits (like the physics trait or the particle trait) instead of subclassing the `BaseObject` class. For example, an object is now able to both be a source of fireworks and a source of particles at the same time because you can simply add both traits to the same object.
* Added the new behavior API to the object system which replaces the previously separate `ModificationRule` and `Source` APIs. It is way more flexible than any of the former APIs and fully leverages the power of Java 8 lambdas. Now, you are free to write your own rule code without having to think in the weird and narrow terms of a specialized system.
* The new behavior API not only allows to specify code which will be executed on the entire object system. You can also attach pieces of code to groups of objects (using `CategoryTrait`) or even individual objects (using `BehaviorTrait`) now.
* Added some new more generic object system utility classes which are now responsible for tasks previously executed using the `ModificationRule` and `Source` APIs. One prominent example is the `Forces` class.
* Previously, most of the users and -- in some cases -- even the object classes themselves had no clue about the context of an object and were instead faced with just the object on it's own. Now, however, each object stores the `ActiveObjectSystem` it is part of and makes that variable accessible to everyone.
* Object system renderers no longer need to return a `RenderingResult` in order to remove an object. Instead, they can now remove objects completely by themselves.
* Active object systems can now be nested. In fact, those systems are now traits by themselves and can therefore be added to other objects inside other active systems.

### Removals
* Removed the separate `ModificationRule` and `Source` APIs used by the object system in favor of the behavior API.
* No object system components (e.g. behaviors) are provided with a predefined `java.util.Random` object anymore since it's too easy to not use it. As soon as someone does that, the whole idea of reproducibility is lost.

### Fixes
* QuarterBukkit now works with the latest versions of Bukkit for Minecraft 1.10.x.

### Notes
* Changed the license to LGPL v3.
* Updated the plugin to Java 8. That means that Java 6 and 7 support has been dropped, and the plugin will no longer work in Java 6 or 7 environments.
* Some big changes to the object system API will most certainly break existing code. Watch out for that if you used the API in 0.3.x!

0.3.1
-----

### Fixes
* HTTP redirects which result in a protocol change (e.g. HTTP to HTTPS) are now correctly followed. That means that the autoupdater and the integration mechanism should function again.
* Prevent possibly fatal FQCN collisions between copied API classes in the integration code and proper API classes from the actual API plugin. Since these classes differ slightly, problems could quickly emerge.

0.3.0
-----

### Additions
* The expandable Shape API allows to work with a lot of different shapes in 3D space and does the math for the user.
* The flexible Object System API allows to create complex systems of different objects with physics and visual effects.

### Removals
* Removed the CANNOT_CLOSE_RESPONSE_STREAM query api exception type because it was trashing other more important exceptions.
* Removed the constructors which do not require an inventory title from SelectInventory because minecraft no longer supports that.

### Fixes
* The FileUtils class now logs exceptions that occur during closing resources.
* The CommandExecutor class now provides a DEFAULT_COMMAND_LABEL constant. It should be used instead of the plain "\<empty\>".
* The SelectInventory now cancels the event when the player clicks on an item so the player can no longer take the item.
* The integrated MetricsLite client now properly shuts down when the plugin is disabled.
* The QuarterBukkitIntegration mechanism no longer relies on classloader specifics and uses temporary files instead. It should now work on literally every machine.
* Fixed JavaDoc errors which caused generation warnings.
* Fixed some other very minor issues.

### Notes
* Maven will publish the sources and JavaDocs from this version onwards.

0.2.1
-----

### Fixes
* Fixed a bug which caused the ServerModsAPIQuery to throw a NullPointerException if the connection to the server mods api failed.

0.2.0
-----

### Additions
* ServerMods Query API for querying the official ServerMods API which is available under https://api.curseforge.com/servermods.
* The QuarterBukkitUpdater and QuarterBukkitIntegration classes now use the ServerMods Query API.

### Removals
* Removed the updater; it was replaced by the ServerMods Query API along with the FileUtils utility.

### Fixes
* Updated the information in the project object model descriptor.
* Made the FileUtils class more resource-frienldy by closing open file handles.
* The integration message only shows up every ten seconds.

0.1.3
-----

### Additions
* Utility events which extend the currently available bukkit events.
* RedstoneToggleEvent which is fired whenever the power status of any block changes.
* The integration method can now handle multiple caller plugins.

### Removals
* The integration method no longer disables the calling plugin.

### Fixes
* The updater now works with the new format of the distribution file.
* Outsourced the file utilities from the updater into the FileUtils class.

0.1.2
-----

### Fixes
* The integration system now works with the new file format.

0.1.1
-----

### Fixes
* The README.md file now tells you how to use QuarterBukkit in maven properly.

0.1.0
-----

### Notes
* First maven build; start of new changelog.
