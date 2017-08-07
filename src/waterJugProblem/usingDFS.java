/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waterJugProblem;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author user
 */
public class usingDFS {
	
	public static int MAX_CAPACITY_OF_LEFT_JUG, MAX_CAPACITY_OF_RIGHT_JUG, MAX_DEPTH_OF_TREE;
	public static Stack<Node> stack = new Stack<>();
	public static Set<Node> alreadyEncountered;
	
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the capacities of the two jugs");
		System.out.println("The capacity of the larger jug should be followed by the capacity of the smaller jug and they should not be equal");
        MAX_CAPACITY_OF_LEFT_JUG = sc.nextInt();
        MAX_CAPACITY_OF_RIGHT_JUG = sc.nextInt();
		MAX_DEPTH_OF_TREE = 10;
        System.out.println("Enter the required capacity");
        int t = sc.nextInt();
		int level = 0;
		Node root = new Node(0, 0);
		stack.push(root);
		alreadyEncountered = new HashSet<>();
		alreadyEncountered.add(root);
		Stack<Integer> keepsTrackOfCurrentLevel = new Stack<>();
		keepsTrackOfCurrentLevel.push(0);
		boolean solvedFlag = false;
		int numberOfNodesTraversed = 0;
		while(!stack.isEmpty()){
			numberOfNodesTraversed++;
			int tempVariableForChildren = 0;
			Node node = stack.pop();
			int currentLevel = keepsTrackOfCurrentLevel.pop();
			if(node.x == t || node.y == t){
				System.out.println("The required capacity can be achieved after "+currentLevel+" move(s)");
				System.out.println("Number of nodes traversed: "+numberOfNodesTraversed);
				solvedFlag = true;
				break;
			}
			if(currentLevel > MAX_DEPTH_OF_TREE){
				continue;
			}
			if(node.x == MAX_CAPACITY_OF_LEFT_JUG && node.y == 0){
				Node newNode = new Node(MAX_CAPACITY_OF_LEFT_JUG-MAX_CAPACITY_OF_RIGHT_JUG, MAX_CAPACITY_OF_RIGHT_JUG);
				if(checkIfEncountered(newNode)){
					keepsTrackOfCurrentLevel.push(currentLevel+1);
				}
			} else if(node.y == MAX_CAPACITY_OF_RIGHT_JUG && node.x == 0){
				Node newNode = new Node(MAX_CAPACITY_OF_RIGHT_JUG, 0);
				if(checkIfEncountered(newNode)){
					keepsTrackOfCurrentLevel.push(currentLevel+1);
				}
			} else if(node.x == MAX_CAPACITY_OF_LEFT_JUG && node.y == MAX_CAPACITY_OF_RIGHT_JUG){
				Node newNode1 = new Node(0, MAX_CAPACITY_OF_RIGHT_JUG);
				if(checkIfEncountered(newNode1)){
					keepsTrackOfCurrentLevel.push(currentLevel+1);
				}
				Node newNode2 = new Node(MAX_CAPACITY_OF_LEFT_JUG, 0);
				if(checkIfEncountered(newNode2)){
					keepsTrackOfCurrentLevel.push(currentLevel+1);
				}
			} else if(node.x != 0 && node.y != 0){
				Node newNode1 = new Node(0, node.y);
				if(checkIfEncountered(newNode1))
					keepsTrackOfCurrentLevel.push(currentLevel+1);
				Node newNode2 = new Node(node.x, 0);
				if(checkIfEncountered(newNode2))
					keepsTrackOfCurrentLevel.push(currentLevel+1);
				Node newNode3 = new Node(node.x-MAX_CAPACITY_OF_RIGHT_JUG+node.y, MAX_CAPACITY_OF_RIGHT_JUG);
				if(checkIfEncountered(newNode3))
					keepsTrackOfCurrentLevel.push(currentLevel+1);
			} else if(node.x != 0){						// => y=0
				if(node.x>MAX_CAPACITY_OF_RIGHT_JUG){
					Node newNode = new Node(node.x-MAX_CAPACITY_OF_RIGHT_JUG, MAX_CAPACITY_OF_RIGHT_JUG);
					if(checkIfEncountered(newNode))
						keepsTrackOfCurrentLevel.push(currentLevel+1);
				} else{
					Node newNode = new Node(0, node.x);
					if(checkIfEncountered(newNode))
						keepsTrackOfCurrentLevel.push(currentLevel+1);
					Node newNode2 = new Node(MAX_CAPACITY_OF_LEFT_JUG, node.x);
					if(checkIfEncountered(newNode2))
						keepsTrackOfCurrentLevel.push(currentLevel+1);
					Node newNode3 = new Node(node.x, MAX_CAPACITY_OF_RIGHT_JUG);
					if(checkIfEncountered(newNode3))
						keepsTrackOfCurrentLevel.push(currentLevel+1);
				}
			} else if(node.y != 0){						// => x=0
				Node newNode = new Node(node.y, 0);
				if(checkIfEncountered(newNode))
					keepsTrackOfCurrentLevel.push(currentLevel+1);
				Node newNode2 = new Node(node.y, MAX_CAPACITY_OF_RIGHT_JUG);
				if(checkIfEncountered(newNode2))
						keepsTrackOfCurrentLevel.push(currentLevel+1);
				Node newNode3 =  new Node(MAX_CAPACITY_OF_LEFT_JUG, node.y);
				if(checkIfEncountered(newNode3))
					keepsTrackOfCurrentLevel.push(currentLevel+1);
			} else {									// When x and y are both 0 
				Node newNode1 = new Node(MAX_CAPACITY_OF_LEFT_JUG, 0);
				if(checkIfEncountered(newNode1)){
					keepsTrackOfCurrentLevel.push(currentLevel+1);
				}
				Node newNode2 = new Node(0, MAX_CAPACITY_OF_RIGHT_JUG);
				if(checkIfEncountered(newNode2)){
					keepsTrackOfCurrentLevel.push(currentLevel+1);
				}
			}
		}
		if(!solvedFlag){
			System.out.println("The desired capacity was not achieved within a tree depth of 5");
			System.out.println("Number of nodes traversed: "+numberOfNodesTraversed);
		}
	}
	
	public static boolean checkIfEncountered(Node node){
		if(!alreadyEncountered.contains(node)){
			stack.add(node);
			alreadyEncountered.add(node);
			return true;						//Child added to the queue
		}
		return false;							//No new child added to the queue
	}
}
