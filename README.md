# TalesOfVallhalla
A 2D Tile game made in Java for an University project.

<b>Gameplay:</b><br> Consists of a pseudo-campaign where the player must defeat two enemies by
surviving the necessary amount of time. While resisting the chase of the monster specific to the
level, the player might chose to collect coins in order to increase the overall walkthrough score.
Gameplay involves continuous moving in the level the hero has entered in order to survive the
chase of the two enemies he needs to face. The hero has got the option to kick his enemies in
order to avoid immediate damage, but shall think thoroughly before doing so, as he does not
have the possibility of continuously slamming his foes.
The game has got the top-down camera type and relies deeply on continuous moving and smart
path creation and selection.

<b>Plot:</b><br> The game is set in the mighty realm known to humans as Valhalla, where Vikings feast
and celebrate their battle accomplishments achieved in the normal life. It is known that only true
Vikings reach Valhalla. Ragnar, the main character of the game, has an unknown fate due to his
immoral actions done in his various raids on the Saxons.
In order to prove his might and be granted a place in Valhalla, he must defeat two extremely
powerful enemies chosen by Odin himself. He must reach the cryptic land known to humans as
Muspellheim, where he needs to face the bloodthirsty dragon Fafnir as the first challenge, while
also attempting to steal his treasure, and, then, he must face Death itself. Shall he survive, he will
be granted immediately a place in Valhalla.

<b>Characters:</b><br>
<b>•Rangar:</b> he is the protagonist – the character controlled by the player itself. Ragnar is a
former Viking mighty warrior that needs to prove his worth to the God Odin in person in
order to reach the dream land of Valhalla. His agility and power are two qualities that
will help him on his path.<br>
<b>•The dragon Fafnir:</b> the first foe Ragnar needs to face, known in the Norse mythology
for his extreme power and fearless soul. Also known for the treasures its lair hides.
<br>
<b>•Death:</b> the second and last enemy that Ragnar needs to survive to. Portrayed commonly
as a hollow figure with a black robe and a sharp scythe.

<b>Mechanics:</b><br> The main menu of the game allows the player to choose from the following options: PLAY, LOAD, SETTINGS, EXIT, and ABOUT.<br>
• <b>PLAY</b> will start a NEW game.<br>
• <b>LOAD</b> will start only the LAST saved game<br>
• <b>SETTINGS</b> allows the player to control the volume of the music present in the Menu and in the game, the difficulty of the game, and to choose whether the auto-retry option is active or not.<br>
• <b>EXIT</b> button will immediately close the game.<br>
• <b>ABOUT</b> will offer more information on the game.<br>
The game consists of two levels, similar to two dungeons in which Ragnar needs to survive for a specific amount of time the chase of that level’s monster.<br>
Once entered, a level cannot be left until the monster which the hero needs to survived to has vanished ( that implies surviving the given amount of time, and remaining with an amount of health greater or equal to zero.<br>
While running from the monsters, the hero might collect coins which will increase the score obtained on the respective walkthrough.
Also, if the hero gets too close and the hero risks on getting damage, the player might chose to kick back the monster to make room for his movement and prevent getting his health-bar chunked. It is preferred that the hero uses as less attacks as possible in order to achieve high scores when exiting the dungeon. The monster is not damaged by the kickback, as it cannot die. It will simply vanish when the time has elapsed, and the hero won’t be able to pick the coins after its disappearing.<br>
Hero gains game points by: collecting coins, using less to no attacks, successfully finishing a dungeon, getting no damage, etc.<br>
If the hero reaches zero HP, he dies and loses all of the progress made until that very moment of time. A menu will appear, asking the player if he wants to retry the attempt or simply exit the game. The prompt will not appear if the player selected the AUTORETRY option in the settings menu. In this case, the game will restart automatically.<br>
The player can chose to save the progress made until that very second of the game, and might use the LOAD option in the menu in the future to load only the last save that he made. Clicking the play button while also having a game saved will not delete the save. The save will remain loadable until it is overwritten specifically by the player. The `ESC` key will have to be pressed for a player to have the opportunity to save.<br>
The game will be over(won) when both of the enemies have vanished and Ragnar has accomplished the final action needed for him to enter Valhalla.<br>

