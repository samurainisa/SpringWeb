package lab1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class BinarySearchTree<K : Comparable<K>, V> {
    private data class Node<K, V>(var key: K, var value: V, var left: Node<K, V>? = null, var right: Node<K, V>? = null)

    private var root: Node<K, V>? = null

    fun insert(key: K, value: V) {
        root = insert(root, key, value)
    }

    private fun insert(node: Node<K, V>?, key: K, value: V): Node<K, V> {
        node ?: return Node(key, value)

        when {
            key < node.key -> node.left = insert(node.left, key, value)
            key > node.key -> node.right = insert(node.right, key, value)
            else -> node.value = value
        }

        return node
    }

    fun delete(key: K) {
        root = delete(root, key)
    }

    private fun delete(node: Node<K, V>?, key: K): Node<K, V>? {
        node ?: return null

        when {
            key < node.key -> node.left = delete(node.left, key)
            key > node.key -> node.right = delete(node.right, key)
            else -> {
                when {
                    node.left == null && node.right == null -> return null
                    node.left == null -> return node.right
                    node.right == null -> return node.left
                    else -> {
                        val successor = findSuccessor(node.right!!)
                        node.key = successor.key
                        node.value = successor.value
                        node.right = delete(node.right, successor.key)
                    }
                }
            }
        }

        return node
    }

    private fun findSuccessor(node: Node<K, V>): Node<K, V> {
        var current = node
        while (current.left != null) {
            current = current.left!!
        }
        return current
    }

    fun search(key: K): V? {
        var current = root
        while (current != null) {
            when {
                key < current.key -> current = current.left
                key > current.key -> current = current.right
                else -> return current.value
            }
        }
        return null
    }

    fun traverseInOrder(action: (K, V) -> Unit) {
        traverseInOrder(root, action)
    }

    private fun traverseInOrder(node: Node<K, V>?, action: (K, V) -> Unit) {
        node ?: return
        traverseInOrder(node.left, action)
        action(node.key, node.value)
        traverseInOrder(node.right, action)
    }
}


class BinarySearchTreeTest {
    @Test
    fun `insert and search`() {
        val tree = BinarySearchTree<Int, String>()

        tree.insert(4, "four")
        tree.insert(2, "two")
        tree.insert(1, "one")
        tree.insert(3, "three")
        tree.insert(6, "six")
        tree.insert(5, "five")
        tree.insert(7, "seven")

        assertEquals("four", tree.search(4))
        assertEquals("one", tree.search(1))
        assertEquals("seven", tree.search(7))
        assertNull(tree.search(0))
        assertNull(tree.search(8))
    }

    @Test
    fun `delete`() {
        val tree = BinarySearchTree<Int, String>()

        tree.insert(4, "four")
        tree.insert(2, "two")
        tree.insert(1, "one")
        tree.insert(3, "three")
        tree.insert(6, "six")
        tree.insert(5, "five")
        tree.insert(7, "seven")

        tree.delete(4)
        assertNull(tree.search(4))
        assertEquals("one", tree.search(1))
        assertEquals("seven", tree.search(7))

        tree.delete(1)
        assertNull(tree.search(1))
        assertEquals("two", tree.search(2))
        assertEquals("three", tree.search(3))

        tree.delete(7)
        assertNull(tree.search(7))
        assertEquals("six", tree.search(6))
        assertEquals("five", tree.search(5))
    }

    @Test
    fun `traverseInOrder`() {
        val tree = BinarySearchTree<Int, String>()

        tree.insert(4, "four")
        tree.insert(2, "two")
        tree.insert(1, "one")
        tree.insert(3, "three")
        tree.insert(6, "six")
        tree.insert(5, "five")
        tree.insert(7, "seven")

        val expected = listOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five", 6 to "six", 7 to "seven")
        val actual = mutableListOf<Pair<Int, String>>()
        tree.traverseInOrder { key, value ->
            actual.add(key to value)
        }
        assertEquals(expected, actual)
    }
}

fun main() {
    val tree = BinarySearchTree<Int, String>()

    tree.insert(4, "four")
    tree.insert(2, "two")
    tree.insert(1, "one")
    tree.insert(3, "three")
    tree.insert(6, "six")
    tree.insert(5, "five")
    tree.insert(7, "seven")

    println("In-order traversal:")
    tree.traverseInOrder { key, value ->
        println("$key: $value")
    }

    val keyToDelete = 4
    tree.delete(keyToDelete)
    println("After deleting key $keyToDelete:")
    tree.traverseInOrder { key, value ->
        println("$key: $value")
    }

    val keyToSearch = 6
    val value = tree.search(keyToSearch)
    if (value != null) {
        println("Value of key $keyToSearch: $value")
    } else {
        println("Key $keyToSearch not found")
    }

    val newKey = 8
    val newValue = "eight"
    tree.insert(newKey, newValue)
    println("After inserting key $newKey:")
    tree.traverseInOrder { key, value ->
        println("$key: $value")
    }


}
