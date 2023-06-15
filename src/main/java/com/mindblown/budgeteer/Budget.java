/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.budgeteer;

import com.mindblown.util.ArrayListUtil;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author beamj
 */
public class Budget {

    private ArrayList<BudgetedCategory> categories;

    private ArrayList<Transaction> transactions;
    private ArrayList<Storage> storages;

    public Budget() {
        categories = new ArrayList<>();
    }

    private int getCategoryIdx(String category) {
        //creates a blank category with the same name, because the name is how BudgetedCategories are sorted
        return ArrayListUtil.sortedIndexOf(categories, new BudgetedCategory(category, 0));
    }

    public void addCategory(String category) {
        addCategory(category, 0f);
    }

    public void addCategory(String category, float budget) {
        int catIdx = getCategoryIdx(category);
        if (catIdx < 0) {
            int idx = ArrayListUtil.translateIndex(catIdx);
            categories.add(idx, new BudgetedCategory(category, budget));
        }
    }

    public void setBudget(String category, float budget) {
        int catIdx = getCategoryIdx(category);
        if (catIdx < 0) {
            return;
        }
        categories.get(catIdx).setAmount(budget);
    }

    public Float getBudget(String category) {
        int catIdx = getCategoryIdx(category);
        if (catIdx < 0) {
            return null;
        }
        return categories.get(catIdx).getBudget();
    }
    
    //If category doesn't exist, doesn't do anything
    private void recordCategoryTransaction(String category, float amount){
        int catIdx = getCategoryIdx(category);
        if(catIdx >= 0){
            categories.get(catIdx).transact(amount);
        }
    }

    public void recordTransaction(Date date, float amount, String recStorage, String description, String category) {
        Storage recS = getStorage(recStorage);
        Transaction t = new Transaction(date, amount, recS, description);
        recS.transact(amount);
        addTransaction(t);
        recordCategoryTransaction(category, amount);
    }
    
    public void recordTransfer(Date date, float amount, String recStorage, String sendStorage, String description){
        assert amount >= 0;
        Storage recS = getStorage(recStorage);
        Storage sendS = getStorage(sendStorage);
        Transaction t = new Transaction(date, amount, recS, sendS, description);
        recS.transact(amount);
        sendS.transact(-1f * amount);
        addTransaction(t);
    }
    
    private void addTransaction(Transaction t){
        ArrayListUtil.sortedAdd(transactions, t);
    }
    
    /**
     * Gets the storage from the storages ArrayList with the name given as the argument given.
     * @param name
     * @return 
     */
    private Storage getStorage(String name){
        int stoIdx = ArrayListUtil.sortedIndexOf(storages, new Storage(name));
        if(stoIdx < 0){
            stoIdx = ArrayListUtil.translateIndex(stoIdx);
            storages.add(stoIdx, new Storage(name));
        }
        return storages.get(stoIdx);
    }

    private class Transaction implements Comparable<Transaction> {

        private Date date;
        private float amount;
        private Storage recStorage;
        private Storage sendStorage;
        private boolean isTransfer;
        private String description;

        /**
         * Create a Transaction where either you receive money or you
         *
         * @param date
         * @param amount
         * @param recStorage
         * @param description
         */
        public Transaction(Date date, float amount, Storage recStorage, String description) {
            this.date = date;
            this.amount = amount;
            this.recStorage = recStorage;
            this.sendStorage = null;
            this.isTransfer = false;
            this.description = description;
        }

        public Transaction(Date date, float amount, Storage recStorage, Storage sendStorage, String description) {
            this.date = date;
            this.amount = amount;
            this.recStorage = recStorage;
            this.sendStorage = sendStorage;
            this.isTransfer = true;
            this.description = description;
        }

        @Override
        public int compareTo(Transaction o) {
            return date.compareTo(o.date);
        }

    }

}
