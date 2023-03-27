//Izabela Golec
/* checking the correctness of the received input ONP, we count the number of operators and operands (excluding unary), checking
correctness of INF we use the steer function and control the number of brackets. When converting INF->ONP we use
from 3 stacks (chars, ints, and strings) into the operand stack, their priority stack, and their connectivity stack, respectively. By
ONP -> INF conversion we use two stacks (strings and ints), in the string stack we store temporary outputs,
but also single operands, while in the stack of ints their priorities (correct priority also for temporary output)*/
import java.util.Scanner;

public class Source
{
    public static Scanner in = new Scanner(System.in);
    //stos charow
    static class StackChar {
        private int maxSize; // the size of the array containing the stack
        public char[] Stos_char; // array containing the stack
        private int t; // top of the stack index

        //-------------------------------------------------------------
        public StackChar(int size) { // constructor - Create()
            maxSize = size; // we set the size of the array
            Stos_char = new char[maxSize]; // we create arrays for elements
            t = maxSize; // no items yet (going up)
        }

        //-------------------------------------------------------------
        public void push(char x) { // puts an item on top of the stack
            t = t - 1;
            Stos_char[t] = x;
        }

        //-------------------------------------------------------------
        public void pop() { // removes an item from the top of the stack
            Stos_char[t] = '0';
            t = t + 1;
        }

        //-------------------------------------------------------------
        public char top() { // returns the value on top of the stack

            return Stos_char[t];
        }

        //------------------------------------------------------------
        public boolean isempty() // returns true if the stack is empty
        {
            return t == maxSize;
        }

        //------------------------------------------------------------
    } // end of StackChar class
    //stos int (priorities)
    static class StackInt {
        private int maxSize;
        public int[] Stos_int;
        private int t;

        //-------------------------------------------------------------
        public StackInt(int size) {
            maxSize = size;
            Stos_int = new int[maxSize];
            t = maxSize;
        }

        //-------------------------------------------------------------
        public void push(int x) {
            t = t - 1;
            Stos_int[t] = x;
        }

        //-------------------------------------------------------------
        public void pop() {
            Stos_int[t] = -1;
            t = t + 1;
        }

        //-------------------------------------------------------------
        public int top() {

            return Stos_int[t];
        }

        //------------------------------------------------------------
        public boolean isempty() { // zwraca true, jezeli stos pusty
            return t == maxSize;
        }

        //------------------------------------------------------------
    } // end of StackInt class
    //string stack (left-right communication)
    static class StackString {
        private int maxSize;
        public String[] Stos_string;
        private int t;

        //-------------------------------------------------------------
        public StackString(int size) {
            maxSize = size;
            Stos_string = new String[maxSize];
            t = maxSize;
        }

        //-------------------------------------------------------------
        public void push(String x) {
            t = t - 1;
            Stos_string[t] = x;
        }

        //-------------------------------------------------------------
        public void pop() {
            Stos_string[t] = "";
            t = t + 1;
        }

        //-------------------------------------------------------------
        public String top() { // zwraca wartosc na szczycie stosu
            return Stos_string[t];
        }
        public int top_index ()
        {
            return t;
        }
        //------------------------------------------------------------
        public boolean isempty() { // zwraca true, jezeli stos pusty
            return t == maxSize;
        }

        //------------------------------------------------------------
    } // end of StackString class

