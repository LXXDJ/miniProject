package com.ohgiraffers.section01.controller;

import com.ohgiraffers.section01.comparator.AscPrice;
import com.ohgiraffers.section01.comparator.AscTitle;
import com.ohgiraffers.section01.comparator.DescPrice;
import com.ohgiraffers.section01.comparator.DescTitle;
import com.ohgiraffers.section01.dto.Member;
import com.ohgiraffers.section01.dto.stockDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class stockManager {
    private Scanner sc = new Scanner(System.in);
    private Member mem = null;
    private ArrayList<stockDTO> account;
    private ArrayList<stockDTO> stockList;

    public ArrayList<stockDTO> account(){return account;}
    public ArrayList<stockDTO> stockList(){return stockList;}

    public stockManager(){
        account = new ArrayList<>();
        stockList = new ArrayList<>();
        insertStock();
    }
    public void insertStock(){
        stockList.add(new stockDTO("삼성전자", 73300));
        stockList.add(new stockDTO("SK하이닉스", 149600));
        stockList.add(new stockDTO("LG에너지솔루션", 404000));
        stockList.add(new stockDTO("삼성바이오로직스", 818000));
        stockList.add(new stockDTO("삼성전자우", 62900));
        stockList.add(new stockDTO("현대차", 242000));
        stockList.add(new stockDTO("기아", 116100));
        stockList.add(new stockDTO("셀트리온", 179900));
        stockList.add(new stockDTO("POSCO홀딩스", 440000));
        stockList.add(new stockDTO("LG화학", 506000));
    }
    public void displayAll(){
        sortedStockList(stockList);
        for (stockDTO i : stockList){
            System.out.println(i);
        }
    }
    public void insertInfo(Member mem){
        this.mem = mem;
    }
    public void myInfo(){
        System.out.println(this.mem);
        myAccount();
    }
    public boolean haveAccount(){
        boolean have = false;
        if(account.size() <= 0) {
            have = false;
        }
        if(account.size() >= 1){
            have = true;
        }
        return have;

    }
    public void myAccount(){
        if(account.size() <= 0) {
            System.out.println("보유 주식이 없습니다.");
            return;
        }
        for(int i=0; i<account.size(); i++){
            System.out.println("보유 종목명 : " + account.get(i).getTitle() + ", 개수 : " + account.get(i).getCount() + ", 현재가 : " + account.get(i).getPrice() + "원");
        }
    }
    public ArrayList<stockDTO> sortedStockList(ArrayList<stockDTO> list){
        label : while (true){
            System.out.print("정렬 방식 선택(1.종목명 오름차순 / 2.종목명 내림차순 / 3.현재가 오름차순 / 4.현재가 내림차순) : ");
            String type = sc.nextLine();

            switch (type){
                case "1" :
                    Comparator<stockDTO> ascTtl = new AscTitle();
                    list.sort(ascTtl);
                    break;
                case "2" :
                    Comparator<stockDTO> descTtl = new DescTitle();
                    list.sort(descTtl);
                    break;
                case "3" :
                    Comparator<stockDTO> ascNo = new AscPrice();
                    list.sort(ascNo);
                    break;
                case "4" :
                    Comparator<stockDTO> descNo = new DescPrice();
                    list.sort(descNo);
                    break;
                default:
                    System.out.println("보기중에서 선택해주세요.");
                    continue label;
            }
            break;
        }

        return list;
    }

    public void addMoney(int num){
        long totalMoney=(long)num+mem.getMoney();
        if(totalMoney!=(int)totalMoney || totalMoney>2000000000){
            System.out.println(num+" 입금시 보유 금액이 20억을 초과하기 때문에 입금이 불가능합니다.");
            System.out.println("현재 최대 "+(2000000000- mem.getMoney())+" 까지 입금 가능합니다.");
        }else{
            mem.setMoney(mem.getMoney() + num);
            System.out.println("현재 보유금액 : " + mem.getMoney());
        }


    }
    public boolean buyStock(String ttl, int num){
        boolean isTrue = false;
        int price = 0;

        for (int i=0; i<stockList.size(); i++){
            if(stockList.get(i).getTitle().equals(ttl)){
                price = stockList.get(i).getPrice();
            }
        }

        System.out.println("가격 : " + price);
        int needs = price * num;
        boolean has = true;
//        stockDTO dto = null;

        for(int i=0; i<account.size(); i++){
            if(account.get(i).getTitle().equals(ttl)){
                has = false;
                account.get(i).setCount(account.get(i).getCount() + num);
            }
        }


        if(needs < mem.getMoney()) {
            isTrue = true;
            if(has){
                stockDTO dto = new stockDTO(ttl, num, price);
                account.add(dto);
            }
            mem.setMoney(mem.getMoney() - needs);
        }

        return isTrue;
    }
    public int sellStock(String ttl, int num){
        int result = 0;
        int present = 0;

        for(int i=0; i<account.size(); i++){
            if(account.get(i).getTitle().equals(ttl)){
                present = account.get(i).getPrice();
            }
        }

        present = calcPercent(present);

        if(present == 0) return result;

        System.out.println(ttl + " 주식의 현재가는 " + present + "원 입니다.");
        int index = -1;
        label : while(true){
            System.out.print("매도 하시겠습니까?(1.네 / 2.아니오): ");
            try{
                index = sc.nextInt();
            }catch (InputMismatchException e){
                sc.nextLine();
            }

            if(index != 1 && index != 2){
                System.out.println("보기중에서 선택해주세요");
                continue label;
            }
            break;
        }

        if(index == 2){
            result = 2;
            for(int i=0; i<account.size(); i++){
                if(account.get(i).getTitle().equals(ttl)){
                    account.get(i).setPrice(present);
                }
            }
        }else if(index == 1){
            result = 1;
            for(int i=0; i<account.size(); i++){
                if(account.get(i).getTitle().equals(ttl)){
                    int earn = present * num;
                    mem.setMoney(mem.getMoney() + earn);

                    int count = account.get(i).getCount() - num;
                    if(count == 0){
                        account.remove(i);
                    }else if(count > 0){
                        account.get(i).setCount(count);
                        account.get(i).setPrice(present);
                    }

                }
            }
        }

        return result;
    }
    public int randomPercent(){
        int number = (int)(Math.random()*100);
        int sign = (int)(Math.random()*2);
        int percent = 0;

        if(sign == 0){
            percent = -number;
        }else{
            percent = number;
        }

        return percent;
    }
    public int calcPercent(int num){
        int percent = randomPercent();

        int money = (int)(num+(num*(percent*0.01)));

        return money;
    }
}
