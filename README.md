# **Aleph-CouncilOfFour** #

##  General information ##
Aleph-CouncilOfFour is a multiplayer game designed for Software Engineering course 2016 at Politecnico di Milano. That is a reinterpretation of the board game Council Of Four (2015) designed by Simone Luciani, Daniele Tascini from Cranio Creation s.r.l. The fully rulesbook here: [ItalianVersion](http://www.goblins.net/sites/default/files/CD4_regolamento_ita.pdf), [EnglishVersion](http://boardgamegeek.com/file/download/rky93trbck/rules_Co4.pdf)
##  Repository Folders ##
* ```src/main/java```: contains the java search code of the game (following the MVC model structure).
* ```src/main/resources```: contains the images of the game, some UML diagrams to describe the structure of our project and all the XML files to start up the initial configuration of the game.  
* ```src/test/java```:  contains the JUnit Test Cases of the model and controller classes. 

##  Operating instructions ##
### How to start Server ###
In order to play, you first need to start the server, to do that run ```MainServer.java``` in the directory ```it.polimi.ingsw.ps16.server```; it will start automatically and wait for incoming RMI or Socket connections.
  
### How to start Clients ###
In the source folder ```it.polimi.ingsw.ps16.client``` run ```MainClient.java``` for each player you want to start. you will then be prompted to choose between **RMI** or **Socket** connection just typing the name of the protocol in the bracket:

* Insert ```RMI``` to use Remote Method Invocation
* Insert ```SOCKET``` to use a Socket connection

You are now able to type the ```IPAddress/HostName``` and the ```PortNumber``` of the server ( just press enter in both fields for a local match). 
After all you will choose your best User Interface **CLI** or **GUI**, just typing the corrisponding number. 

* Insert ```1``` for a Graphical User Interface
* Insert ```2``` for a Command Line Interface

You will see a screen like this:

![Cattura di schermata .jpg](https://bitbucket.org/repo/5kx7eM/images/4003442902-Cattura%20di%20schermata%20.jpg)

There is a timeout of 30 seconds starting where the second player join the game; after this time other connections will no more be accepted (the following player will join an other game) and the game will start. There is not player limit for a single match and the game handler supports multiple concurrent games.


## Interface Description ##
### How to start game ###
The first player have to choose the map of the game (Aleph-CouncilOfFour already provide 8 different maps according to the real Council Of Four game board) and the king name.

  
![Cattura di schermata 1.jpg](https://bitbucket.org/repo/5kx7eM/images/930085340-Cattura%20di%20schermata%201.jpg)
  

All the player choose a name and set a personal color and where the time has run out begin the turn of the first player.
   
![Cattura di schermata 2.png](https://bitbucket.org/repo/5kx7eM/images/1137636279-Cattura%20di%20schermata%202.png)
  

During the game, when the player has to choose between different options, he has to write the words suggested in brackets [].

### Color Output Explanation ###
In the output box is used the following legend for the text colors:

*  ```BLACK``` : gives general information and updates the user on the game situation   

*  ```GREY``` : displays the choices entered by the player  

*  ```RED``` : signals input or other errors and also indicates the end of the game.

*  ```BLUE``` : requests some input words from the user  

*  ```GREEN``` : notify the current player about other players' moves and where is their turn or where they disconnected


### Chat ###

There is an ever active chat through which users can exchange messages in real time; messages are identified by the name of the player and the color chosen at the beginning of the game. 
Before the match starts the chat is not active, and if the player tries to write he is notified with an error message.

## Screenshot of the game ##

![Cattura di schermata 3.jpg](https://bitbucket.org/repo/5kx7eM/images/2224398668-Cattura%20di%20schermata%203.jpg)

## Credits ##

### Designed by: ###

***Giovanni Gianola***  

***Filippo Leveni***

***Valentina Ionata***