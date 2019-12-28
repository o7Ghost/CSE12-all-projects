#include <stdio.h>
#include <string.h>
#include "TQTree.h"

// A safe way to read string data from the user
void readAnswer(char* data, int length) {
    char ch;
    fgets(data, length, stdin);
    if (data[strlen(data)-1] != '\n') {
        // If we didn't read the whole line, eat the rest of the line
        while (((ch = getchar()) != '\n')  && (ch != EOF));
    }
    else {
        // Otherwise get rid of the newline we read
        data[strlen(data)-1] = '\0';
    }
}

// Make a new node to hold the data in data
TQNode* newNode(char* data) {
    // Try to allocate vector structure.
    TQNode *currNode = malloc(sizeof(TQNode));
    if (currNode == NULL)
        return NULL;

    // Try to allocate vector data, free structure if fail.
    currNode->data = malloc(MAX_LENGTH * sizeof (char));
    if (currNode->data == NULL) {
        free(currNode);
        return NULL;
    }
    
    strcpy(currNode->data, data);
    currNode->noChild = NULL;
    currNode->yesChild = NULL;
    return currNode;
}

// Build a new tree
TQTree* newTree() {
    TQTree *tree = malloc (sizeof (TQTree));
    return tree;
}

// Free the memory in the Tree
void delTree(TQTree* tree) {
    printf("Freeing the game tree\n");
    delTreeHelper(tree->root);
    free(tree);
}

// Recursively delete the nodes and the data strings 
// in the tree via a post-order traversal.
// This method should:
//    do nothing and return if root is NULL
//    else it should:
//        recursively delete root's yesChild
//        recursively delete root's noChild
//        delete (free) root's data
//        delete (free) the root
void delTreeHelper(TQNode* root) {
    
  if (root == NULL) {
    return;
  }

  else {
      
    //deleting the tree
    delTreeHelper(root->yesChild);
    delTreeHelper(root->noChild);
    free(root->data);
    free(root);
  }
}

void buildDefaultTree(TQTree* tree) {
    tree->root = newNode("Is it bigger than a breadbox?");
    tree->root->noChild = newNode("spam");
    tree->root->yesChild = newNode("a computer scientist");
}


void save(struct TQTree* tree, char* filename) {
    FILE* fptr = fopen(filename, "w");
    if (fptr == NULL) {
        printf("Error opening file!\n");
        exit(1);
    }
    saveTree(tree->root, fptr);
    fclose(fptr);
}

void saveTree(struct TQNode* node, FILE* fptr) {
    if (node == NULL) {
        return;
    }
    if (node->noChild == NULL && node->yesChild == NULL) {
        fprintf(fptr, "A:%s\n", node->data);
    } else {
        fprintf(fptr, "Q:%s\n", node->data);
    }
    saveTree(node->noChild, fptr);
    saveTree(node->yesChild, fptr);
}

//Play one round of the game of Twenty Questions using this game tree Augments
//he tree if the computer does not guess the right answer
void play(TQTree* tree) {

  //variables
  TQNode *previous = NULL;
  TQNode *current = tree->root;
  char response[MAX_LENGTH];
  char guesses[MAX_LENGTH];

  //iterate till the leaf node
  while (current->noChild != NULL) {
    
    previous = current;

    printf("%s\n", current->data);

    readAnswer(response, 2);

    if (!strcmp(response, "y")) {

      current = current->yesChild;
    }

    if (!strcmp(response, "n")) {
	    
      current = current->noChild;
    }
  }

  //try to guess user answer
  printf("is it %s\n", current->data);
  readAnswer(guesses, 2);

  if (!strcmp(guesses, "y")) {
      
    printf("Got It!\n");
  }

  if (!strcmp(guesses, "n")) {
      
    guess(previous, response);
  }
}

//guess user answer and add questions
void guess (TQNode* previous, char* respond) {

  //local variables
  TQNode *answer = NULL;
  TQNode *newAnswer = NULL;
  TQNode *newQuestion = NULL;
  char resp[MAX_LENGTH];

  //save old answer
  if (!strcmp(respond, "y")) {
    answer = previous->yesChild;
  }

  if (!strcmp(respond, "n")) {
    answer = previous->noChild;
  }
    
  //ask user what the new answer is
  //and is the answer should be yes or no
  printf("Ok, what is it?\n");

  readAnswer(resp, MAX_LENGTH);

  newAnswer = newNode(resp);

  printf("Give me a question that would distinguish %s from %s\n", 
	  newAnswer->data, answer->data);

  readAnswer(resp, MAX_LENGTH);

  newQuestion = newNode(resp);

  printf("And would the answer to the question %s be yes or no?\n",
          newAnswer->data);
           
  readAnswer(resp, 2);
   
  //set question's pointer
  if (!strcmp(respond, "y")) {
      
    previous->yesChild = newQuestion;
    setPointers(resp, answer, newQuestion, newAnswer);
  }

  if (!strcmp(respond,"n")) {

    previous->noChild = newQuestion;
    setPointers(resp, answer, newQuestion, newAnswer);
  }
}

//set pointers for the answers
void setPointers(char* responses, TQNode* answer,
		  TQNode* newQuestion, TQNode* newAnswer) {

  if (!strcmp(responses, "y")) {
    
     newQuestion->yesChild = newAnswer;
     newQuestion->noChild = answer;
  }

  if (!strcmp(responses, "n")) {
      
    newQuestion->noChild = newAnswer;
    newQuestion->yesChild = answer;
  }
}

void print(TQTree* tree) {
    int index = 0;
    tree->root->idx = index;
    TQLink  *present = newLinkNode(tree->root);
    recursivelyPrint(tree->root, present, index);
    TQLink *loop = present;
    
      printf("%s", loop->link->node->data);
    while (loop != NULL) {

      if (loop->node->noChild != NULL) {
        printf("%d: '%s' no:(%d) yes:(%d)\n", loop->node->idx, loop->node->data, loop->node->noChild->idx, loop->node->yesChild->idx);
      }
      
      else {
        printf("%d: '%s' no:(null) yes:(null)\n", loop->node->idx, loop->node->data);
      }
        loop = loop->link;
   
    }
}

TQLink* newLinkNode (TQNode* nodes) {
   TQLink *current = malloc(sizeof(TQLink));
    current->node = nodes;
    current->link = NULL;
    return current;
}

int recursivelyPrint (TQNode* root, TQLink* pass, int index) {

    if (root->noChild == NULL) {
        
        
    //printf("%d: '%s' no:(null) yes:(null)\n", root->idx, root->data);
      return index;
    }
    
    else {
      index++;
      root->noChild->idx = index;
      index++;
      root->yesChild->idx = index;
      TQLink* firstChild = newLinkNode(root->noChild);
      TQLink* secondChild = newLinkNode(root->yesChild);
      pass->link = firstChild;
      firstChild->link = secondChild;
        //printf("%s", firstChild->link->node->data);
      //printf("%d: '%s' no:(%d) yes:(%d)\n", root->idx, root->data, root->noChild->idx, root->yesChild->idx);
      index = recursivelyPrint(root->noChild, secondChild,index);
      index = recursivelyPrint(root->yesChild, secondChild,index);
      return index;
    }
    
  }
