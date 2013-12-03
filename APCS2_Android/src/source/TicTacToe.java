package source;


import java.util.Random;

import android.graphics.Color;


public class TicTacToe  implements Game
{
	Random randal=new Random();
	private boolean []spots1=new boolean[9];
	private boolean []spots2=new boolean[9];
	private boolean player1;
	private boolean shouldGoEasy;
	private int [] places={0,0,0,0,0,0,0,0,0};
	public MerlinCore1 mc1;
	public TicTacToe(MerlinCore1 core)
	{
		mc1=core;
	}
	
	public boolean player1Turn()
	{
		return player1;
	}
	
	public boolean spotTaken(int spot)
	{
		if(spots1[spot-1]||spots2[spot-1])return true;
		return false;
	}
	
	public void switchPLayer()
	{
		if(player1)player1=false;
		else player1=true;
	}
	
	public void getNextMove(int spot)	
	{		
		if(spotTaken(spot))
		{	
			
		}
		else
		{
			if (player1) 
			{
				spots1[spot-1]=true;
				mc1.buttonOn(spot);
				mc1.numberButton[spot].setText("X");
				mc1.numberButton[spot].setBackgroundColor(Color.RED);
				places[spot-1]=1;
			}
			else 
			{
				
				spots2[spot-1]=true;
				mc1.buttonOn(spot);
				mc1.numberButton[spot].setText("O");
				mc1.numberButton[spot].setBackgroundColor(Color.BLUE);
				places[spot-1]=2;
			}
			
			switchPLayer();
		}
	}
	public boolean game1()
	{
		boolean[] s=spots1;
		if(s[0]&&s[1]&&s[2])return true;if(s[3]&&s[0]&&s[6])return true;if(s[6]&&s[4]&&s[2])return true;
		if(s[3]&&s[4]&&s[5])return true;if(s[7]&&s[4]&&s[1])return true;if(s[8]&&s[4]&&s[0])return true;
		if(s[6]&&s[7]&&s[8])return true;if(s[2]&&s[8]&&s[5])return true;
		
		return false;
	}
	
	public boolean game2()
	{
		boolean[] s=spots2;
		if(s[0]&&s[1]&&s[2])return true;if(s[3]&&s[0]&&s[6])return true;if(s[6]&&s[4]&&s[2])return true;
		if(s[3]&&s[4]&&s[5])return true;if(s[7]&&s[4]&&s[1])return true;if(s[8]&&s[4]&&s[0])return true;
		if(s[6]&&s[7]&&s[8])return true;if(s[2]&&s[8]&&s[5])return true;
		
		return false;
	}
	
	public boolean tie()
	{
		boolean[] p1=spots1;
		boolean[]p2=spots2;
		
		if((p1[0]||p2[0])&&(p1[1]||p2[1])&&(p1[2]||p2[2])&&(p1[3]||p2[3])&&(p1[4]||p2[4])&&(p1[5]||p2[5])
				&&(p1[6]||p2[6])&&(p1[7]||p2[7])&&(p1[8]||p2[8])&&!game1()&&!game2())return true;		
		
		return false;		
	}
	public boolean gameOver()
	{
		
		if(game1()||game2()||tie())return true;		
		return false;
	}	

	public int chooseRandomSpot()
	{
		Random randy=new Random();
		int spot=randy.nextInt(9)+1;
		if(spotTaken(spot))
		do spot=randy.nextInt(9)+1;
		while(spotTaken(spot));
		return spot;
	}
	public int chooseRandomSpot(int[] spots)
	{		
		Random randy=new Random();
		int spot=0;		
		do
		{
			spot=randy.nextInt(spots.length);			
			
		}while(spotTaken(spots[spot]));
		
		
		return spots[spot];
	}
	public boolean allTaken(int[]spots)
	{
		int taken=0;
		for(int check=0;check<spots.length;check++)
		{
			if(spotTaken(spots[check])){taken++;}
		}
		if(taken==spots.length)return true;
		return false;
	}
	public int chooseProSpot()
	{
		int selection = 0;
		int [] bestSpots={1,3,5,7,9};
		int[] edgeSpots={2,4,6,8};
		if(canWin()){selection=win();}
		else
			if(oppCanWin()){selection=blockOtherPlayer();}
			else 
				if(!spotTaken(5))selection=5;
				else 
					if(potentialLoss()){selection=chooseRandomSpot(edgeSpots);}
					else
						if(!allTaken(bestSpots)){selection=chooseRandomSpot(bestSpots);}
						else {chooseRandomSpot();}


		if(selection==0)selection=chooseRandomSpot();		
		return selection;
	}
	
