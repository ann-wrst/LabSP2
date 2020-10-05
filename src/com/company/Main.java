package com.company;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.io.File;

class MySol {
    public class STransition {
        public int from;
        public int to;
        public char letter;

        public STransition(int f, int t, char l) {
            from = f;
            to = t;
            letter = l;
        }
    }

    public ArrayList<STransition> FSMachine = new ArrayList<>();

    int alphabetCount;
    int statesCount;
    int First_State;
    int CountOfTrans;
    int finalStatesCount;
    HashSet<Integer> states = new HashSet<>();

    public void Start(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));
        alphabetCount = sc.nextInt();
        statesCount = sc.nextInt();
        First_State = sc.nextInt();
        CountOfTrans = sc.nextInt();
        finalStatesCount = sc.nextInt();
        ArrayList<Integer> finalStates = new ArrayList<>();
        for (int i = 0; i < finalStatesCount; i++) {
            finalStates.add(sc.nextInt());
        }
        while (CountOfTrans-- > 0) {
            FSMachine.add(new STransition(sc.nextInt(), sc.nextInt(), sc.next().charAt(0)));
        }
        for (STransition ST : FSMachine) {
            states.add(ST.from);
            states.add(ST.to);
        }
        System.out.println(GetLetters());
    }

    public HashSet<Character> GetLetters() {
        HashSet<Integer> unreachable = getUnreachableStates();
        HashSet<Character> correspondingLetters = new HashSet<>();
        for (STransition ST : FSMachine) {
            for (Integer el : unreachable) {
                if (ST.from == el)
                    correspondingLetters.add(ST.letter);
            }
        }
        for (char letter : correspondingLetters) {
            for (STransition ST : FSMachine) {
                if (letter == ST.letter && !unreachable.contains(ST.from)) {
                    correspondingLetters.remove(letter);
                }
            }
        }
        return correspondingLetters;
    }

    public HashSet<Integer> getUnreachableStates() {
        HashSet<Integer> reachableStates = new HashSet<>();
        reachableStates.add(First_State);
        while (true) {
            boolean newEl = false;
            for (STransition t : FSMachine) {
                if (reachableStates.contains(t.from) && !reachableStates.contains(t.to)) {
                    reachableStates.add(t.to);
                    newEl = true;
                }
            }
            if (!newEl) break;
        }
        HashSet<Integer> temp = new HashSet<>(states);
        HashSet<Integer> unreachableStates = new HashSet<>();
        for (int el : temp) {
            if (!reachableStates.contains(el)) {
                unreachableStates.add(el);
            }
        }
        return unreachableStates;
    }
}

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        MySol mySol = new MySol();
        mySol.Start("C:\\Users\\annwr\\Desktop\\Lab2SP\\src\\com\\company\\data.txt");
    }
}