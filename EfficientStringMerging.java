//Izabela Golec gr4
import java.util.Scanner;

/*in the following solution of merging n sorted strings, we use their sorting and create an ascending heap (nlogn) from the first
elements of all strings (actually from the class in which we store the values and properties of these elements), when the smallest
value is fetched and given to the output array we check if the string it comes from has more elements, if it does, we take the next
one and put it in the heap in the right place, if not, we take the maximum int value and put it at the end of the heap, it will
never be downloaded from it because the loop will execute as many times as we have elements, which gives us a complexity of m*nlog(n).
*/

class Sequence
{
    int value;
    int actualIndex;
    int sizeOfSequence;
    int whichArray;
    //each element of the sequence will be represented by a Sequence object which will store the description below the properties of that element
    public Sequence(int value_, int index_, int size_, int whichArray_)
    {
        value = value_; //value from base array
        actualIndex = index_; //actual index in heap
        sizeOfSequence = size_;
        whichArray = whichArray_; //info from which table is the string
    }
}
public class Source
{
    static class MinHeap
    {
        int[][] base_array; //sequence table
        public int result_size; //the number of all numbers in all trains
        Sequence[] sequences; // each number will be an object, thanks to which we will have information about which sequence it comes from and whether the sequence has ended
        int[] result_array; //result array
        int[] array_of_lengths; //array with lenghts of sequences
        MinHeap(int[][] base, int number_of_sequences, int[] result, int[] lengths) //constructor
        {
            result_size = result.length;
            base_array = base;
            result_array = new int [result_size];
            array_of_lengths = lengths;
            sequences = new Sequence[number_of_sequences];
            for (int i = 0; i < number_of_sequences; i++)
            {
                sequences[i] =  new Sequence(base_array[i][0], 0, array_of_lengths[i], i); //creating a heap from the first elements of a string
            }
            for (int i = (number_of_sequences - 1) / 2; i >= 0; i--) {
                heapify(sequences, number_of_sequences, i);
            }
        }
        void heapify(Sequence[] array, int n, int index)
        {
            int min = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            if (left < n && array[left].value < array[min].value)
                min = left;
            if (right < n && array[right].value < array[min].value)
                min = right;
            if (min != index) {
                Sequence swap = array[index];
                array[index] = array[min];
                array[min] = swap;
                heapify(array, n, min);
            }
        }
        int[] SortHeap()
        {
            int n = result_size;
            int i = 0;
            while (i < n)
            {
                result_array[i] = sequences[0].value; //assigning the result array to the root of the heap (which is the smallest value)
                sequences[0].actualIndex++; //increment the currently selected index in a given sequence
                //if it is in range, we take the next element of the sequence
                if (sequences[0].actualIndex < sequences[0].sizeOfSequence)
                {
                    sequences[0].value = base_array[sequences[0].whichArray][sequences[0].actualIndex];
                    heapify(sequences, sequences.length, 0);
                }
                //otherwise we take Integer.MAX_VALUE which will be skipped
                else
                {
                    sequences[0].value = Integer.MAX_VALUE;
                    heapify(sequences, sequences.length, 0);
                }
                i++;
            }
            return result_array;
        }
    }
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args)
    {
        int z = in.nextInt();  //number of sets

        for (int i = 0; i < z; i++) {

            int number_of_sequences = in.nextInt();//number of sequences
            int[] array_of_lengths = new int[number_of_sequences]; //a 1D array of the length of the given sequences
            int[][] array_of_numbers = new int[number_of_sequences][]; //2D array for given sequences

            int resultingArraySize = 0; //the size of the result array that will contain all the strings
            for (int j = 0; j < number_of_sequences; j++)
            {
                array_of_lengths[j] = in.nextInt(); //enter the length of string no. j
                array_of_numbers[j] = new int[array_of_lengths[j]]; //declaring the size of a 2D array
                resultingArraySize+=array_of_lengths[j]; //zwiekszenie rozumiaru tabicy wynikowej
            }
            for(int j = 0; j < number_of_sequences; j++) //writing in
            {
                for (int m = 0; m < array_of_lengths[j]; m++)
                {
                    array_of_numbers[j][m] = in.nextInt();
                }
            }

            int[] output_array = new int[resultingArraySize];
            MinHeap heap = new MinHeap(array_of_numbers, number_of_sequences , output_array, array_of_lengths);
            output_array = heap.SortHeap();
            for(int j = 0; j < resultingArraySize; j++){    //output
                System.out.print(output_array[j] + " ");
            }
            System.out.println();
        }
    }
}

/*
*
* in:
3
4
2 5 3 10
0 1
2 3 5 7
13
2 4 6
1 2 3 4 5 6 7
8 9 10
4
2 1 1 1
0 15
1
13
2
4
1 1 1 1
0
1
13
2
out:
0 1 1 2 2 2 3 3 4 4 5 5 6 6 7 7 8 9 10 13
0 1 2 13 15
0 1 2 13
* */