package openquiz.website.pages.statistics;

import java.util.List;

import openquiz.website.util.Messages;
import openquiz.website.util.Misc;

import org.vaadin.vaadinvisualizations.LineChart;
import org.vaadin.vaadinvisualizations.PieChart;

import ca.openquiz.comms.enums.CategoryType;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.model.GameStat;
import ca.openquiz.comms.model.Stat;

public class StatChartHelper {
	public static void updateNumberOfGamesCharts(List<GameStat> gameStats, LineChart lineChart, PieChart pieChart){
		String gamesWon = Messages.getString("StatChartHelper.gamesWon"); //$NON-NLS-1$
		String gamesLost = Messages.getString("StatChartHelper.gamesLost"); //$NON-NLS-1$
		String gamesTie = Messages.getString("StatChartHelper.gamesTied"); //$NON-NLS-1$
		
		pieChart.remove(gamesWon);
		pieChart.remove(gamesLost);
		pieChart.remove(gamesTie);
		
		lineChart.remove(gamesWon);
		lineChart.remove(gamesLost);
		lineChart.remove(gamesTie);
		lineChart.removeAllData();
		
		if( gameStats != null && gameStats.size() > 0){
			lineChart.addLine(gamesWon);
			lineChart.addLine(gamesLost);
			lineChart.addLine(gamesTie);
			
			int gamesWonCount = 0;
			int gamesLostCount = 0;
			int gamesTieCount = 0;
			for(int i = gameStats.size()-1; i>=0 ; i--){
				GameStat stat = gameStats.get(i);	
				if (stat.getWinner() != null) {
					if (stat.getWinner().booleanValue()) {
						gamesWonCount++;
					} else {
						gamesLostCount++;
					}
				}else{
					gamesTieCount++;
				}
				lineChart.add(stat.getGameName() + " - " + Misc.formatDate(stat.getGameDate()), new double[]{(double) gamesWonCount,(double) gamesLostCount, (double)gamesTieCount}); //$NON-NLS-1$
			}
			
			pieChart.add(gamesWon, gamesWonCount);
			pieChart.add(gamesLost, gamesLostCount);
			pieChart.add(gamesTie, gamesTieCount);
		}
	}
	
	public static void updateResponseChart(List<GameStat> gameStats, QuestionType questionType, CategoryType categoryType, LineChart lineChart, PieChart pieChart){
		String goodAnswerStr = Messages.getString("StatChartHelper.goodAnser"); //$NON-NLS-1$
		String badAnswerStr = Messages.getString("StatChartHelper.badAnswers"); //$NON-NLS-1$
		String penaltyStr = Messages.getString("StatChartHelper.penalty"); //$NON-NLS-1$
		
		pieChart.remove(goodAnswerStr);
		pieChart.remove(badAnswerStr);
		pieChart.remove(penaltyStr);
		
		lineChart.remove(goodAnswerStr);
		lineChart.remove(badAnswerStr);
		lineChart.remove(penaltyStr);
		lineChart.removeAllData();
		
		if( gameStats != null &&  gameStats.size() > 0){
			lineChart.addLine(goodAnswerStr);
			lineChart.addLine(badAnswerStr);
			lineChart.addLine(penaltyStr);
			
			int goodAnswerCount = 0;
			int badAnswerCount = 0;
			int penaltyCount = 0;
					
			for(int i = gameStats.size()-1; i>=0 ; i--){
				GameStat gameStat = gameStats.get(i);	
				Stat stat = null;
				if(questionType == null && categoryType == null){
					stat = gameStat.getOverallStats();
				}
				else if(questionType != null) {
					if(gameStat.getQuestionTypeStats() != null){
						stat = gameStat.getQuestionTypeStats().get(questionType);
					}
				}
				else{
					if( gameStat.getCategoryStats() != null ){
						stat = gameStat.getCategoryStats().get(categoryType);
					}
				}
				
				if(stat != null){
					goodAnswerCount += stat.getCorrectAnswers();
					badAnswerCount += stat.getAttempts() - stat.getCorrectAnswers() - stat.getPenalties();
					penaltyCount += stat.getPenalties();
					
				}
				lineChart.add(gameStat.getGameName() + " - " + Misc.formatDate(gameStat.getGameDate()), new double[]{(double) goodAnswerCount,(double) badAnswerCount, (double) penaltyCount}); //$NON-NLS-1$
			}
			
			pieChart.add(goodAnswerStr, goodAnswerCount);
			pieChart.add(badAnswerStr, badAnswerCount);
			pieChart.add(penaltyStr, penaltyCount);
		}
	}
	
	public static void updateParticipationChart(List<GameStat> gameStats, LineChart lineChart, PieChart pieChart){
		String attemptStr = Messages.getString("StatChartHelper.attempts"); //$NON-NLS-1$
		String noAttemptStr = Messages.getString("StatChartHelper.noAttempts"); //$NON-NLS-1$
		
		pieChart.remove(attemptStr);
		pieChart.remove(noAttemptStr);
		
		lineChart.remove(attemptStr);
		lineChart.remove(noAttemptStr);
		lineChart.removeAllData();
		if(  gameStats != null && gameStats.size() > 0){
			lineChart.addLine(attemptStr);
			lineChart.addLine(noAttemptStr);
			
			int attemptCount = 0;
			int noAttemptCount = 0;
			
			for(int i = gameStats.size()-1; i>=0 ; i--){
				GameStat gameStat = gameStats.get(i);	
				Stat stat = gameStat.getOverallStats();
				attemptCount += stat.getAttempts();
				
				int noAttemptTemp =  gameStat.getPossibleAttempts() - stat.getAttempts();
				noAttemptCount += noAttemptTemp;
				
				lineChart.add(gameStat.getGameName() + " - " + Misc.formatDate(gameStat.getGameDate()), new double[] {attemptCount, noAttemptCount}); //$NON-NLS-1$
			}
			
			pieChart.add(attemptStr, attemptCount);
			pieChart.add(noAttemptStr, noAttemptCount);
		}
	}
}
