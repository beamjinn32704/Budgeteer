/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.budgeteer;

/**
 *
 * @author beamj
 */
public class BudgetedCategory implements Comparable<BudgetedCategory>{
    private String name;
    private float budget;
    private float moneyFlow; // money spent or deposited

    public BudgetedCategory(String name, float budget) {
        this.name = name;
        this.budget = budget;
    }

    public float getBudget() {
        return budget;
    }

    public String getName() {
        return name;
    }

    public void setAmount(float budget) {
        this.budget = budget;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void transact(float amount){
        moneyFlow += amount;
    }

    @Override
    public int compareTo(BudgetedCategory o) {
        return name.compareTo(o.name);
    }
}
