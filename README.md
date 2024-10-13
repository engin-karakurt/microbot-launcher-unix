# microbot-launcher-unix

microbot-launcher-unix is an unofficial Windows, macOS and Linux Launcher for [Microbot](https://github.com/chsami/microbot)!

Although it can be used on all 3 platforms just fine, it is mainly intended for using Microbot on macOS or Linux (Hence why the -unix is in the name!). For Windows you can use the [Official Launcher](https://themicrobot.com/) too.

## Table of contents
- [Features](#features)
- [How to use](#how-to-use)
- [Using a Jagex Account](#using-a-jagex-account)
- [Contributions](#contributions)
- [Support me](#support-me)

## Features
- Automatically downloads the latest non-nightly .jar file from GitHub releases & puts it into a /jars directory which gets created in the same folder as the launcher

- Deletes old microbot versions and checks for new ones
  
- Launches the .jar file for you

- Portable (Writes to its own directory)

## How to use
NOTE: Requires Java 11 (Just like Microbot)

- Put the Launcher in the folder where you want to have the files organized.

- Launch using Terminal: ```java -jar microbot-launcher-linux.jar```

- Enjoy!

## Using a Jagex Account
You can use this with a Jagex Account, however you currently need to do some manual steps:

- Obtain a `credentials.properties` file from a RuneLite launcher. This can be from a Windows, macOS or Linux Launcher. 

For Windows and macOS, see [here](https://github.com/runelite/runelite/wiki/Using-Jagex-Accounts).

For Linux using the third-party [Bolt Launcher](https://github.com/Adamcake/Bolt/):

- Install Bolt Launcher from Flathub, see the link above for more info.

- After logging into Bolt Launcher, select RuneLite as the Game client.

- Go to the Application Options and click on Configure RuneLite

- In the window that pops up, paste the following argument for Client arguments: `--insecure-write-credentials` (Just like the guide from RuneLite above)

- Now if you start RuneLite, a `credentials.properties` file will be generated to the following path: `/home/YOUR-USERNAME/.var/app/com.adamcake.Bolt/data/bolt-launcher/.runelite/`

- Copy the `credentials.properties` file in to the `.runelite` folder at the following path (If you don't have a .runelite folder there, make sure to launch Microbot through the launcher once): `/home/YOUR-USERNAME/.runelite/`

- Now when you start Microbot through the Launcher again, it should work with your Jagex Account!

NOTE: There are probably other methods aswell. Feel free to share them!

## Contributions
Feel free to submit a pull request to the dev branch if you have worked on something (Feature / Bug)! 

Also take a look at the Issues tab for things to solve and suggest features / report bugs if you encounter anything that could be improved!

## Support me
If you want to support me, you can do so by sending me a donation.

Bitcoin Wallet Address: bc1qwvel984ptpnmz02zxtjqfm0skj4adz39dwx5wv
