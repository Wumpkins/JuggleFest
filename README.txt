To the Yodle Development Team:

Hi. Thanks for giving me this JuggleFest problem - it was quite fun to solve. My program works very simply, and simply requires a command line input of a text file to run (the text file with circuits and jugglers). It is not completely robust, so if you provide it with a text-file that is not formatted perfectly, the program will crash with errors.

First, it parses the text file into Circuit and Juggler objects, and then places these objects into arrays. 

The program then goes and divides the # of jugglers by the # of circuits, as to find the size of each circuit. 

Once this is found, it will go through and attempt to give each Juggler his/her first preference. If the juggler's first preference is full, it checks to see if the other jugglers in the group all have higher scores than him, and if so, moves on to look for his second preference, and so on. If there exists a Juggler with a lower score than him, that juggler is kicked out, and must proceed to check for his second preference and so on. 

Jugglers who have exhausted their list of preferences are put into a group (array) of jugglers who have no preferences, and will be randomly matched into empty circuits at the end.

The run-time of the program is around 5-10 seconds, and it will produce a text file called Output.txt, where the results can be seen. 