package com.biz.score.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.biz.score.config.SplitPos;
import com.biz.score.config.Lines;
import com.biz.score.vo.ScoreVO;

public class ScoreServiceImplV1 implements ScoreService {

	private List<ScoreVO> scoreList;
	private Scanner scan;
	private String fileName;
	private String fileName2;

	private String[] strSub;
	private Integer[] intScores;

	private int[] totalSum;
	private int[] totalAvg;

	public ScoreServiceImplV1() {
		scoreList = new ArrayList<ScoreVO>();
		scan = new Scanner(System.in);
		fileName = "src/com/biz/score/exec/data/score.txt";
		fileName2 = "src/com/biz/score/exec/data/scoreList.txt";

		strSub = new String[] { "국어", "영어", "수학", "음악" };
		intScores = new Integer[strSub.length];
		totalSum = new int[strSub.length];
		totalAvg = new int[strSub.length];

	}

	public void loadScore() {
		
		
		FileReader fileReader = null;
		BufferedReader buffer = null;

		try {
			fileReader = new FileReader(this.fileName);
			buffer = new BufferedReader(fileReader);
			String reader = "";
			while (true) {
				reader = buffer.readLine();
				if (reader == null) {
					break;
				}
				
				String[] scores = reader.split(":");
				ScoreVO scoreVO = new ScoreVO();
				scoreVO.setNum(scores[SplitPos.SCORE.SC_NUM]);
				scoreVO.setKor(Integer.valueOf(scores[SplitPos.SCORE.SC_KOR]));
				scoreVO.setEng(Integer.valueOf(scores[SplitPos.SCORE.SC_ENG]));
				scoreVO.setMath(Integer.valueOf(scores[SplitPos.SCORE.SC_MATH]));
				scoreVO.setMusic(Integer.valueOf(scores[SplitPos.SCORE.SC_MUSIC]));
				scoreList.add(scoreVO);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("학생정보 파일 열기 오류!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("학생정보 파일 읽기 오류!!!");
		}
	}

	private Integer scoreCheck(String sc_score) {

		if (sc_score.equals("END")) {
			return -1;
		}

		Integer intScore = null;
		try {
			intScore = Integer.valueOf(sc_score);
		} catch (Exception e) {
			System.out.println("점수는 숫자만 가능");
			System.out.println("입력한 문자열 : " + sc_score);
			return null;
		}
		if (intScore < 0 || intScore > 100) {
			System.out.println("점수는 0 ~ 100까지만 가능");
			System.out.println("다시 입력해 주세요");
			return null;
		}
		return intScore;
	}

	public boolean inputScore() {

		ScoreVO scoreVO = new ScoreVO();

		System.out.print("학번(END:종료) >> ");
		String st_num = scan.nextLine();
		if (st_num.equals("END")) {
			return false;
		}

		int intNum = 0;
		try {
			intNum = Integer.valueOf(st_num);
		} catch (Exception e) {
			System.out.println("학번은 숫자만 가능");
			System.out.println("입력한 문자열 : " + st_num);
			return true;
		}
		if (intNum < 1 || intNum > 99999) {
			System.out.println("학번은 1 ~ 99999까지만 가능");
			System.out.println("다시 입력해 주세요");
			return true;
		}

		st_num = String.format("%05d", intNum);

		for (ScoreVO sVO : scoreList) {
			if (sVO.getNum().equals(st_num)) {
				System.out.println(st_num + " 학생의 성적이 이미 등록 되어 있습니다");
				return true;
			}
		}

		scoreVO.setNum(st_num);

		for (int i = 0; i < strSub.length; i++) {

			System.out.printf("%s 점수(END:종료)", strSub[i]);
			String sc_score = scan.nextLine();

			Integer intScore = this.scoreCheck(sc_score);
			if (intScore == null) {

				i--;
				continue;
			} else if (intScore < 0) {
				return false;
			}

			intScores[i] = intScore;
		}
		scoreVO.setKor(intScores[0]);
		scoreVO.setEng(intScores[1]);
		scoreVO.setMath(intScores[2]);
		scoreVO.setMusic(intScores[3]);

		scoreList.add(scoreVO);
		this.saveScoreVO(scoreVO);
		return false;

	}

	public void saveScore() {

	}

	public void scoreList() {
	

		Arrays.fill(totalSum, 0);
		Arrays.fill(totalAvg, 0);

		System.out.println("================================================================");
		System.out.println("성적 일람표");
		System.out.println("================================================================");
		System.out.println("학번\t이름\t국어\t영어\t수학\t음악\t총점\t평균\t");
		System.out.println("----------------------------------------------------------------------");
		for (ScoreVO sVO : scoreList) {
			System.out.printf("%s\t|", sVO.getNum());

			System.out.printf("%d\t|", sVO.getKor());
			System.out.printf("%d\t|", sVO.getEng());
			System.out.printf("%d\t|", sVO.getMath());
			System.out.printf("%d\t|", sVO.getMusic());
			System.out.printf("%d\t|", sVO.getSum());
			System.out.printf("%5.2f\t|\n", sVO.getAvg());

			totalSum[0] += sVO.getKor();
			totalSum[1] += sVO.getEng();
			totalSum[2] += sVO.getMath();
			totalSum[3] += sVO.getMusic();

		}
		System.out.println("----------------------------------------------------------------------");

		System.out.print("과목총점:\t");
		int sumAndSum = 0;
		for (int sum : totalSum) {
			System.out.printf("%s\t", sum);
			sumAndSum += sum;
		}
		System.out.printf("%s\t\n", sumAndSum);

		System.out.print("과목평균:\t");
		float avgAndAvg = 0f;
		for (int sum : totalSum) {
			float avg = (float) sum / scoreList.size();
			System.out.printf("%5.2f\t", avg);
			avgAndAvg += avg;
		}
		System.out.printf("\t%5.2f\t\n", avgAndAvg / totalSum.length);
		System.out.println("====================================================================");
	}

	public void saveScoreVO(ScoreVO scoreVO) {

		FileWriter fileWriter = null;
		PrintWriter pWriter = null;

		try {
			fileWriter = new FileWriter(this.fileName, true);
			pWriter = new PrintWriter(fileWriter);

			pWriter.printf("%s:", scoreVO.getNum());
			pWriter.printf("%d:", scoreVO.getKor());
			pWriter.printf("%d:", scoreVO.getEng());
			pWriter.printf("%d:", scoreVO.getMath());
			pWriter.printf("%d\n", scoreVO.getMusic());
			pWriter.flush();
			pWriter.close();

		} catch (IOException e) {
		
			// e.printStackTrace();
		}
	}

	public void calcSum() {
		for (ScoreVO scoreVO : scoreList) {
			int sum = scoreVO.getKor();
			sum += scoreVO.getEng();
			sum += scoreVO.getMath();
			sum += scoreVO.getMusic();
			sum += scoreVO.getKor();
			scoreVO.setSum(sum);
		}
	}

	public void calcAvg() {
		for (ScoreVO scoreVO : scoreList) {
			int sum = scoreVO.getSum();
			float avg = (float) sum / 4;
			scoreVO.setAvg(avg);
		}
	}
}
