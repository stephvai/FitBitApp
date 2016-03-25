# CS 2212 Team08 - Winter 2016

If you do not have git configured you will need to run these commands in your terminal to configure git and clone the repo.

## Configure Git for the first time

```
git config --global user.name "Your Name"
git config --global user.email "youremail@uwo.ca"
```

## Generate an SSH key and add it to your account

On Linux, UNIX, Cygwin, or Git Bash, generate a key:

```
ssh-keygen -t rsa -b 4096
```

This will generate a private key (`~/.ssh/id_rsa`) as well as a public key (`~/.ssh/id_rsa.pub`).
**Do not share your private key with others.**

Display your public key:

```
cat ~/.ssh/id_rsa.pub
```

Copy the entire contents of the key, and associate it with your Bitbucket account:

* Click the avatar icon in the top right corner
* Select *Manage account* from the drop down list.
* Select *SSH keys* from the sidebar.
* Click *Add key*.
* Paste the contents of your public key in the *Key* field.  Be sure to paste the entire file.
* Click *Add key*.

## Working with your repository

Clone your repository onto your local system:

```
git clone ssh://git@repo.gaul.csd.uwo.ca:7999/cs2212_w2016/team08.git
```

## To Run the Program
First compile and package the program with maven
## To Compile
To compile the program:

```
mvn compile
mvn package
```
The application has two different modes. Test mode which does not require an internet connection and regular mode which gets data off the fitbit servers.

## To Run the program in regular mode:
```
java -jar target/team08_app-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## To run the program in test mode:
```
java -jar target/team08_app-1.0-SNAPSHOT-jar-with-dependencies.jar test
```
## Credit to flickr User Musume Miyuki for the background image
```
Here is the link to the profile: 
http://bit.ly/1RAg9U6
```
## Our application was designed on the MAC 
```
Please grade our GUI based on the MAC OS X Platform
```
