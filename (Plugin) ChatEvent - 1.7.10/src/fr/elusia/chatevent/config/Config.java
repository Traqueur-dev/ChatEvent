package fr.elusia.chatevent.config;

import lombok.Data;

@Data
public class Config {
	
	private float rewardEasy;
	private float rewardNormal;
	private float rewardHard;
	
	private int delayInSecondBetweenTwoGames;
	
}