    public static boolean czy_operator (char current_char)
    {
        return ((current_char == '<' || current_char == '>' || current_char == '+' || current_char == '-' ||
                current_char == '*' || current_char == '/' || current_char == '%' || current_char == '=' ||
                current_char == '^' || current_char == '&' || current_char == '?' || current_char == '|' ||
                current_char == '!' || current_char == '~' || (current_char>96 && current_char<123) ||
                current_char == '(' || current_char == ')'));
    }
    //a function that checks whether the given ONP expression is correct
    public static boolean onp_machine(char[] tab)
    {
        int is_ok=0;
        int counter_operand = 0;
        int counter_operator = 0;
        for (int i = 4; i < tab.length; i++)
        {
            char current_char = tab[i];
            if (tab[i] >= 97 && tab[i] <= 122)
            {
                is_ok = is_ok + 1;
                counter_operand++;
            }
            else if ( current_char == '<' || current_char == '>' || current_char == '+' || current_char == '-' ||
                    current_char == '*' || current_char == '/' || current_char == '%' || current_char == '=' ||
                    current_char == '^' || current_char == '&' || current_char == '?' || current_char == '|')
            {
                if(is_ok < 2)
                    return false;
                counter_operator++;
            }
        }
        return (counter_operand == counter_operator + 1);
    }
    //state control function from the INF_machine function
    public static int ster (int state, char  char_)
    {
        if (state == 0)
        {
            if (char_ == '(')
                return 0;
            else if ( char_ >= 97 && char_ <=122)
                return 1;
            else if (char_ == '!' || char_ == '~')
                return 2;
        }
        else if (state == 1)
        {
            if (char_ == ')')
                return 1;
            else if (char_ == '<' || char_ == '>' || char_ == '+' || char_ == '-' ||
                    char_ == '*' || char_ == '/' || char_ == '%' || char_ == '=' ||
                    char_ == '^' || char_ == '&' || char_ == '?' || char_ == '|')
                return 0;
        }
        else if (state == 2)
        {
            if (char_ == '!' || char_ == '~')
                return 2;
            else if (char_ == '(')
                return 0;
            else if ( char_ >= 97 && char_ <=122)
                return 1;
        }
        return -1;
    }
    // function that checks the correctness of the given INF expression
    public static boolean inf_machine(char[] tab) {
        int is_operator = 0;
        int state = 0;
        char first_bracket = '(';
        char last_bracket = ')';
        int counter_first_bracket = 0;
        int counter_last_bracket = 0;
        for(int i = 4; i < tab.length; i++)
        {
            char current_char = tab[i];
            if (current_char == '<' || current_char == '>' || current_char == '+' || current_char == '-' ||
                    current_char == '*' || current_char == '/' || current_char == '%' || current_char == '=' ||
                    current_char == '^' || current_char == '&' || current_char == '?' || current_char == '|' ||
                    current_char == '!' || current_char == '~' || (current_char>96 && current_char<123) ||
                    current_char == '(' || current_char == ')')
            {
                state = ster(state, tab[i]);
                if(tab[i] == '(')
                {
                    if(counter_first_bracket == 0 && counter_last_bracket == 0) first_bracket = '(';
                    last_bracket = '(';
                    counter_first_bracket++;
                }
                else if(tab[i] == ')')
                {
                    if(counter_first_bracket == 0 && counter_last_bracket == 0) first_bracket = ')';
                    last_bracket = ')';
                    counter_last_bracket++;
                }
            }
        }
        return ((state == 1) && (counter_first_bracket == counter_last_bracket) &&
                first_bracket == '(' && last_bracket == ')');
    }
    //priority function
    public static int set_priority(char char_) {
        if (char_ == '=') return 1;
        else if (char_ == '|') return 2;
        else if (char_ == '&') return 3;
        else if (char_ == '?') return 4;
        else if (char_ == '<' || char_ == '>') return 5;
        else if (char_ == '+' || char_ == '-') return 6;
        else if (char_ == '*' || char_ == '/' || char_ == '%') return 7;
        else if (char_ == '^') return 8;
        else if (char_ == '!' || char_ == '~') return 9;
        else if (char_ == '(') return 10;
        else if (char_ == ')') return 11;
        else if (char_>96 && char_ < 123) return 12;
        else return -1;
    }
    //INF to ONP conversion
    public static void infToONP(String input) {
        char[] tab = input.toCharArray();
        if (!inf_machine(tab))
            System.out.println("ONP: error");
        else
        {
            String output = "ONP:";
            StackChar stack = new StackChar(256); //stack with operators
            StackInt stack_int = new StackInt(256); //operator priority stack
            StackString stack_string = new StackString(256); //stack with information about right-left operator connectivity
            for (int i = 4; i < input.length(); i++)
            {
                char current_char = tab[i];
                if (current_char > 96 && current_char < 123)
                    output = output + " " + current_char; // if it encounters an operand, it will add it to the output
                else if (current_char == 33 || current_char == 37 || current_char == 38 || current_char == 45 ||
                        current_char == 47 || current_char == 94 || current_char == 124 || current_char == 126 ||
                        (current_char > 39 && current_char < 44) || (current_char > 59 && current_char < 64))
                {
                    int p = set_priority(current_char); //sets the priority of the input character
                    int int_top = -1;
                    char char_top = ' ';
                    //if the stacks are not empty then we check what is on top of the stack (whether it should be downloaded)
                    if (!stack.isempty())
                    {
                        int_top = stack_int.top();
                        char_top = stack.top();
                    }
                    String tmp_con = "";
                    //we set the right/left link
                    if (p == 1 || p == 8 || p == 9)
                        tmp_con = "right";
                    else
                        tmp_con = "left";
                    //if the stack is empty or the left parenthesis is always put on the stack
                    if (stack.isempty() || p == 10)
                    {
                        stack.push(current_char);
                        stack_int.push(p);
                        stack_string.push(tmp_con);
                    }
                    else
                    {
                        if (p == 11) //if the right parenthesis, we pull until we encounter the left parenthesis
                        {
                            while (char_top != '(' && !stack.isempty()) {
                                output = output + " " + char_top;
                                stack.pop();
                                stack_int.pop();
                                stack_string.pop();
                                if (!stack.isempty())
                                {
                                    char_top = stack.top();
                                    int_top = stack_int.top();
                                }
                            }
                        }
                        else if ((p <= int_top && tmp_con == "left" && int_top!=10)) //if priority lower and left-hand link
                        {
                            while (p <= int_top && int_top!=10 && !stack.isempty())  //we're popping until <=
                            {
                                output = output + " " + char_top;
                                stack.pop();
                                stack_int.pop();
                                stack_string.pop();
                                if (!stack.isempty())
                                {
                                    char_top = stack.top();
                                    int_top = stack_int.top();
                                }
                            }
                        }
                        else if ((p < int_top) && tmp_con == "right" && int_top != 10)//if priority is lower and we have right-hand link
                        {
                            while (p < int_top && int_top!=10 && !stack.isempty()) //we're popping until <
                            {
                                output = output + " " + char_top;
                                stack.pop();
                                stack_int.pop();
                                stack_string.pop();
                                if (!stack.isempty())
                                {
                                    char_top = stack.top();
                                    int_top = stack_int.top();
                                }
                            }
                        }
                        if (p!=11)//if it is not a right parenthesis, we put it on the stack
                        {
                            stack.push(current_char);
                            stack_int.push(p);
                            stack_string.push(tmp_con);
                        }
                        else if (p == 11) // if it is a right parenthesis, we pop the remaining left parenthesis from the stack
                        {
                            stack.pop();
                            stack_int.pop();
                            stack_string.pop();
                        }
                    }
                }
            }
            while (!stack.isempty()) //if something is left on the stack, we add to the output until the stack is empty
            {
                int int_top = -1;
                char char_top = ' ';
                if (!stack.isempty())
                {
                    int_top = stack_int.top();
                    char_top = stack.top();
                }
                if (char_top!='(')
                    output = output + " " + char_top;
                stack.pop();
                stack_int.pop();
                stack_string.pop();
            }
            System.out.println(output);
        }
    }
    // conversion of ONP to INF
    public static void onpToInf (char[] tab)
    {
        if (!onp_machine(tab))
            System.out.println("INF: error");
        else
        {
            StackString stack_string = new StackString(256); // a stack of strings that will be our output
            StackInt stack_p = new StackInt(256); //priority stack
            String output= "";
            for(int j = 4; j < tab.length; j++)
            {
                char current_char = tab[j];
                if(current_char >= 97 && current_char <= 122) // operators are always stacked
                {
                    stack_string.push(String.valueOf(current_char));
                    stack_p.push(set_priority(current_char));
                }
                else if((current_char == 33 || current_char == 37 || current_char == 38 || current_char == 45 ||
                        current_char == 47 || current_char == 94 || current_char == 124 || current_char == 126 ||
                        (current_char > 41 && current_char < 44) || (current_char > 59 && current_char < 64))
                        && stack_string.top_index() <=256  && stack_p.top() <= 256 )
                {

                    int p = set_priority(current_char);
                    int top_int = 0;
                    String top_string1 = "";
                    if (!stack_p.isempty())
                    {
                        top_int = stack_p.top();
                        stack_p.pop();
                    }
                    if (!stack_string.isempty())
                    {
                        top_string1 = stack_string.top();
                        stack_string.pop();
                    }
                    if(current_char != '~' && current_char!='!')
                    {
                        if(current_char == '=' || current_char == '^')
                        {
                            if(p > top_int)
                            {
                                top_string1 = "( " + top_string1 + " ) ";
                            }
                        }
                        else
                        {
                            if(p >= top_int)
                            {
                                top_string1 = "( " + top_string1 + " )";
                            }
                        }
                        if (!stack_p.isempty())
                        {
                            top_int = stack_p.top();
                            stack_p.pop();
                        }
                        String top_string2 = "";
                        if (!stack_string.isempty())
                        {
                            top_string2 = stack_string.top();
                            stack_string.pop();
                        }
                        if(current_char == '=' || current_char == '^')
                        {
                            if(p >= top_int)
                            {
                                top_string2 = "( " + top_string2 + " )";
                            }
                        }
                        else
                        {
                            if(p > top_int)
                            {
                                top_string2 = "( " + top_string2 + " )";
                            }
                        }
                        output = top_string2 + " " + current_char + " " + top_string1;
                    }
                    else
                    {
                        if(p > top_int && top_string1!="")
                        {
                            top_string1 = "( " + top_string1 + " )";
                        }
                        output = current_char + " " + top_string1;
                    }
                    stack_p.push(p);
                    stack_string.push(output);
                }
            }
            if (!stack_string.isempty())
            {
                output="";
                while (!stack_string.isempty()) {
                    String string_top = stack_string.top();
                    output = output + string_top;
                    stack_string.pop();
                }
            }
            char tablica[] = output.toCharArray();
            if (tablica[tablica.length-1] == ' ')
            {
                output = " ";
                for (int k=0; k < tablica.length -1; k++)
                    output = output + tablica[k];
            }
            else
            {
                output = " " + output;
            }
            char t[] = output.toCharArray();
            output = "" + t[0];
            for (int i = 1; i < t.length; i++)
            {
                if (!(t[i-1]==' ' && t[i]==' '))
                    output = output + t[i];
            }
            System.out.println("INF:" + output);
        }
    }
    public static void main (String[] args)
    {
        int n = in.nextInt();
        in.nextLine();
        //String input;
        for (int j = 0; j < n; j++)
        {
            String input = in.nextLine();
            char tab[] = input.toCharArray();
            if (tab[0] == 'I' && tab[1]=='N' && tab[2]=='F')
                infToONP (input);
            else if (tab[0] == 'O' && tab[1]=='N' && tab[2]=='P')
                onpToInf (tab);
        }
    }
}

