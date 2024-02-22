package com.ohgiraffers.section01.view;

import com.ohgiraffers.section01.controller.stockManager;
import com.ohgiraffers.section01.dto.Member;

import java.util.InputMismatchException;
import java.util.Scanner;

public class stockMenu {
    private Scanner sc = new Scanner(System.in);
    private stockManager sm = new stockManager();

    public stockMenu(){}
    public void mainPage(){
        System.out.println("주식 프로그램 실행");

        System.out.print("이름 입력 : ");
        String name = sc.nextLine();

        int money=0;
        label: while (true){
            money=0;
            System.out.print("현금 입금(최대 20억) : ");

            try{
                long longMoney=0;
                longMoney =sc.nextLong();
                money=(int)longMoney;
                if(money > 2000000000 || longMoney!=money){
                    System.out.println("최대 입금 금액은 20억 입니다. 20억을 입금합니다.");
                    money=2000000000;
                }else if(money <= 0){
                    System.out.println("0 또는 음수는 입력하실 수 없습니다. 다시 입력해주세요.");
                    continue label;
                }
            }catch (InputMismatchException e){
                System.out.println("유효하지 않은 숫자입니다. 다시 입력해주세요.");
                continue label;
            }
            sc.nextLine();
            break;
        }

        sm.insertInfo(new Member(name, (int)money));

        label: while(true){
            System.out.println("=== 메인 메뉴 ===\n0. 프로그램 종료\n1. 전체 주식보기\n2. 내 정보\n3. 현금 추가입금\n4. 주식 구매\n5. 주식 판매");
            System.out.print("메뉴 선택 : ");
            String index = sc.nextLine();

            switch (index){
                case "0" :
                    System.out.println("프로그램 종료");
                    break;
                case "1" : sm.displayAll();
                    continue label;
                case "2" : sm.myInfo();
                    continue label;
                case "3" : inputMoney();
                    continue label;
                case "4" : buyStock();
                    continue label;
                case "5" : sellStock();
                    continue label;
                default:
                    System.out.println("보기중에서 선택해주세요.");
                    continue label;
            }
            break;
        }
    }

    public void inputMoney(){
        System.out.print("입금 금액 : ");

        try{
            long longNum=0;
            longNum = sc.nextLong();
            int num = (int)longNum;
            sc.nextLine();
            if(num > 2000000000 || longNum!=num){
                System.out.println("20억이 넘는 금액을 입금하실 수 없습니다.");
            }else{
                sm.addMoney(num);
            }

        }catch (InputMismatchException e){
            System.out.println("20억이 넘는 금액을 입금하실 수 없습니다.");
            sc.nextLine();
        }
    }

    public void buyStock(){
        sm.displayAll();
        String ttl;
        int num;

        label: while (true){
            System.out.print("매수할 종목명 입력 : ");
            ttl = sc.nextLine();
            boolean isTrue = true;

            for (int i=0; i<sm.stockList().size(); i++){
                if(sm.stockList().get(i).getTitle().equals(ttl)) isTrue = false;
            }

            if(isTrue){
                System.out.println(ttl+" 주식이 존재하지 않습니다. 보기중에서 선택하여 주세요.");
                continue label;
            }
            break;
        }

        label: while (true){
            System.out.print("매수할 종목개수 입력 : ");
            num = sc.nextInt();
            sc.nextLine();

            if(num <= 0){
                System.out.println("0개 이하의 주식은 거래할 수 없습니다. 다시 입력해주세요.");
                continue label;
            }
            break;
        }

        boolean isTrue = sm.buyStock(ttl,num);

        if(isTrue){
            System.out.println(ttl + "주식 " + num + "개 매수를 성공하였습니다.");
        }else{
            System.out.println("현재 보유금액이 모자라 " + ttl + " 매수를 실패하였습니다.");
        }
    }
    public void sellStock(){
        if(sm.haveAccount()){
            sm.myAccount();
            String ttl;
            int num;

            label: while (true){
                System.out.print("매도할 종목명 입력 : ");
                ttl = sc.nextLine();
                boolean isTrue = true;

                for (int i=0; i<sm.account().size(); i++){
                    if(sm.account().get(i).getTitle().equals(ttl)) isTrue = false;
                }

                if(isTrue){
                    System.out.println(ttl+" 주식이 존재하지 않습니다. 보유한 주식 중에서 선택하여 주세요.");
                    continue label;
                }
                break;
            }

            label: while (true){
                System.out.print("매도할 종목개수 입력 : ");
                num = sc.nextInt();
                sc.nextLine();
                boolean isTrue = true;

                for(int i=0; i<sm.account().size(); i++){
                    if(num > sm.account().get(i).getCount()) {
                        System.out.println("보유 개수가 " + sm.account().get(i).getCount() + "개 입니다. 보유개수 이하로 입력해주세요");
                        continue label;
                    }else if(num <= 0){
                        System.out.println("0개 이하의 주식은 거래할 수 없습니다. 다시 입력해주세요.");
                        continue label;
                    }
                }

                break;
            }

            int result = sm.sellStock(ttl,num);

            switch (result){
                case 0 :
                    System.out.println(ttl + " 주식이 상장폐지 되었습니다.");
                    break;
                case 1 :
                    System.out.println(ttl + " " + num + "개 매도를 성공하였습니다.");
                    break;
                case 2 :
                    System.out.println("매도자에 의해 매도가 거절되었습니다.");
                    break;
            }
        }else {
            System.out.println("보유 주식이 없습니다.");
        }
    }


}
