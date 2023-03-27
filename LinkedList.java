//Izabela Golec
import java.util.Scanner;

/* We create a list of trains that points to the first train, each train
points to the first car of its train, and the cars are connected cyclically*/

public class Source
{
    public static class Train
    {
        String name;
        Train next;
        Car first;
        Car second;
        Train(String nameTrain, String nameCar)
        {
            Car car = new Car(nameCar);
            name = nameTrain;
            next = null;
            first = car;
        }
    }

    public static class Car
    {
        String name;
        Car next;
        Car prev;

        Car(String nameCar)
        {
            name = nameCar;
            next = null;
            prev = null;
        }
    }

    public static class ListOfTrains
    {
        Train trains;

        ListOfTrains()
        {
            trains = null;
        }
    }

    public static boolean CheckIfTrainExists(ListOfTrains list, String name)
    {
        Train m_train = list.trains;
        while(m_train != null)
        {
            if(m_train.name.equals(name))
            {
                return true;
            }
            m_train = m_train.next;
        }
        return false;
    }

    public static Train FindTrain (String name, ListOfTrains list)
    {
        Train train = list.trains;
        while(train != null)
        {
            if(train.name.equals(name))
            {
                return train;
            }
            train = train.next;
        }
        return train;
    }

    public static void NewTrain(ListOfTrains list, String nameOfTrain, String nameOfCar)
    {
        if(!CheckIfTrainExists(list, nameOfTrain))
        {
            Train newTrain = new Train(nameOfTrain, nameOfCar);
            newTrain.next = list.trains;
            list.trains = newTrain;
            newTrain.first.next = newTrain.first;
            newTrain.first.prev = newTrain.first;
        }
        else
        {
            System.out.println("Train " + nameOfTrain + " already exists");
        }
    }

    public static void InsertNewCar(ListOfTrains list, String nameOfTrain, String nameOfCar, boolean onFirst)
    {
        if(CheckIfTrainExists(list, nameOfTrain))
        {
            Train train = FindTrain(nameOfTrain, list);
            if (!CheckIfTrainReversed(train))
            {
                Car car = train.first.prev;
                Car newCar = new Car(nameOfCar);
                car.next = newCar;
                newCar.prev = car;
                newCar.next = train.first;
                train.first.prev = newCar;
                if (onFirst)
                    train.first = newCar;
            }
            else
            {
                Reverse(list, nameOfTrain);
                onFirst = !onFirst;
                Car car = train.first.prev;
                Car newCar = new Car(nameOfCar);
                car.next = newCar;
                newCar.prev = car;
                newCar.next = train.first;
                train.first.prev = newCar;
                if (onFirst)
                    train.first = newCar;
                Reverse (list, nameOfTrain);
            }
        }
        else
        {
            System.out.println("Train " + nameOfTrain + " does not exist");
        }
    }

