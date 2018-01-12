# Snake-AI-GameSolver
For a computer to play Snake automatically there are three
searching algorithms related to artiﬁcial intelligence, Best First Search, A* Search and improved A* Search with forward checking.
## Characteristics 
In the most general way, our implementation consists of the snake moving on a square board, trying to eat  
as many apples as possible without biting itself.  
Once the snake eats an apple, a new apple is placed in a free position on the board and the snake length grows by one unit.   
When the snake has no choice other than biting itself, the game is over and a ﬁnal score is returned.  
In our implementation, we simply calculate the score as the number of apples the snake has eaten or equivalently, the  
length increased by the snake.  
The following are some basic rules followed by our implementation:  

1. Goal - the snake tries to eat as many apples as possible, within ﬁnite steps.   
    The ﬁrst priority for the snake is to not bite itself while the second is to increase the score.  
2. There are four possible directions the snake can move: north, south, east, and west.   
    However, because of the placement of its tail some directions may not be available.   
    The most clear example is that the snake can never swap to an opposite direction i.e. north to south, east to west,    etc.
3. The snake grows by one unit when eating an apple.   
    The growth is immediately reﬂected by the gained length of the tail, i.e. the tip of the tail occupies the square on which the apple       was.
4. The board size is ﬁxed to square.  
5. After an apple is eaten by the snake, another apple is placed randomly with uniform probability on one available squares of the board.     Here the availability of a square is denoted by the fact that it is not occupied by the snake.

### 1. Best First Search

This Greedy Best-First Search algorithm has a one-move horizon and only considers moving the snake to the position on the board that appears to be closest to the goal, i.e. apple. We use Manhattan distance to deﬁne how close the snake head is to the apple. This method has almost guaranteed that the snake will be able to eat in an optimal (shortest) way at least the ﬁrst four apples.

However, the one-step horizon also makes it easy to get stuck on local minima and plateaus.The intuitive explanation is that, the snake only looks for the next step that is assumed best or closest to the apple without considering its tail. It is easy for the snake to bite itself once it gets longer. Eventually, this method will stop being optimal after the snake has eaten more than four apples.

![bfs](https://user-images.githubusercontent.com/8587332/34895417-86ce6f9c-f79a-11e7-82d2-66e4d64df88f.gif)

### 2. A* Search
A* incorporates a heuristic in a multiple move horizon. Before taking action, it considers not only where the goal
is and how far it is, but also the current state it has searched so far.
This A* algorithm uses the Manhattan distance from the head to the apple as a heuristic and the number of
steps as the “cost so far”. Each iteration of the algorithm lasts until a path is found that leads the snake to eat an apple. It improves the Best First Search algorithm by ﬁnding a full path to the apple and not stopping at the ﬁrst move, this has the advantage of not getting stuck at a dead end on the way to the apple. Without memory or time restrictions, the algorithm is guaranteed to ﬁndan optimal path to the apple if one exists.
However, the maximum number of nodes  expanded is limited. This makes the algorithm stop if a path to goal cannot be found (for any reason). In case the maximum nodes bound is reached, the algorithm will switch back to Best First Search for that iteration.

### Pseudocode
``` 
if (can eat food)
      Send a virtual snake to eat,
             If (after eating can follow the tail) really snake to eat
             If (can not follow the tail after eating) True snake followed the tail
else
     True snake followed the tail
if (can not eat food can not follow the tail) Random stroll,
```
![ezgif com-crop](https://user-images.githubusercontent.com/8587332/34896161-94f66b8a-f79d-11e7-8b01-7e69ca7c065b.gif)

However there is a higher probability of a situation that the snake head will often turn around the tail.
![automatedsnakegamesolvers2](https://user-images.githubusercontent.com/8587332/34896799-12aad906-f7a0-11e7-80fb-5a888692219c.jpg)

### 3. A* Search with Forward Checking
The A* algorithm still has some shortcomings. One of the shortcomings includes the fact that, once the apple is eaten, the snake can reach a dead end which can be avoided with other paths. In other words, the algorithm does not take into account the effects of the selected path once the apple is eaten.
![automatedsnakegamesolvers](https://user-images.githubusercontent.com/8587332/34896467-d58ffa48-f79e-11e7-8bb6-b0b7465e29ce.jpg)
To avoid these dead ends, the A* algorithm is also equipped with a Breadth First Search algorithm that is used to compute if a path to a goal also leads to a dead end. Once an iteration of the A* algorithm ends, it will then call the Breadth First Search algorithm starting at the goal state found by the A*algorithm.   
From here,it will explore the full tree up to a certain number of nodes. If the tree is contained inside this node bound,i.e. it is a dead end, the path to the apple is rendered as not good, and the goal node from the A *algorithm will be discarded (the A* iteration will continue though).
This dead end check is also used when the A*algorithm cannot ﬁnd a path to the goal. It will then select the Best First direction that does not lead to a dead end.

Steps are as follows

``` 
// Will start node into the open table while (open table is not empty) {
       0. Find the largest F value in the open table (Description from the target farthest), if we have the same choice in the back of the row is the latest   addition of. 
	   1. Remove the current node from the open list and add it to the closed list. 
	   2. Traverse the neighboring nodes in all four directions 
	      (0). If the neighboring node is not accessible or the neighboring node is already in the closed list, what actions 
		  (1) If the neighbor node is not in the open list, add the node to the open list and set the parent node of the neighbor node as the current node,  and save the phase G and H values of neighboring nodes [0] When the destination node is added to the open list as the node to be checked, the path is found, and the loop is terminated and the direction is returned. 
		  (2) If the neighboring node is in an open list, It is determined whether the G value of the neighboring node arriving at the neighboring node via the current node is greater than or less than the G value originally stored in the neighboring node, and if the G value is greater than or smaller than the G value of the neighboring node, the parent node of the adjacent node is set as the current node , And reset the G and H values of the neighboring nodes} 
     
// When the open list is empty, indicating that no new node can be added, and there is no end node in the tested node, the path can not be found, at this time End -1 ring;
```
![ezgif com-crop 1](https://user-images.githubusercontent.com/8587332/34896912-72b7b350-f7a0-11e7-8c0d-b7d4f48f9bcc.gif)
![ezgif com-crop 2](https://user-images.githubusercontent.com/8587332/34896964-a0dd4d3a-f7a0-11e7-8a9b-c93c0e9258c7.gif)
![ezgif com-crop 3](https://user-images.githubusercontent.com/8587332/34897059-1832ba82-f7a1-11e7-8d0c-1f58ed6bbb3b.gif)
![ezgif com-crop 4](https://user-images.githubusercontent.com/8587332/34897090-354a6b6a-f7a1-11e7-807d-fb78e8c2daf0.gif)

From the last result, we can see that because of the randomly generated food, we can only optimize the food appearing algorithm or the path finding algorithm. (Want to eat full map, you can always go S Road, but this boring).
