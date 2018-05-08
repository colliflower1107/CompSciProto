
package com.robothamster.coleslaw.compsciproto;
public class HashEntry {

    private int key;
    private String value;

    HashEntry(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
/**
 * 
 * @author 16clangham
 * create an empty hash table with 128 entries
 */
class HashMap {
    private final static int TABLE_SIZE = 128;
    HashEntry[] table;
    HashMap() {
        table = new HashEntry[TABLE_SIZE];

        for (int i = 0; i < TABLE_SIZE; i++)
            table[i] = null;
    }
    /**
     * return the value of the entry with a specified key
     */
    public String get(int key) {
        int hash = (key % TABLE_SIZE);

        while (table[hash] != null && table[hash].getKey() != key)
            hash = (hash + 1) % TABLE_SIZE;
        if (table[hash] == null)
            return null;
        else
            return table[hash].getValue();
    }
    /**
     * 
     * @param key
     * @param value
     * place an object into the hash table using a hashed integer as the key and a 
     * string as the value
     */
    public void put(int key, String value) {
        int hash = (key % TABLE_SIZE);

        while (table[hash] != null && table[hash].getKey() != key)
            hash = (hash + 1) % TABLE_SIZE;
        table[hash] = new HashEntry(key, value);
    }
}
