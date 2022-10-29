#include <iostream>
#include "assign2.hpp"
#include "assign2_exception.hpp"
#include "assign2_tree.hpp"

void maintain(tree_node *node);

void l_rotate(tree_node *node);

void r_rotate(tree_node *node);

tree_node *init_node(tree_node *father, tree_node *child, uint64_t data);

assign2_exception::exception add_node(tree_node *father, tree_node *child, int child_direction) {
    assign2_exception::exception e = 0;
    // exception handle
    if (!father || !child) {
        e += NULL_POINTER_EXCEPTION;
    }
    if (child_direction != CHILD_DIRECTION_LEFT && child_direction != CHILD_DIRECTION_RIGHT) {
        e += INVALID_CHILD_DIRECTION_EXCEPTION;
    } else {
        if (father) {
            if (child_direction == CHILD_DIRECTION_LEFT && father->l_child) {
                e += DUPLICATED_LEFT_CHILD_EXCEPTION;
            }
            if (child_direction == CHILD_DIRECTION_RIGHT && father->r_child) {
                e += DUPLICATED_RIGHT_CHILD_EXCEPTION;
            }
        }
    }
    if (child && child->father) {
        e += DUPLICATED_FATHER_EXCEPTION;
    }
    // operation
    if (e == 0) {
        if (child_direction == CHILD_DIRECTION_LEFT) {
            father->l_child = child;
        } else {
            father->r_child = child;
        }
        child->father = father;
        tree_node *tmp = child;
        while (tmp) {
            maintain(tmp);
            tmp = tmp->father;
        }
    }
    return e;
}

assign2_exception::exception judge_child_direction(tree_node *node, int *child_direction) {
    assign2_exception::exception e = 0;
    // exception handle
    if (!node || !child_direction) {
        e += NULL_POINTER_EXCEPTION;
    }
    if (node && !node->father) {
        e += ROOTS_FATHER_EXCEPTION;
    }
    // operation
    if (e == 0) {
        if (node->father->l_child == node) {
            *child_direction = CHILD_DIRECTION_LEFT;
        } else {
            *child_direction = CHILD_DIRECTION_RIGHT;
        }
    }
    return e;
}

assign2_exception::exception insert_into_BST(BST *bst, uint64_t data, tree_node **inserted_node) {
    assign2_exception::exception e = 0;
    // exception handle
    if (!bst || !inserted_node) {
        e += NULL_POINTER_EXCEPTION;
    }
    if (bst && !bst->comp) {
        e += NULL_COMP_FUNCTION_EXCEPTION;
    }
    // insert
    if (e == 0) {
        tree_node *tmp = bst->root;
        if (tmp) {
            while (1) {
                if (bst->comp(data, tmp->data) == 0) {
                    tmp->node_count++;
                    tmp->tree_count++;
                    break;
                } else if (bst->comp(data, tmp->data) > 0) {
                    if (tmp->r_child) {
                        tmp = tmp->r_child;
                    } else {
                        tmp->r_child = init_node(tmp, tmp->r_child, data);
                        tmp = tmp->r_child;
                        break;
                    }
                } else {
                    if (tmp->l_child) {
                        tmp = tmp->l_child;
                    } else {
                        tmp->l_child = init_node(tmp, tmp->l_child, data);
                        tmp = tmp->l_child;
                        break;
                    }
                }
            }
            *inserted_node = tmp;
            while (tmp->father) {
                tmp = tmp->father;
                maintain(tmp);
            }
        } else {
            bst->root = init_node(NULL, bst->root, data);
            *inserted_node = bst->root;
        }
    }
    return e;
}

assign2_exception::exception find_in_BST(BST *bst, uint64_t data, tree_node **target_node) {
    assign2_exception::exception e = 0;
    // exception handle
    if (!bst || !target_node) {
        e += NULL_POINTER_EXCEPTION;
    }
    if (bst && !bst->comp) {
        e += NULL_COMP_FUNCTION_EXCEPTION;
    }
    if (e == 0) {
        tree_node *tmp = bst->root;
        *target_node = NULL;
        while (tmp) {
            if (bst->comp(data, tmp->data) == 0) {
                *target_node = tmp;
                break;
            } else if (bst->comp(data, tmp->data) > 0) {
                tmp = tmp->r_child;
            } else {
                tmp = tmp->l_child;
            }
        }
    }
    return e;
}

assign2_exception::exception splay(BST *bst, tree_node *node) {
    assign2_exception::exception e = 0;
    // exception handle
    if (!bst || !node) {  // FIXME All exception
        e += NULL_POINTER_EXCEPTION;
    }
    if (bst && !bst->comp) {
        e += NULL_COMP_FUNCTION_EXCEPTION;
    }
    if (node && bst) {
        tree_node *tmp = node;
        while (tmp->father) {
            tmp = tmp->father;
        }
        if (tmp != bst->root) {
            e += SPLAY_NODE_NOT_IN_TREE_EXCEPTION;
        }
    }
    if (e == 0) {
        int *dir1 = new int, *dir2 = new int;
        while (node->father) {
            if (!node->father->father) {
                break;
            }
            judge_child_direction(node, dir1);
            judge_child_direction(node->father, dir2);
            if (*dir1 == *dir2) {
                if (*dir1 == CHILD_DIRECTION_LEFT) {
                    r_rotate(node->father);
                    r_rotate(node);
                } else {
                    l_rotate(node->father);
                    l_rotate(node);
                }
            } else if (*dir1 == CHILD_DIRECTION_LEFT) {
                r_rotate(node);
                l_rotate(node);
            } else {
                l_rotate(node);
                r_rotate(node);
            }
        }
        if (node->father) {
            judge_child_direction(node, dir1);
            if (*dir1 == CHILD_DIRECTION_LEFT) {
                r_rotate(node);
            } else {
                l_rotate(node);
            }
        }
        bst->root = node;
        delete dir1;
        delete dir2;
    }
    return e;
}

tree_node *init_node(tree_node *father, tree_node *child, uint64_t data) {
    child = new tree_node;
    child->father = father;
    child->data = data;
    child->l_child = NULL;
    child->r_child = NULL;
    child->tree_count = 1;
    child->node_count = 1;
    return child;
}

void l_rotate(tree_node *node) {
    tree_node *tmp = node->father;
    tmp->r_child = node->l_child;
    if (node->l_child) {
        node->l_child->father = tmp;
    }
    node->father = tmp->father;
    if (tmp->father) {
        int *dir = new int;
        judge_child_direction(tmp, dir);
        if (*dir == CHILD_DIRECTION_LEFT) {
            tmp->father->l_child = node;
        } else {
            tmp->father->r_child = node;
        }
        delete dir;
    }
    node->l_child = tmp;
    tmp->father = node;
    maintain(tmp);
    maintain(node);
}

void r_rotate(tree_node *node) {
    tree_node *tmp = node->father;
    tmp->l_child = node->r_child;
    if (node->r_child) {
        node->r_child->father = tmp;
    }
    node->father = tmp->father;
    if (tmp->father) {
        int *dir = new int;
        judge_child_direction(tmp, dir);
        if (*dir == CHILD_DIRECTION_LEFT) {
            tmp->father->l_child = node;
        } else {
            tmp->father->r_child = node;
        }
        delete dir;
    }
    node->r_child = tmp;
    tmp->father = node;
    maintain(tmp);
    maintain(node);
}

// update tree_count
void maintain(tree_node *node) {
    node->tree_count = node->node_count;
    if (node->l_child) {
        node->tree_count += node->l_child->tree_count;
    }
    if (node->r_child) {
        node->tree_count += node->r_child->tree_count;
    }
}