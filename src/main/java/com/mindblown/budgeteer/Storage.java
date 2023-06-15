/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.budgeteer;

/**
 *
 * @author beamj
 */
public class Storage implements Comparable<Storage>{
    private String name;
    private float amount;

    public Storage(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }
    
    public Storage(String name){
        this.name = name;
        this.amount = 0f;
    }

    public float getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Storage o) {
        return name.compareTo(o.name);
    }
    
    public void transact(float a){
        amount += a;
    }
}
