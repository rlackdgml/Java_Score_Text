package com.biz.score.exec;

import java.util.Scanner;

import com.biz.score.config.SplitPos;

import com.biz.score.config.Lines;

import com.biz.score.service.ScoreService;
import com.biz.score.service.ScoreServiceImplV1;
import com.biz.score.vo.ScoreVO;

public class ScoreEx_01 {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		ScoreService sService = new ScoreServiceImplV1();
		sService.loadScore();
		while (true) {
			System.out.println("=========================================================");
			System.out.println("=========================================================");
			System.out.println("1.성적 등록");
			System.out.println("2.성적일람표 출력");
			System.out.println("3.업무선택>>");
			System.out.println("4.성적일람표 출력");
			System.out.println("=========================================================");
			System.out.println("QUIT. 업무종료");
			System.out.println("=========================================================");
			System.out.print("업무선택>>");
			String stMenu = scan.nextLine();
			if (stMenu.equals("QUIT")) {
				break;
			}
			int intMenu = 0;
			try {

				intMenu = Integer.valueOf(stMenu);

			} catch (Exception e) {
				System.out.println("메뉴는 숫자로만 선택하루 있음!!");
				continue;

			}
			if(intMenu == SplitPos.MENU.성적등록) {
				while (sService.inputScore() );
				sService.calcSum();
				sService.calcAvg();
				
				
				
			}else if (intMenu == SplitPos.MENU.성적일람표) {
				
				sService.calcSum();
				sService.calcAvg();
				sService.scoreList();

		}

	}

}
}
