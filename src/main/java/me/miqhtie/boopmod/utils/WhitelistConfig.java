package me.miqhtie.boopmod.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class WhitelistConfig {
    private final String fileName;

    public WhitelistConfig(String fileName) {
        this.fileName = fileName;
    }

    public void save(ArrayList<String> whitelist) throws IOException {
        FileOutputStream fout = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(whitelist);
        fout.close();
    }

    public ArrayList<String> read() throws IOException, ClassNotFoundException {

        FileInputStream fin = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fin);
        ArrayList names;
        names = (ArrayList) ois.readObject();
        fin.close();

        return names;
    }

    public void add(String name) throws IOException, ClassNotFoundException {
        ArrayList<String> waitlist = read();
        waitlist.add(name);
        save(waitlist);
    }

    public void remove(String name) throws IOException, ClassNotFoundException {
        ArrayList<String> waitlist = read();
        waitlist.remove(name);
        save(waitlist);
    }

}
