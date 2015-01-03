#include <stdio.h>
#include <stdlib.h>
#define MAX_Q_SIZE 500

/*  We use BFS in trees when we can't have cycles,
    meaning that we can't return in the same position*/

/*  A binary tree node has data, pointer to left child
    and a pointer to right child */
struct node {
    int data;
    struct node* left;
    struct node* right;
};

struct node** create_queue(int *front, int *rear) {
    struct node **queue = (struct node **) malloc(sizeof(struct node*)*MAX_Q_SIZE);
    *front = *rear = 0;
    return queue;
}

void enqueue(struct node **queue, int *rear, struct node *new_node) {
    queue[*rear] = new_node;
    (*rear)++;
}

struct node *dequeue(struct node **queue, int *front) {
    (*front)++;
    return queue[*front - 1];
}

/* Given a binary tree, print its nodes in level order using array for implementing queue */
void print_level_order(struct node* root) {
    int rear, front;
    struct node **queue = create_queue(&front, &rear);
    struct node *temp_node = root;

    while(temp_node) {
        printf("%d ", temp_node->data);

        /*Enqueue left child */
        if(temp_node->left) enqueue(queue, &rear, temp_node->left);

        /*Enqueue right child */
        if(temp_node->right) enqueue(queue, &rear, temp_node->right);

        /*Dequeue node and make it temp_node*/
        temp_node = dequeue(queue, &front);
    }
}

/* Helper function that allocates a new node with the
   given data and NULL left and right pointers. */
struct node* new_node(int data) {
    struct node* node = (struct node*) malloc(sizeof(struct node));
    node->data = data;
    node->left = NULL;
    node->right = NULL;

    return(node);
}

int main() {
    struct node *root = new_node(1);
    root->left        = new_node(2);
    root->right       = new_node(3);
    root->left->left  = new_node(4);
    root->left->right = new_node(5);

    printf("Level Order traversal of binary tree is \n");
    print_level_order(root);

    return 0;
}
