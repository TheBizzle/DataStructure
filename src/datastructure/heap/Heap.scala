package datastructure.heap

import annotation.tailrec
import java.lang.IllegalStateException

/**
 * Created by IntelliJ IDEA.
 * User: Jason
 * Date: 11/11/11
 * Time: 3:50 PM
 */
class Heap[T : Manifest] protected[datastructure] (ordering: (T, T) => Int, elemArr: Array[Option[T]]) {

    val orderProp = ordering
    var heapArr = elemArr     // Need to fix this var-age somehow (maybe) (nope)

    def this(ordering: (T, T) => Int) {
        this(ordering, new Array[Option[T]](Heap.BaseArrSize))
        initializeArr()
    }

    def clear() {
        heapArr = new Array[Option[T]](heapArr.size)
        initializeArr()
    }

    def insert(elem: T) {
        insertAtEnd(elem)
        heapUp(size - 1)
    }

    private def insertAtEnd(elem: T) {
        val arrSize = size
        if (arrSize >= heapArr.size) increaseArrSize()
        heapArr(arrSize) = Some(elem)
    }

    private def increaseArrSize() {
        val newArr = new Array[Option[T]](heapArr.size * Heap.LoadDiminishFactor)
        initializeArr(newArr, None)
        heapArr = arrTransfer(newArr, heapArr)
    }

    private def arrTransfer(newArr: Array[Option[T]], originalArr: Array[Option[T]]) : Array[Option[T]] = {
        @tailrec def arrTransferHelper(newArr: Array[Option[T]], originalArr: Array[Option[T]], originalSize: Int, counter: Int) : Array[Option[T]] = {
            if (counter < (originalSize - 1)) {
                newArr(counter) = originalArr(counter)
                arrTransferHelper(newArr, originalArr, originalSize, counter + 1)
            }
            else
                newArr
        }
        arrTransferHelper(newArr, originalArr, originalArr.size, 0)
    }

    @tailrec
    private def heapUp(elemIndex: Int) {
        if (elemIndex != 0) {
            val parentIndex = parentIndexOf(elemIndex)
            if (isBetter(elemIndex, parentIndex)) { swap(elemIndex, parentIndex); heapUp(parentIndex) }
        }
    }

    private def parentIndexOf(index: Int) : Int = {
        ((index-1)/2).floor.toInt
    }

    private def swap(startIndex: Int,  endIndex: Int) {
        val temp = heapArr(endIndex)
        heapArr(endIndex) = heapArr(startIndex)
        heapArr(startIndex) = temp
    }

    def remove() : T = {
        val retVal = heapArr(0)
        if (retVal == None) throw new NoSuchElementException
        val lastIndex = size - 1
        heapArr(0) = heapArr(lastIndex)
        heapArr(lastIndex) = None
        heapDown(0)
        retVal.get
    }

    @tailrec
    private def heapDown(elemIndex: Int) {
        if (!isLeaf(elemIndex)) {
            val childIndex = findBestChildIndex(elemIndex)
            if (isBetter(childIndex, elemIndex)) { swap(childIndex, elemIndex); heapDown(childIndex) }
        }
    }

    private def isLeaf(index: Int) : Boolean = {
        ((size == 0) || (depthOf(index) == depthOf(size - 1)) || (firstChildIndexOf(index) > (size - 1)))
    }

    private def depthOf(index: Int) : Int = {
        log2(index + 1).floor.toInt
    }

    private def log2(num: Int) : Double = {
        import scala.math.log
        log(num) / log(2)
    }

    private def findBestChildIndex(parentIndex: Int) : Int = {
        val first = firstChildIndexOf(parentIndex)
        val second = first + 1
        if (isBetter(first, second)) first else second
    }

    private def firstChildIndexOf(parentIndex: Int) : Int = {
        (2 * parentIndex) + 1
    }

    private def isBetter(firstIndex: Int, secondIndex: Int) : Boolean = {
        if (heapArr(firstIndex) == None) throw new IllegalStateException("What did you do to my heap?!")
        else if (heapArr(secondIndex) == None) true
        else orderProp(heapArr(firstIndex).get, heapArr(secondIndex).get) > 0
    }

    def peek : Option[T] = {
        heapArr(0)
    }

    // Sadly, it's preferable to just run this every time we want the size, rather than
    // juggling 'size' vals on the Heap reconstruction that occurs after each array resize
    def size : Int = {
        @tailrec def sizeHelper(arr: Array[Option[T]], currentSize: Int) : Int = {
            if ((arr.size > currentSize) && (!arr(currentSize).isEmpty))
                sizeHelper(arr, currentSize + 1)
            else
                currentSize
        }
        sizeHelper(heapArr, 0)
    }

    def isEmpty : Boolean = {
        size == 0
    }

    def foreach[U](f: (T => U)) {
        val tempArr = heapArr.foldRight(List[T]()){ case (x, acc) => if (x != None) x.get :: acc else acc }
        tempArr.foreach(f)
    }

    protected def initializeArr() {
        initializeArr(heapArr, None)
    }

    private def initializeArr(arr: Array[Option[T]], initVal: Option[T]) {
        for (i <- 0 until arr.size) {
            arr(i) = initVal
        }
    }

//    // For debugging right now (write correctly later)
//    override def toString : String = {
//
//        var outStr = ""
//        var counter = 0
//        var elem = heapArr(counter)
//
//        while (!elem.isEmpty) {
//            outStr += elem.asInstanceOf[PriorityCoordinate].priority + ","
//            counter += 1
//            elem = heapArr(counter)
//        }
//
//        "[" + outStr + "]"
//
//    }
//
//    // For debugging (not actually all that awesome)
//    def toAwesomeString : String = {
//        "[" + orderedListMaker.foldLeft("")((acc, x) => x.priority + "," + acc) + "]"
//    }
//
//    // For debugging
//    private def orderedListMaker : List[PriorityCoordinate] = {
//        var outList = List[PriorityCoordinate]()
//        val clonedHeap = clone()
//        while (!clonedHeap.isEmpty) {
//            outList = clonedHeap.remove().asInstanceOf[PriorityCoordinate] :: outList
//        }
//        outList.reverse
//    }

    override def clone() : Heap[T] = {
        new Heap[T](ordering, heapArr.clone())
    }

}

object Heap {
    protected[datastructure] val BaseArrSize = 10
    private val LoadDiminishFactor = 2
}