/*
    #test.in
42
ONP: xabc**=
ONP: ab+a~a-+
INF: a+b+(~a-a)
INF: x=~~a+b*c
INF: t=~a<x<~b
INF: ( a,+ b)/..[c3
ONP: ( a,b,.).c;-,*
ONP: abc++def++g+++
INF: x=a=b=c
ONP: xabc===
INF: a)+(b
ONP: ab+a~a-+
INF: a+b+(~a-a)
INF: x=~~a+b*c
INF: t=~a<x<~b
INF: ~a-~~b<c+d&!p|!!q
INF: a^b*c-d<xp|q+x
INF: x=~a*b/c-d+e%~f
ONP: xabcdefg++++++=
ONP: ab+c+d+e+f+g+
ONP: abc++def++g++
ONP: abc++def++g+++
INF: x=a=b=c
ONP: xabc===
INF: x=a^b^c
INF: x=a=b=c^d^e
INF: x=(a=(b=c^(d^e)))
ONP: xa~~~~~~=
INF: x=~~~~a
INF: x=~(~(~(~a)))
ONP: xa~~~~=
INF: x=a+(b-c+d)
ONP: xabc-d++=
INF: x=a+(((a-b)+c))
ONP: xaab-c++=
INF: a~+b
INF: a~~
INF: a+b~
INF: ()a+b
INF: (a+b)+()
INF: ~()a
ONP: xabcde^^===

    #test.out
INF: x = a * ( b * c )
INF: a + b + ( ~ a - a )
ONP: a b + a ~ a - +
ONP: x a ~ ~ b c * + =
ONP: t a ~ x < b ~ < =
ONP: a b + c /
INF: a * ( b - c )
INF: error
ONP: x a b c = = =
INF: x = a = b = c
ONP: error
INF: a + b + ( ~ a - a )
ONP: a b + a ~ a - +
ONP: x a ~ ~ b c * + =
ONP: t a ~ x < b ~ < =
ONP: a ~ b ~ ~ - c d + < p ! & q ! ! |
ONP: error
ONP: x a ~ b * c / d - e f ~ % + =
INF: x = a + ( b + ( c + ( d + ( e + ( f + g ) ) ) ) )
INF: a + b + c + d + e + f + g
INF: a + ( b + c ) + ( d + ( e + f ) + g )
INF: error
ONP: x a b c = = =
INF: x = a = b = c
ONP: x a b c ^ ^ =
ONP: x a b c d e ^ ^ = = =
ONP: x a b c d e ^ ^ = = =
INF: x = ~ ~ ~ ~ ~ ~ a
ONP: x a ~ ~ ~ ~ =
ONP: x a ~ ~ ~ ~ =
INF: x = ~ ~ ~ ~ a
ONP: x a b c - d + + =
INF: x = a + ( b - c + d )
ONP: x a a b - c + + =
INF: x = a + ( a - b + c )
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
ONP: error
INF: x = a = b = c ^ d ^ e
*/