    public static void DisplayCar(ListOfTrains list, String nameOfTrain, boolean display)
    {
        if(CheckIfTrainExists(list, nameOfTrain))
        {
            Train train = FindTrain(nameOfTrain, list);
            if (!CheckIfTrainReversed(train))
            {
                System.out.print(train.name + ":");
                Car car = train.first;
                do {
                    System.out.print(" " + car.name);
                    car = car.next;

                } while (car != train.first && car != null);
                System.out.println();
            }
            else
            {
                if (display)
                    System.out.print(train.name + ":");
                train.first.next = train.second;
                train.second = null;
                train.first = train.first.prev;
                Car car = train.first;

                do {
                    if (display)
                        System.out.print(" " + car.name);
                    Car help=car.next;
                    car.next = car.prev;
                    car.prev = help;
                    car = car.next;

                } while (car != train.first && car != null);
                if (display)
                    System.out.println();
            }
        }
        else
        {
            System.out.println("Train " + nameOfTrain + " does not exist");
        }
    }
    public static void R(Train train)
    {
                train.first.next = train.second;
                train.second = null;
                train.first = train.first.prev;
                Car car = train.first;
                do {
                    Car help=car.next;
                    car.next = car.prev;
                    car.prev = help;
                    car = car.next;

                } while (car != train.first && car != null);
        }
    }
    public static void DisplayTrain(ListOfTrains list)
    {
        System.out.print("Trains:");
        Train train = list.trains;
        while(train != null)
        {
            System.out.print(" " + train.name);
            train = train.next;
        }
        System.out.println();
    }
    public static void DeleteTrain (ListOfTrains list, String nameOfTrain)
    {
        Train train = list.trains;
        if (CheckIfTrainExists(list, nameOfTrain))
        {
            if (list.trains.name.equals(nameOfTrain))
            {
                list.trains = list.trains.next;
            }
            else
            {
                while (train.next != null)
                {
                    if (train.next.name.equals(nameOfTrain))
                    {
                        train.next = train.next.next;
                        break;
                    }
                    train = train.next;
                }
            }
        }
    }
    public static void Union (String nameOfTrain1, String nameOfTrain2, ListOfTrains list)
    {
        if(CheckIfTrainExists(list, nameOfTrain1) && CheckIfTrainExists(list, nameOfTrain2))
        {
            Train train1 = FindTrain(nameOfTrain1, list);
            Train train2 = FindTrain(nameOfTrain2, list);
            if (CheckIfTrainReversed(train1))
                R(train1);
            if (CheckIfTrainReversed(train2))
                R(train2);
            Car car = train1.first.prev; //the car is the last car of the first train
            car.next = train2.first;
            train1.first.prev = train2.first.prev;
            train2.first.prev.next = train1.first;
            train2.first.prev = car;
            //deleting train no. 2:
            DeleteTrain(list, train2.name);
        }
        else if (!(CheckIfTrainExists(list, nameOfTrain1)))
        {
            System.out.println("Train " + nameOfTrain1 + " does not exist");
        }
        else
        {
            System.out.println("Train " + nameOfTrain2 + " does not exist");
        }
    }

    public static void DelCar (String nameOfTrain1, String nameOfTrain2, ListOfTrains list, boolean last)
    {
        if (CheckIfTrainExists(list, nameOfTrain1) && !CheckIfTrainExists(list, nameOfTrain2))
        {
            Train train = FindTrain(nameOfTrain1, list);
            if (!CheckIfTrainReversed(train))
            {
                Car car = train.first;
                if (last)
                    car = train.first.prev; //the car is the last car
                else
                    train.first = car.next;

                NewTrain(list, nameOfTrain2, car.name);
                if (car.next == car) {
                    DeleteTrain(list, nameOfTrain1);
                }
                car.prev.next = car.next;
                car.next.prev = car.prev;
            }
            else
            {
                Reverse(list, nameOfTrain1);
                last = !last;
                Car car = train.first;
                if (last)
                    car = train.first.prev; //the car is the last car
                else
                    train.first = car.next;

                NewTrain(list, nameOfTrain2, car.name);
                if (car.next == car) {
                    DeleteTrain(list, nameOfTrain1);
                }
                car.prev.next = car.next;
                car.next.prev = car.prev;
                Reverse(list, nameOfTrain1);
            }
        }
        else if (CheckIfTrainExists(list, nameOfTrain2))
        {
            System.out.println("Train " + nameOfTrain2 + " already exists");
        }
        else if (!CheckIfTrainExists(list, nameOfTrain1))
        {
            System.out.println("Train " + nameOfTrain1 + " does not exist");
        }

    }

    public static boolean CheckIfTrainReversed (Train train)
    {
        return train.first.next == null;
    }

    public static void Reverse (ListOfTrains list, String nameOfTrain)
    {
        if (CheckIfTrainExists(list, nameOfTrain))
        {
            Train train = FindTrain(nameOfTrain, list);
            if (!CheckIfTrainReversed (train))
            {
                if (train.first.next == train.first) // one car
                {
                    return;
                }
                else if (train.first == train.first.next.next) //two carc
                {
                    train.first = train.first.next;
                }
                else // > 2 cars
                {
                    train.second = train.first.next;
                    train.first.next = null;
                }
            }
            else
            {
                train.first.next = train.second;
                train.second = null;
            }
        }
        else
        {
            System.out.println("Train " + nameOfTrain + " does not exist");
        }
    }

    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args)
    {
        int numberOfSets = in.nextInt();
        for(int i = 0; i < numberOfSets; i++)
        {
            int numberOfOperations = in.nextInt();
            String operation = in.nextLine();
            ListOfTrains list = new ListOfTrains();
            for(int j = 0; j < numberOfOperations; j++)
            {
                operation = in.nextLine();
                String[] words = operation.split(" ");
                if(words[0].equals ("New"))
                {
                    NewTrain(list, words[1], words[2]);
                }
                else if(words[0].equals("InsertFirst"))
                {
                    InsertNewCar(list, words[1], words[2], true);
                }
                else if(words[0].equals("InsertLast"))
                {
                    InsertNewCar(list, words[1], words[2], false);
                }
                else if(words[0].equals("Display"))
                {
                    DisplayCar(list, words[1], true);

                }
                else if(words[0].equals("Trains"))
                {
                    DisplayTrain(list);
                }
                else if(words[0].equals("Reverse"))
                {
                    Reverse (list, words[1]);
                }
                else if(words[0].equals("Union"))
                {
                    Union (words[1], words[2], list);
                }
                else if(words[0].equals("DelFirst"))
                {
                    DelCar (words[1], words[2], list, false);
                }
                else if(words[0].equals("DelLast"))
                {
                    DelCar (words[1], words[2], list, true);
                }
            }
        }
    }
}
/*
    #test.in
    3
    21
    New T1 W1
    InsertLast T1 W2
    Display T1
    InsertFirst T1 W0
    Display T1
    DelFirst T1 T2
    Display T1
    Display T2
    DelLast T1 T3
    Display T1
    Display T3
    New T4 Z1
    InsertLast T4 Z2
    Reverse T4
    Display T4
    Union T3 T4
    Display T3
    Union T3 T2
    Display T3
    Reverse T3
    Display T3
    15
    New T1 W1
    InsertLast T1 W2
    InsertFirst T1 W0
    Display T1
    InsertFirst T1 Z0
    InsertLast T1 W3
    InsertFirst T1 W4
    Display T1
    InsertLast T1 W5
    InsertLast T1 W6
    InsertFirst T1 W7
    InsertFirst T1 W8
    Display T1
    Reverse T1
    Display T1
    20
    New T1 W0
    InsertLast T1 W1
    InsertLast T1 W2
    New T2 Z0
    InsertLast T2 Z1
    InsertLast T2 Z2
    Reverse T2
    Reverse T1
    Union T1 T2
    Display T1
    New T3 K1
    InsertFirst T3 K0
    InsertLast T3 K2
    Union T3 T1
    Display T3
    DelLast T3 T4
    DelFirst T3 T5
    Display T3
    Display T4
    Display T5

    #test.out
    T1: W1 W2
    T1: W0 W1 W2
    T1: W1 W2
    T2: W0
    T1: W1
    T3: W2
    T4: Z2 Z1
    T3: W2 Z2 Z1
    T3: W2 Z2 Z1 W0
    T3: W0 Z1 Z2 W2
    T1: W0 W1 W2
    T1: W4 Z0 W0 W1 W2 W3
    T1: W8 W7 W4 Z0 W0 W1 W2 W3 W5 W6
    T1: W6 W5 W3 W2 W1 W0 Z0 W4 W7 W8
    T1: W2 W1 W0 Z2 Z1 Z0
    T3: K0 K1 K2 W2 W1 W0 Z2 Z1 Z0
    T3: K1 K2 W2 W1 W0 Z2 Z1
    T4: Z0
    T5: K0
*/