	public int chooseEasySpot()
	{
		int selection = 0;
		int [] bestSpots={1,3,5,7,9};
		int[] edgeSpots={2,4,6,8};
		int luck=randal.nextInt(10);
		if(luck==2||luck==3||luck==4)
		{
			chooseRandomSpot();			
		}
		else
		{	
			if(canWin()){selection=win();}
			else
				if(oppCanWin()){selection=blockOtherPlayer();}
				else 
					if(!spotTaken(5))selection=5;
					else 						
						if(!allTaken(bestSpots)){selection=chooseRandomSpot(bestSpots);}
						else {chooseRandomSpot();}

		}
		if(selection==0)selection=chooseRandomSpot();		
		return selection;
	}
	
	public boolean oppCanWin()
	{	
		int[][] places=placesTaken();
		if(player1)
		{
			for(int group=0;group<8;group++)
			{
				int oppCount=0;int spaces=0;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==2)oppCount++;
					if(places[group][space]==0&&places[group][space]!=1)spaces++;
					if(oppCount==2&&spaces==1){return true;}
				}
								
			}
		}

		if(!player1)
		{
			for(int group=0;group<8;group++)
			{
				int oppCount=0;int spaces=0;

				for(int space=0;space<3;space++)
				{
					if(places[group][space]==1)oppCount++;
					if(places[group][space]==0&&places[group][space]!=2)spaces++;
					if(oppCount==2&&spaces==1){return true;}
				}								
			}
		}
		return false;
	}
	public boolean canWin()
	{	
		int[][] places=placesTaken();
		if(!player1)
		{
			for(int group=0;group<8;group++)
			{
				int count=0;int spaces=0;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==2)count++;
					if(places[group][space]==0)spaces++;
					if(count==2&&spaces==1){return true;}
				}
								
			}
		}

		if(player1)
		{
			for(int group=0;group<8;group++)
			{
				int count=0;int spaces=0;

				for(int space=0;space<3;space++)
				{
					if(places[group][space]==1)count++;
					if(places[group][space]==0)spaces++;
					if(count==2&&spaces==1){return true;}
				}								
			}
		}
		return false;
	}
	
	public int win()
	{
		int[][] places=placesTaken();
		int win=0;
		if(!player1)
		{
			for(int group=0;group<8;group++)
			{
				int count=0;int spaces=0;				
				int freeSpace=3;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==2){count++;}
					if(places[group][space]==0){freeSpace=space;spaces++;}					
				}	
				if(count==2&&spaces==1)
					if(freeSpace!=3){win=getNumber(group,freeSpace);}
					else {win=getNumber(group,2);}								
								
			}
		}
		
		if(player1)
		{
			for(int group=0;group<8;group++)
			{
				int count=0;int spaces=0;				
				int freeSpace=3;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==1){count++;}
					if(places[group][space]==0){freeSpace=space;spaces++;}					
				}	
				if(count==2&&spaces==1)
					if(freeSpace!=3)win=getNumber(group,freeSpace);
					else win=getNumber(group,2);								
									
			}
		}		
		return win;
	}
	public boolean potentialLoss()
	{
		boolean[]s1=spots1;
		boolean[]s2=spots2;
		int count=0;
		for(int x=0;x<s1.length;x++)
			if(s1[x])count++;
		if(!player1)
		{
			if(s1[0]&&s1[8]&&s2[4]&&count==2)return true;
			if(s1[2]&&s1[6]&&s2[4]&&count==2)return true;
		}			
		return false;
	}
	public int blockOtherPlayer()
	{
		int[][] places=placesTaken();
		int block=0;
		if(player1)
		{
			for(int group=0;group<8;group++)
			{
				int oppCount=0;				
				int freeSpace=3;
				int spaces=0;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==2){oppCount++;}
					if(places[group][space]==0){freeSpace=space;spaces++;}					
				}	
				if(oppCount==2&&spaces==1)
				{
					if(freeSpace!=3){block=getNumber(group,freeSpace);}
					else {block=getNumber(group,2);}
					break;
				}								
								
			}
		}		
		if(!player1)
		{
			for(int group=0;group<8;group++)
			{
				int oppCount=0;				
				int freeSpace=3;
				int spaces=0;
				for(int space=0;space<3;space++)
				{
					if(places[group][space]==1){oppCount++;}
					if(places[group][space]==0){freeSpace=space;spaces++;}					
				}	
				if(oppCount==2&&spaces==1)
				{
					if(freeSpace!=3){block=getNumber(group,freeSpace);}
					else {block=getNumber(group,2);}
					break;
				}					
			}
		}
				
		
		return block;
	}
	int[][] placesTaken()
	{
		int[][] spots =new int [8][3];
		boolean[]status1=gridStatus1();
		boolean[]status2=gridStatus2();
		for(int checks=0;checks<9;checks++)
		{
			int[] places=calcSpotInGrid(checks+1);
			if(status1[checks])
			{				
				for(int i=0;i<places.length;i+=2)
				{
					spots[places[i]][places[i+1]]=1;
				}
			}
			if(status2[checks])
			{				
				for(int i=0;i<places.length;i+=2)
				{
					spots[places[i]][places[i+1]]=2;
				}
			}
			if(!status1[checks]&&!status2[checks])
			{				
				for(int i=0;i<places.length;i+=2)
				{
					spots[places[i]][places[i+1]]=0;
				}
			}
		}
		
		return spots;
		
	}
	int[] calcSpotInGrid(int place)
	{
		int[]places = null;
		if(place==1)places=new int[]{0,0,3,0,6,0};
		if(place==2)places=new int[]{0,1,4,0};
		if(place==3)places=new int[]{0,2,5,0,7,0};
		if(place==4)places=new int[]{1,0,3,1};
		if(place==5)places=new int[]{1,1,4,1,6,1,7,1};
		if(place==6)places=new int[]{1,2,5,1};
		if(place==7)places=new int[]{2,0,3,2,7,2};
		if(place==8)places=new int[]{2,1,4,2};
		if(place==9)places=new int[]{2,2,5,2,6,2};
		
		return places;
	}
	public boolean[] gridStatus1()
	{
		boolean[]status=new boolean[9];
		for(int x=0;x<spots1.length;x++)
			status[x]=spots1[x];
		
		return status;
	}
	
	public boolean[] gridStatus2()
	{
		boolean[]status=new boolean[9];
		for(int x=0;x<spots2.length;x++)
			status[x]=spots2[x];		
		return status;
	}
	
	public int getNumber(int group, int place)
	{
		int num=0;		
		if(group>=0&&group<=2)
			for(int i=0;i<=group;i++)
				{
					for(int p=0;p<3;p++)
					{
						num++;System.out.println(num);		
						if(i==group&&p==place)break;
						
					}
					
				}
		if(group>=3&&group<=5)
		{
			num=1;
			for(int g=0;g<=group-3;g++)
			{	
				num=1+g;
				for(int p=0;p<3;p++)									
					{if(g==group-3&&p==place)break;
					num+=3;}												
			}				
		}
		if(group==6&&place==0)num=1;
		if((group==6&&place==1)||(group==7&&place==1))num=5;		
		if(group==6&&place==2)num=9;
		if(group==7&&place==0)num=3;
		if(group==7&&place==2)num=7;
			
		
		return num;
	}	

	@Override
	public void startGame() 
	{
		for(int x=0;x<9;x++)
			{
				spots1[x]=false;
				spots2[x]=false;
			}
		int chance=randal.nextInt(10);
		int luck=randal.nextInt(10);
		if(chance==luck)shouldGoEasy=true;else shouldGoEasy=false;		
		player1=true;
	}

	@Override
	public void clickProcessor(int num) 
	{
		if(!gameOver()) getNextMove(num);
		if(game1())mc1.makeToast("Player 1 Wins!");
		if(game2())mc1.makeToast("Player 2 Wins!");
		if(tie())mc1.makeToast("The game is tied!");
	}

	@Override
	public void controlProcessor(int num) 
	{
		if(!gameOver())
			if(num==mc1.COMP_TURN)			
				if(!shouldGoEasy)getNextMove(chooseProSpot());
				else getNextMove(chooseEasySpot());
		if(game1())mc1.makeToast("Player 1 Wins!");
		if(game2())mc1.makeToast("Player 2 Wins!");
		if(tie())mc1.makeToast("The game is tied!");
		
	}

}

