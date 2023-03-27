//Izabela Golec
import java.util.Scanner;

/* this program sorts data collected in CSV format (comma-separated values) after the selected column.
In my solution to implement the iterative quciksort method, I used the method described in the lecture.
Assuming that the sorted array A contains no duplicates, selecting the right end of R_ can be done using only
the elements in the array. It is enough to replace A[R_] with the largest element from the subarray A[L..q-1], e.g.
place A[q-1], and A[q-1] instead of A[k]. Going to the right subtask, we will determine L_=R+2, and the element A[R_]
will be found first smaller to the right of A[L_]. After finding it, you need to restore the previous values in the table.
At the same time, in the quikSort method, we will change two arrays, one with key words whose elements we will compare and
the other with the entire database of information about songs */

public class Source {
    public static Scanner in = new Scanner(System.in);

    public static void SelectionSort(int[] array, String[] base_array)
    {
        for (int i = 0; i < array.length-1; i++)
        {
            int min_idx = i;
            for (int j = i+1; j < array.length; j++)
            {
                if (array[j] < array[min_idx])
                {
                    min_idx = j;
                }
            }
            int tmp = array[min_idx];
            array[min_idx] = array[i];
            array[i] = tmp;
            String tmp_ = base_array[min_idx];
            base_array[min_idx] = base_array[i];
            base_array[i] = tmp_;
        }
    }

    public static void SelectionSortString(String[] array, String[] base_array)
    {
        for (int i = 0; i < array.length-1; i++)
        {
            int min_idx = i;
            for (int j = i+1; j < array.length; j++)
            {
                if (array[j].compareTo(array[min_idx]) < 0)
                {
                    min_idx = j;
                }
            }
            String tmp = array[min_idx];
            array[min_idx] = array[i];
            array[i] = tmp;
            String tmp_ = base_array[min_idx];
            base_array[min_idx] = base_array[i];
            base_array[i] = tmp_;
        }
    }

