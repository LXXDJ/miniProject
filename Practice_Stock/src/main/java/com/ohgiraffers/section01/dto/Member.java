package com.ohgiraffers.section01.dto;

public class Member {
    private String name;
    private int money;

    public Member(){}
    public Member(String name, int money){
        this.name = name;
        this.money = money;
    }

    public void setName(String name){this.name = name;}
    public void setMoney(int money){this.money = money;}

    public String getName(){return this.name;}
    public int getMoney(){return this.money;}

    public String toString(){
        return "=== 내 정보 ====\n이름 : " + this.name + ", 보유금액 : " + this.money;
    }
}
