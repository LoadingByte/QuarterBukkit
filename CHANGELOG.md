0.3.0
-----

### Removals
* Removed the CANNOT_CLOSE_RESPONSE_STREAM query api exception type because it was trashing other more important exceptions.
* Removed the constructors which do not require an inventory title from SelectInventory because minecraft no longer supports that.

### Fixes
* The FileUtils class now logs exceptions that occur during closing resources.
* The CommandExecutor class now provides a DEFAULT_COMMAND_LABEL constant. It should be used instead of the plain "\<empty\>".
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