    static int partition(String[] base_array, int[] arr, int L, int R) //Lomuto version
    {
        int pivot = arr[R];
        int i = (L - 1);

        for (int j = L; j < R; j++) {
            if (arr[j] <= pivot)
            {
                i = i + 1;
                swap(arr, i, j);
                swap_string(base_array, i, j);
            }
        }
        swap(arr, i + 1, R);
        swap_string(base_array, i + 1, R);
        return (i + 1);
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    static int partition_string (String[] base_array, String[] arr, int L, int R) //Lomuto version
    {
        String  pivot = arr[R];
        int i = (L - 1);

        for (int j = L; j < R; j++) {
            if (pivot.compareTo(arr[j])>=0)
            {
                i = i + 1;
                swap_string(arr, i, j);
                swap_string(base_array, i, j);
            }
        }
        swap_string(arr, i + 1, R);
        swap_string(base_array, i + 1, R);
        return (i + 1);
    }

    public static void swap_string(String[] arr, int i, int j) {
        String tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    public static void iterativeQuickSort_Int(String[] base_array, int[] array, int L, int R)
    {
        while(L < R || L < array.length-1) //if the condition is not met, the array is sorted
        {
            if (L < R)
            {
                int q = partition(base_array, array, L, R);
                //looking fot MAX in array
                int max = array[L];
                int k = L;
                for(int j = L + 1; j < q; j++)
                {
                    if (array[j] > max)
                    {
                        max = array[j];
                        k = j;
                    }
                }
                if (q == L) //the first element is in the first place and we don't need to sort it
                    L++;
                else if (q == R) //the last item is the last one and we don't need to sort it
                    R--;
                else
                {
                    int _R = array[R]; //remembering the right end of the right subarray:
                    int _q = array[q]; //at this point, max will be found earlier, array[R'] instead of array[q],
                    int _q_minus_one = array[q-1];//array[q] in place of array[q-1], and if q-1 != k then in place of array[k] I put array[q-1]
                    array[R] = array[k];
                    array[q] = _R;
                    array[q-1] = _q;
                    // the same for the base_arrey
                    String __R = base_array[R];
                    String __q = base_array[q];
                    String __q_minus_one = base_array[q-1];
                    base_array[R] = base_array[k];
                    base_array[q] = __R;
                    base_array[q-1] = __q;

                    if(q-1 != k)
                    {
                        array[k] = _q_minus_one;
                        base_array[k] = __q_minus_one;
                    }
                    //in position array[R'] is max(L,q-1) but definitely less than q so later I can easily find it and "get" its index
                    R = q -2;
                }
            }
            else
            {
                int i = R + 1;
                int q = array[R + 1];
                while(array[i] >= q && i< array.length - 1) //we are looking for the right end of the right subarray
                    i++;

                if(array[i] < q) //we found it
                {
                    int tmp = array[R+1];    //everything returns to its place
                    array[R+1] = array[i];
                    array[i] = array[R+2];
                    array[R+2] = tmp;
                    //the same for the base_array
                    String _tmp = base_array[R+1];
                    base_array[R+1] = base_array[i];
                    base_array[i] = base_array[R+2];
                    base_array[R+2] = _tmp;
                    L = R + 3;  //we pass the right end, returning element and pivot
                    R = i;  //the right end of the subarray where the element is found
                }
                else //we didn't find it and L==R so we increment it
                {
                    L++;
                    R++;
                }
            }
        }
    }
    //function working analogously to iterativeQuickSort_Int only on two arrays of strings
    public static void iterativeQuickSort_String(String[] base_array, String [] array, int L, int R)
    {
        while( L < R || L < array.length-1 )
        {
            if (L < R)
            {
                int q = partition_string(base_array, array, L, R);

                String max = array[L];
                int k = L;
                for(int j = L + 1; j < q; j++)
                {
                    if (array[j].compareTo(max)>0)
                    {
                        max = array[j];
                        k = j;
                    }
                }
                if (q == L)
                    L++;
                else if (q == R)
                    R--;
                else
                {
                    String _R = array[R];
                    String _q = array[q];
                    String _q_minus_one = array[q-1];
                    array[R] = array[k];
                    array[q] = _R;
                    array[q-1] = _q;

                    String __R = base_array[R];
                    String __q = base_array[q];
                    String __q_minus_one = base_array[q-1];
                    base_array[R] = base_array[k];
                    base_array[q] = __R;
                    base_array[q-1] = __q;

                    if(q-1 != k)
                    {
                        array[k] = _q_minus_one;
                        base_array[k] = __q_minus_one;
                    }
                    R = q - 2;
                }
            }
            else
            {
                int i = R + 1;
                String q = array[R + 1];
                while(array[i].compareTo(q)>=0 && i< array.length - 1)
                    i++;

                if(q.compareTo(array[i])>0)
                {
                    String tmp = array[R+1];    //everything returns to its place
                    array[R+1] = array[i];
                    array[i] = array[R+2];
                    array[R+2] = tmp;
                    //the same for the base_array
                    String _tmp = base_array[R+1];
                    base_array[R+1] = base_array[i];
                    base_array[i] = base_array[R+2];
                    base_array[R+2] = _tmp;
                    L = R + 3;
                    R = i;
                }
                else
                {
                    L++;
                    R++;
                }
            }
        }
    }

    public static void main(String[] args)
    {
        int z = in.nextInt();
        String info = in.nextLine();
        for (int i = 0; i < z; i++)
        {
            info = in.nextLine();
            String[] help_arr_1 = info.split(",");
            int n_row = Integer.parseInt(help_arr_1[0]);
            int n_column = Integer.parseInt(help_arr_1[1]) ;
            n_column--;
            String chaos = help_arr_1[2]; //ascending/descending order

            info = in.nextLine(); //read headers
            //write the heading in the correct order
            String[] output_ = info.split(",");
            {
                System.out.print(output_[n_column] );
                for (int l=0; l < output_.length; l++)
                    if (l!=n_column)
                        System.out.print ("," + output_[l]);
            }
            System.out.println();

            String[] base_array = new String[n_row]; //table with all the data

            base_array[0] = in.nextLine(); //reading the first line
            help_arr_1 = base_array[0].split(","); // splitting it up into chunks
            String key_word = help_arr_1[n_column]; // keyword in n_column

            char[] key_word_tab = key_word.toCharArray(); // keyword in char array
            boolean is_int = false; //whether the variable we are interested in is a number
            int[] key_arr_int = new int[n_row];
            String[] key_arr_string = new String[n_row];
            if (key_word_tab[0] >= 48 && key_word_tab[0] <= 57)
            {
                is_int = true;
                key_arr_int[0] = Integer.parseInt(key_word);
            }
            else
            {
                key_arr_string[0] = key_word;
            }
            //reading data and saving it to the table with key words
            for (int j = 1; j < n_row; j++)
            {
                base_array[j] = in.nextLine();
                String[] help_arr = base_array[j].split(",");
                key_word = help_arr[n_column];
                if (is_int)
                {
                    key_arr_int[j] = Integer.parseInt(key_word);
                }
                else
                {
                    key_arr_string[j] = key_word;
                }
            }
            if (is_int)
            {
                if (base_array.length > 5)
                    iterativeQuickSort_Int(base_array, key_arr_int, 0, key_arr_int.length - 1);
                else
                    SelectionSort(key_arr_int, base_array);
                if (chaos.equals("1"))
                {
                    for (int k = 0; k < n_row; k++)
                    {
                            String[] output = base_array[k].split(",");
                            {
                                System.out.print(output[n_column]);
                                for (int l = 0; l < output.length; l++)
                                    if (l != n_column)
                                        System.out.print("," + output[l]);
                            }
                            System.out.println();
                    }
                }
                else
                {
                    for (int k = n_row - 1; k >= 0; k--) {
                        String[] output = base_array[k].split(",");
                        {
                            System.out.print(output[n_column]);
                            for (int l = 0; l < output.length; l++)
                                if (l != n_column)
                                    System.out.print("," + output[l]);
                        }
                        System.out.println();
                    }
                }
            }
            else
            {
                if (base_array.length > 5)
                    iterativeQuickSort_String(base_array, key_arr_string, 0, key_arr_int.length - 1);
                else
                    SelectionSortString(key_arr_string, base_array);
                if (chaos.equals("1"))
                {
                    for (int k = 0; k < n_row; k++)
                    {
                        String[] output = base_array[k].split(",");
                        {
                            System.out.print(output[n_column] );
                            for (int l=0; l < output.length; l++)
                                if (l!=n_column)
                                    System.out.print ("," + output[l]);
                        }
                        System.out.println();
                    }
                }
                else
                {
                    for (int k = n_row-1; k >= 0; k--)
                    {
                        String[] output = base_array[k].split(",");
                        {
                            System.out.print(output[n_column] );
                            for (int l=0; l < output.length; l++)
                                if (l!=n_column)
                                    System.out.print ("," + output[l]);
                        }
                        System.out.println();
                    }
                }
            }
            System.out.println();
        }
    }
}

/*
test.in

6
3,1,-1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56
3,4,-1
Album,Year,Songs,Length
Stadium Arcadium,2006,28,122
Unlimited Love,2022,17,73
Californication,1999,15,56
3,2,1
Album,Year,Songs,Length
Stars,1906,48,238
Nancy,2021,40,70
Mulligan,2022,16,89
3,2,-1
Album,Year,Songs,Length
Stars,1906,48,238
Nancy,2021,40,70
Mulligan,2022,16,89
3,4,-1
Album,Year,Songs,Length
Moon,2022,48,23
TalkingToYou,2021,45,27
Lemon,2020,20,58
3,3,-1
Album,Year,Songs,Length
Moon,2022,48,23
TalkingToYou,2021,45,27
Lemon,2020,20,58

test.out:
Album,Year,Songs,Length
Unlimited Love,2022,17,73
Stadium Arcadium,2006,28,122
Californication,1999,15,56

Length,Album,Year,Songs
122,Stadium Arcadium,2006,28
73,Unlimited Love,2022,17
56,Californication,1999,15

Year,Album,Songs,Length
1906,Stars,48,238
2021,Nancy,40,70
2022,Mulligan,16,89

Year,Album,Songs,Length
2022,Mulligan,16,89
2021,Nancy,40,70
1906,Stars,48,238

Length,Album,Year,Songs
58,Lemon,2020,20
27,TalkingToYou,2021,45
23,Moon,2022,48

Album,Year,Songs,Length
TalkingToYou,2021,45,27
Moon,2022,48,23
Lemon,2020,20,58
*/