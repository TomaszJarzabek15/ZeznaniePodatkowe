//Tomasz Jarzabek - 5

/*
IDEA PROGRAMU: implementujemy wyszukiwanie binarne ktore ma zlozonosc O(logn) i pamieciowa O(1) poniewaz
nie uzywamy zadnej pomoczniczej tablicy. Mamy tez DisplayDifferent ktory po prostu przechodzi przez cala tablice wiec
O(n) i tez nie uzywamy dodatkowej pamieci O(1), liczymy ile jest roznych liczb.
W mainie pobieramy wszystkie potrzebne dane i drukujemy to co trzeba uzywajac dwoch powyzszych metod.
Ilosc liczb nalezacych do przedzialu liczymy prosta operacja odejmujac pierwsza pozycje od ostatniej pozycji.
 */

import java.util.Scanner;
class Source {

    public static Scanner scn = new Scanner(System.in);

    static long[] tablica;  //deklaruje tablice globalnie, SearchBinFirst musi byc jednoargumentowy

    static int SearchBinFirst(long liczba){

        int lewa = 0;   //na poczatku lewa i prawa na ekstremach
        int prawa = tablica.length - 1;
        int pos = -1;   //nie ma elementu z default
        int srodek;
        int pierwszaPrawa = prawa;

        while(lewa <= prawa){
            srodek = (lewa + prawa) / 2;    //zaczynamy od srodka i pozniej liczymy nowe srodki

            if(tablica[srodek] > liczba){   //idziemy w lewo
                prawa = srodek - 1;
            }
            else if(tablica[srodek] < liczba){  //idziemy w prawo
                lewa = srodek + 1;
            }
            else{   //znalezlismy element teraz idziemy w lewo szukajac pierwszego
                pos = srodek;
                prawa = srodek - 1;
            }

            if(pos == -1){  //przypadek kiedy liczba nie nalezy do tablicy
                if(lewa > prawa){

                    if(prawa != pierwszaPrawa){
                        //liczba pomiedzy najwieksza i najmniejsza ale jej nie ma w tablicy, pozycja to lewa bo jest to pozycja pomiedzy dwoma wartosciami
                        //jedna jest mniejsza a druga wieksza, ustawiamy pomiedzy
                        pos = lewa;
                        lewa = prawa + 1; //koniec petli
                    }
                    else{
                        //liczba wieksza od najwiekszego elementu tablicy, pozycja to ostatni indeks tablicy
                        pos = pierwszaPrawa + 1;
                    }
                }
                if((prawa < lewa) && (lewa == 0)){
                    //liczba mniejsza od najmniejszego elementu tablicy, pozycja = 0;
                    pos = 0;
                }
            }
        }

        return pos;
    }

    static void DisplayDifferent(){   //W O(n) przechodzimy cala tablice i liczymy ile mamy roznych elementow

        int licznik = 1;    //zawsze jest jeden element

        for(int i = 0; i < tablica.length; i++){
            if(i == (tablica.length - 1)){

            }
            else {
                if (tablica[i + 1] != tablica[i]) {     //jak sie rozni to dodajemy
                    licznik++;
                }
            }
        }

        System.out.println(licznik);    //od razu wypisujemy w metodzie
    }

    public static void main(String[] args) {

        int zestawy;
        zestawy = scn.nextInt();
        int ilosc;
        int pytania;
        long lewa;  //uzywam long poniewaz te dane sa z zakresu -2^48 do 2^48
        long prawa;
        long pierwszy = -1111;
        long ostatni = -1111;

        int wynik = 0;
        int pierwszaPozycja = 0;
        int ostatniaPozycja = 0;

        for(int i = 0; i < zestawy; i++){   //tyle zestawow wczytujemy
            ilosc = scn.nextInt();

            tablica = new long[ilosc];   //tablica ma byc na longach bo zakres od -2^48 do 2^48 przekracza inta

            for(int j = 0; j < ilosc; j++){
                tablica[j] = scn.nextLong();    //wczytujemy zawartosc tablicy

                if(j == 0){
                    pierwszy = tablica[j];  //pamietamy zakres zeby od razu eliminowac niepoprawne przedzialy
                }
                if(j == (ilosc - 1)){
                    ostatni = tablica[j];
                }
            }

            pytania = scn.nextInt();

            for(int j = 0; j < pytania; j++){
                lewa = scn.nextLong();  //wczytujemy przedzialy
                prawa = scn.nextLong();

                if(lewa > prawa){   //nieprawidlowy przedzial wiec nic nie jest w nim zawarte
                    System.out.println("0");
                }
                else{
                    //prawdilowy przedzial aplikujemy algorytm
                    if(lewa > ostatni){
                        System.out.println("0");    //przedzial zaczyna sie na elemencie wiekszym od ostatniego z tablicy
                    }
                    else if(prawa < pierwszy){
                        System.out.println("0");    //przedzial konczy sie na elemencie mniejszym od pierwszego
                    }
                    else {
                        //dopiero jak tak nie jest na pewno cos znajdziemy w tablicy

                        wynik = 0;

                        pierwszaPozycja = SearchBinFirst(lewa);    //znajdujemy pierwsza pozycje elementu
                        ostatniaPozycja = SearchBinFirst((prawa + 1)); //znajdujemy ostatnia pozycje elementu ktory zamyka przedzial, czyli pierwsza elementu n + 1

                        wynik = ostatniaPozycja - pierwszaPozycja;

                        System.out.println(wynik);  //wypisujemy wynik

                    }
                }
            }

            DisplayDifferent();  //wypisujemy ile bylo roznych elementow tablicy
        }
    }
}

/*
test 1
2
12
 -1 1 2 2 2 3 5 5 7 7 9 9
12
 1 2
 2 2
 3 6
 2 1
-1 10
 1 0
 4 6
 4 3
-1 9
 1 1
 4 4
 0 9
10
1 1 1 1 1 1 1 1 1 1
7
 1 2
 0 1
 1 1
 0 0
 2 2
 3 1
 -1 -1

wynik 1
4
3
3
0
12
0
2
0
12
1
0
11
7
10
10
10
0
0
0
0
1

1
12
-1 1 2 2 2 3 5 5 7 7 9 9
1
4 6



1
12
 -1 1 2 2 2 3 5 5 7 7 9 9
1
0 9


 */