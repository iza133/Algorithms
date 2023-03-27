//Izabela Golec
import java.util.Scanner;

/*The program deals with a popular problem of packing a backpack.
The program contains a recursive and iterative solution. In both solutions, I go from the initial elements,
checking all possible combinations, until I come across a case that does the job. If after going through all
possible combinations such a case has not been found, it means that there is no such solution. In the iterative part,
I use the stack for auxiliary purposes, and in the recursion, an additional array. These auxiliary structures remember
the indexes in the table, which are included in the current solution, if the temporary sum is too large, we subtract
from it the last value that is hidden under the index from the top of the stack (in the case of a stack) and we pull
the index from the stack, and in the case of an array, these combinations are checked differently (recursion is called
for each possible combination, so if the sum is exceeded, the program will abort))*/

public class Source
{
    // a global variable telling us if the sum was found
    public static boolean is_found= false;
    public static Scanner in = new Scanner(System.in);
    //stack
    static class Stos
    {
        private int maxSize;
        public int[] Stos;
        private int t;
        public Stos (int size)
        {
            maxSize = size;
            Stos = new int[maxSize];
            t = -1;
        }
        public void push(int x)
        {
            t = t + 1;
            Stos[t] = x;
        }
        public void pop()
        {
            Stos[t] = '0';
            t = t - 1;
        }
        public int size()
        {
            return t;
        }
        public int top()
        {
            return Stos[t];
        }
        public boolean isempty()
        {
            return t == -1;
        }
    }

    //Recursive function
    public static void rec_pakuj(int[] array, int tmp_sum, int index, int sum, int[] help_array)
    {
        if (is_found)
            return;
        else if (tmp_sum == sum) //Final condition and summation
        {
            is_found = true;
            System.out.print("REC:  "+ sum + " =");
            for (int j = 0; j < help_array.length; j++)
                if (help_array[j] == 1)
                    System.out.print(" " + array[j]);
            System.out.println();
        }
        else if (tmp_sum > sum || index == array.length) //This combination is not OK
            return;
        else
        {
            help_array[index] = 1;// remember the index
            tmp_sum += array[index];
            rec_pakuj(array, tmp_sum, index + 1, sum, help_array);
            tmp_sum -= array[index];
            help_array[index] = 0;// doesn't remember the index, keep going
            rec_pakuj(array, tmp_sum, index + 1, sum, help_array);
        }
    }

    public static void iter_pakuj(int[] array, int sum)
    {
        int tmp_sum = 0; //temporary sum
        int actualIndex = 0;
        int firstIndex = 0;
        Stos stos = new Stos(256);

        stos.push(0);
        tmp_sum += array[0];

        while(!stos.isempty())
        {
            if (tmp_sum == sum) //Found sum
            {
                System.out.print("ITER: " + sum + " =");
                int[] result_array = new int [stos.size()+1];
                //we write the values from the indexes from the stack into the array to print them in the right order
                for (int k = 0; k < result_array.length; k++)
                {
                    result_array[k] = array[stos.top()];
                    stos.pop();
                }
                for (int k = result_array.length-1; k >= 0; k--)
                {
                    System.out.print(" " + result_array[k]);
                }
                System.out.println();
                break;
            }
            else if (tmp_sum > sum) //temporary sum is too big
            {
                tmp_sum -= array[stos.top()]; //we pop one element
                stos.pop();
                if(stos.top() == firstIndex) //There is only one element on the stack so we remove it because it has already been checked
                {                               //On the stack there will be still one more item, but next than the previous one
                    tmp_sum -= array[stos.top()];
                    stos.pop();
                    firstIndex++;
                    if(firstIndex == array.length) //We have gone through all possibilities and there is no solution
                        break;
                    stos.push(firstIndex);
                    tmp_sum += array[stos.top()];
                    actualIndex = firstIndex;
                }
                else //There is more than 1 item in the stack and tmp_sum > sum
                {
                    actualIndex++;
                    if(actualIndex == array.length)
                    {
                        int ind = stos.top();
                        tmp_sum -= array[ind];
                        stos.pop(); //pop one more element to start checking other possibilities, actual index set to what we poped + 1;
                        tmp_sum += array[ind+1];
                        stos.push(ind+1);
                        actualIndex = ind + 1;
                    }
                    else // if it does not go out of range, we add the next element
                    {
                        stos.push(actualIndex);
                        tmp_sum += array[actualIndex];
                    }
                }
            }
            else //The provisional sum is too small
            {
                actualIndex++;
                if(actualIndex == array.length) //similar situation as above
                {
                    tmp_sum -= array[stos.top()];
                    stos.pop();
                    int ind = stos.top();
                    tmp_sum -= array[stos.top()];
                    stos.pop();
                    tmp_sum += array[ind+1];
                    stos.push(ind+1);
                    actualIndex = ind + 1;
                }
                else //We add another element
                {
                    stos.push(actualIndex);
                    tmp_sum += array[actualIndex];
                }
            }
        }
    }


    public static void main(String[] args)
    {
        int z = in.nextInt();
        for (int i = 0; i < z; i++)
        {
            int sum = in.nextInt();
            int n = in.nextInt();
            int[] array =new int [n];
            int[] result_array =new int [n];

            for (int j = 0; j < n; j++)
                array[j] = in.nextInt();

            rec_pakuj (array, 0, 0, sum, result_array);
            if(is_found)
                iter_pakuj(array, sum);
            else
                System.out.println("BRAK");
            is_found = false;
        }
    }
}
/*
test.in
7
11
18
6 2 2 6 3 5 9 11 7 3 7 7 3 3 5 6 5 4
9
6
6 2 3 4 6 9
11
12
2 8 7 5 11 1 2 4 2 11 7 8
24
9
3 5 2 1 3 5 7 9 10
22
19
19 16 10 10 8 22 16 4 22 20 5 11 14 2 7 8 7 16 15
24
19
14 18 12 13 8 3 4 15 17 15 24 22 1 19 23 22 21 19 6
900
10
234 221 34 221 9 23 123 30 901 2

test.out
REC:  11 = 6 2 3
ITER: 11 = 6 2 3
REC:  9 = 6 3
ITER: 9 = 6 3
REC:  11 = 2 8 1
ITER: 11 = 2 8 1
REC:  24 = 3 5 2 1 3 10
ITER: 24 = 3 5 2 1 3 10
REC:  22 = 16 4 2
ITER: 22 = 10 10 2
REC:  24 = 14 3 1 6
ITER: 24 = 13 8 3
BRAK
 */
