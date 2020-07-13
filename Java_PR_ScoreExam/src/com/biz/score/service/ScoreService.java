package com.biz.score.service;

import com.biz.score.vo.ScoreVO;

public interface ScoreService {
	
	public void loadScore();
	public boolean inputScore();
	
	public void saveScoreVO(ScoreVO scoreVO);
	
	public void calcSum();
	public void calcAvg();
	
	public void saveScore();
	public void scoreList();
	
	

}
